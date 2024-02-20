package handlers;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;


public class RegistrationHandler {

    void handleThisRequest(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(something); // not sure what I need here yet

    }
}
