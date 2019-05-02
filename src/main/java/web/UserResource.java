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

        try {
            return Response.ok((UserDTO)userFunc.getUser(id)).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for user Id: " + id).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            return Response.ok(userFunc.getUserList()).build();
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred").build();
    }

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        try {
            return Response.ok(userFunc.getRolesList()).build();
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred").build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserDTO user) {
        try {
            userFunc.createUser(user);
            return Response.ok(userFunc.getUser(user.getUserId())).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Incomplete user").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(UserDTO user) {
        try {
            userFunc.updateUser(user);
            return Response.ok(userFunc.getUser(user.getUserId())).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Incomplete user").build();
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("userId") int id) {
        try {
            userFunc.deleteUser(id);
            return Response.ok("Deleted " + id).build();
        } catch (IUserFunctionality.UserInputException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Incomplete user").build();
    }
}
