package it.unimore.awd.ShadesAPI.Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Room {
    @Parent Ref<Floor> floor;
    @Id Long roomNum;
    String name;

    public Room(){

    }

    public Room(Floor floor, Long roomNum, String name){
        super();
        this.floor=Ref.create(floor);
        this.roomNum=roomNum;
        this.name=name;
    }

    public String getFloor(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(floor.get());
    }

    public void setFloor(Floor floor){
        this.floor=Ref.create(floor);
    }

    public Long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Long roomNum) {
        this.roomNum = roomNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
