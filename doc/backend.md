# Backend Documentation

## Conceptual Design

![UML Design](img/uml-poster.png)

## Logic Design (Relational Model)

User(**id**, login, password, name, avatarUrl)

Category(**id**, name)

Post(**id**, title, description, url, price, creationDate, userId, 
categoryId)
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE 
+ **categoryId** references Category(id) ON UPDATE CASCADE ON DELETE SET 
NULL

Image(**id**, url, postId)
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

