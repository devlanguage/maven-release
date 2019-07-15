
import java.security.Principal;

/**
 * 
 * @author Dao
 */
public class SimplePrincipal implements Principal {
	private String name;

	public SimplePrincipal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object ob) {
		System.out.println("SamplePrincipal");
		if (ob instanceof SimplePrincipal) {
			SimplePrincipal principal = (SimplePrincipal) ob;

			return this.name.equalsIgnoreCase(principal.getName());
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return "Principal_"+name+"/"+Integer.toHexString(hashCode());
	}
	public int hashCode() {
		return name.toUpperCase().hashCode();
	}
}
