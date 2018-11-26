curl -X GET http://localhost:8080/topjava/rest/meals -H "Content-Type: application/json" 
curl -X GET http://localhost:8080/topjava/rest/meals/between?startDate=2015-05-30&StartTime=00:00:00&endDate=2015-05-30&endTime=23:59:59 -H "Content-Type: application/json" 
curl -X GET http://localhost:8080/topjava/rest/meals/100007 -H "Content-Type: application/json" 
curl -X PUT http://localhost:8080/topjava/rest/meals/100007 -H 'Content-Type: application/json' -d '{"id":100007,"dateTime":"2015-05-31T20:00:00","description":"Обновленный Ужин","calories":510}'
curl -X POST http://localhost:8080/topjava/rest/meals -H "Content-Type: application/json" -d '{"dateTime":"2015-05-31T22:00:00","description":"Новая еда","calories":310}' 
curl -X DELETE http://localhost:8080/topjava/rest/meals/100006 -H "Content-Type: application/json" 