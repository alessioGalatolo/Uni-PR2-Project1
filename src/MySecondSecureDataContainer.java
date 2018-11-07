import java.util.Iterator;

public class MySecondSecureDataContainer implements SecureDataContainer {
    //implementato con una lista di user e pass e una lista con i relativi dati
    //

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {

    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, NotAuthorizedException {
        return 0;
    }

    @Override
    public boolean put(String Owner, String passw, Object data) {
        return false;
    }

    @Override
    public Object get(String Owner, String passw, Object data) {
        return null;
    }

    @Override
    public Object remove(String Owner, String passw, Object data) {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, Object data) {

    }

    @Override
    public void share(String Owner, String passw, String Other, Object data) {

    }

    @Override
    public Iterator getIterator(String Owner, String passw) {
        return null;
    }
}
