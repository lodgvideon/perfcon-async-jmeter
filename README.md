# perfcon-async-jmeter
# Русская версия
Для запуска нам понадобится:
JDK-11

 1. Запускаем DemoApplication 
	 >mvnv.cmd clean install spring-boot:run
 
 Maven - очистит все папки, установит необходимые модули и запустит простой Rest-Service
 2. Запускаем Jmeter
 
		> mvnw.cmd -pl jmeter jmeter:configure jmeter:gui
3. Запускаем Тест в Jmeter
Jmeter подключится к DemoApplication по WebSocketу  и начнет слать  HTTP запросы. Далее будет ждать событий из приложения по Websocket.  




# English version:
To  run example:
Required: JDK-11
1. We need to run DemoApplication first

	>mvnv.cmd clean install spring-boot:run
	
	Maven will clean destination folders and run DemoApplication with simple Rest Service, that support Async Events

2. Run Jmeter 

	> mvnw.cmd -pl jmeter jmeter:configure jmeter:gui

3) Run Test in Jmeter
Test will connect to DemoApplication by Websocket and then  send Request to Rest-Service and next Sampler will wait for Event from Websocket about processing of this request on server.
