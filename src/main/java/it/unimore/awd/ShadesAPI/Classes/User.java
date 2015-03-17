package it.unimore.awd.ShadesAPI.Classes;

import java.io.Serializable;

public class User implements Serializable{

    private String first_name;
    private String last_name;
    private String email;
    private String profile_pic;

    public User(){
    }

    public User(String first_name, String last_name, String email, String profile_pic){
        super();
        this.email=email;
        this.first_name=first_name;
        this.last_name=last_name;
        this.profile_pic=profile_pic;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
