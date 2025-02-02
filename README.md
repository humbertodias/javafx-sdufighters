# SDU Fighters (2nd, improved, version is coming in Summer 2018 or later with thoroughly rewritten code)

SDU (Suleyman Demirel University) Fighters is an analog to the well-known Mortal Kombat, and made as a course work, written in Java.

Initial idea was to make the places on the territory of SDU as the arena backgrounds, and some of the students to be used as the sprites of fighters, and so on, but the time I had was not enough to make our own sprites and some special abilities. That's why there are only two color variants of Sektor's sprite found in the Web, and the player can only move, jump, kick and punch.

Modes available: single player with AI, two players playing in one window, and two players playing from two different windows/clients connected to single host/server.

It had been done in just 4-5 days, so you may notice a bunch of Bad Code lines. Someday I will edit classes, rewrite them, and maybe add some new features.
Nothing changed since May 2016.

## License

SDU Fighters is licensed under the terms of the MIT license.


## How to run
```shell
sdk install java 21.0.6.fx-zulu
sdk install maven 3.6.0
```
### Use
```shell
sdk use java 21.0.6.fx-zulu
sdk use maven 3.6.0
```
### Javafx run
```shell
mvn clean compile
mvn javafx:run
```

### Run debug
```shell
mvn clean package
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005 target/sdufighters-1.0.jar
```



Distributing the Application

To create a platform-specific package (such as .app for macOS or .exe for Windows), use the jlink goal:
```shell
mvn javafx:jlink
```
The generated package can be found in the target directory (e.g., target/app.zip).