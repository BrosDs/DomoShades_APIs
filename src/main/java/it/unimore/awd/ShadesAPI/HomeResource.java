package it.unimore.awd.ShadesAPI;

import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import it.unimore.awd.ShadesAPI.Classes.Home;
import it.unimore.awd.ShadesAPI.Classes.User;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import com.google.gson.Gson;

import java.util.List;

import static it.unimore.awd.ShadesAPI.OfyService.ofy;

public class HomeResource extends ServerResource {

    /**
     * TODO: Controllare che i valori non siano precedentemente già inseriti / esistano
     * */

    @Get
    public String get_houses_list() {
        User usr = ofy().load().type(User.class).id(getQuery().getValues("owner")).now();
        List<Home> h = ofy().load().type(Home.class).ancestor(usr).list();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(h);
    }

    @Put
    public String new_house() {
        Home house = new Home();
        User usr = ofy().load().type(User.class).id(getQuery().getValues("owner")).now();

        house.setOwner(usr);
        //Id
        house.setDescription(getQuery().getValues("description"));
        house.setCity(getQuery().getValues("city"));
        house.setCap(Integer.parseInt(getQuery().getValues("cap")));
        house.setCountry(getQuery().getValues("country"));
        house.setAddress(getQuery().getValues("address"));


        ofy().save().entity(house).now();

        Home fetched = ofy().load().entity(house).now();
        return get_house(fetched);
    }

    @Delete
    public String remove_house() {
        System.out.println(getQuery().getValues("owner"));
        User own = ofy().load().type(User.class).id(getQuery().getValues("owner")).now();
        Long id = Long.parseLong(getQuery().getValues("id"));

        ofy().delete().type(Home.class).parent(own).id(id).now();

        return get_houses_list();
    }

    private String get_owners_houses(String owner) {
        Query<Home> h = ofy().load().type(Home.class);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //return gson.toJson(house);
        return "ok";

    }

    private String get_house(Home house){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(house);
    }
}
