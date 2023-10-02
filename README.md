# DemoApp

I created the skeleton with bootify.io, at https://bootify.io/app/5UO9247950Q5

# Challenges done 

* Device and Vehicle Management | "As a user, I can add, view, edit and delete a new vehicle or device to my account."

Öffne dazu nach dem Start der Applikation (s.d. "starting the servers") http://localhost:8080/ und wechsle im Menu auf die Registerkarte "Anna"

Theoretisch umgesetzt:
* Frontend | "As a user, I can access the web application."
* Frontend | "As a user, I can view a dashboard displaying my fleet of vehicles and their charging schedules."
* Backend | "As a system, I can store user data, vehicle data, and schedules in a database."
* Backend | "As a system, I can handle requests to generate and update charging schedules."

Siehe dazu auf der Website im Menu die verschiedenen Entities, die man setzen kann. Diese werden alle in einer DB ("Postgres")
persistiert. 

# Prerequisites

On your local computer, you need the following:
* Docker Desktop
* Maven
* Java 17

further, the commands `java`, `mvn` and `docker-compose` should be on your PATH (on Windows) or accessible in your
shell (i.e. added in your .bashrc)

# Initialization
run the following in your terminal:
```bash
mvnw clean package
```
this will install all packages for java plus will install nodeJs for the frontend server.This will also install
all frontend dependencies.

# startup procedures
For the database, we need to have a docker host available (or alternatively, we can configure our db connection to a local
postgres server).

To configure it locally, change src/main/resources/application.yml to the exposed port (normally it is 5432 for postgres)

## starting the servers
We have to servers, one for providing the frontend, the other for the backend.

in a terminal start the postgres server (as a docker container):
```bash
docker-compose up
```
and in another one, start the application server:
```bash
mvn spring-boot:run
```

# The following was generated by Bootify.io


This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.yml` file to override settings for development.

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

In addition to the Spring Boot application, the DevServer must also be started. [Node.js](https://nodejs.org/) has to be
available on the system - the latest LTS version is recommended. On first usage and after updates the dependencies have to be installed:

```
npm install
```

The DevServer can now be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:8081`. All changes to the templates and JS/CSS
files are immediately visible in the browser.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Node.js is automatically downloaded using the `frontend-maven-plugin` and the final JS/CSS files are integrated into the jar.

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/demo-app-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=com.genser/demo-app
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)
* [Webpack concepts](https://webpack.js.org/concepts/)
* [npm docs](https://docs.npmjs.com/)
* [Tailwind CSS](https://tailwindcss.com/)
* [Htmx in a nutshell](https://htmx.org/docs/)  
