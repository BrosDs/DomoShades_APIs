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

    /**
     * /home? owner=owner_email_address
     */
    @Get
    public String getHousesList() {
        try {
            User usr = UserResource.getUser(getKeyValue("owner"));
            List<Home> h = ofy().load().type(Home.class).ancestor(usr).list();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(h);

        } catch (error error) {
            return error.toString();
        }
    }

    /**
     *   /home? owner=owner_email_address&
     *          home=home_id&
     *          description=home_description&
     *          city=city_name&
     *          cap=city_postal_code&
     *          country=where_i_live&
     *          address=home_address_plus_civic_number
     */
    @Put
    public String putHouse() {
        try {
            Home newHome = new Home();
            User usr = UserResource.getUser(getKeyValue("owner"));

            newHome.setOwner(usr);

            try{
                newHome.setId(Long.parseLong(getKeyValue("home")));
            }catch(error e){
            }

            newHome.setDescription(getQueryValue("description"));
            newHome.setCity(getQueryValue("city"));
            newHome.setCap(Integer.parseInt(getQueryValue("cap")));
            newHome.setCountry(getQueryValue("country"));
            newHome.setAddress(getQueryValue("address"));


            List<Home> homeList = ofy().load().type(Home.class).ancestor(usr).list();

            for(Home home : homeList){
                if(home.equals(newHome))
                    return new error("house already saved").toString();
            }

            ofy().save().entity(newHome).now();

            Home fetched = ofy().load().entity(newHome).now();
            return getHouse(fetched);

        } catch (error error) {
            return error.toString();
        }
    }

    /**
     * /home? owner=owner_email_address&
     *        id=home_id_number
     */
    @Delete
    public String deleteHouse() {

        try {
            User own = UserResource.getUser(getKeyValue("owner"));
            Long id = Long.parseLong(getKeyValue("id"));

            ofy().delete().type(Home.class).parent(own).id(id).now();

            return getHousesList();

        } catch (error error) {
            return error.toString();
        }
    }


    public static Home getHome(String owner, Long id){
        User own = UserResource.getUser(owner);
        return ofy().load().type(Home.class).parent(own).id(id).now();
    }

    /** Private methods **/
    private String getHouse(Home house){
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
