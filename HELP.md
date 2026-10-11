# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.yigitaslan.call-center-system' is invalid and this project uses 'com.yigitaslan.call_center_system' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.1/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.1/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.1/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/4.1.1/reference/web/spring-security.html)
* [Validation](https://docs.spring.io/spring-boot/4.1.1/reference/io/validation.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/4.1.1/reference/actuator/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Runtime security configuration

For local development, copy `application-local.properties.example` to
`application-local.properties` in the project root and replace the database
password and API password with your own values. This local file is loaded
automatically and excluded from Git; do not commit it.

Provide `APP_SECURITY_USERNAME` and an `APP_SECURITY_PASSWORD` of at least 12
characters, plus
`DB_USERNAME` and `DB_PASSWORD`, through the deployment's secret manager or
environment. The application intentionally has no built-in credentials and
will not start without API credentials. Use a least-privilege database account;
the SQL Server connection encrypts traffic and validates the server
certificate by default.

HTTPS is required by default. Configure `SERVER_SSL_KEY_STORE` (a PKCS#12
keystore), `SERVER_SSL_KEY_STORE_PASSWORD`, and optionally `SERVER_PORT`.
For local development only, HTTPS enforcement can be disabled with
`APP_SECURITY_REQUIRE_HTTPS=false` and the embedded SSL listener with
`SERVER_SSL_ENABLED=false`. Do not use those settings in a deployed environment.
For state-changing API requests, first fetch `/api/csrf`, then send the
`XSRF-TOKEN` cookie back as the `X-XSRF-TOKEN` request header.

### Asynchronous API request handling

Business API controller methods return `CompletableFuture<ResponseEntity<...>>`.
They schedule the service call on the named `apiExecutor` configured in
`AsyncConfig`. The executor has 8 core threads, a maximum of 32 threads, and a
queue capacity of 500. Spring MVC uses the future to process the servlet request
asynchronously while the work runs, then sends the normal response when it
finishes. Clients still wait for that response; this is not a `202 Accepted`
background-job API.

The service and repository methods remain synchronous. They use Spring Data
JPA, so database calls block an `apiExecutor` worker while waiting for the
database. This moves that blocking work off the servlet request thread but
does not provide nonblocking database I/O. Tune executor sizing together with
the database connection pool and expected workload; adding worker threads
cannot increase database capacity by itself. A fully nonblocking database
stack would require replacing JPA with a reactive driver/repository layer.
