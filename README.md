# Board Game Social
CST438-Project3 (Group 1)

## Members:
- Barbara Kondo: [bKondo](https://github.com/bKondo)
- Boris Marin: [BorisMarin8004](https://github.com/BorisMarin8004)
- Chaitanya Parwatkar: [parw8649](https://github.com/parw8649)
- Keyoni McNair: [keyoni](https://github.com/keyoni)

## Tech Stack
Frontend: Android<br>
Backend: Django<br>
Database: Postgres Heroku<br>

## Description:<br>
Board Game Social (working title) will allow users to keep track of their board games and connect with other users who share similar interests. They will be able to share their opinions via a post feed, reviews for games within the app database, and events centered around available games.
<br>
Django tokens will be used to verify valid user activity when sending and receiving data from the Postgres Heroku database.<br>
## ERDs:<br>
![BGS ERDS](https://github.com/parw8649/CST438-Project3/blob/homePost_profile_fragments/docs/Board%20Game%20Social%20ERD.png)

## Initial API Endpoints:<br>
- Login / signup
- Logout
- Get all users
- Edit user
- Get all posts
- Add post
- Get all games
- Get user’s games
- Add game to user’s game collection
- Get all events
- Add event
- Delete user/account

## Mockups:<br>
![BGS mockups](https://github.com/parw8649/CST438-Project3/blob/patch/readme_update/proj03-group01--initial-mockups.png)

## Libraries Used:<br>
- [Retrofit](https://square.github.io/retrofit/#download)
- [RxJava](https://github.com/ReactiveX/RxJava)

## Additional Documentation:<br>

## Routes:
| Route  |  Methods  | Required fields | 
| ------- | ------- | ------- | 
| http://boardgamesocial.herokuapp.com/admin/  |  GET |  - |
| http://boardgamesocial.herokuapp.com/login/  |  POST | username, password |
| http://boardgamesocial.herokuapp.com/sign_up/  |  POST | username, password |
| http://boardgamesocial.herokuapp.com/logout/ | GET | userId|
| http://boardgamesocial.herokuapp.com/api/user/  |  GET, POST, DELETE, PUT | username, password |
| http://boardgamesocial.herokuapp.com/api/event/ | GET, POST, DELETE, PUT | name, hostUserId |
| http://boardgamesocial.herokuapp.com/api/post/ | GET, POST, DELETE, PUT |  userId, postBody, postType |
| http://boardgamesocial.herokuapp.com/api/game/ | GET, POST, DELETE, PUT | gameTitle, genre, minPlayer, maxPlayer |
| http://boardgamesocial.herokuapp.com/api/user_to_user/ | GET, POST, DELETE | userOneId, userTwoId |
| http://boardgamesocial.herokuapp.com/api/message/ | GET, POST, DELETE, PUT | senderId, receiverId, content |
| http://boardgamesocial.herokuapp.com/api/event_to_user/ | GET, POST, DELETE | userId, eventId |
| http://boardgamesocial.herokuapp.com/api/comment/ | GET, POST, DELETE, PUT | userId, postId, content |
| http://boardgamesocial.herokuapp.com/api/game_to_user/ | GET, POST, DELETE, PUT | userId, gameId, private |
| http://boardgamesocial.herokuapp.com/api/hosted_game/ | GET, POST, DELETE, PUT | eventId, gameId, seatsAvailable |
| http://boardgamesocial.herokuapp.com/api/review/ | GET, POST, DELETE, PUT | userId, gameId, content |
| http://boardgamesocial.herokuapp.com/api/hosted_game_to_user/ | GET, POST, DELETE, PUT | userId, gameId |


## How to use it:

### Authentication:
Backend uses token in order to authenticate requests. Thus for every request the token of the current user must be supplied. It should be in the `header` as:
```
"Authorization": "Token {token}"
```

### Getting, Setting, Deleting data:
Data from the database can be accessed and modified from any of the `http://boardgamesocial.herokuapp.com/api/...` routes

### Body/Query examples:

### login
Request, body:
```
{
    "username": "your-username",
    "password": "your-password"
}
```
Response, body:
```
{
    "token": "token-will-be-here"
}
```
### sign_up
Request, body:
```
{
    "username": "your-username",
    "password": "your-password"
}
```
Response, body:
```
{
    "username": "your-username",
    "password": "your-password"
}
```
### Other API routes
__GET__
This query will filter data in the table and return matches according to field values, they would have to get an exact match for API to return matches, if there is no matches empty array is returned. If there is no query all objects of the given table are returned.
Query(Not in body, use url query parameters):
```
{
    "any-field-name-in-table-entry": "value-to-match",
    ...
}
```
__POST__
This method will allow to to create an entry in DB. Make sure all prerequisites are met before sending a request. For example if there is any FK in DB entry, make sure references by this FK exist. 
Some of the fields are required, refer to the routes table above
Request, body:

__DO NOT MAKE POST WITH PK__
Backend will set pk for you and respond with an object that was created, including pk that was assigned

```
{
    "any-field-name-in-table-entry": "value-to-match",
    ...
}
```
Response, body:
```
{
    data of created object will be here, including id as a pk
}
```
__DELETE__
I recommend to use pk in order to delete specific data, but other fields can also be used. However that can lead to multiple deletions. 
Request, body:
```
{
    "any-field-name-in-table-entry": "value-to-match",
    ...
}
```
Response, body:
```
{
    data of deleted object will be here, including id as a pk
}
```
__PUT__
This endpoint will update the data in DB entry. __PUT__ is not allowed to relationship tables that do not have additional data, refer to routes table on the top. The pk is required.   
Request, body:
```
{
    "id": pk-int-here,
    "any-field-name-in-table-entry": "value-to-match",
    ...
}
```
Response, body:
```
{
    data of updated object will be here, including id as a pk
}
```


## Retrofit Calls:
| Return Type  |  Function Name  | Parameters | Token Protected |
| :-------: | :-------: | :-------: | :-------: |
| User | signUpCall | User | :x: |
| Token | loginCall | User | :x: |
| JsonObject | logoutCall | Map<String, String> | :x: |
| JsonArray | getCall | Class<?>, Map<String, String> | :white_check_mark: |
| JsonObject | postCall | Class<?>, Object | :white_check_mark: |
| JsonObject | putCall | Class<?>, Map<String, String> | :white_check_mark: |
| JsonArray | deleteCall | Class<?>, Map<String, String> | :white_check_mark: |

## How to use it:
There are 2 types of Calls this implementation of Retrofit can do:
- AuthCalls (for `login`, `sign_up`, `logout` routes)
- DBCalls (for any db queries, routes will start with `api`) 
You do not need to `login` user if you are using AuthCalls, since they do not require token. However you must login the user and set token if you want to access database. Once you get the token from `loginCall` you can set token like this:
```java
HeaderInterceptor.token.setToken("your-token-here");
```   
Once token is set you can make queries to database.

## DBQueries:
When using DBCalls you will either receive `JsonArray` or `JsonObject` use this function to convert them to `List` or `Object` respectively:
```java
getObjectList(JsonArray jsonArray, Class<T> cls)
getObject(JsonObject jsonObject, Class<T> cls)
``` 
`cls` needs to be a class of your response. For example if you send `getCall` to `api/user/` endpoint, you will receive `List<User>` but in `JsonArray` format. So you will need to parse it like this:
```java
List<User> listOfUsers = getObjectList(jsonArray, User.class);
```
Also make sure to pass correct class to DBCalls, they are used to deduct the url to send the request to. For example:
```java
retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
            put("gameTitle", "7 Wonders Duel");
        }})
```
will send `GET` request to `api/game/` with query `gameTitle=7 Wonders Duel`, this will return `JsonArray` with all games that matched this`gameTitle`.

Here is `urlMap` for reference, however it should quite intuitive:
```java
HashMap<Class<?>, String> urlMap = new HashMap<Class<?>, String>() {{ 
     put(User.class, "api/user/"); 
     put(Event.class, "api/event/"); 
     put(Post.class, "api/post/"); 
     put(Game.class, "api/game/"); 
     put(UserToUser.class, "api/user_to_user/"); 
     put(Message.class, "api/message/"); 
     put(EventToUser.class, "api/event_to_user/"); 
     put(Comment.class, "api/comment/"); 
     put(GameToUser.class, "api/game_to_user/"); 
     put(HostedGame.class, "api/hosted_game/"); 
     put(Review.class, "api/review/"); 
     put(HostedGameToUser.class, "api/hosted_game_to_user/"); 
 }}; 
```

Keep in mind, when making requests with Retrofit in activities, you must use `enqueue`, here is an example:
```java
retrofitClient = RetrofitClient.getClient();

// This token needs to be acquired with login request
HeaderInterceptor.token.setToken("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");

retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
    put("gameTitle", "7 Wonders Duel");
}}).enqueue(new Callback<JsonArray>() {
    @Override
    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
        if (response.isSuccessful()) {
            assert response.body() != null;
            List<Game> list = getObjectList(response.body(), Game.class);
            Log.i("Game", list.get(0).getGameTitle());
        } else {
            new Exception("Request failed, code: " + response.code()).printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<JsonArray> call, Throwable t) {
        try {
            throw t;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
});
```