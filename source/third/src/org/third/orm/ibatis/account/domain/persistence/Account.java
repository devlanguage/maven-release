
public class Account implements IPersistenceBase {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6121358319064811399L;

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;

    private String sortField;
    private boolean ascending = false;

    @Override
    public String toString() {

        return new StringBuilder(this.getClass().getSimpleName()).append(":[id=").append(this.id)
                .append(",name=").append(this.lastName).append(this.firstName).append("]")
                .toString();
    }

    /**
     * @return get method for the field ascending
     */
    public boolean isAscending() {

        return this.ascending;
    }

    /**
     * @param ascending
     *            the ascending to set
     */
    public void setAscending(boolean ascending) {

        this.ascending = ascending;
    }

    /**
     * @return get method for the field emailAddress
     */
    public String getEmailAddress() {

        return this.emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    /**
     * @return get method for the field firstName
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return get method for the field id
     */
    public int getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * @return get method for the field lastName
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    /**
     * @return get method for the field sortField
     */
    public String getSortField() {

        return this.sortField;
    }

    /**
     * @param sortField
     *            the sortField to set
     */
    public void setSortField(String sortField) {

        this.sortField = sortField;
    }
}
