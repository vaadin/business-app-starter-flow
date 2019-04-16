# Running the Project in Development Mode

`mvn jetty:run`

Wait for the application to start

Open http://localhost:8080/ to view the application.

Note that when running in development mode, the application will not work in IE11.

# Running the Project in Production Mode

`mvn jetty:run-exploded -Dvaadin.productionMode`

The default mode when the application is built or started is 'development'. The 'production' mode is turned on by setting the `vaadin.productionMode` system property when building or starting the app.

In the 'production' mode all frontend resources of the application are minified and transpiled to also support Internet Explorer 11. That adds extra time to the build process, but reduces the total download size.

Note that if you switch between running in production mode and development mode, you need to do
```
mvn clean
```
before running in the other mode.

# License
A paid Pro or Prime subscription is required for creating a new software project from this starter. After its creation, results can be used, developed and distributed freely, but licenses for the used commercial components are required during development. The starter or its parts cannot be redistributed as a code example or template.

For full terms, see LICENSE
