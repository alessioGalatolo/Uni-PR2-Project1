import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySecondSecureDataContainer<E> implements SecureDataContainer<E> {
    //implementato con una lista di user e pass e una lista con i relativi dati

    private List<String> users;
    private List<String> passws;
    private List<List<E>> datas;

    public MySecondSecureDataContainer(){
        users = new ArrayList<>();
        passws = new ArrayList<>();
        datas = new ArrayList<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        for(String user : users)
            if(user.equals(Id))
                throw new UserTakenException("The user already exists in the Container");
        users.add(Id);
        passws.add(passw);
        datas.add(new ArrayList<>());
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
