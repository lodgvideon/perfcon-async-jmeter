# perfcon-async-jmeter

Для запуска нам понадобится:
JDK-11
1)  В одном cmd Запускаем DemoApplication: 
>mvnv.cmd clean install spring-boot:run

Maven - очистит все папки, установит необходимые модули и запустит простой Rest-Service
2) В другом cmd Запускаем Jmeter
> mvnw.cmd -pl jmeter jmeter:configure jmeter:gui

3) Далее можем запустить наш тест и посмотреть как происходит отправка и получение сообщений.