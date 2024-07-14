import os.path
import pickle
from google.auth.transport.requests import Request
from googleapiclient.discovery import build
from datetime import datetime, timedelta
import time


def authenticate_google_calendar():
    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('/run/secrets/token.pickle'):
        with open('/run/secrets/token.pickle', 'rb') as token:
            creds = pickle.load(token)
    else:
        print('No authentication !!!')
        quit()
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())

    service = build('calendar', 'v3', credentials=creds)
    return service


def list_calendar_events(time_min, time_max):
    sp3 = '<span class="f3">'
    sp4 = '<span class="f4">'
    spe = '</span>'
    output = ''

    service = authenticate_google_calendar()

    # Call the Calendar API
    events_result = service.events().list(
        calendarId='primary',
        timeMin=time_min,
        timeMax=time_max,
        singleEvents=True,
        orderBy='startTime'
    ).execute()
    events = events_result.get('items', [])

    if not events:
        print('No upcoming events found.')
        return

    printed_dates = set()

    for event in events:
        start = event['start'].get('dateTime', event['start'].get('date'))
        # Convert and format the start date-time
        if 'dateTime' in event['start']:
            dt = datetime.fromisoformat(start)
            formatted_date = dt.strftime('%Y-%m-%d')
            start_time = dt.strftime('%H:%M')
        else:
            formatted_date = start
            start_time = "All day event"

        if formatted_date not in printed_dates:
            printed_dates.add(formatted_date)
            output += f"\n{sp3}{formatted_date}{spe}   {sp4}{start_time}{spe} {event['summary']}\n"
        else:
            output += f"{sp3}{' ' * len(formatted_date)}{spe}   {sp4}{start_time}{spe} {event['summary']}\n"

    return (output)


def create_html_page():
    # Calculate the time range
    now = datetime.now()
    tmin = (now - timedelta(days=6*30)).isoformat() + 'Z'  # 6 months before now
    tmax = (now + timedelta(days=6*30)).isoformat() + 'Z'  # 6 months after now
    tnow = now.isoformat() + 'Z'  # now
    # create page
    page_begin = '''<html><head>
                 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                 <style type="text/css">
                 pre { white-space: pre-wrap; }
                 .ef0,.f0 { color: #000000; } .eb0,.b0 { background-color: #000000; }
                 .ef1,.f1 { color: #AA0000; } .eb1,.b1 { background-color: #AA0000; }
                 .ef2,.f2 { color: #00AA00; } .eb2,.b2 { background-color: #00AA00; }
                 .ef3,.f3 { color: #AA5500; } .eb3,.b3 { background-color: #AA5500; }
                 .ef4,.f4 { color: #0000AA; } .eb4,.b4 { background-color: #0000AA; }
                 .ef5,.f5 { color: #AA00AA; } .eb5,.b5 { background-color: #AA00AA; }
                 .ef6,.f6 { color: #00AAAA; } .eb6,.b6 { background-color: #00AAAA; }
                 .ef7,.f7 { color: #AAAAAA; } .eb7,.b7 { background-color: #AAAAAA; }
                 </style>
                 </head>
                 <body><pre>'''
    page_end = '\n</pre></body></html>'
    b = list_calendar_events(tmin, tnow)
    a = list_calendar_events(tnow, tmax)
    ln = "------------------------------------------------------------------"
    generated_on = now.strftime('%Y-%m-%d')
    generated_at = now.strftime('%H:%M:%S')
    footer = f'\nGenerated on {generated_on} at {generated_at}'
    page_html = page_begin + b + ln + a + footer + page_end
    return(page_html)


if __name__ == '__main__':
    while True:
        page_html = create_html_page()
        with open('/www/cal.html', 'w') as file:
            file.write(page_html)
        time.sleep(57 * 60)  # Sleep for 57 minutes
