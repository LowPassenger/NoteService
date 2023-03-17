# <u> NoteService </u>  

The little backend part of short message exchanger. Do not contain a visual frontend unit!
This application was written in Java with Spring Boot Framework used for CRUD operations for outgoing messages. The database implementation is NoSQL MongoDB (I'm using cloud Azure MongoDB for tests). The Tomcat web server start endpoint is

 **localhost:8080/**

Role Based Access Control system are used for users registration, authorization and authentication. The JWT token technology is used for authorized users recognition processes. There are three type of users:
  * ADMIN has a "God mode" with no limit access (already registered, username: admin, password: adminpassword)
  * USER has some limits in notes CRUD operations, like and unlike messages
  * users without registration, or ANONYMOUS users can only read and write messages

The **notes** saves in database in format:

{"nodeId" : "String_node_id",
 "userId" : "String_author's_id",
 "content" : "String_node's_content",
 [{"userLikeId" : "String_user's_who_like_this_note_id",
  "timestamp" : "Date_time_then_like_takes"},
  {...other likes...}],
 "timestamp" : "Date_time_then_note_was_written"}

All data exclude id's sent by JSON format in HTTP messages body. 

The endpoints for **notes**

  * **localhost:8080/notes** POST request with content for CREATE operation, any access, "content" field may be 1...140 characters
   {"userId" : "String_author's_id_may_be_null",
    "content" : "String_node's_content",
    [{"userLikeId" : "String_user's_who_like_this_note_id",
      "timestamp" : "Date_time_then_like_takes"},
      {...other likes...}]}

  * **localhost:8080/notes/all** GET request for READ operation, any access, returns all anonymous users messages sorted by timestamp and descent ordered

  * * **localhost:8080/notes/all{id}** GET request for READ operation, any access, returns all messages for user with **id** sorted by timestamp and descent ordered

  * **localhost:8080/notes/{id}** GET request for READ operation, any access, returns message noteId **id**

  * **localhost:8080/notes/{id}** PUT request with content for UPDATE operation, access only for ADMIN and user who create this note, "content" field may be 1...140 characters
   {"userId" : "String_author's_id_may_be_null",
    "content" : "String_node's_content",
    [{"userLikeId" : "String_user's_who_like_this_note_id",
      "timestamp" : "Date_time_then_like_takes"},
      {...other likes...}]}

  * **localhost:8080/notes/{id}** DELETE request for DELETE operation, access only for ADMIN and user who create this note

The endpoints for **likes**

 * **http://localhost:8080/notes/likes/like?noteid={noteId}&userid={userId}** GET request to save the "like" in note with "noteId" from user with "userId" for UPDATE operation, access only for ADMIN and user who create this note

* **http://localhost:8080/notes/likes/unlike?noteid={noteId}&userid={userId}** GET request to delete the "like" in note with "noteId" from user with "userId" for UPDATE operation, access only for ADMIN and user who create this note

The endpoints for **registration and authentication** 
register can only USER access client, login operation available for pre registered in source code ADMIN and USER's

 * **localhost:8080/auth/signin** POST request with content for READ operation, any access, "username" field may be 2...40 characters, "password" field may be 8...50 characters; all checks and validation must be on the frontend part of the application, the HTTP(s) messages encryption too.
  {"userName" : "String_user_name",
   "password" : "String_password"}

 * **localhost:8080/auth/signup** POST request with content for CREATE operation, any access, "username" field may be 2...40 characters, "password" field may be 8...50 characters; all checks and validation (password and repeat password, for example) must be on the frontend part of the application, the HTTP(s) messages encryption too.
  {"userName" : "String_user_name",
   "password" : "String_password"}

P.S. Yes, my code and this README file is not ideal, but two days is not enough for this kind of task. Give me more time and I will write more better application)



