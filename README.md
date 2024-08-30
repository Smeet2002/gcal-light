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
* d. Add test user to the Google App
* e. From the ./auth folder run 'google-auth.py'
	- authenticate with Google in the browser, using test user credentials
* f. Confirm that 'token.pickle' file is generated
* g. Run "docker compose up -d"
	- docker container will be started
* h. Check 'cal.html' file generated in the ./www folder
	- file will be re-generated every 57 minutes, when container is running
UPDATE 2024-08
*  Added activity_settings.xml and SettingsActivity.kt to be able to edit url, username and password 
