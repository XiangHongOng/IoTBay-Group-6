## Database Setup

Before running the application, set up the database by following these steps:

1. Locate the bundled SQL script `setup.sql` in the `resources` folder of the project.

2. Open your SQL software of choice (e.g., MySQL Workbench).

3. Create a new database for the application.

4. Execute the `setup.sql` script in the created database. This script will create the necessary schema and tables for the application to function correctly.

5. Update the database connection settings in the `DBConnector` class to match your local MySQL user account:
    - Open the `DBConnector` class file.
    - Locate the placeholder password in the connection URL.
    - Replace the placeholder password with your actual MySQL user account password.

After completing these steps, the database will be set up and ready for the application to use. Make sure to update the `DBConnector` class with the correct database connection details to establish a successful connection.

## Tomcat Setup

To set up the application to run on Tomcat, follow these steps:

1. Create a new run configuration in your IDE.

2. Set the run configuration to use Tomcat local.

3. Go to the Deployment tab in the run configuration settings.

4. Add the exploded artifact of your application to the deployment list.
    - This is done by clicking the "+" button and selecting the exploded artifact from your project.

5. Ensure that the application context URL is pointing to the correct path.
    - This URL should match the desired context path for your application.
    - For example, if your application context is set to `/IotProjectTest_war_exploded`, set the desired URL to `http://localhost:8080/IotProjectTest_war_exploded/`.
    - This should be done automatically when adding the exploded artifact to the deployment list, but it's required to check the URL is correct.

6. Save the run configuration.

7. Start the Tomcat server using the new run configuration.

Once the Tomcat server is up and running, your application should be deployed and accessible at the specified application context URL.