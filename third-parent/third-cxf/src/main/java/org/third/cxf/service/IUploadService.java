package org.third.cxf.service;

import java.util.List;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

/**
 * 文件上传service
 * 
 * @author Administrator
 * 
 */
@Path("/upload")

public interface IUploadService {
    /**
     * 文本文档下载 url:http://localhost:9000/rest/upload/dlText
     * 
     * @return
     */
    @GET
    @Path("dlText")
    @Produces("text/plain")
    public Response downloadText();

    /**
     * 表单提交，文件上传
     * 
     * @return
     */
    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response uploadFileByForm(@Multipart(value = "id", type = "text/plain")
    String id, @Multipart(value = "name", type = "text/plain")
    String name, @Multipart(value = "file", type = "image/png")
    Attachment image);

    /**
     * 多文件上传
     * 
     * @param attchments
     * @param request
     * @return
     */
    @POST
    @Path("/uploadlist")
    @Consumes("multipart/form-data")
    public Response uploadFileList(List<Attachment> attachments, @Context
    HttpServletRequest request);
}