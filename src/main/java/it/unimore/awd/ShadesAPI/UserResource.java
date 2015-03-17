package it.unimore.awd.ShadesAPI;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import com.google.gson.Gson;
import it.unimore.awd.ShadesAPI.Classes.User;
import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class UserResource extends ServerResource {

    @Get
    public String get_user() {

        String email;
        email = getQuery().getValues("email");

        return get_user_by_email(email);
    }

    @Put
    public String insert_user(){

        User usr = new User();
        usr.setEmail(getQuery().getValues("email"));
        usr.setFirst_name(getQuery().getValues("first_name"));
        usr.setLast_name(getQuery().getValues("last_name"));
        usr.setProfile_pic(getQuery().getValues("profile_pic"));

        ofy().save().entity(usr);
        return get_user_by_email(usr.getEmail());
    }

    @Delete
    public String delete_user(){

        String email;
        email=getQuery().getValues("email");

        String deleted_user=get_user_by_email(email);
        ofy().delete().entity(ofy().load().type(User.class).id(email).now());
        return deleted_user;
    }


    private String get_user_by_email(String email) {

        User usr = ofy().load().type(User.class).id(email).now();
        Gson gson = new Gson();
        return gson.toJson(usr);
    }
}
