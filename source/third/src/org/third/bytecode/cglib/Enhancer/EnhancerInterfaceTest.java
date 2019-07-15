
import net.sf.cglib.proxy.Enhancer;

public class EnhancerInterfaceTest {

    public static void main(String[] args) {
        EchoCaller ec = new EchoCaller();
        Enhancer eh = new Enhancer();
        eh.setSuperclass(Teller.class);
        eh.setCallback(ec);
        Teller teller = (Teller) eh.create();
        teller.tell();
        teller.test();
    }
}
