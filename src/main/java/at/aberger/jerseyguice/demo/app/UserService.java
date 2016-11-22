package at.aberger.jerseyguice.demo.app;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import at.aberger.jerseyguice.demo.dao.UserDao;
import at.aberger.jerseyguice.demo.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	@Inject
	UserDao dao;

	@GET
	public List<User> users() {
		return dao.getAll();
	}
	@GET
	@Path("{id}")
	public User user(@PathParam("id") int id) {
		return dao.getById(id);
	}
	@POST
	public Response create(User user, @Context UriInfo uriInfo) {
		User createdUser = dao.add(user);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(createdUser.id));
        return Response.created(builder.build()).build();
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		User user = dao.getById(id);
		if (user != null) {
			dao.delete(user);
		}
		return Response.noContent().build();
	}
	@PUT
	public Response update(User user, @Context UriInfo uriInfo) {
		boolean updateOk = dao.update(user);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(user.id));
        ResponseBuilder responseBuilder = updateOk ? Response.ok() : Response.noContent();
        responseBuilder.contentLocation(builder.build());
        return responseBuilder.build();
	}
}
