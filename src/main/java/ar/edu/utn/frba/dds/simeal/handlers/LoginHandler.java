package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

public class LoginHandler {
    public void handle(Context context) {

        String username = context.formParam("user");
        String password = context.formParam("password");

        // Simple check for credentials (replace this with your authentication logic)
        if ("admin".equals(username) && "password123".equals(password)) {
            // Store the user ID or any other relevant info in the session
            context.sessionAttribute("user", username);
            context.result("Login successful! Welcome, " + username);
        } else {
            // Invalid credentials, return an error
            context.status(401).result("Invalid username or password.");
        }
    }
}
