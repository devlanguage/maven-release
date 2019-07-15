
import net.sf.cglib.beans.BulkBean;

public class BulkBeanTest {

    public static void main(String[] args) {
        String getters[] = new String[] { "getName", "getTime", "getNumber" };
        String setters[] = new String[] { "setName", "setTime", "setNumber" };
        Class types[] = new Class[] { String.class, String.class, Integer.class };
        BulkBean bb = BulkBean.create(TargetBean.class, getters, setters, types);
        TargetBean tb = new TargetBean();
//        tb.setName("Test");
//        tb.setNumber(new Integer(20));
//        tb.setTime("2005-12-23");
        Object objs[] = bb.getPropertyValues(tb);
        for (int i = 0; i < objs.length; i++) {
            System.out.println(objs[i]);
        }
    }
}
