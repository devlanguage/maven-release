
import java.io.Serializable;

public class NbiBody implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2147220835522831326L;

    private Object nbiMessageData;

    private NbiFault nbiFault;

    // used to send provision exception information
    private String errMsg;

    public String getErrMsg() {

        return errMsg;
    }

    public void setErrMsg(String errMsg) {

        this.errMsg = errMsg;
    }

    public void setNbiFault(NbiFault nbiFault) {

        this.nbiFault = nbiFault;
    }

    public void setMessageData(Object messageData) {

        this.nbiMessageData = messageData;
    }

    public NbiFault getNbiFault() {

        return nbiFault;
    }

    public Object getMessageData() {

        return nbiMessageData;
    }

    @Override
    public String toString() {

        return new StringBuffer().append(this.getClass().getSimpleName()).append(":[Object=").append(
                this.nbiMessageData).append("]").toString();
    }
}
