import DataAccess.DataAccessException;

public class Main {

    public static void main(String[] args) throws DataAccessException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        Client client = new Client(serverUrl);
        client.notLoggedInMenu();
//        new Client(serverUrl).run();
    }
}
