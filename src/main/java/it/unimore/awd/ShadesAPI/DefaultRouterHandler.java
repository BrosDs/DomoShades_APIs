package it.unimore.awd.ShadesAPI;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class DefaultRouterHandler extends Application{
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attachDefault(it.unimore.awd.ShadesAPI.UserResource.class);

        return router;
    }
}
