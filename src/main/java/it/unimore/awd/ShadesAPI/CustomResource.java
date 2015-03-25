package it.unimore.awd.ShadesAPI;

import it.unimore.awd.ShadesAPI.Classes.*;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class CustomResource extends ServerResource{
    /**
     * /custom?     owner=email&
     *              home=home_id&
     *              type=[0|1|2|3]
     *              1 -> id (floor)
     *              2 -> room_id
     *              3 -> window_id
     * **/
    @Put
    public String putCustomRule(){
        try {
            int type = Integer.parseInt(getKeyValue("type"));
            User usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            Home home = ofy().load().type(Home.class).parent(usr).id(getKeyValue("home")).now();

            if(type==0){
                closeHome(home);
                return "Home";
            }

            Floor floor = ofy().load().type(Floor.class).parent(home).id(getKeyValue("id")).now();
            if(type==1){
                closeFloor(floor);
                return "Floor";
            }

            Room room = ofy().load().type(Room.class).parent(floor).id(getKeyValue("room_id")).now();
            if(type==2){
                closeRoom(room);
                return "Room";
            }

            Window window = ofy().load().type(Window.class).parent(room).id(getKeyValue("window_id")).now();
            closeWindow(window);
            return "Window";

        } catch (error error){
            return error.toString();
        }
    }

    @Delete
    public String deleteCustomRule(){
        return null;
    }


    private void closeWindow(Window w){
        List<Rules> rulesList = w.getRulesLists();
        rulesList.add(new Rules("Always Closed",100,0,0,23,59,100));
        w.setRulesLists(rulesList);
        ofy().save().entity(w).now();
    }

    private void closeRoom(Room r){
        List<Window> windows = ofy().load().type(Window.class).ancestor(r).list();
        for (Window window : windows) {
            closeWindow(window);
        }
    }

    private void closeFloor(Floor f){
        List<Room> rooms = ofy().load().type(Room.class).ancestor(f).list();
        for(Room room : rooms){
            closeRoom(room);
        }
    }

    private void closeHome(Home h){
        List<Floor> floors = ofy().load().type(Floor.class).ancestor(h).list();
        for(Floor floor : floors){
            closeFloor(floor);
        }
    }

    /** Private methods **/
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
