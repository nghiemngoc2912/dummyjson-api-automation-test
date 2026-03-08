A project of API Automation Test of dummyjson-api, using RestAssured.

Feature: Auth, Cart (Add, Update) because dummy-json do not provide db or order api, so here are the most important features one.

Some concepts used in the project: DataProvider, Assert, JsonSchemaValidator

In this project, I aim to build a solid architecture of api testing include:
- main
  + apis: call the api
  + config: set up the environment
  + constants: used for each feature for clear concepts, easy to maintain and extend
  + models: class of request and response for mapping api
  + services: provide action (step) to use in test
- test
  + java
    
    . base: include BaseTest
    
    . assertions: provide the verify step
    
    . testcases: provide the tc include data, step provided by services and step of verification in assertions
    
    . test data: provide solid test data for each test case and changable when needed
    
  + resources: include test env and schema for response matching

Link TC: https://docs.google.com/spreadsheets/d/1QQyQgFILqmnXHirR5xnObmIB6A0OQAsWCntP3pDx7Us/edit?usp=sharing
