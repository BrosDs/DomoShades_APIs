package it.unimore.awd.ShadesAPI.Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.List;

@Entity
public class Window{
    @Parent Ref<Room> room;
    @Id Long windowId;
    List<Rules> rulesLists;

    public Window(){

    }

    public Window(Room room, Long windowId, List<Rules> rulesLists) {
        super();
        this.room = Ref.create(room);
        this.windowId = windowId;
        this.rulesLists = rulesLists;
    }

    public String getRoom() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this.room.get());
    }

    public void setRoom(Room room) {
        this.room = Ref.create(room);
    }

    public Long getWindowId() {
        return windowId;
    }

    public void setWindowId(Long windowId) {
        this.windowId = windowId;
    }

    public List<Rules> getRulesLists() {
        return rulesLists;
    }

    public void setRulesLists(List<Rules> rulesLists) {
        this.rulesLists = rulesLists;
    }
}
