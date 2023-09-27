# REST Controllers Development

## UserController

| Resource                           | Method | Input                            | Output                                       |
| ---------------------------------- | ------ | -------------------------------- | -------------------------------------------- |
| `/api/users/signUp`                | `POST` | UserDto [body]                   | ResponseEntity<AuthenticatedUserDto\> [body] |
| `/api/users/login`                 | `POST` | LoginParamsDto [body]            | AuthenticatedUserDto [body]                  |
| `/api/users/loginFromServiceToken` | `POST` | userId [jwt], serviceToken [jwt] | AuthenticatedUserDto [body]                  |

## PostController

| Resource                | Method | Input                              | Output                            |
| ----------------------- | ------ | ---------------------------------- | --------------------------------- |
| `/api/posts/post`       | `POST` | userId [jwt], PostParamsDto [body] | void                              |
| `/api/posts/feed`       | `GET`  | page [param]                       | BlockDto\<PostSummaryDto\> [body] |
| `/api/posts/categories` | `GET`  | void                               | List<CategoryDto\>                |

## Sample requests

### New user creation

- POST: http://localhost:8080/poster/api/users/login with body:

```json
{
  "userName" : "alejandro",
  "password" : "fd2324",
  "firstName" : "Alejandro",
  "lastName" : "Bujan",
  "email" : "alejandro.bujan.pampin@udc.es",
  "avatar" : ""
}
```

### Login to an existent account

- POST: http://localhost:8080/poster/api/users/login with body:

```json
{
  "userName" : "alejandro",
  "password" : "fd2324"
}
```

### New post creation

- POST: http://localhost:8080/poster/api/posts/post with body:

**Note**: Required header `Authorization: Bearer eyJhbGciOiJIU...` related to a user account.

```json
{
  "title" : "Post 1",
  "description" : "First time posting",
  "url" : "http://poster.com",
  "price" : "1.00",
  "categoryId" : "",
  "images" : []
}
```

### Feed retrieval

- GET: http://localhost:8080/poster/api/posts/feed?page=0

### Categories retrieval

- GET: http://localhost:8080/poster/api/posts/categories
