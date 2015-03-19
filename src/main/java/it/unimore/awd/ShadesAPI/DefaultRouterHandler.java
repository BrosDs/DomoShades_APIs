package it.unimore.awd.ShadesAPI;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class DefaultRouterHandler extends Application{

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        // router.attachDefault();

        router.attach("/user",it.unimore.awd.ShadesAPI.UserResource.class);
        /*
        *   /user?email=brosds@gmail.com&first_name=Dario&last_name=Stabili&profile_pic=http://dummy.com/pic
        */

        router.attach("/home",it.unimore.awd.ShadesAPI.HomeResource.class);
        /*
        *   /home?owner=brosds@gmail.com&description=CasaMantova&city=Mantova&cap=46100&country=Mantova&address=Via+Principe+Amedeo+,+6
        */



        return router;
    }
}
