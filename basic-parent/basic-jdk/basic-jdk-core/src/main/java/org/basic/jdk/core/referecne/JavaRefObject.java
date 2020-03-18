package org.basic.jdk.core.referecne;

/**
 * Created on Oct 31, 2014, 5:19:59 PM
 */
public class JavaRefObject {
    String value;

    JavaRefObject(String _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString() + "@value=" + value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " finalize()");
        super.finalize();
    }
}
