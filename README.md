<div align="center">
<img src="https://img.shields.io/badge/RSS-Feed-orange?style=for-the-badge&logo=rss&logoColor=white" alt="SkateFeed Logo" width="200"/>
<h1>SkateFeed</h1>
<p>
<strong>Skateboarding news aggregator.</strong>
</p>
</div>

SkateFeed is a backend application built with Java and Spring Boot that acts as an intelligent news aggregator for the skateboarding world. The service monitors RSS feeds from industry websites, filters news based on users' preferred keywords, and sends email notifications.

This project was developed to demonstrate the capabilities of Spring Integration in orchestrating complex data flows in an elegant and decoupled way.
(but also to have myself a newsletter with filtered articles of skateboarders i love to follow without having to surf websites many times per week!)


🚀 Key Features
---------------

REST API for Subscriptions: Exposes an endpoint to allow users to subscribe to the service, specifying their email address and a list of keywords of interest (e.g., "baker skateboards", "tiago lemos").


Periodic RSS Feed Reading: At regular (configurable) intervals, the application checks RSS feeds from skate sites (currently The Berrics) for new stories.

Intelligent Filtering: Each article is analyzed. If the title or content matches one of a user's saved keywords, the article is selected for notification.

Duplicate Handling: The system keeps track of already processed articles to avoid sending the same news multiple times.

HTML Email Sending: Users receive well-formatted HTML emails with the details of the news found.

API Documentation with Swagger: The API is self-documented and testable through the Swagger UI interface.




🛠️ Tools & Technologies
-----------------------



<p align="center">
<a href="#"><img src="https://img.shields.io/badge/macOS-000000?logo=apple&logoColor=F0F0F0" alt="macOS"></a>
<a href="#"><img src="https://img.shields.io/badge/Spotify-1ED760?logo=spotify&logoColor=white" alt="Spotify"></a>  
<a href="#"><img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?logo=intellij-idea&logoColor=white" alt="INTJ IDEA"></a> 
<a href="#"><img src="https://img.shields.io/badge/Insomnia-4000BF?logo=insomnia&logoColor=white" alt="Insomnia"></a>  
<a href="#"><img src="https://img.shields.io/badge/Opera-FF1B2D?logo=Opera&logoColor=white" alt="Opera"></a>
<a href="#"><img src="https://img.shields.io/badge/DuckDuckGo-FF5722?logo=duckduckgo&logoColor=white" alt="DuckDuckGo"></a> 
<a href="#"><img src="https://img.shields.io/badge/Google-4285F4?logo=google&logoColor=white" alt="Google"></a>  
<a href="#"><img src="https://img.shields.io/badge/Gmail-D14836?logo=gmail&logoColor=white" alt="Gmail"></a>  
<a href="#"><img src="https://img.shields.io/badge/Git-F05032?logo=git&logoColor=fff" alt="Git"></a> 
<a href="#"><img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white" alt="GitHub"></a> 
<a href="#"><img src="https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white" alt="Java"></a>
<a href="#"><img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff" alt="Spring Boot"></a>
<a href="#"><img src="https://img.shields.io/badge/Swagger-85EA2D%3Flogo%3Dswagger%26logoColor%3D000" alt="Swagger"></a>
<a href="#"><img src="https://img.shields.io/badge/OpenAPI-6BA539?logo=openapiinitiative&logoColor=white" alt="OpenAPI"></a>
<a href="#"><img src="https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=fff" alt="Hibernate"></a>
<a href="#"><img src="https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white" alt="Postgres"></a>
</p>



📌 Utilized Dependencies
---------------------


<strong>SPRING WEB:</strong> Facilitates the construction of the RESTful Application Programming Interface, which encompasses the requisite endpoint for subscription functionalities.

<strong>SPRING INTEGRATION:</strong> Constitutes the principal orchestration framework for the application. Its function is to govern the complete automated data processing workflow, which includes the ingestion of syndicated feeds, the application of filtering criteria, data transformation, and the subsequent dispatch of messages.

<strong>SPRING INTEGRATION FEED:</strong> A specialized module within the Spring Integration ecosystem, engineered for the specific purpose of reading and parsing RSS (Really Simple Syndication) and Atom syndicated feeds.

<strong>SPRING INTEGRATION MAIL:</strong> A distinct module designated for the programmatic composition and transmission of electronic mail correspondence.

<strong>SPRING BOOT STARTER MAIL:</strong> Furnishes the automatic configuration mechanisms for the email dispatch service, deriving its operational parameters from the credentials specified within the application.properties configuration file.

<strong>SPRING DATA JPA:</strong> The designated persistence layer abstraction that enables interaction with the underlying database system. It facilitates data manipulation operations—such as creation, retrieval, and modification of user and keyword entities—through the mapping of standard Java objects.

<strong>POSTRESQL DRIVER:</strong> The software driver that provides the necessary interface for establishing communication and interaction between the application and a PostgreSQL relational database management system.

<strong>H2 DATABASE</strong> A lightweight, in-memory database engine, deemed suitable for rapid application development and testing protocols, as it obviates the need for external installation and configuration.

<strong>LOMBOK:</strong> A utility library employed to mitigate the verbosity of boilerplate code within the data model classes through compile-time annotation processing, thereby reducing the manual implementation of accessor and mutator methods.

<strong>SPRINGDOC OPENAPI:</strong> (Swagger UI): A component responsible for the automatic generation of interactive, web-based documentation for the RESTful API, conforming to the OpenAPI Specification. This facilitates streamlined API inspection and functional testing.



🏁 How to Get Started
---------------------

PREREQUISITES:

JDK 21 or higher

Apache Maven

A Gmail account with an App Password for sending emails.


CONFIGURATION:


Clone the repository:

git clone git@github.com:danotoriousflaco101/skateFeed.git

cd skateFeed


Configure email credentials:


Open the src/main/resources/application.properties file. 

You need to edit the email sending section with your actual data.

# Enter your full Gmail address here
spring.mail.username=your-email@gmail.com

# Enter the 16-character password generated by Google here
spring.mail.password=xxxxxxxxxxxxxxxx

Running the Application
You can run the application using the Maven wrapper provided by Spring Boot:

./mvnw spring-boot:run

The application will be running at http://localhost:8080.


⚙️ Using the API
----------------

Once the application is running, you can start interacting with the API to subscribe to the service.


</br>
<p>
<strong>SWAGGER UI 🤙</strong>
</p>
</br>

The easiest way to test the API is through the Swagger UI interface.

Open your browser and go to: http://localhost:8080/swagger-ui.html

You will see the POST /api/subscriptions endpoint.

Click on "Try it out" and enter a JSON object in the request body to subscribe.

Example of JSON request:

{
  "email": "your.name@example.com",
  "keywords": [
    "primitive",
    "new balance numeric"
  ]
}


</br>
<p>
<strong>H2 CONSOLE 💾</strong>
</p>
</br>

For development, you can access a web console to inspect the in-memory database. 


1. Make sure these properties are in your application.properties:

spring.h2.console.enabled=true

spring.h2.console.path=/h2-console


2. Go!

