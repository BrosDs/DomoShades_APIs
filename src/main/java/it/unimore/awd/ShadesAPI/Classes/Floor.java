package it.unimore.awd.ShadesAPI.Classes;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Floor {
    @Id Long id;
    @Parent Ref<Home> house;
    String canvas;
    Integer type;

    public Floor(){

    }

    public Floor(Long id, Home house, String canvas, Integer type){
        super();
        this.id=id;
        this.house=Ref.create(house);
        this.canvas=canvas;
        this.type=type;
    }

    public String getHouse(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(house.get());
    }

    public void setHouse(Home house) {
        this.house = Ref.create(house);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanvas() {
        return canvas;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
