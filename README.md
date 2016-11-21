# jerseyguice
REST Service Example with Jersey2 and Guice Dependency Injection


This project implements a RESTful Webservice using Jersey,
a very popular Java toolkit that abstracts aways the low level details of the client-server communication.
The solution will work on any application server.
In this pproject we use the Jetty servlet engine, which can run standalone and also is the base of the Google App Engine.

It is necessary to take a small detour,
because Jersey uses its own depencency injection Library HK2. The project bridges HK2 to Guice.
This pays off by giving us a clear and elegant application structure.

For details how to install the development environment and for the design principles behind it see my
[blog](http://www.aberger.at/en/blog/implementation/java/2016/11/12/jersey-guice.html).