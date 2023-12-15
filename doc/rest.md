# REST Controllers Development

## UserController
| Resource                           | Method | Input                                                  | Output                                       |
| ---------------------------------- | ------ | ------------------------------------------------------ | -------------------------------------------- |
| `/api/users/signUp`                | `POST` | UserDto [body]                                         | ResponseEntity<AuthenticatedUserDto\> [body] |
| `/api/users/login`                 | `POST` | LoginParamsDto [body]                                  | AuthenticatedUserDto [body]                  |
| `/api/users/loginFromServiceToken` | `POST` | userId [jwt], serviceToken [jwt]                       | AuthenticatedUserDto [body]                  |
| `/api/users/{id}`                  | `PUT`  | userId [jwt], id[path], UserDto [body]                 | UserDto [body]                               |
| `/api/users/{id}/changePassword`   | `POST` | userId [jwt], id[path], ChangePasswordParamsDto [body] | UserDto [body]                               |

## PostController
| Resource                             | Method   | Input                                         | Output              |
| ------------------------------------ | ------   | --------------------------------------------- | ------------------- |
| `/api/posts/post`                    | `POST`   | userId [jwt], PostParamsDto [body]            | PostDto [body]      |
| `/api/posts/post/{id}`               | `DELETE` | userId [jwt], id [path]                       | void                |
| `/api/posts/post/{id}/markAsExpired` | `POST`   | userId [jwt], id [path]                       | Long [body]         |
| `/api/posts/post/{postId}`           | `PUT`    | userId [jwt], id [path], PostUpdateDto [body] | Long [body]         |
| `/api/posts/post/{id}/markAsValid`   | `POST`   | userId [jwt], id [path]                       | PostValidDto [body] |

## CatalogController
| Resource                       | Method | Input                         | Output                            |
| ------------------------------ | ------ | ----------------------------- | --------------------------------- |
| `/api/catalog/feed`            | `POST` | SearchParamsDto params [body] | BlockDto\<PostSummaryDto\> [body] |
| `/api/catalog/categories`      | `GET`  | void                          | List<CategoryDto>                 |
| `/api/catalog/postDetail/{id}` | `GET`  | id [path]                     | PostDto                           |

## CommentController
| Resource                          | Method | Input                                            | Output                        |
| --------------------------------- | ------ | ------------------------------------------------ | ----------------------------- |
| `/api/comments/post/{id}/comment` | `POST` | userId [jwt], id [path], CommentParamsDto [body] | void                          |
| `/api/comments/post/{id}`         | `POST` | id [path], FindCommentsParamsDto [body]          | BlockDto\<CommentDto\> [body] |

## NotificationController
| Resource                           | Method | Input                              | Output             |
| ---------------------------------- | ------ | ----------------------- | ----------------------------- |
| `/api/notifications/notifications` | `GET`  | userId [jwt]            | List<NotificationDto>  [body] |
| `/api/notifications/{id}/view`     | `POST` | userId [jwt], id [path] | void                          |

## SaveController
| Resource                        | Method   | Input                       | Output         |
| ------------------------------- | -------- | --------------------------- | -------------- |
| `/api/saves/post/{postId}`      | `POST`   | userId [jwt], postId [path] | void           |
| `/api/saves/post/{postId}/save` | `GET`    | userId [jwt], postId [path] | boolean [body] |
| `/api/saves/post/{postId}`      | `DELETE` | userId [jwt], postId [path] | void           |

## RatingController
| Resource                             | Method | Input                    | Output             |
| ------------------------------------ | ------ | ------------------------ | ------------------ |
| `/api/rating/post/{id}/ratePositive` | `POST` | userId [jwt], id [path]  | RatePostDto [body] |
| `/api/rating/post/{id}/rateNegative` | `POST` | userId [jwt], id [path]  | RatePostDto [body] |

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
