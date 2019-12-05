## Issue

```java

if (redirectUriParam.contains("session_state=")) {
    redirectUriParam = KeycloakUriBuilder.fromUri(redirectUriParam)
        .replaceQueryParam(OAuth2Constants.SESSION_STATE, null).build().toString();
}

if (redirectUri != null && !redirectUri.equals(redirectUriParam)) {
    event.error(Errors.INVALID_CODE);
    throw new CorsErrorResponseException(cors, OAuthErrorException.INVALID_GRANT, "Incorrect redirect_uri",
        Response.Status.BAD_REQUEST);
}

```