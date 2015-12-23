/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ant.study.tasks.DownloadTask.java is created on 2007-12-28
 */
package org.third.build.ant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * 
 */
public class DownloadTask extends org.apache.tools.ant.Task {

    private String src = "";
    private String destDir = "";
    private String destFile = "";

    /**
     * @return get method for the field destDir
     */
    public String getDestDir() {

        return this.destDir;
    }

    /**
     * @param destDir
     *            the destDir to set
     */
    public void setDestDir(String destDir) {

        this.destDir = destDir;
    }

    /**
     * @return get method for the field destFile
     */
    public String getDestFile() {

        return this.destFile;
    }

    /**
     * @param destFile
     *            the destFile to set
     */
    public void setDestFile(String destFile) {

        this.destFile = destFile;
    }

    /**
     * @return get method for the field src
     */
    public String getSrc() {

        return this.src;
    }

    /**
     * @param src
     *            the src to set
     */
    public void setSrc(String src) {

        this.src = src;
    }

    @Override
    public void init() throws org.apache.tools.ant.BuildException {

        super.init();
        System.err.println(this.getTaskName() + " is initialized");
    }

    @Override
    public void execute() throws org.apache.tools.ant.BuildException {

        super.execute();

        URL url;
        try {
            url = new URL(this.getSrc());// MalformedURLException
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            InputStream ins = httpurlconnection.getInputStream();
            Map<String, List<String>> requests = httpurlconnection.getRequestProperties();
            String message = httpurlconnection.getResponseMessage();
            Map<String, List<String>> headers = httpurlconnection.getHeaderFields();
            // Content-Length, Expires, Set-Cookie, Date, Server, Content-Type, Cache-Control
            store(ins, this.getDestFile());
            ins.close();
            httpurlconnection.disconnect();

            // httpurlconnection.setDoOutput(true);
            // httpurlconnection.setRequestMethod("POST");
            // String username = "username=02000001";

            // httpurlconnection.setRequestProperty("Content-type","text/html");
            // httpurlconnection.setRequestProperty("Connection", "close");
            // httpurlconnection.setRequestProperty("Content-Length","");

            // httpurlconnection.getOutputStream().write(username.getBytes());
            // httpurlconnection.getOutputStream().flush();
            // httpurlconnection.getOutputStream().close();
            //            
            // int code = httpurlconnection.getResponseCode();
            // System.out.println("code " + code);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void store(InputStream ins, String destFile2) {

        byte[] buffer = new byte[512];
        int received = 0;
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(destFile2);
            while ((received = ins.read(buffer, 0, 512)) != -1) {
                fout.write(buffer, 0, received);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getTaskName() {

        return super.getTaskName();
    }

    public static void main(String[] args) {

        DownloadTask d = new DownloadTask();
        FileInputStream ins;
        try {
            // ins = new FileInputStream("http://www.baidu.com");
            // d.store(ins, "test.xml");
            d.setSrc("http://www.baidu.com");
            d.setDestFile("test.xml");
            d.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
