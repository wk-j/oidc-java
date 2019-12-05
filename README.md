## OpenID Connect

```bash
docker-compose up
open http://localhost:8080/auth
```

## Java

```bash
mvn exec:java -Dexec.mainClass="wk.Program"
mvn clean install
mvn spring-boot:run
open http://localhost:8083/login

mvn test
```

## .NET

```bash
dotnet run --project  dotnet/Connect22/Connect22.csproj
open http://localhost:5000/hello/hello
```

## Resource

- https://www.pac4j.org/docs/clients/openid-connect.html
- https://dzone.com/articles/hello-world-program-spring-boot
- https://developer.okta.com/blog/2017/10/31/add-authentication-to-play-framework-with-oidc
- https://connect2id.com/learn/openid-connect
- https://www.mathieupassenaud.fr/oauth-backend
- https://medium.com/@bcarunmail/securing-rest-api-using-keycloak-and-spring-oauth2-6ddf3a1efcc2
