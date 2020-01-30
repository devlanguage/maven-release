package org.third.cxf.rest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.SystemUtils;
import org.third.cxf.service.teacher.binding.RespCode;
import org.third.cxf.service.teacher.binding.RestResponse;
import org.third.cxf.service.teacher.dao.TeacherDao;
import org.third.cxf.service.teacher.dao.TeacherDaoImpl;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created on Aug 22, 2014, 5:47:07 PM
 */
class RestServiceCglibProxy implements net.sf.cglib.proxy.MethodInterceptor, net.sf.cglib.proxy.CallbackFilter {
    private final static net.sf.cglib.proxy.Enhancer enhancer = new Enhancer();
    private Object target;
    private static final CommonLogger logger = CommonLogger.getLogger(RestServiceRelectionProxy.class);

    public Object createProxy(Object delegate) {
        this.target = delegate;
        enhancer.setSuperclass(delegate.getClass());// 代理类会生产一个被代理类的子类，final方法除外
        enhancer.setCallbacks(new net.sf.cglib.proxy.Callback[] { net.sf.cglib.proxy.NoOp.INSTANCE, this });// 将本身设为回调对象，必须实现接口MehtoInterceptor
        enhancer.setCallbackFilter(this);
        return enhancer.create();
    }

    @Override
    // MethodInterceptor 
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] aobj, net.sf.cglib.proxy.MethodProxy methodproxy)
            throws Throwable {
        logger.info("====Calling the " + method);
        Object returnedObj = null;
        try {
            returnedObj = methodproxy.invoke(target, aobj);
        } catch (Exception e) {
            ResponseBuilder builder = Response.status(Status.INTERNAL_SERVER_ERROR);
            RestResponse response = new RestResponse();
            response.setCode(RespCode.EX_UNKNOWN);
            builder.entity(response);
            throw new WebApplicationException(builder.build());
        }

        return returnedObj;
    }

    final static int CallbackNoOp = 0;
    final static int CallbackFacade = 1;

    @Override
    // CallbackFilter
    public int accept(Method method) {
        return 1;
        //
        // if (method.getName().startsWith("say")) {
        // return CallbackFacade;
        // } else if (method.getName().startsWith("print")) {
        // System.out.println("====Skip Callback Intercepter");
        // }
        // return 0;
    }
}

class RestServiceRelectionProxy implements InvocationHandler {
    private static final CommonLogger logger = CommonLogger.getLogger(RestServiceRelectionProxy.class);

    private Object targetObject;

    public RestServiceRelectionProxy(Object targetObject_) {
        this.targetObject = targetObject_;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (logger.isDebugEnabled()) {
            logger.debug(targetObject.getClass().getName() + "." + method.getName() + " called");
        }
        try {
            result = method.invoke(targetObject, args);
        } catch (Exception e) {
            ResponseBuilder builder = Response.status(Status.INTERNAL_SERVER_ERROR);
            builder.entity("<EthernetResponse><ResponseStatus>failure</ResponseStatus><ResponseMessage>Code:500," + e.getMessage()
                    + SystemUtils.getExceptionStackTrace(e) + "</ResponseMessage></EthernetResponse");
            throw new WebApplicationException(builder.build());
        }
        // Response resp = JAXRSUtils.createResponse(null, message, errorMsg.toString(),
        // Response.Status.NOT_FOUND.getStatusCode(), false);
        // throw new NotFoundException(resp);

        // if (result instanceof EthernetQueryResponse) {
        // EthernetQueryResponse response = (EthernetQueryResponse) result;
        // if (ResponseStatusT.FAILURE == response.getResponseStatus()) {
        // ResponseBuilder builder = Response.status(getStatuCode(response.getResponseMessage()));
        // builder.entity(result);
        // throw new WebApplicationException(builder.build());
        // }
        // } else if (result instanceof EthernetResponse) {
        // EthernetResponse response = (EthernetResponse) result;
        // if (ResponseStatusT.FAILURE == response.getResponseStatus()) {
        // String responseMessage = response.getResponseMessage();
        // int statusCode = getStatuCode(response.getResponseMessage());
        // ResponseBuilder builder = Response.status(statusCode);
        // if (statusCode == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
        // responseMessage = updateStatusCode(responseMessage, statusCode);
        // }
        // builder.entity(result);
        // throw new WebApplicationException(builder.build());
        // }
        // } else if (result instanceof MefLoginResponse) {
        // MefLoginResponse response = (MefLoginResponse) result;
        // if (ResponseStatusT.FAILURE == response.getResponseStatus()) {
        // ResponseBuilder builder = Response.status(getStatuCode(response.getResponseMessage()));
        // builder.entity(result);
        // throw new WebApplicationException(builder.build());
        // }
        // }
        return result;
    }

}

public class RestServiceUtil {

    private static final CommonLogger logger = CommonLogger.getLogger(RestServiceUtil.class);
    static final RestServiceCglibProxy factory = new RestServiceCglibProxy();

    public static final Object createProxyIntance(Object targetObject_) {
        return factory.createProxy(targetObject_);
    }

    private static final String updateStatusCode(String responseMessage, int statusCode) {
        int idx1 = responseMessage.indexOf("Code:") + 5;
        if (idx1 == 5) {
            int idx2 = responseMessage.indexOf(",", 2);
            if (!"500".equals(responseMessage.substring(idx1, idx2))) {
                return "Code:500" + responseMessage.substring(idx2);
            }
        }
        return responseMessage;
    }

    public static final int getStatuCode(String statusMessage) {
        try {
            int statusCode = Integer.parseInt(statusMessage.substring(statusMessage.indexOf(":") + 1, statusMessage.indexOf(",")));
            if (statusCode < 100 || statusCode > 599) {
                return Status.INTERNAL_SERVER_ERROR.getStatusCode();
            }
            return statusCode;
        } catch (Exception e) {
            return Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
    }

    public static final int getStatuCode(List<String> statusMessages) {
        if (statusMessages.size() > 0) {
            int statusCode = getStatuCode(statusMessages.get(0));
            if (statusCode == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                String statusMessage = updateStatusCode(statusMessages.get(0), statusCode);
                if (!statusMessages.get(0).equals(statusMessage)) {
                    statusMessages.set(0, statusMessage);
                }
            }
            return statusCode;
        } else {
            return Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
    }

    /**
     * @return
     */
    static final TeacherDao teacherDao = new TeacherDaoImpl();

    public static TeacherDao getTeacherDao() {
        return teacherDao;
    }
}
