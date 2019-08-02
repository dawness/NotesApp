# NotesApp

## Overview
It is a Note taking Application.  
NotesApp is a Spring Boot application with an embedded Tomcat server and can run on any system with java installed.

## Technology Used  
Java 8, SpringBoot, Lombok, H2

## How to deploy and run it
```
 - Clone the repository : git clone https://github.com/dawness/NotesApp.git
 - Go to the Project folder : cd NotesApp
 - Do a Maven clean build which will create an executable jar: mvn clean package
 - Run the application  : java -jar ./target/NotesApp-1.0.jar
  Running the runnable jar starts the application and the embedded server listens to the
  host and the port as specified in the application.properties file.
```
### How to access the NotesApp
 Once the application starts running, the App can be accessed by entering the url.  
 There are 5 endpoints for this Application  
  * Add a Note  
     POST- api/notes,  
     body - a note,  
     Returns - a saved note  

  * Get all the Notes or Get all the Notes which contains a word  
     Get - api/notes  
     Returns - a list of Notes  
     or  
     Get- api/notes  
     RequestParam - a word  
     eg:  api/notes?query=milk   
     Returns = a list of Notes that contains the search string (looks for the whole word, case insensitive ) 
     
     (milk and milk! are 2 different words, compares the whole word )  

  * Get a note if provided with id    
      Get - api/notes/{id}  
      Returns - a note  

  * Update a note for a specific Id    
      Put - api/notes/{id}  
      body - a note  
      Returns - updated note  

  * Delete a note for a specific Id  
      Delete = api/notes/{id}  
      Returns - Confirmation message if note is deleted.  
