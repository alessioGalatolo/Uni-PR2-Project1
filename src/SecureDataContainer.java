import java.util.Iterator;

public interface SecureDataContainer<E>{
    //OverView: La classe rappresenta un insieme di insiemi di dati associati ad un utente

    //typical element: un insieme S di triple, S = {<owner(i), pass(i), datas(i)> : i = 1..n}
    // dove n è la cardinalità dell'insieme, owner è la stringa del nome, pass è la stringa della password, 
    // datas è un insieme di dati di tipo E  datas = {elem(j) : j = 1..m}


    void createUser(String Id, String passw) throws UserTakenException;
    //requires: Id != null, passw != passw, for all i = 1..n : owner i != Id
    //modifies: this
    //effects: aggiunge la triple <Id, passw, insieme vuoto> a this
    //throws: UserTakenException (checked) se esiste i = 1..n tale che owner i == Id
    //        NullPointerException (unchecked) se Id o passw == null


    int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException;
    //requires: Owner != null, passw != null, esiste i = 1..n tale che owner i = Owner e pass i = passw ???????????????????????????????
    //effects: restituisce cardinalità di data i, per un i tale che owner i == Owner, pass i == passw
    //throws: IdNotFoundException (checked) se non esiste i = 1..n tale che owner i == Owner
    //        UnauthorizedException (checked) se esiste i = 1..n tale che owner i == Owner e pass i != passw
    //        NullPointerException (unchecked) se Owner == null || passw == null


    boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: data(i)
    //effects: datas(i) = datas(i) U data  per i tale che owner(i) == Owner e pass(i) == passw, restituisce true
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner
    //        NullPointerException (unchecked) se Owner == null || passw == null || data == null


    E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: restituisce elem(j) presente in datas(i) per i tale che owner(i) == Owner e pass(i) == passw, per j = 1..m tale che elem(j) == data
    //         altrimenti resituisce null
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner
    //        NullPointerException (unchecked) se Owner == null || passw == null || data == null


    E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: datas(i) = datas(i) - data, per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner
    //        NullPointerException (unchecked) se Owner == null || passw == null || data == null


    void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: datas(i) = datas(i) U data, per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner
    //        NullPointerException (unchecked) se Owner == null || passw == null || data == null


    void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException, DataNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw, esiste j = 1..n tale che owner(j) == Other
    //modifies: datas(j) per j = 1..n tale che owner(j) == Other
    //effects: datas(j) = datas(j) U data, per j = 1..n tale che owner(j) == Other
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner, se for all j = 1..n : owner(j) != Other
    //        NullPointerException (unchecked) se Owner == null || passw == null || Other == null || data == null


    Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: restituisce l'iteratore (senza remove) di datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException (checked) se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException (checked) se for all i = 1..n : owner(i) != Owner
    //        NullPointerException (unchecked) se Owner == null || passw == null
    //        UnsupportedOperationException (unchecked) se viene chiamato Iterator.remove()
}

class UserTakenException extends Exception {
    public UserTakenException(){
        super();
    }

    public UserTakenException(String s){
        super(s);
    }
}


class IdNotFoundException extends Exception {
    public IdNotFoundException(){
        super();
    }

    public IdNotFoundException(String s){
        super(s);
    }
}

class UnauthorizedException extends Exception{
    public UnauthorizedException(){
        super();
    }

    public UnauthorizedException(String s){
        super(s);
    }
}

class DataNotFoundException extends Exception{
    public DataNotFoundException(){
        super();
    }

    public DataNotFoundException(String s){
        super(s);
    }
}

class ActionNotAllowedException extends Exception{
    public ActionNotAllowedException(){
        super();
    }

    public ActionNotAllowedException(String s){
        super(s);
    }
}

class MyIterator<E> implements Iterator<E>{
    private Iterator<E> iterator;

    public MyIterator(Iterator<E> iterator){
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

    @Override
    public void remove(){
        throw new UnsupportedOperationException();
    }
}