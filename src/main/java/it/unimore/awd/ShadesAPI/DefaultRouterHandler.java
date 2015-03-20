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
        router.attach("/home",it.unimore.awd.ShadesAPI.HomeResource.class);
        router.attach("/floor",it.unimore.awd.ShadesAPI.FloorResource.class);
        router.attach("/room",it.unimore.awd.ShadesAPI.RoomResource.class);

        return router;
    }
}
