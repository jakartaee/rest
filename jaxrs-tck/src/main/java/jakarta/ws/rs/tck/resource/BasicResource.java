package jakarta.ws.rs.tck.resource;

import java.util.logging.Logger;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("basic")
public class BasicResource {
    private static final Logger LOG = Logger.getLogger(BasicResource.class.getName());

    @GET
    public Response getResponse(@PathParam("id") String id) {
        LOG.info("getResponse " + id);
        System.out.println("getResponse " + id);
        return Response.ok("GET basic " + id).build();
    }

    @POST
    public Response postResponse(String entity) {
        LOG.info("postResponse " + entity);
        return Response.ok("POST basic " + entity).build();
    }

    @PUT
    public Response putResponse(String entity) {
        LOG.info("putResponse " + entity);
        return Response.ok("PUT basic " + entity).build();
    }

    @PATCH
    public Response patchResponse(String entity) {
        LOG.info("patchResponse " + entity);
        return Response.ok("PATCH basic " + entity).build();
    }

    @DELETE
    public Response deleteResponse(@QueryParam("id") String id) {
        LOG.info("deleteResponse " + id);
        return Response.ok("DELETE basic " + id).build();
    }
}
