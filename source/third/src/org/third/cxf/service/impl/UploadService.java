/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:20:05 PM Feb 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.third.cxf.service.IUploadService;

public class UploadService implements IUploadService {
    @Override
    public Response downloadText() {
        File file = new File("D:\\test.txt");
        ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename='test.txt'");
        return response.build();
    }

    @Override
    public Response uploadFileByForm(String id, String name, Attachment image) {
        System.out.println("id:" + id);
        System.out.println("name:" + name);
        DataHandler dh = image.getDataHandler();

        try {
            InputStream ins = dh.getInputStream();
            writeToFile(ins, "D:\\upload\\" + dh.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().entity("ok").build();
    }

    @Override
    public Response uploadFileList(List<Attachment> attachments, HttpServletRequest request) {
        if (attachments.size() > 0)
            System.out.println("ok");

        for (Attachment attach : attachments) {
            DataHandler dh = attach.getDataHandler();
            System.out.println(attach.getContentType().toString());

//            attach.getHeaders()//{Content-Transfer-Encoding=[binary], Content-Disposition=[form-data; name="filedata"; filename="logging.properties"], Content-Type=[application/octet-stream; charset=ISO-8859-1], Content-ID=[root.message@cxf.apache.org]}
            System.out.println(attach.getHeaders());
            if (attach.getContentType().toString().equals("text/plain")) {
                try {
                    System.out.println(dh.getName());
                    System.out.println(writeToString(dh.getInputStream()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    writeToFile(dh.getInputStream(), "D:\\" + dh.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Response.ok().entity("ok").build();
    }

    private void writeToFile(InputStream ins, String path) {
        try {
            OutputStream out = new FileOutputStream(new File(path));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = ins.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeToString(InputStream ins) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int i = -1;
        while ((i = ins.read(b)) != -1) {
            out.write(b, 0, i);
        }
        ins.close();
        return new String(out.toByteArray(), "UTF-8");
    }
}