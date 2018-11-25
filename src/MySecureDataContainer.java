import java.util.*;

public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //AF(c): S = {<c.container.values().get(i).id, c.container.values().get(i).passw, c.container.values().get(i).dataList> : i = 0..c.container.size()}
    //      datas(i) = {c.container.values().get(i - 1).dataList.get(j) : j = 0..c.container.values().get(i - 1).dataList.size()} forAll i = 1..n


    //implementato con un'unica hashtable di user, password e data

    private Hashtable<String, User> container;


    public MySecureDataContainer(){
        container = new Hashtable<>(11, 0.5f);
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        if(Id == null || passw == null)
            throw new NullPointerException("Cannot create user: Id or passw are null");
        if(container.containsKey(Id))
            throw new UserTakenException();
        else
            container.put(Id, new User(Id, passw, new ArrayList<>()));
    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return getUser(Owner, passw).dataList.size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(data == null)
            throw new NullPointerException();
        getUser(Owner, passw).dataList.add(data);
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(data == null)
            throw new NullPointerException();
        List<E> dataList = getUser(Owner, passw, data).dataList;
        return dataList.get(dataList.indexOf(data));
    }

    @Override
    public E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(data == null)
            throw new NullPointerException("Cannot remove data: Owner, passw or data are null");
        //attenzione bisogna cancellare il dato da tutta la collezione
        getUser(Owner, passw, data).dataList.remove(data);
        return data;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(data == null)
            throw new NullPointerException("Cannot copy data: Owner, passw or data are null");

        List<E> ownerDataList = getUser(Owner, passw,data).dataList;

        ownerDataList.add(ownerDataList.get(ownerDataList.indexOf(data)));
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(data == null)
            throw new NullPointerException("Cannot share data: Owner, passw or data are null");
        List<E> mainDataList = getUser(Owner, passw, data).dataList; //non ci servono i dati ma solo il controllo delle credenziali
        User otherUser = container.get(Other);
        if(otherUser == null)
            throw new IdNotFoundException("Other user not Found");
        otherUser.dataList.add(mainDataList.get(mainDataList.indexOf(data)));
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException {
        return getUser(Owner, passw).dataList.iterator();
    }


    private User getUser(String owner, String pass) throws UnauthorizedException, IdNotFoundException {
        if(owner == null || pass == null)
            throw new NullPointerException();
        User user = container.get(owner);
        if(user == null)
            throw new IdNotFoundException();
        if(user.id.equals(owner) && user.passw.equals(pass))
            return user;
        else
            throw new UnauthorizedException();
    }

    private User getUser(String owner, String pass, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(owner == null || pass == null)
            throw new NullPointerException();
        User user = container.get(owner);
        if(user == null)
            throw new IdNotFoundException();
        if(user.id.equals(owner) && user.passw.equals(pass)) //controlla le credenziali
            if(user.dataList.contains(data))    //controlla che ci siano i dati su cui operare
                return user;
            else
                throw new DataNotFoundException();
        else
            throw new UnauthorizedException();
    }

    @Override
    public String toString() {
        return container.toString();
    }

    private class User{
        private String id;
        private String passw;
        private List<E> dataList;

        User(String id, String pass, List<E> dataList){
            this.id = id;
            this.passw = pass;
            this.dataList = dataList;
        }

    }


}