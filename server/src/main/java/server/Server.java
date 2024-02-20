package server;

import handlers.RegistrationHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();

        Spark.awaitInitialization();
        System.out.println("Listening on port " + desiredPort);
        return Spark.port();
    }


    private static void createRoutes() {
        Spark.post("/user", (req, res) -> new RegistrationHandler());
        //Spark.post("/session", (req, res) -> new LoginHandler());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
