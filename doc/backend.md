# Backend Documentation

## Conceptual Design

![UML Design](img/poster-uml.png)

## Logic Design (Relational Model)

User(**id**, login, password, name, avatar)

Category(**id**, name)

Post(**id**, title, description, url, price, creationDate, userId, 
categoryId)
+ **userId** references User(id) ON UPDATE CASCADE ON DELETE CASCADE 
+ **categoryId** references Category(id) ON UPDATE CASCADE ON DELETE SET 
NULL

Image(**id**, data, postId)
+ **postId** references Post(id) ON UPDATE CASCADE ON DELETE CASCADE

