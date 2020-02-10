package org.third.cxf.rest.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created on Aug 19, 2014, 2:14:40 PM
 */
@Path("/pub")
public class EBSFService {

    @POST
    @Consumes({ "text/xml" })
    @Produces("text/xml")
    @Path("/egba")
    public Response postMethod(@FormParam("sso_key") String inpa, @Context HttpHeaders hd, @Context Request rq) throws URISyntaxException,
            IOException {
        System.out.println("sso_key : " + inpa);
        
        System.out.println("Host : " + hd.getRequestHeader(HttpHeaders.HOST));
        System.out.println("AUTHORIZATION : " + hd.getRequestHeader(HttpHeaders.AUTHORIZATION));
        System.out.println("DATE : " + hd.getRequestHeader(HttpHeaders.DATE));
        System.out.println("CONTENT_TYPE : " + hd.getRequestHeader(HttpHeaders.CONTENT_TYPE));
        System.out.println("ACCEPT : " + hd.getRequestHeader(HttpHeaders.ACCEPT));
        System.out.println("ACCEPT_LANGUAGE : " + hd.getRequestHeader(HttpHeaders.ACCEPT_LANGUAGE));
        System.out.println("CONTENT_LENGTH : " + hd.getRequestHeader(HttpHeaders.CONTENT_LENGTH));
        System.out.println("CONTENT_LOCATION : " + hd.getRequestHeader(HttpHeaders.CONTENT_LOCATION));
        System.out.println("WWW_AUTHENTICATE : " + hd.getRequestHeader(HttpHeaders.WWW_AUTHENTICATE));

        ResponseBuilder rp;
        String content = "abcdefgaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        rp = Response.status(Response.Status.UNAUTHORIZED);
        rp.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length()));
        rp.header(HttpHeaders.WWW_AUTHENTICATE,
                "Digest algorithm='AKAv1-MD5',realm='bsf.chinamobile.com',uri='/pub/egba',username='13912345678'");
        rp.entity(content);

        return rp.build();
    }

    @GET
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/getL")
    public String getMethod(@QueryParam("name") String s, @Context HttpServletRequest hr) throws IOException {
        System.out.println("name is " + s);

        InputStream in = hr.getInputStream();

        byte[] buf = new byte[1024];

        int len = 0;

        while ((len = in.read(buf)) != -1) {
            System.out.println(new String(buf));
        }

        return "Server Recieved!" + s;
    }

    @POST
    @Path("/postL")
    @Consumes("application/json")
    public String postMethod(String in) throws IOException {

        System.out.println("aaaa" + in);
        // byte[] buf = new byte[1024];
        //
        // int len = 0;
        //
        // while((len=in.read(buf))!= -1){
        // System.out.println(new String(buf));
        // }
        //
        return "Server Recieved!" + in;
    }
}