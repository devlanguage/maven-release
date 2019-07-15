
public class PersonManager {

    public void init() {

    }

    public void destory() {

    }

    private static PersonManager instance = null;

    public synchronized final static PersonManager getInstance() {

        if (instance == null) {
            instance = new PersonManager();

        }
        return instance;
    }

    public final Person createPerson() {

        return new Person();
    }

    public final Person createPerson(int id, String userName) {

        return new Person(id, userName);
    }

}
