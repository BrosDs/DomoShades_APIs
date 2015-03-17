package it.unimore.awd.ShadesAPI;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import it.unimore.awd.ShadesAPI.Classes.User;

public class OfyService {
    /* Register all entities here */
    static {
        factory().register(User.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
