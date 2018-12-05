import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySecondSecureDataContainer<E> implements SecureDataContainer<E> {
    //AF(c): S = {<c.users.get(i), c.passws.get(i), c.datas.get(i)> : i = 0..c.users.size()}
    // datas(i) = {c.datas.get(i - 1).get(j) : j = 0..c.datas.size()} forAll i = 1..n

    //IR(c): c.users.size() == c.passws.size() == c.datas.size(), c.users != null, c.passws != null,
    //       c.datas != null, for all i = 0..c.datas.size() : c.datas.get(i) != null,
    //       for all i, j  = 0..(c.users.size() - 1) with i != j: c.users.get(i) != c.users.get(j)
    //


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
        if(data == null)
            throw new NullPointerException();

        datas.get(credentialIndex(Owner, passw)).add(data);
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
       int index = credentialIndex(Owner, passw, data);

       return datas.get(index).get(datas.get(index).indexOf(data));
    }

    @Override
    public E remove(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException, DataNotFoundException {
        datas.get(credentialIndex(Owner, passw, data)).remove(data);
        return data;
    }


    @Override
    public void copy(String Owner, String passw, E data) throws IdNotFoundException, UnauthorizedException, DataNotFoundException {
        datas.get(credentialIndex(Owner, passw, data)).add(data);
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException {
        if(Other == null)
            throw new NullPointerException();

        int otherIndex = users.indexOf(Other);
        credentialIndex(Owner, passw, data);

        if(otherIndex == -1)
            throw new IdNotFoundException("Cannot share data: Other is not in the collection");
        datas.get(otherIndex).add(data);
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return new MyIterator<>(datas.get(credentialIndex(Owner, passw)).iterator());
    }


    //Un metodo privato che fa i null checks, il controllo delle credenziali(lanciando eventuali eccezioni) e dell'esistenza del nome utente.
    //restituisce l'indice del rispettivo utente
    private int credentialIndex(String owner, String pass) throws IdNotFoundException, UnauthorizedException {
        if(owner == null || pass == null)
            throw new NullPointerException("Owner or passw are null");

        int index = users.indexOf(owner);

        if(index == -1)
            throw new IdNotFoundException("Owner is not in the collection");
        if(passws.get(index).equals(pass)) {
            return index;
        }else
            throw new UnauthorizedException("Owner-password mismatch");
    }


    //stesso metodo rispetto a sopra ma che in più prende data per fare il null check e controllare che sia presente nella collezione dell'utente
    private int credentialIndex(String owner, String pass, E data) throws IdNotFoundException, UnauthorizedException, DataNotFoundException {
        if(owner == null || pass == null || data == null)
            throw new NullPointerException("Owner, passw or data are null");

        int index = users.indexOf(owner);

        if(index == -1)
            throw new IdNotFoundException("Owner is not in the collection");
        if(passws.get(index).equals(pass)) {
            if(datas.get(index).contains(data))
                return index;
            else
                throw new DataNotFoundException("Data not found in the user list");
        }else
            throw new UnauthorizedException("Owner-password mismatch");
    }
}
