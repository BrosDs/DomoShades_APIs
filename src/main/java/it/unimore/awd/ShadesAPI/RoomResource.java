package it.unimore.awd.ShadesAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimore.awd.ShadesAPI.Classes.Floor;
import it.unimore.awd.ShadesAPI.Classes.Room;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;


public class RoomResource extends ServerResource {


    /**
     * /room?   owner=owner_email_address&
     *          home=home_id_number&
     *          id=floor_number&
     **/
    @Get
    public String getRoomsByFloor(){
        try{
            Floor floor = FloorResource.getFloor(getKeyValue("owner"),Long.parseLong(getKeyValue("home")),Long.parseLong(getKeyValue("id")));
            List<Room> room = ofy().load().type(Room.class).ancestor(floor).list();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(room);
        }catch (error error){
            return error.toString();
        }
    }


    /**
     * /room?   owner=owner_email_address&
     *          home=home_id_number&
     *          id=floor_number&
     *          room_id=room_id_on_this_floor&
     *          name=room_name
     **/
    @Put
    public String putRoom(){
        try{
            Floor floor = FloorResource.getFloor(getKeyValue("owner"),Long.parseLong(getKeyValue("home")),Long.parseLong(getKeyValue("id")));
            Room newRoom = new Room(floor,Long.parseLong(getKeyValue("room_id")),getQueryValue("name"));

            /*
            Room r = ofy().load().entity(newRoom).now();
            if(r==null){
                ofy().save().entity(newRoom).now();
                return getRoom(newRoom);
            }
            */

            return new error("room already saved").toString();
        }catch(error error){
            return error.toString();
        }
    }

    /**
     * /room?   owner=owner_email_address&
     *          home=home_id_number&
     *          id=floor_number&
     *          room_id=room_id_on_this_floor&
     **/
    @Delete
    public String deleteRoom(){
        try{
            Floor floor = FloorResource.getFloor(getKeyValue("owner"),Long.parseLong(getKeyValue("home")),Long.parseLong(getKeyValue("id")));
            ofy().delete().type(Room.class).parent(floor).id(Long.parseLong(getKeyValue("room_id"))).now();
            return getRoomsByFloor();
        }catch (error error){
            return error.toString();
        }
    }

    public static Room getRoom(String owner, Long home, Long id, Long roomId){
        Floor floor = FloorResource.getFloor(owner,home,id);
        return ofy().load().type(Room.class).parent(floor).id(roomId).now();
    }

    /** Private methods **/
    private String getRoom(Room room){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(room);
    }

    private String getKeyValue(String key_name) throws error {
        if(getQueryValue(key_name)!=null) {
            String key_val = getQueryValue(key_name);
            if (key_val.isEmpty())
                throw new error("No key value found");
            return key_val;
        }else
            throw new error("Key name not found");
    }}
