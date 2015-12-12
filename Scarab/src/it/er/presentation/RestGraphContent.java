package it.er.presentation;

import it.er.manage.graph.GraphManage;
import it.er.object.content.GraphContent;
import it.er.object.content.Palette16;
import it.er.object.content.PaletteBox;
import it.er.object.content.ScSvgWrap;
import it.er.util.SingletonLookup;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/graph")
public class RestGraphContent {

	private GraphManage graph;
	
	@GET
	@Path("palette")
	public Response get8BitPalette(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@QueryParam("width") String width,
			@QueryParam("height") String height,
			@QueryParam("t") String t){
		graph = SingletonLookup.getGraphManage(cx);
		
		return Response.ok(graph.getPalette16(req, res, cx, width, height,t),"application/xml").build();
	}
}
