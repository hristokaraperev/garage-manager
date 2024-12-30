# Garage Manager Setup Guide

## Prerequisites

- Git
- Java Development Kit (JDK) 21 or higher
- Visual Studio Code with Java and Spring Boot extensions installed

## Steps

1. **Clone the Repository**
    ```sh
    git clone https://github.com/your-username/garage-manager.git
    ```

2. **Open the Project in VS Code**
    - Launch Visual Studio Code.
    - Open the cloned repository folder (`garage-manager`) in VS Code.

3. **Run the Application**
    - Open the Command Palette (`Ctrl+Shift+P` or `Cmd+Shift+P` on macOS).
    - Type `Spring Boot Dashboard` and select it.
    - In the Spring Boot Dashboard, find your application and click the play button to run it.

## Additional Information

- Ensure your `JAVA_HOME` environment variable is set to the JDK installation path.
- Ensure you've added the \bin folder path (JAVA_HOME\bin) at the end of the `Path` environment variable.

## Accessing the Built-in H2 Database Manager Console

To access the built-in H2 database manager console, follow these steps:

1. Start your Spring Boot application.

3. Open your web browser and navigate to `http://localhost:8088/database`.

4. In the login page, use the following details:
    - **JDBC URL**: `jdbc:h2:file:./garage-manager-db`
    - **User Name**: `sysadmin`
    - **Password**: `changeit`

5. Click on the "Connect" button to access the H2 database manager console.