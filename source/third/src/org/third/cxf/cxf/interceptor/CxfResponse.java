/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:58:41 PM Feb 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.cxf.interceptor;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.jaxrs.utils.HttpUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * Created on Feb 28, 2014, 2:58:41 PM
 */
public class CxfResponse extends AbstractPhaseInterceptor {

    public CxfResponse() {
        super(Phase.POST_PROTOCOL_ENDING);
    }

    public void handleMessage(Message message) throws Fault {

        try {
            processRequest(message);

        } catch (Exception ex) {
            Response excResponse = JAXRSUtils.convertFaultToResponse(ex, message);
            if (excResponse == null) {
                ProviderFactory.getInstance(message).clearThreadLocalProxies();
                message.getExchange().put(Message.PROPOGATE_EXCEPTION, Boolean.TRUE);

                // NetworkResponse responseInfo = new NetworkResponse();
                // responseInfo.setResponseStatus(ResponseStatusT.FAILURE);
                //
                // ResponseMessage msg = new ResponseMessage();
                // msg.setMessage("Service invoking failure");
                // responseInfo.getResponseMessage().add(msg);
                //
                // AdditionalInfo addInfo = new AdditionalInfo();
                // addInfo.setName("Internal error");
                // responseInfo.getAdditionalInfo().add(addInfo);

                ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                rb.type(MediaType.APPLICATION_XML);
                rb.entity("error");
                Response response = rb.build();

                Fault newFault = new Fault(ex);
                message.setContent(Exception.class, newFault);

                message.getExchange().put(Response.class, response);
            }
            if (excResponse != null) {
                message.getExchange().put(Response.class, excResponse);
            }
        }
    }

    private void processRequest(Message message) throws Exception {

        String rawPath = HttpUtils.getPathToMatch(message, true);
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        try {
            // check(headers);
        } catch (Exception ex) {
        }
    }
}

// private boolean shouldAuthenticate(String rawPath) {
// if (rawPath.startsWith("/mef")) {
// if (rawPath.equalsIgnoreCase(UserService.LOGIN_PATH) || rawPath.equalsIgnoreCase(UserService.LOGOUT_PATH))
// return false;
// else
// return true;
// }
// return false;
// }
//
// private void check(Map<String, List<String>> headers) throws TException {
// List<String> lst = headers.get(TLB_CHECK_TAG);
// if (lst == null)
// throw new AccessDeniedException("invalid invovation.");
// String security = lst.get(0);
// SessionManager.getInstance().checkSession(security);
// }
// }
