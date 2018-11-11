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
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot get size: Owner is not in the collection");
        if(passws.get(index).equals(passw)){
            return datas.get(index).size();
        }else
            throw new UnauthorizedException();
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot put data: Owner is not in the collection");
        else if(passws.get(index).equals(passw)) {
            datas.get(index).add(data);
            return true;
        }
        else
            throw new UnauthorizedException("Cannot put data: user-pass mismatch");
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot get data: Owner is not in the collection");
        else if(passws.get(index).equals(passw)) {
            return datas.get(index).get(datas.get(index).indexOf(data));
        }
        else
            throw new UnauthorizedException("Cannot get data: user-pass mismatch");
    }

    @Override
    public E remove(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot remove data: Owner is not in the collection");
        else if(passws.get(index).equals(passw)) {
            datas.get(index).remove(data);
            return data;
        }
        else
            throw new UnauthorizedException("Cannot remove data: user-pass mismatch");    }

    @Override
    public void copy(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot copy data: Owner is not in the collection");
        else if(passws.get(index).equals(passw)) {
            datas.get(index).add(data);
        }
        else
            throw new UnauthorizedException("Cannot copy data: user-pass mismatch");
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null || data == null)
            throw new NullPointerException("Cannot share data: Owner, passw or data are null");

        int otherIndex = users.indexOf(Other);
        int ownerIndex = users.indexOf(Owner);

        if(otherIndex == -1)
            throw new IdNotFoundException("Cannot share data: Other is not in the collection");
        if(ownerIndex == -1)
            throw new IdNotFoundException("Cannot share data: Owner is not in the collection");
        else if(passws.get(ownerIndex).equals(passw)) { //rispetta i controlli di identità, aggiungiamo data a Other
            datas.get(otherIndex).add(data);
        }
        else
            throw new UnauthorizedException("Cannot share data: user-pass mismatch");
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        int index = users.indexOf(Owner);
        if(index == -1)
            throw new IdNotFoundException("Cannot get Iterator: Owner is not in the collection");
        else if(passws.get(index).equals(passw)) {
            return datas.get(index).iterator();
        }
        else
            throw new UnauthorizedException("Cannot get Iterator: user-pass mismatch");
    }
}
