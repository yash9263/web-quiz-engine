# Web Quiz Engine

This is a Web Quiz API in which a user can create, solve, delete and view quizzes. The user need to be registered
for performing these actions.

## Register a new user

To register a new user, the client needs to send a JSON with ```email``` and ```password``` via ```POST``` request to ```/api/register```:
```
{
    "email": "test@email.com", 
    "password": "secret"
}
```

All the following operations need a registered user to be successfully completed.

## Create a new quiz

To create a new quiz, the client needs to send a JSON as the request's body via 
```POST``` to ```/api/quizzes```. The JSON should contain the four fields:<br>
- ```title```: a string, **required**;
- ```text```: a string, **required**;
- ```options```: an array of strings, required , should contain at least 2 items;
- ```answer```: an array of indexes of correct options, optional, since all options can be wrong.

Here is a new JSON quiz as an example: 
```
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": 2
}
```
The server response is a JSON with four fields: ```id```, ```title```, ```text``` and ```options```.
Here is an example.
```
"id": 1,
"title": "The Java Logo",
"text": "What is depicted on the Java logo?",
"options": ["Robot","Tea leaf","Cup of coffee","Bug"]
```

## Get a Quiz by id

The quiz has exactly four fields:```id```  (int) ```title``` (string) ```text``` (string) and 
```options``` (array). To get the quiz, the client sends the ```Get``` request to ```/api/quizzes/{id}```.<br>
The server should return the following JSON structure:
```
"id": 1,
"title": "The Java Logo",
"text": "What is depicted on the Java logo?",
"options": ["Robot", "Tea leaf", "Cup of coffee", "Bug"]
```
If the specified quiz does not exist, the server will return the ```404 (not found)```.

## Get all quizzes with paging

To get all quizzes in the service, the client sends the ```GET``` request to ```/api/quizzes```<br>
The API supports the navigation through pages by passing the ```page``` parameter (```/api/quizzes?page=1```).
The response contains a JSON with quizzes (inside ```content``) like the following:
```
{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":102,"title":"Test 1","text":"Text 1","options":["a","b","c"]},
    {"id":103,"title":"Test 2","text":"Text 2","options":["a", "b", "c", "d"]},
    {"id":202,"title":"The Java Logo","text":"What is depicted on the Java logo?",
     "options":["Robot","Tea leaf","Cup of coffee","Bug"]}
  ]
}
```



## Solving the quiz

To solve the quiz, the client sends the ```Post``` request to ```/api/quizzes/{id}/solve``` with a JSON that contains the indexes 
of all chosen options as the answer. This looks like a regular JSON object with key 
```answer``` and value as the array: ```{"answer": [0, 2]}```.**Index starts from 0**.

The server will return JSON with two fields:```success``` (true or false) and ```feedback``` (just a string).
There are three possible responses.
- If the passed answer is correct (POST to /api/quiz with content ```answer=2```):
    
    ```{"success":true,"feedback":"Congratulations, you're right!"}```
  

- If the answer is incorrect (e.g., ```POST``` to ```/api/quiz``` with content ```answer=1```):

    ```{"success":false,"feedback":"Wrong answer! Please, try again."}```
  

- If the specified quiz does not exist, the server returns the ```404 (Not found)```.

## Delete a quiz

A user can delete their quiz by sending the ```DELETE``` request to ```/api/quizzes/{id}```.

If the specified quiz does not exist, the server returns ```404 (Not found)```.
If the specified user is not the author of this quiz, the response is the ```403 (Forbidden)```.

## Get all completions of quizzes with paging

The service also provides a operation for getting all completions of quizzes for a specified user by sending
the ```GET``` request to ```/api/quizzes/completed```.

Here is a response example:
```
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```
