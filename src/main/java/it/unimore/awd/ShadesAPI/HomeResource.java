package it.unimore.awd.ShadesAPI;

import com.google.gson.GsonBuilder;
import com.googlecode.objectify.cmd.Query;
import it.unimore.awd.ShadesAPI.Classes.Home;
import it.unimore.awd.ShadesAPI.Classes.User;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import com.google.gson.Gson;

import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class HomeResource extends ServerResource {

    @Get
    public String get_houses_list() {
        try {
            User usr;
            usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            List<Home> h = ofy().load().type(Home.class).ancestor(usr).list();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(h);

        } catch (error error) {
            return error.toString();
        }
    }

    @Put
    public String new_house() {
        try {
            Home newHome = new Home();
            User usr;


            usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();

            newHome.setOwner(usr);
            //Id
            newHome.setDescription(getQueryValue("description"));
            newHome.setCity(getQueryValue("city"));
            newHome.setCap(Integer.parseInt(getQueryValue("cap")));
            newHome.setCountry(getQueryValue("country"));
            newHome.setAddress(getQueryValue("address"));


            Query<Home> h = ofy().load().type(Home.class).ancestor(usr);
            h.filter("description", newHome.getDescription())
                    .filter("city", newHome.getCity())
                    .filter("cap", newHome.getCap())
                    .filter("country", newHome.getCountry())
                    .filter("address", newHome.getAddress());

            for(Home home : h){
                /** home is equal to newHouse */
                return new error("house already saved").toString();
            }




            ofy().save().entity(newHome).now();

            Home fetched = ofy().load().entity(newHome).now();
            return get_house(fetched);

        } catch (error error) {
            return error.toString();
        }
    }

    @Delete
    public String remove_house() {

        try {
            User own;
            own = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            Long id = Long.parseLong(getKeyValue("id"));

            ofy().delete().type(Home.class).parent(own).id(id).now();

            return get_houses_list();

        } catch (error error) {
            return error.toString();
        }
    }


    /** Private methods **/
    private String get_house(Home house){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(house);
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
