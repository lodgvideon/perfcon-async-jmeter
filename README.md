# perfcon-async-jmeter

Для запуска нам понадобится:
JDK-11
1)  В одном cmd Запускаем DemoApplication: 
>mvnv.cmd clean install spring-boot:run

Maven - очистит все папки, установит необходимые модули и запустит простой Rest-Service
2) В другом cmd Запускаем Jmeter
> mvnw.cmd -pl jmeter jmeter:configure jmeter:gui

3) Далее можем запустить наш тест и посмотреть как происходит отправка и получение сообщений.


To  run example:
Required: JDK-11
1) We need to run DemoApplication first
>mvnv.cmd clean install spring-boot:run
Maven will clean destination folders and run DemoApplication with simple Rest Service, that support Async Events

2) Run Jmeter 
> mvnw.cmd -pl jmeter jmeter:configure jmeter:gui

3) Run Test in Jmeter

Test will send Request to Rest-Service and next Sampler will wait for Event from Websocket about processing of this request on server.
