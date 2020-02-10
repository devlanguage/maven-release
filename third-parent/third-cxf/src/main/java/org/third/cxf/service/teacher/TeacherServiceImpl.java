package org.third.cxf.service.teacher;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.basic.common.util.BasicRuntimeException;
import org.basic.common.util.StreamUtils;
import org.third.cxf.rest.RestServiceUtil;
import org.third.cxf.service.teacher.binding.ListData;
import org.third.cxf.service.teacher.binding.PageData;
import org.third.cxf.service.teacher.binding.RestResponse;
import org.third.cxf.service.teacher.binding.SimpleData;
import org.third.cxf.service.teacher.binding.SingleData;
import org.third.cxf.service.teacher.dao.Student;
import org.third.cxf.service.teacher.dao.Teacher;

/**
 * Created on Aug 19, 2014, 2:11:39 PM
 */
public class TeacherServiceImpl extends TeacherService {
	static final TeacherService proxy = (TeacherService) RestServiceUtil.createProxyIntance(new TeacherServiceImpl());

	@Override
	public Response getHeader(String userAgent) {
		return Response.ok().entity(userAgent).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.third.cxf.rest.TeacherService#getTeacher(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response getRequest(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder output = new StringBuilder("<result>");
		output.append("<headers>");
		for (java.util.Enumeration<String> headerEnum = request.getHeaderNames(); headerEnum.hasMoreElements();) {
			String headerName = headerEnum.nextElement();
			output.append("<header ");
			output.append("name=\"");
			output.append(headerName);
			output.append("\" value=\"");
			output.append(request.getHeader(headerName));
			output.append("\"");
			output.append("/>");
		}
		output.append("</headers>");

		output.append("<params>");
		for (java.util.Enumeration<String> headerEnum = request.getParameterNames(); headerEnum.hasMoreElements();) {
			String headerName = headerEnum.nextElement();
			output.append("<param ");
			output.append("name=\"");
			output.append(headerName);
			output.append("\" value=\"");
			output.append(request.getParameter(headerName));
			output.append("\"");
			output.append("/>");
		}
		output.append("</params>");
		output.append("<attrs>");
		for (java.util.Enumeration<String> headerEnum = request.getAttributeNames(); headerEnum.hasMoreElements();) {
			String headerName = headerEnum.nextElement();
			output.append("<attr ");
			output.append("name=\"");
			output.append(headerName);
			output.append("\" value=\"");
			output.append(request.getAttribute(headerName));
			output.append("\"");
			output.append("/>");
		}
		output.append("</attrs>");
		output.append("<data>");
		try {
			String ins = StreamUtils.streamToString(request.getInputStream());
			output.append(request.getContentLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		output.append("</data>");
		output.append("</result>");
		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(output.toString()).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.third.cxf.rest.TeacherService#getTeacherByMatrix(long,
	 * java.lang.String, long)
	 */
	@Override
	public Response getTeacherByMatrix(long id, String name, long studentId) {
		Teacher teacher = RestServiceUtil.getTeacherDao().getTeacher(id);
		boolean notFound = true;
		if (teacher != null) {
			for (Iterator<Student> iter = teacher.getStudents().iterator(); iter.hasNext();) {
				Student stu = iter.next();
				if (stu.getId() == studentId) {
					notFound = false;
					break;
				}
			}
			if (notFound) {
				return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
			} else {
				return Response.status(javax.ws.rs.core.Response.Status.OK).entity(teacher).build();
			}
		} else {
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
		}
	}

	public RestResponse getTeachers(long id) {
		ListData<Teacher> teachers = new ListData<org.third.cxf.service.teacher.dao.Teacher>();
		if (id > 0) {
			teachers.getData().add(RestServiceUtil.getTeacherDao().getTeacher(id));
		} else {
			if (id == 0) {
				teachers.setData(RestServiceUtil.getTeacherDao().getAllTeachers());
			} else {
				throw new BasicRuntimeException("invalid id=" + id + ", it should be >=0");
			}
		}
		return new RestResponse(teachers);
	}

	public RestResponse getTeacher(@PathParam("id") int id) {
		SingleData<Teacher> result = new SingleData<Teacher>();
		result.setData(RestServiceUtil.getTeacherDao().getTeacher(id));
		return new RestResponse(result);
	}

	public RestResponse listTeachersByPage(@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		PageData<Teacher> result = new PageData<Teacher>();
		long id = 0;
		result.getData().add(RestServiceUtil.getTeacherDao().getTeacher(id));
		return new RestResponse(result);
	}

	public RestResponse addTeacher(@QueryParam("name") String name) {
		SingleData<Teacher> result = new SingleData<Teacher>();
		result.setData(RestServiceUtil.getTeacherDao().addTeacher(name));
		return new RestResponse(result);
	}

	public RestResponse deleteTeacher(@PathParam("id") int id) {
		SimpleData<String> result = new SimpleData<String>();
		result.setResult(RestServiceUtil.getTeacherDao().deleteTeacher(id).getName());
		return new RestResponse(result);
	}

	public RestResponse updateTeacher(@PathParam("id") int id, @QueryParam("name") String name) {
		SimpleData<String> result = new SimpleData<String>();
		result.setResult(RestServiceUtil.getTeacherDao().updateTeacher(id, name).getName());
		return new RestResponse(result);
	}
}
