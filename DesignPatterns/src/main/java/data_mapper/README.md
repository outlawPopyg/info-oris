# Data Mapper
![](./img.gif)    
Objects and relational databases have different mechanisms for structuring data. Many parts of an object, such as collections and inheritance, aren't present in relational databases. When you build an object model with a lot of business logic it's valuable to use these mechanisms to better organize the data and the behavior that goes with it. Doing so leads to variant schemas; that is, the object schema and the relational schema don't match up.

You still need to transfer data between the two schemas, and this data transfer becomes a complexity in its own right. If the in-memory objects know about the relational database structure, changes in one tend to ripple to the other.

The Data Mapper is a layer of software that separates the in-memory objects from the database. Its responsibility is to transfer data between the two and also to isolate them from each other. With Data Mapper the in-memory objects needn't know even that there's a database present; they need no SQL interface code, and certainly no knowledge of the database schema. (The database schema is always ignorant of the objects that use it.) Since it's a form of Mapper (473), Data Mapper itself is even unknown to the domain layer.

___

Объекты и реляционные бд имеют разные механизыми для структурирования данных. Так, в объектной 
модели есть коллекции и наследование, которых нет в реляционных бд. Когда вы создаете модель с
большим кол-вом бизнес-логики, полезно будет использовать эти механизмы для лучшей организации
и поведения данных. Это приведет к разным схемам. Объектная и реляционная не будут совпадать.

Вам все еще нужно передавать данные между двумя схемами, и эта передача сама становится сложной.
Если in-memory объекты знают про структуру реляционной бд, то изменяя структуру в одной вам
придется менять и в другой.