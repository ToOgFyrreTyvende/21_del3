package web;

import dto.IUserDTO;
import dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/users")
public class UserResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@DefaultValue("0") @PathParam("userId") int id) {
        if (id == 0){
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for user Id: " + id).build();
        }

        IUserDTO user = new UserDTO();
        user.setUserId(id);
        return Response.ok(user).build();
    }

    @GET
    @Path("jwt")
    //@JWTNeeded
    @Produces(MediaType.TEXT_PLAIN)
    public String getJWT() {
        return "Hello, Heroku! - jwt edition";
    }
}
