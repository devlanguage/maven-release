/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:17:15 PM Aug 19, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.rest.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created on Aug 19, 2014, 2:17:15 PM
 */
public class EsbServiceTest {
    public void testPost() {
        try {
            HttpClient client = new DefaultHttpClient();

            // HttpPut put = new HttpPut("http://192.168.1.101:8080/rest/putHello/param");
            HttpPost put = new HttpPost("http://localhost:8080/ws/rest/pub/postL");
            StringEntity inentity = new StringEntity("cccabcd");

            put.setEntity(inentity);

            HttpResponse response = client.execute(put);

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String s = EntityUtils.toString(entity);
                System.out.println("Content is : [" + s + "]");

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
