
import java.io.Serializable;

public class NbiMessage implements Serializable {
    private static final long serialVersionUID = 1L;
   private NbiHeader header;
   private NbiBody body;
      
   public NbiMessage(NbiHeader nbiHeader, NbiBody nbiBody) {
       this.header = nbiHeader;
       this.body = nbiBody;
   }
   
   public NbiHeader getHeader() { return header; }
   
   public NbiBody getBody() { return body; }
   
   public String toString() {
       StringBuffer strBuf  = new StringBuffer();
       strBuf.append("\nNbiMessage ");
       strBuf.append("\nHeader : " + header);
       strBuf.append("\nBody " + body);
       return strBuf.toString();
    }
}