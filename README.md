***Goal:***

* Connect an angular front end to a springboot back end.
* Have user input from angular front end be processed by a Camunda 8 service task.
* Verify zip code using an api.
* Be able to run locally only, no current plans to host online.

***Setup Instructions***

* Run c8run.exe start to start the Camunda platform.
* Deploy the process via the Camunda Modeler.
* Start the Angular frontend with ng serve.
* Run the Spring Boot server on port 8081.

***Service Ports:***

* Spring Boot server: http://localhost:8081
* Angular frontend: http://localhost:4200
* Camunda Operate UI: http://localhost:8080
