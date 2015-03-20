package it.unimore.awd.ShadesAPI;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.routing.TemplateRoute;

public class DefaultRouterHandler extends Application{

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        /** Router handlers for default resources **/
        router.attach("/user",      UserResource.class);
        router.attach("/home",      HomeResource.class);
        router.attach("/floor",     FloorResource.class);
        router.attach("/room",      RoomResource.class);
        router.attach("/window",    WindowResource.class);

        /** Custom methods for specific APIs **/
        /*
        router.attach("/close_all_home", null);
        router.attach("/close_all_floor",null);
        router.attach("/close_all_room",null);
        router.attach("/insert_new_rule",null):
        */
        return router;
    }
}
