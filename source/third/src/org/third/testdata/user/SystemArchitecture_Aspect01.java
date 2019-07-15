
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture_Aspect01 {

    /**
     * A join point is in the web layer if the method is defined in a type in
     * the org.third.testdata.user.web package or any sub-package under that.
     */
    @Pointcut("within(org.third.testdata.user.web..*)")
    @AdviceName(value = "tx-advice")
    public void inWebLayer() {
    }

    /**
     * A join point is in the service layer if the method is defined in a type
     * in the org.third.testdata.user.service package or any sub-package under that.
     */
    @Pointcut("within(org.third.testdata.user.service..*)")
    public void inServiceLayer() {
        System.out.println("SystemArchitecture.inServiceLayer is called");
    }

    /**
     * A join point is in the data access layer if the method is defined in a
     * type in the org.third.testdata.user.dao package or any sub-package under
     * that.
     */
    @Pointcut("within(org.third.testdata.user.dao..*)")
    public void inDataAccessLayer() {
    }

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     * 
     * If you group service interfaces by functional area (for example, in
     * packages org.third.testdata.user.abc.service and com.xyz.def.service) then
     * the pointcut expression "execution(*
     * org.third.testdata.user..service.*.*(..))" could be used instead.
     */
    @Pointcut("execution(* org.third.testdata.user.service.*.*(..))")
    public void businessService() {
    }

    /**
     * A data access operation is the execution of any method defined on a dao
     * interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* org.third.testdata.user.dao.*.create*(..))")
    public void dataAccessOperation() {
    }

}