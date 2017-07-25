Server Assignment 3 | University of Trento

SOAP Web Services

Server

introsde.document.dao: contains LifeCoachDao.java file that manages the connection to the database;
introsde.document.endpoint: contains PeoplePublisher.java file which exposes all server functionalities;
introsde.document.model: contains Person.java and Measure.java that corrispond to tables in the database. They have methods to query the database;
introsde.document.ws: contains the implementation PeopleImpl.java which implements all the server functionalities and the interface People.java that declare them.

Configuration files

- build.xml: contains all targets to run the code;
- ivy.xml: contains all dependencies to run the project and to download them.

Setup

The server is deployed on Heroku and it is possible to clone it with:

https://github.com/raffu12/introsde-2016-assignment-3-server.git
My Client

You can find my Client here:

https://github.com/raffu12/introsde-2016-assignment-3-client