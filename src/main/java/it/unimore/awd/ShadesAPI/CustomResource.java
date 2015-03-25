package it.unimore.awd.ShadesAPI;

import it.unimore.awd.ShadesAPI.Classes.*;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.Iterator;
import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class CustomResource extends ServerResource{
    /**
     * /custom?
     *              owner=email&
     *              home=home_id&
     *              rule=[0|1]&
     *                  0 -> Close All
     *                  1 -> Open All
     *              type=[0|1|2|3]
     *              1 -> id (floor)
     *              2 -> room_id
     *              3 -> window_id
     * **/
    @Put
    public String putSpecialRule(){
        try {
            int type = Integer.parseInt(getKeyValue("type"));
            int rule = Integer.parseInt(getKeyValue("rule"));
            User usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            Home home = ofy().load().type(Home.class).parent(usr).id(getKeyValueL("home")).now();

            if(type==0){
                specialHomeRemover(home);
                specialHome(home,rule);
                return "Home";
            }

            Floor floor = ofy().load().type(Floor.class).parent(home).id(getKeyValueL("id")).now();
            if(type==1){
                specialFloorRemover(floor);
                specialFloor(floor,rule);
                return "Floor";
            }

            Room room = ofy().load().type(Room.class).parent(floor).id(getKeyValueL("room_id")).now();
            if(type==2){
                specialRoomRemover(room);
                specialRoom(room,rule);
                return "Room";
            }

            Window window = ofy().load().type(Window.class).parent(room).id(getKeyValueL("window_id")).now();
            specialWindowRemover(window);
            specialWindow(window,rule);
            return "Window";

        } catch (error error){
            return error.toString();
        }
    }

    @Delete
    public String removeSpecial(){
        try {
            int type = Integer.parseInt(getKeyValue("type"));

            User usr = ofy().load().type(User.class).id(getKeyValue("owner")).now();
            Home home = ofy().load().type(Home.class).parent(usr).id(getKeyValueL("home")).now();

            if(type==0){
                specialHomeRemover(home);
                return "Home";
            }

            Floor floor = ofy().load().type(Floor.class).parent(home).id(getKeyValueL("id")).now();
            if(type==1){
                specialFloorRemover(floor);
                return "Floor";
            }

            Room room = ofy().load().type(Room.class).parent(floor).id(getKeyValueL("room_id")).now();
            if(type==2){
                specialRoomRemover(room);
                return "Room";
            }

            Window window = ofy().load().type(Window.class).parent(room).id(getKeyValueL("window_id")).now();
            specialWindowRemover(window);
            return "Window";

        } catch (error error){
            return error.toString();
        }
    }


    private void specialWindow(Window w,Integer rule){
        List<Rules> rulesList = w.getRulesLists();
        switch(rule){
            case 0:
                rulesList.add(new Rules("Always Closed",100,0,0,23,59,100));
                break;
            case 1:
                rulesList.add(new Rules("Always Open",100,0,0,23,59,0));
                break;
        }
        w.setRulesLists(rulesList);
        ofy().save().entity(w).now();
    }

    private void specialRoom(Room r,Integer rule){
        List<Window> windows = ofy().load().type(Window.class).ancestor(r).list();
        for (Window window : windows) {
            specialWindow(window,rule);
        }
    }

    private void specialFloor(Floor f, Integer rule){
        List<Room> rooms = ofy().load().type(Room.class).ancestor(f).list();
        for(Room room : rooms){
            specialRoom(room, rule);
        }
    }

    private void specialHome(Home h, Integer rule){
        List<Floor> floors = ofy().load().type(Floor.class).ancestor(h).list();
        for(Floor floor : floors){
            specialFloor(floor, rule);
        }
    }


    /** Special Rules remover **/
    private void specialWindowRemover(Window w){
        Rules open = new Rules("Always Open", 100, 0, 0, 23, 59, 100);
        Rules close = new Rules("Always Closed",100,0,0,23,59,100);

        List<Rules> rulesList = w.getRulesLists();
        Iterator<Rules> i = rulesList.iterator();
        while(i.hasNext()){
            Rules r = i.next();
            if(open.equals(r) || close.equals(r)){
                i.remove();
            }
        }
        w.setRulesLists(rulesList);
        ofy().save().entity(w).now();
    }

    private void specialRoomRemover(Room r){
        List<Window> windows = ofy().load().type(Window.class).ancestor(r).list();
        for (Window window : windows) {
            specialWindowRemover(window);
        }
    }

    private void specialFloorRemover(Floor f){
        List<Room> rooms = ofy().load().type(Room.class).ancestor(f).list();
        for(Room room : rooms){
            specialRoomRemover(room);
        }
    }

    private void specialHomeRemover(Home h){
        List<Floor> floors = ofy().load().type(Floor.class).ancestor(h).list();
        for(Floor floor : floors){
            specialFloorRemover(floor);
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

    private Long getKeyValueL(String key_name) throws error {
        if(getQueryValue(key_name)!=null) {
            String key_val = getQueryValue(key_name);
            if (key_val.isEmpty())
                throw new error("No key value found");
            return Long.parseLong(key_val);
        }else
            throw new error("Key name not found");
    }
}
