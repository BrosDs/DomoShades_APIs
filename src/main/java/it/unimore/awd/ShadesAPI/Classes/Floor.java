package it.unimore.awd.ShadesAPI.Classes;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Floor {
    @Id long id;
    @Parent Ref<Home> house;
    String canvas;

    public Floor(){

    }

    public Floor(long id, Home house, String canvas){
        super();
        this.id=id;
        this.house=Ref.create(house);
        this.canvas=canvas;
    }

    public String getHouse(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(house.get());
    }

    public void setHouse(Home house) {
        this.house = Ref.create(house);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCanvas() {
        return canvas;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }
}
