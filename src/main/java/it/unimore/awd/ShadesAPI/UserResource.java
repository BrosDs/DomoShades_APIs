package it.unimore.awd.ShadesAPI;

import com.google.gson.GsonBuilder;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import com.google.gson.Gson;
import it.unimore.awd.ShadesAPI.Classes.User;
import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class UserResource extends ServerResource {

    /**
     *   /user? email=user_email_address&
     **/
    @Get
    public String getUser() {
        try {
            String email;
            email = getKeyValue("email");
            return getUserByEmail(email);
        } catch (error error) {
            return error.toString();
        }
    }

    /**
     *   /user? email=user_email_address&
     *          first_name=user_first_name&
     *          last_name=user_last_name&
     *          profile_pic=user_profile_pic_link
     **/
    @Put
    public String putUser(){

        try {
            User newUser = new User();
            newUser.setEmail(getKeyValue("email"));
            newUser.setFirst_name(getQueryValue("first_name"));
            newUser.setLast_name(getQueryValue("last_name"));
            newUser.setProfile_pic(getQueryValue("profile_pic"));

            /** If exists don't change */
            if(ofy().load().type(User.class).id(newUser.getEmail()).now()!=null)
                return getUserByEmail(newUser.getEmail());

            ofy().save().entity(newUser).now();
            return getUserByEmail(newUser.getEmail());

        } catch (error error) {
            return error.toString();
        }
    }

    /**
     *   /user? email=user_email_address&
     **/
    @Delete
    public String deleteUser(){
        try {
            String email;
            email=getKeyValue("email");

            if(ofy().load().type(User.class).id(email).now()!=null) {
                String deleted_user = getUserByEmail(email);
                ofy().delete().entity(ofy().load().type(User.class).id(email).now());
                return deleted_user;
            }
            return new error("no user to delete").toString();
        } catch (error error) {
            return error.toString();
        }
    }


    public static User getUser(String email){
        return ofy().load().type(User.class).id(email).now();
    }

    /** Private methods **/
    private String getUserByEmail(String email) {
        User usr = ofy().load().type(User.class).id(email).now();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(usr);
    }
    private String getKeyValue(String key_name) throws error {
        if(getQueryValue(key_name)!=null) {
            String key_val = getQueryValue(key_name);
            if (key_val.isEmpty())
                throw new error("No key value found");
            return key_val;
        }else
            throw new error("Key name not found");
    }
}
