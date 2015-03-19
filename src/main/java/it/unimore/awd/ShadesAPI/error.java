package it.unimore.awd.ShadesAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Throwable exception for missing Key value or Key id in query values
 */

public class error extends Throwable {
    private String error;

    public error(){
        this.error=null;
    }

    public error(String key_name){
        super();
        this.error=key_name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
