package it.unimore.awd.ShadesAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimore.awd.ShadesAPI.Classes.Floor;
import it.unimore.awd.ShadesAPI.Classes.Home;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;


public class FloorResource extends ServerResource{


    /**
     * /floor?  owner=owner_email_address&
     *          home=home_id_number&
     **/
    @Get
    public String getFloorByHouse(){
        try {
            Home home;
            home=HomeResource.getHome(getKeyValue("owner"), Long.parseLong(getKeyValue("home")));
            List<Floor> h = ofy().load().type(Floor.class).ancestor(home).list();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(h);

        } catch (error error) {
            return error.toString();
        }
    }

    /**
     * /floor?  owner=owner_email_address&
     *          home=home_id_number&
     *          id=floor_number&
     *          type=floor_type&
     *          canvas=canvas_value
     **/
    @Put
    public String putFloor(){

        try{
            Home home;
            home=HomeResource.getHome(getKeyValue("owner"),Long.parseLong(getKeyValue("home")));
            Floor newFloor = new Floor(Long.parseLong(getKeyValue("id")),home,getQueryValue("canvas"),Integer.parseInt(getQueryValue("type")));


            Floor f = ofy().load().entity(newFloor).now();
            if(f!=null){
                ofy().delete().entity(f).now();
            }


            ofy().save().entity(newFloor).now();
            Floor fetched = ofy().load().entity(newFloor).now();
            return getFloor(fetched);

        }catch (error error) {
            return error.toString();
        }
    }

    /**
     * /floor?  owner=owner_email_address&
     *          home=home_id_number&
     *          id=floor_number&
     **/
    @Delete
    public String deleteFloor(){
        try {
            Home home;
            home = HomeResource.getHome(getKeyValue("owner"),Long.parseLong(getKeyValue("home")));
            Long id = Long.parseLong(getKeyValue("id"));

            ofy().delete().type(Floor.class).parent(home).id(id).now();

            return getFloorByHouse();
        } catch (error error) {
            return error.toString();
        }
    }

    public static Floor getFloor(String owner, Long home, Long id){
        Home house = HomeResource.getHome(owner,home);
        return ofy().load().type(Floor.class).parent(house).id(id).now();
    }

    /** Private methods **/
    private String getFloor(Floor floor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(floor);
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
