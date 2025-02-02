
# JavaFX - SDUFighters

(2nd, improved, version is coming in Summer 2018 or later with thoroughly rewritten code)

SDU (Suleyman Demirel University) Fighters is an analog to the well-known Mortal Kombat, and made as a course work, written in Java.

Initial idea was to make the places on the territory of SDU as the arena backgrounds, and some of the students to be used as the sprites of fighters, and so on, but the time I had was not enough to make our own sprites and some special abilities.

That's why there are only two color variants of Sektor's sprite found in the Web, and the player can only move, jump, kick and punch.

Modes available: single player with AI, two players playing in one window, and two players playing from two different windows/clients connected to single host/server.

It had been done in just 4-5 days, so you may notice a bunch of Bad Code lines. 

Someday I will edit classes, rewrite them, and maybe add some new features.

Nothing changed since May 2016.

## Requirements
- **Java**: 21+
- **Maven**: 3.6.0+
- **[SDKMAN](http://sdkman.io)**

---

## Setting Up the Environment with SDKMAN

1. Install Java and Maven:
    ```bash
    sdk install java 21.0.6.fx-zulu
    sdk install maven 3.6.0
    ```

2. Set the installed versions:
    ```bash
    sdk use java 21.0.6.fx-zulu
    sdk use maven 3.6.0
    ```

---

## Running the Project

1. Clean and compile the project:
    ```bash
    mvn clean compile
    ```

2. Run the application using the JavaFX Maven Plugin:
    ```bash
    mvn javafx:run
    ```
![image](https://github.com/user-attachments/assets/8c75eb89-034f-4a52-a2be-b6fa56a4a7b2)

---

## Distributing the Application

To create a platform-specific package (such as `.app` for macOS or `.exe` for Windows), use the `jlink` goal:

   ```bash
   mvn javafx:jlink
   ```

The generated package can be found in the `target` directory (e.g., `target/app.zip`).

---

## Manual Executable JAR

To run the application as an executable JAR, you will need to download JavaFX from [Gluon](https://gluonhq.com/products/javafx/) for your platform.
1. Setup JAVAFX_SDK
   ```bash
   cd $HOME/Downloads
   wget https://download2.gluonhq.com/openjfx/23.0.1/openjfx-23.0.1_osx-aarch64_bin-sdk.zip
   unzip openjfx-23.0.1_osx-aarch64_bin-sdk.zip
   export JAVAFX_SDK=$HOME/Downloads/javafx-sdk-23.0.1
   ```

2. Build the JAR:
    ```bash
    mvn clean package
    ```

3. Run the application with the following command, making sure to specify the JavaFX SDK path:
    ```bash
    java --add-modules javafx.controls,javafx.fxml --module-path $JAVAFX_SDK/lib -jar target/sdufighters-1.0.jar
    ```

---

## References
- [JavaFX (Gluon)](https://gluonhq.com/products/javafx/)
