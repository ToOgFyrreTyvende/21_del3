package web;

import dal.IUserDAO;
import dal.UserDAOMySQL;
import dto.IUserDTO;
import dto.UserDTO;
import functionality.IUserFunctionality;
import functionality.UserFunctionality;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/users")
public class UserResource {

    private IUserDAO userdao = new UserDAOMySQL();
    private IUserFunctionality userFunc = new UserFunctionality(userdao);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws IUserDAO.DALException {
        return Response.ok(userFunc.getUserList()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(UserDTO user) throws IUserDAO.DALException {
        try {
            userFunc.createUser(user);
            return Response.ok(userFunc.getUser(user.getUserId())).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Incomplete user").build();
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("userId") int id) throws IUserDAO.DALException {
        try {
            userFunc.deleteUser(id);
            return Response.ok("Deleted " + id).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Incomplete user").build();
    }
}
