
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-06-09T15:51:44.679+08:00
 * Generated source version: 2.7.10
 * 
 */
@WebServiceClient(name = "BookService", 
                  wsdlLocation = "http://localhost:9002/cxf/BookServiceSoapRpcLiteralImpl?wsdl",
                  targetNamespace = "http://service.cxf.third.org/Book/") 
public class BookService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.cxf.third.org/Book/", "BookService");
    public final static QName BookServicePortHttpPost = new QName("http://service.cxf.third.org/Book/", "BookServicePort_HttpPost");
    public final static QName BookServicePortSoapDocumentLiteral = new QName("http://service.cxf.third.org/Book/", "BookServicePort_SoapDocumentLiteral");
    public final static QName BookServicePortSoapRpcLiteral = new QName("http://service.cxf.third.org/Book/", "BookServicePort_SoapRpcLiteral");
    public final static QName BookServicePortHttpGet = new QName("http://service.cxf.third.org/Book/", "BookServicePort_HttpGet");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:9002/cxf/BookServiceSoapRpcLiteralImpl?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(BookService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:9002/cxf/BookServiceSoapRpcLiteralImpl?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public BookService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BookService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BookService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public BookService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public BookService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public BookService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_HttpPost")
    public IBookService getBookServicePortHttpPost() {
        return super.getPort(BookServicePortHttpPost, IBookService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_HttpPost")
    public IBookService getBookServicePortHttpPost(WebServiceFeature... features) {
        return super.getPort(BookServicePortHttpPost, IBookService.class, features);
    }
    /**
     *
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_SoapDocumentLiteral")
    public IBookService getBookServicePortSoapDocumentLiteral() {
        return super.getPort(BookServicePortSoapDocumentLiteral, IBookService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_SoapDocumentLiteral")
    public IBookService getBookServicePortSoapDocumentLiteral(WebServiceFeature... features) {
        return super.getPort(BookServicePortSoapDocumentLiteral, IBookService.class, features);
    }
    /**
     *
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_SoapRpcLiteral")
    public IBookService getBookServicePortSoapRpcLiteral() {
        return super.getPort(BookServicePortSoapRpcLiteral, IBookService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_SoapRpcLiteral")
    public IBookService getBookServicePortSoapRpcLiteral(WebServiceFeature... features) {
        return super.getPort(BookServicePortSoapRpcLiteral, IBookService.class, features);
    }
    /**
     *
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_HttpGet")
    public IBookService getBookServicePortHttpGet() {
        return super.getPort(BookServicePortHttpGet, IBookService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IBookService
     */
    @WebEndpoint(name = "BookServicePort_HttpGet")
    public IBookService getBookServicePortHttpGet(WebServiceFeature... features) {
        return super.getPort(BookServicePortHttpGet, IBookService.class, features);
    }

}