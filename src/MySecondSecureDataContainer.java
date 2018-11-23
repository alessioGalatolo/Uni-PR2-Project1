import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySecondSecureDataContainer<E> implements SecureDataContainer<E> {
    //AF(c): S = {<c.users.get(i), c.passws.get(i), c.datas.get(i)> : i = 0..c.users.size()}
    // datas(i) = {c.datas.get(i - 1).get(j) : j = 0..c.datas.size()} forAll i = 1..n

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
        if(Id == null || passw == null)
            throw new NullPointerException("Cannot create user: Id or passw are null");
        if(users.contains(Id))
            throw new UserTakenException("The user already exists in the Container");
        users.add(Id);
        passws.add(passw);
        datas.add(new ArrayList<>());
    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return datas.get(credentialIndex(Owner, passw)).size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        datas.get(credentialIndex(Owner, passw)).add(data);
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(data == null)
            throw new NullPointerException("Cannot get data: Owner, passw or data are null");

       int index = credentialIndex(Owner, passw);
       return datas.get(index).get(datas.get(index).indexOf(data));

    }

    @Override
    public E remove(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        if (data == null)
            throw new NullPointerException("Cannot remove data: Owner, passw or data are null");
        datas.get(credentialIndex(Owner, passw)).remove(data);
        return data;
    }


    @Override
    public void copy(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        if(data == null)
            throw new NullPointerException("Cannot copy data: Owner, passw or data are null");

        datas.get(credentialIndex(Owner, passw)).add(data);

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException {
        if(data == null)
            throw new NullPointerException("Cannot share data: Owner, passw or data are null");

        int otherIndex = users.indexOf(Other);
        credentialIndex(Owner, passw);

        if(otherIndex == -1)
            throw new IdNotFoundException("Cannot share data: Other is not in the collection");

        datas.get(otherIndex).add(data);
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return datas.get(credentialIndex(Owner, passw)).iterator();
    }

    private int credentialIndex(String owner, String pass) throws IdNotFoundException, UnauthorizedException {
        if(owner == null || pass == null)
            throw new NullPointerException("Cannot get size: Owner or passw are null");

        int index = users.indexOf(owner);

        if(index == -1)
            throw new IdNotFoundException("Owner is not in the collection");
        if(passws.get(index).equals(pass)) {
            return index;
        }else
            throw new UnauthorizedException("Owner-password mismatch");
    }
}
