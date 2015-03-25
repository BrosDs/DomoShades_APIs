package it.unimore.awd.ShadesAPI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimore.awd.ShadesAPI.Classes.Room;
import it.unimore.awd.ShadesAPI.Classes.Rules;
import it.unimore.awd.ShadesAPI.Classes.Window;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;



public class WindowResource extends ServerResource {
    /**
     * /window?     owner=owner_email_address&
     *              home=home_id_number&
     *              id=floor_number&
     *              room_id=room_id_on_this_floor
     **/
    @Get
    public String getWindowsOfRoom(){
        try{
            Room room = RoomResource.getRoom(getKeyValue("owner"), Long.parseLong(getKeyValue("home")), Long.parseLong(getKeyValue("id")), Long.parseLong(getKeyValue("room_id")));
            List<Window> windows = ofy().load().type(Window.class).ancestor(room).list();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(windows);
        }catch (error error){
            return error.toString();
        }
    }


    /**
     * /window?     owner=owner_email_address&
     *              home=home_id_number&
     *              id=floor_number&
     *              room_id=room_id_on_this_floor&
     *              window_id=window_id_in_this_room
     **/
    @Put
    public String putWindow(){
        try{
            Room room = RoomResource.getRoom(getKeyValue("owner"), Long.parseLong(getKeyValue("home")), Long.parseLong(getKeyValue("id")), Long.parseLong(getKeyValue("room_id")));

            List<Rules> defaultRules = getDefaultRules();
            Window newWindow = new Window(room,Long.parseLong(getKeyValue("window_id")),defaultRules);

            Window w = ofy().load().entity(newWindow).now();

            if(w==null){
                ofy().save().entity(newWindow).now();
                return getWindow(newWindow);
            }

            return new error("window already saved").toString();
        }catch(error error){
            return error.toString();
        }
    }

    /**
     * /window?     owner=owner_email_address&
     *              home=home_id_number&
     *              id=floor_number&
     *              room_id=room_id_on_this_floor&
     *              window_id=window_id_in_this_room
     **/
    @Delete
    public String deleteWindow(){
        try{
            Room room = RoomResource.getRoom(getKeyValue("owner"), Long.parseLong(getKeyValue("home")), Long.parseLong(getKeyValue("id")), Long.parseLong(getKeyValue("room_id")));
            ofy().delete().type(Window.class).parent(room).id(Long.parseLong(getKeyValue("window_id"))).now();
            return getWindowsOfRoom();
        }catch (error error){
            return error.toString();
        }
    }

    public static Window getWindow(String owner, Long home, Long id, Long roomId, Long window_id){
        Room room = RoomResource.getRoom(owner, home, id, roomId);
        return ofy().load().type(Window.class).parent(room).id(window_id).now();
    }

    /** Private methods **/
    public static String getWindow(Window w){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(w);
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

    private List<Rules> getDefaultRules(){
        List<Rules> r=new ArrayList<Rules>();

        Rules r1 = new Rules("Regola Giorno",0,07,00,14,00,0);
        Rules r2 = new Rules("Regola Pomeriggio",0,14,00,22,00,25);
        Rules r3 = new Rules("Regola Notte",0,22,00,07,00,100);

        r.add(r1);
        r.add(r2);
        r.add(r3);

        return r;
    }

}
