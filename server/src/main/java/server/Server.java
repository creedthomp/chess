package server;

import handlers.*;
import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();

        Spark.awaitInitialization();
        System.out.println("Listening on port " + desiredPort);
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes() {

        Spark.post("/user", (req, res) -> new RegistrationHandler().handleThisRequest(req, res));
        Spark.post("/session", (req, res) -> new LoginHandler().handleThisRequest(req, res));
        Spark.delete("/session", (req, res) -> new LogoutHandler().handleThisRequest(req, res));
        //Spark.get("/game", ((req, res) -> new ))
    }
}
