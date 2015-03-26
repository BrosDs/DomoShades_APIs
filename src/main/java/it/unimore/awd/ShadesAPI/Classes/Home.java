package it.unimore.awd.ShadesAPI.Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Home {
    @Id Long id;
    @Parent Ref<User> owner;
    String description;
    String city;        //Città
    int cap;            //CAP
    String country;     //Paese
    String address;     //Indirizzo con Civico

    public Home(){
    }

    public Home(User owner, Long id, String description, String city, int cap, String country, String address){
        super();
        this.owner=Ref.create(owner);
        this.id=id;
        this.description=description;
        this.city=city;
        this.cap=cap;
        this.country=country;
        this.address=address;
    }

    public String getOwner() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(owner.get());
    }

    public void setOwner(User owner) {
        this.owner = Ref.create(owner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(Ref<User> owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(Home h){
        return this.owner.equals(h.owner) &&
                this.description.equals(h.description) &&
                this.city.equals(h.city) &&
                this.cap == h.cap &&
                this.country.equals(h.country) &&
                this.address.equals(h.address);
    }

    public int hashcode(){ return this.description.hashCode();}

}
