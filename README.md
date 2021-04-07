# equirontest

Использование:

1) собрать проект: mvn package
2) в ходе сборки проекта, в том числе, запустятся тесты первой и второй части задания
3) запустить сервис (первая часть задания) командой: java -jar equirontest-0.0.1-SNAPHOT.jar
4) проверить корректность работы сервиса можно посредством отправки POST-запросов на адрес localhost:8080/validate
например, таким образом:

curl -X POST localhost:8080/validate -H 'Content-type:application/json' -d '{"seller":"", "customer":"648563524"} - документ с ошибками, сервис должен вернуть соответствующий ответ
curl -X POST localhost:8080/validate -H 'Content-type:application/json' -d '{"seller":"123534251", "customer":"648563524", "products":[{"name":"milk","code":"2364758363546"},{"name":"water","code":"3656352437590"}]} - валидный документ, сервис должен вернуть ответ "valid" со статусом 200.
