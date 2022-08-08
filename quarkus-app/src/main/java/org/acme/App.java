package org.acme;

import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    @ConfigProperty(name = "my.sys.prop")
    URI prop;

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() throws MalformedURLException, URISyntaxException, ExecutionException, InterruptedException {
        String response = "request............. " + prop;
        LOGGER.info(response);
        return Response.accepted(response).build();
    }
}
