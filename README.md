# Spring-boot-lessons
A rest api application based on several spring boot lessons

Spring Security
Реализована авторизация и аутентификация с помощью DaoAuthenticationProvider. Настроен ролевой доступ к ресурсам сервера. Спринговский юзер создается на основе данных из БД.

Spring JPA
к проекту подключена БД PostgerSQL. Создается несколько таблиц связанных OneToOne OneToMany и ManyToMany зависимостями. Представлены основные аннтотации. 
Запросы к БД по имени метода и с помощью аннотации Querry. РЕализована пагинация.

Spring Validation
Настроена валидация некоторых полей при создании новой записи в БД. Написан обработчик исключений валидации.

Rest API
Реализован rest контролеер с основными HTTP методами (GET, POST, PUT, DELETE).
