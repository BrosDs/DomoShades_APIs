package it.unimore.awd.ShadesAPI;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class DefaultRouterHandler extends Application{

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        /** Router handlers for default resources **/
        router.attach("/api/user",      UserResource.class);
        router.attach("/api/home",      HomeResource.class);
        router.attach("/api/floor",     FloorResource.class);
        router.attach("/api/room",      RoomResource.class);
        router.attach("/api/window",    WindowResource.class);
        router.attach("/api/rule",      RulesResource.class);
        router.attach("/api/custom",    CustomResource.class);

        /** Custom methods for specific APIs **/


        /*
        router.attach("/api/close_all_home", null);
        router.attach("/api/close_all_floor",null);
        router.attach("/api/close_all_room",null);
        router.attach("/api/insert_new_rule",null):
        */
        return router;
    }
}
