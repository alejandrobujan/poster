# Model Documentation

## Conceptual Design

![UML Design](img/poster-uml.png)

## Logic Design (Relational Model)

User(**id**, userName, password, firstName, lastName, email, avatar, role)

Category(**id**, name)

Post(**id**, title, description, url, price, creationDate, positiveRatings, negativeRatings, expirationDate, validationDate, userId, categoryId)
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE 
+ **categoryId** references Category(id) ON UPDATE CASCADE ON DELETE SET 
NULL

> **_NOTE:_**  For inheritance management, we chose the JOINED strategy (although it is not the most efficient option) as a long-term option due to its better handling of requirements evolution and future product maintenance as this project is in a continuous changing environment.

Offer(**id**)
+ **id** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

Coupon(**id**, code)
+ **id** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

Image(**id**, data, postId)
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

Rating(**id**, positive, userId, postId)
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE 
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

Comment(**id**, description, date, level, answers, userId, postId, parentId)
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE
+ **parentId** references Comment(id) ON UPDATE CASCADE ON DELETE CASCADE

Notification(**id**, text, viewed, creationDate, notifierUserId, notifiedUserId, commentId, postId)
+ **notifierUserId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE
+ **notifiedUserId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE SET NULL
+ **commentId** references Comment(id) ON UPDATE CASCADE ON DELETE SET NULL

Save(**id**, postId, userId)
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE SET NULL
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE
