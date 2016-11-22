/*
 * Copyright (c) 2016 Aberger Software GmbH. All Rights Reserved.
 *               http://www.aberger.at
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.example.jerseyguice.app;

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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.jerseyguice.dao.UserDao;
import com.example.jerseyguice.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	@Inject
	UserDao dao;

	@GET
	public List<User> users() {
		return dao.listAll();
	}
	@GET
	@Path("{id}")
	public User user(@PathParam("id") int id) {
		return dao.findById(id);
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
		User user = dao.findById(id);
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
