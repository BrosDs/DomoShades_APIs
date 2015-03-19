package it.unimore.awd.ShadesAPI;

import com.google.gson.GsonBuilder;
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
            Home house = new Home();
            User usr = null;

            usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();

            house.setOwner(usr);
            //Id
            house.setDescription(getQueryValue("description"));
            house.setCity(getQueryValue("city"));
            house.setCap(Integer.parseInt(getQueryValue("cap")));
            house.setCountry(getQueryValue("country"));
            house.setAddress(getQueryValue("address"));

            //TODO: Controllare che non si stia per inserire una casa uguale ad una già presente

            ofy().save().entity(house).now();

            Home fetched = ofy().load().entity(house).now();
            return get_house(fetched);

        } catch (error error) {
            return error.toString();
        }
    }

    @Delete
    public String remove_house() {

        try {
            User own = null;
            own = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            Long id = Long.parseLong(getQueryValue("id"));

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
            System.out.println(key_val);
            if (key_val.isEmpty())
                throw new error("No key value found");
            return key_val;
        }else
            throw new error("Key name not found");
    }
}
