import javafx.util.Pair;

import java.util.*;

public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //AF(c): S = {<c.hashTable.values().get(i).id, c.hashTable.values().get(i).passw,
    //       c.hashTable.values().get(i).dataList> : i = 0..c.hashTable.size()}
    //      datas(i) = {c.hashTable.values().get(i - 1).dataList.get(j) :
    //      j = 0..c.hashTable.values().get(i - 1).dataList.size()} forAll i = 1..n
    //IR(c): c.hashTable != null, for all i = 0..c.hashTable.size()

    //implementato con un'unica hashTable di user, password e data

    private Hashtable<String, Pair<String, List<E>>> hashTable;


    public MySecureDataContainer(){
        hashTable = new Hashtable<>(16, 0.5f);
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        hashTable.
        if(Id == null || passw == null)
            throw new NullPointerException("Cannot create user: Id or passw are null");
        if(hashTable.containsKey(Id))
            throw new UserTakenException();
        else
            hashTable.put(Id, new Pair<>(passw, new ArrayList<>()));
    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return getUser(Owner, passw).getValue().size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(data == null)
            throw new NullPointerException();
        getUser(Owner, passw).getValue().add(data);
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        List<E> dataList = getUser(Owner, passw, data).getValue();
        return dataList.get(dataList.indexOf(data));
    }

    @Override
    public E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        //attenzione bisogna cancellare il dato da tutta la collezione
        getUser(Owner, passw, data).getValue().remove(data);
        return data;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {

        List<E> ownerDataList = getUser(Owner, passw,data).getValue();

        ownerDataList.add(ownerDataList.get(ownerDataList.indexOf(data)));
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(Other == null)
            throw new NullPointerException();

        List<E> mainDataList = getUser(Owner, passw, data).getValue(); //non ci servono i dati ma solo il controllo delle credenziali

        Pair<String, List<E>> otherUser = hashTable.get(Other);
        if(otherUser == null)
            throw new IdNotFoundException("Other user not Found");
        otherUser.getValue().add(mainDataList.get(mainDataList.indexOf(data)));
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException {
        return getUser(Owner, passw).getValue().iterator();
    }


    private Pair<String, List<E>> getUser(String owner, String pass) throws UnauthorizedException, IdNotFoundException {
        if(owner == null || pass == null)
            throw new NullPointerException();
        Pair<String, List<E>> user = hashTable.get(owner);
        if(user == null)
            throw new IdNotFoundException();
        if(user.getKey().equals(pass))
            return user;
        else
            throw new UnauthorizedException();
    }

    private Pair<String, List<E>> getUser(String owner, String pass, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(owner == null || pass == null || data == null)
            throw new NullPointerException();
        Pair<String, List<E>> user = hashTable.get(owner);
        if(user == null)
            throw new IdNotFoundException();
        if(user.getKey().equals(pass)) //controlla le credenziali
            if(user.getValue().contains(data))    //controlla che ci siano i dati su cui operare
                return user;
            else
                throw new DataNotFoundException();
        else
            throw new UnauthorizedException();
    }

    @Override
    public String toString() {
        return hashTable.toString();
    }


}