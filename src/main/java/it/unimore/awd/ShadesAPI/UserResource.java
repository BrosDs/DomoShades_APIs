package it.unimore.awd.ShadesAPI;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.ext.json.JsonConverter;
import it.unimore.awd.ShadesAPI.Classes.User;

public class UserResource extends ServerResource {

    @Get
    public String retrieve() {
        String email;
        try {
            email = getQuery().getValues("email");
        }catch (Exception e){
            throw e;
        }
        return email;
    }

}
