package firstSteps;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 *
 */
public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
        System.out.println("Prova");
        return "<html>hello, world (from the cloud!)<html>";
    }

}
