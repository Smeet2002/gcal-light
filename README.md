# gcal-light

gcal-light
=======

#### Google Calendar Command Line Event Reader 

gcal-light is a Python script, running inside the container and reading the Google
Calendar events. The idea is based on the
gcalcli(https://github.com/insanum/gcalcli/tree/master),
but simplified to only read events in the 12 months window and save them into
HTML page (to show the page later).


Requirements
------------

* [Python3](http://www.python.org)
* [dateutil](http://www.labix.org/python-dateutil)
* [Google API Client](https://developers.google.com/api-client-library/python)
* [Docker](https://docs.docker.com/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/)

How to use
----------------
* a. Pull the repository
* b. Create a project in your Google account.
* c. Create OAuth 2.0 Client IDs
	- download credentials.json and copy it to the ./auth folder
* d. Add test user to the Google App ( !! see Update from August 2024 below !! )
* e. From the ./auth folder run 'google-auth.py'
	- authenticate with Google in the browser, using test user credentials
* f. Confirm that 'token.pickle' file is generated
* g. Run "docker compose up -d"
	- docker container will be started
* h. Check 'cal.html' file generated in the ./www folder
	- file will be re-generated every 57 minutes, when container is running

Update From August 2024
------------------------

If the Google App is not published, it will reset the password every 7 days. You need to publish the app to avoid generating a new token.pickle file every week.

    Go to: https://console.cloud.google.com/apis/dashboard
    Select your project.
    Navigate to OAuth Consent Screen.
    Change the Publishing Status from 'Testing' to 'In Production'.

At this point, the test user is irrelevant. During Google Authentication, the 'Unverified App' screen will appear. You will need to bypass the 'Unverified App' screen in the same way you would ignore an invalid SSL certificate for HTTPS.

