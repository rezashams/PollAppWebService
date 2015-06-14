package com.pollApp.web.dashboard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.api.view.Viewable;



@Path("/createPrizePoll")
public class createPrizePoll {

	@GET
	@Produces("text/html")
	public Viewable index() {
		return new Viewable("/createPrizePoll.jsp");
	}

}
