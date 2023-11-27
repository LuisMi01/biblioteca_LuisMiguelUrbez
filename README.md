# Proyecto de Programacion Concurrente en Java con las biblioteclas JPA e Hibernate de Luis Miguel Urbez <br>
<br>
## Enunciado del proyecto
<br>
**Contexto del problema:** En una biblioteca publica grande, existen miles de libros y cientos de lectores que buscan pedir prestados, devolver y renovar estos libros. ademas, los bibliotecarios deben ser capaces de agregar nuevos libros al sistema, eliminar libros obsoletos o danados, y realizar un seguimiento de los prestamos de libros. Para manejar estas tareas de forma eficiente y segura, necesitamos desarrollar un Sistema de Gestion de Bibliotecas (LMS por sus siglas en ingles) que use Hibernate y JPA para interactuar con una base de datos SQL y que pueda manejar solicitudes concurrentes de manera segura.
<br>
### Requisitos tecnicos:
<br>
<ul>
<li>Dise&ntilde;ar e implementar un modelo de datos para la biblioteca. Esto debe incluir clases para libros, lectores, pr&eacute;stamos, y cualquier otra entidad que considere necesaria. </li>
<li>Utilice Hibernate y JPA para mapear sus clases de dominio a las tablas de la base de datos.</li>
<li>Proporcione una API que permita a los clientes (bibliotecarios y lectores) realizar las operaciones b&aacute;sicas de la biblioteca, como buscar libros, pedir prestados libros, devolver libros, renovar pr&eacute;stamos, agregar nuevos libros y eliminar libros obsoletos.(https://www.nigmacode.com/java/crear-api-rest-con-spring/)</li>
<li>Implemente el control de concurrencia para evitar condiciones de carrera, por ejemplo, dos lectores que intentan pedir prestado el mismo libro al mismo tiempo. </li>
<li>Implemente auditor&iacute;a y control de versiones para realizar un seguimiento de qui&eacute;n hace qu&eacute; y cu&aacute;ndo en el sistema. </li>
<li>Utilice una cach&eacute; para mejorar el rendimiento de las operaciones comunes, como buscar libros. </li>
<li>Utilice pruebas unitarias e integraci&oacute;n para verificar el correcto funcionamiento de su aplicaci&oacute;n. </li>
</ul>
<br>
### Entregables:
<br>
<ul>
<li>C&oacute;digo fuente de la aplicaci&oacute;n.</li>
<li>Diagrama de la base de datos y las clases de dominio.</li>
<li>Un informe que describa el dise&ntilde;o de su aplicaci&oacute;n, c&oacute;mo maneja la concurrencia y c&oacute;mo interact&uacute;a con la base de datos.(https://es.overleaf.com/) </li>
<li>Pruebas unitarias e integraci&oacute;n, junto con un informe de los resultados de las pruebas.</li>
<li>Documentaci&oacute;n del usuario sobre c&oacute;mo utilizar la API de la aplicaci&oacute;n.(https://docs.github.com/es/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax) </li>
<li>Una reflexi&oacute;n sobre los desaf&iacute;os que encontr&oacute; durante el desarrollo de la aplicaci&oacute;n y c&oacute;mo los super&oacute;. </li>
</ul>
<br>

### Criterios de evaluaci&oacute
<br>
<ul>
<li>Correcta implementaci&oacute;n de la concurrencia y el manejo de datos con Hibernate y JPA.</li>
<li>Complejidad y robustez de las pruebas realizadas. </li>
<li>Claridad y calidad del c&oacute;digo, del diagrama de la base de datos y de la documentaci&oacute;n.</li>
<li>Reflexi&oacute;n cr&iacute;tica sobre el proceso de desarrollo. </li>
<li>Funcionalidad y facilidad de uso de la aplicaci&oacute;n.</li>
</ul>
<br>
<br>
<br>

# Biblioteca

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.yml` file to override settings for development.

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
java -Dspring.profiles.active=production -jar ./target/biblioteca-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=io.uax/biblioteca
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)  
* [Webpack concepts](https://webpack.js.org/concepts/)  
* [npm docs](https://docs.npmjs.com/)  
* [Bootstrap docs](https://getbootstrap.com/docs/5.3/getting-started/introduction/)  
* [Htmx in a nutshell](https://htmx.org/docs/)  
* [Learn Spring Boot with Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/)  
