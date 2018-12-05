
import javafx.util.Pair;

import java.util.*;

public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //per semplicità e chiarezza di scrittura chiamiamo A = c.hashTable.values().toArray()
    //B = c.hashtable.keySet().toArray()

    //AF(c): S = {<A[i].id, A[i].passw, A[i].dataList> : i = 0..A.length}
    //      datas(i) = {A[i - 1].dataList.get(j) :
    //      j = 0..A[i - 1].dataList.size()} forAll i = 1..n


    //IR(c): c.hashTable != null, for all i,j = 0..B.length con i != j: B[i] != B[j]
    //       A.length == B.length

    private Hashtable<String, Pair<String, List<E>>> hashTable;


    public MySecureDataContainer(){
        hashTable = new Hashtable<>(16, 0.5f);
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        if(Id == null || passw == null)
            throw new NullPointerException();
        if(hashTable.putIfAbsent(Id, new Pair<>(passw, new ArrayList<>())) != null) //se assente aggiunge e restituisce null
            throw new UserTakenException();
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
        return new MyIterator<>(getUser(Owner, passw).getValue().iterator());
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



}