Java Enterprise Online Project 
==
Полнофункциональное Spring/JPA Enterprise приложение c авторизацией и правами доступа на основе ролей с использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных Postgresql и HSQLDB.
## Demo
[Heroku cloud](http://zhukov.herokuapp.com "Heroku cloud")

### CURL samples

##### get All Users
`curl -s http://zhukov.herokuapp.com/rest/admin/users`

##### get Users 100001
`curl -s http://zhukov.herokuapp.com/rest/admin/users/100001`

##### get All Meals
`curl -s http://zhukov.herokuapp.com/rest/profile/meals`

##### get Meals 100003
`curl -s http://zhukov.herokuapp.com/rest/profile/meals/100003`

##### filter Meals
`curl -s "http://zhukov.herokuapp.com/rest/profile/meals/filter?startDate=2015-05-30&startTime=07:00:00&endDate=2015-05-31&endTime=11:00:00"`

##### get Meals not found
`curl -s -v http://zhukov.herokuapp.com/rest/profile/meals/100008`

##### delete Meals
`curl -s -X DELETE http://zhukov.herokuapp.com/rest/profile/meals/100002`

##### create Meals
`curl -s -X POST -d '{"dateTime":"2015-06-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://zhukov.herokuapp.com/topjava/rest/profile/meals`

##### update Meals
`curl -s -X PUT -d '{"dateTime":"2015-05-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://zhukov.herokuapp.com/topjava/rest/profile/meals/100003`