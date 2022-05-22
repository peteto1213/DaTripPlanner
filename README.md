# CSC8019_Team03_2021-22

# Project Title
DaTripPlanner

## Demo Link:
Access our project site at
[here](http://43.131.56.241:8080/)

## Table of Content:
- About the website
- Screenshots of functions demonstrated
- Technological structure
- Setup
- Credits

## About The Website
This web application aims at providing a handy platform for the University students in the UK to visit different castles at Newcastle

## Screenshots
![castleFeatures](https://i.ibb.co/mqHTG6C/castle-features.png)
User can view different castle information

![create plan](https://i.ibb.co/vzvT072/Create-trip.png)
User can then create a plan

![login](https://i.ibb.co/LQvDXVV/Login-Page.png)
User needs to login to save a plan

![all plan](https://i.ibb.co/j3zHPss/view-plans.png)
View all plans created

![details](https://i.ibb.co/8YZWz9q/plan-details.png)
View individual plan details

## Technologies
This Project is developed using:
- Frontend: React.js, HTML, CSS, fontawesome style sheets
- Backend: Java Spring boot, Hibernate, Log4J, Lombok etc.
- Database: MySQL, Redis

## Architecture
![architecture](https://i.ibb.co/VJxGHFM/architecture.png)

## Setup
- Download and clone the repository
- Run npm install in the terminal
```bash
npm install
```
- Make sure you have JDK version 1.8 or above
- Having a Maven package manager (Recommend using an IntelliJ Idea IDE with maven installed)
- Run the Java program
- After npm package is installed, run npm start in the terminal
```bash
npm start
```

## Project Status
DaTrip Planner is still in progress, currently at version 1.

## Other notes
You can find our test results at "Test result" folder

## Module description 

Modular focusing on the relationships between the various features of a product. These functions are described briefly, while the ways that they interact are described in dept:
- Controller Modular
: This module is primarily designed to enable the reception of front-end requests and data validation. The module defines the URLs that the front-end needs to send requests to and the parameters that need to be passed to the back end. In addition to this, the module is designed to perform initial data validation, so that the type, value and format of the incoming data from the front-end meets the requirements of the back end and is then encapsulated for subsequent data processing and logical judgement.

- Exception Modular
: This module is designed primarily for exception handling of programs. The module defines custom exceptions to make it easier to find problems and handle exceptions when programming.

- Mapper Modular
: This module can also be a Dao layer interface. This module mainly provides access to the database persistence. Our system uses Spring-JPA-hibernate as the data persistence framework to access and interact with the database. The module contains a number of Mapper interface classes that implement JPA's JpaRepository<Entity,Key>, and PagingAndSortingRepository<Entity,Key>. The JpaRepository is the standard data interface provided by JPA and defines the basic common database access methods, such as ‘getById’, which gets the data corresponding to this primary key by comparing it to the Key primary of the database table. The ‘PagingAndSortingRepository<Entity,Key>’ is implemented to achieve the need for paging queries when querying large amounts of data. Response Modular

- Service Modular
: The Service module defines the required interfaces for the Controller module to call. The database persistence interface for the Mapper layer is also injected via SpringBoot. This module is therefore the middle layer and is mainly responsible for the actual processing of the business, such as changing passwords, which requires this layer to call the interfaces in the Mapper layer to access and modify the user data, as well as using Redis to get the authentication code data sent to it and parse it for judgement. This layer is therefore the main layer responsible for the main business logic and functionality of the system.

- Util Modular
: This layer mainly implements some common utility classes such as time conversion, encryption and parsing of user passwords. The module contains mainly common methods in order to reduce code duplication and improve development efficiency.

- Models Modular
: This module mainly holds the entity class, which corresponds to the database tables and describes the relationships between the tables and fields. Its main function is to encapsulate the data, following the JPA requirements for entity classes.

Integral focusing on the function, purpose, and inner workings of each feature, with brief notes covering the relationships between them.
- MyContextLister.class
: As the initial database for the project was located inside the school server, external access to port 3306 of the database was not available. So it was necessary to use an SSH channel to connect to the school's Linux server and use this as a bridging channel to access the internal Mysql database. The main function of this class is to establish an SSH connection when the Tomcat server is started.

- MyRealm
: This class inherits from the AuthorizingRealm class of the Shiro framework. Through this class is mainly for access to user authentication, authorization and access control.

- RedisConfig
: This class is a configuration class and is mainly used for configuration settings for Redis. It includes functions for configuring Redis' template serialisation, cache management methods and cache space initialisation.

- ShiroConfig
: This class configures and sets up configuration information about Shiro. Firstly it defines the access paths that need to be filtered and intercepted by Shiro, such as user orders and user information access, which are interfaces that require the personal information to be verified before they can be accessed. The second defines the dependencies that need to be taken over by SpringBoot and automatically injected into Shiro. For example, DefaultWebSecurityManager and HashedCredentialsMatcher.

- SwaggerConfig
: This class is a configuration class for configuring Swagger documents taken over by SpringBoot. The class defines the information that needs to be displayed for the document, such as the document name, the version of the document, permissions and other information.

- SSHConnection
: This class configures the remote port number, the address of the remote port and other information in order to enable SSH access.

- CastleControllerImp
: It mainly provides front-end access to back-end services related to castle information.

- OrderControllerImp
: This class is the implementation class of OrderController, which mainly implements the response to front-end requests and user order-related information as well as the data validation of the data sent from the front-end to the back-end.

- RouteControllerImp
: This class mainly provides the front-end with interfaces related to route and ticket information. The main function is to return data and perform checks and preliminary processing on the incoming request data from the front end.

- CastleServiceImp
: This class is the main logical judgement class related to Castle. This class contains methods to look up castle information, view castle ticket information, view castle information by castle name, get castle ticket prices, get the date the castle can be visited and the time the castle can be visited

- OrderServiceImp
: This class inherits the OrderService interface and provides logical judgement functions related to Order. It contains the ability to create orders, get a list of user orders, get details about user orders and delete orders.

- RouteServiceImp
: This class is an implementation class of the RouteService interface and mainly implements the functions defined inside the RouterService. It implements functions such as getting route information, getting bus schedules, train schedules and getting return route information.

- TripPlanServiceImp
: This class is an implementation of the tripPlan interface and mainly implements the functions defined in tripPlan. It includes the creation of a plan, the deletion of a plan, the viewing of a plan and the viewing and deletion of content related to the content of the plan.

- UserServiceImp 
: UserServiceImp is an implementation class of the UserService interface that implements the functionality defined in the class UserService. It implements user registration, user login logic, user account activation, user password change, user information viewing and other user-related functions.

## Credits
List of contributors:
- Lei
- Lewis
- Kevin
- Pete
- Weidong

Created By CSC 8019 Team 3 | All Rights Reserved