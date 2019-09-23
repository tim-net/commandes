# commandes
The project front-end is using Angular, so please consider having node.js installed on your 
computer https://nodejs.org/en/. Additionally, you have to have at least Java 10 installed 
on your computer. In order to launch the project, please type in command line sh gradlew bootRun 
in Unix systems or gradlew.bat bootRun in Windows in the root folder of the project. 
The web-ui URL is http://localhost:80. By default the list of orders is shown. 
You can see order's details and notes clicking on its name in the list. 
Details page allows you to edit order and add\remove order lines. 
Order modifications  are saved only after you hit Sauvegarder button.

Also you can run tests with a command sh gradlew test or gradlew.bat test in the root folder of the project.

The project can be run in development mode (by adding -Pdev after bootRun , i.e gradlew.bat bootRun -Pdev), 
giving a possibility to monitor sql queries in console and check database state on address http://localhost:80/h2console.
Also there is Swagger UI to check REST API requests accessible via http://localhost:80/swagger-ui.html (when launching in developer mode). 
The project is using lombok library, so to have the code correctly compiled in IDE you have to install Lombok Plugin and enable
annotation processing in IDE settings(I use Intellij Idea) : Preferences (Ctrl + Alt + S) Build, 
Execution, Deployment Compiler Annotation Processors Enable annotation processing Make sure you have the 
Lombok plugin for IntelliJ installed!

Preferences -> Plugins Search for "Lombok Plugin" Click Browse repositories... Choose Lombok Plugin Install Restart IntelliJ

