/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:05:29 PM Aug 19, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.third.cxf.rest.RestServiceUtil;
import org.third.cxf.service.teacher.binding.RestResponse;

/**
 * Created on Aug 19, 2014, 2:05:29 PM
 */
@Path("/teacher")
public class TeacherService {
    static final TeacherService proxy = (TeacherService) RestServiceUtil.createProxyIntance(new TeacherServiceImpl());

    /**
     * @HeaderParam 测试 获取http头信息 url:http://localhost:9000/rest/teacher/getHeader
     * @param userAgent
     * @return
     */
    @GET
    @Path("/getHeader")
    public javax.ws.rs.core.Response getHeader(@javax.ws.rs.HeaderParam("user-agent") String userAgent) {
        return proxy.getHeader(userAgent);
    }

    @GET
    @Path("/getRequest")
    public Response getRequest(@Context() HttpServletRequest request, @Context() HttpServletResponse response) {
        return proxy.getRequest(request, response);
    }

    @GET
    @Path("/matrix/{id}")
    public Response getTeacherByMatrix(// http://localhost:9000/rest/teacher/matrix/{id};name=df;studentId=1
    @PathParam("id") long id, @MatrixParam("name") String name,//
                                       @MatrixParam("studentId") long studentId) {
        return proxy.getTeacherByMatrix(id, name, studentId);
    }

    @Path("/")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML })
    @Consumes({ "text/xml", "application/xml" })
    // http://localhost:9000/rest/teacher,
    // http://localhost:9000/rest/teacher?id=3
    public RestResponse getTeachers(@QueryParam("id") long id) {

        return proxy.getTeachers(id);
    }

    @Path("/{id}")
    @GET
    public RestResponse getTeacher(@PathParam("id") int id) {
        return null;
    }

    @Path("/{pageNo}/{pageSize}")
    @GET
    public RestResponse listTeachersByPage(@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
        return null;
    }

    @Path("/")
    @POST
    public RestResponse addTeacher(@QueryParam("name") String name) {
        return proxy.addTeacher(name);
    }

    @Path("/{id}")
    @DELETE
    public RestResponse deleteTeacher(@PathParam("id") int id) {
        return null;
    }

    @Path("/{id}")
    @PUT
    public RestResponse updateTeacher(@PathParam("id") int id, @QueryParam("name") String name) {
        return null;
    }
}