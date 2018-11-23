import java.util.Iterator;

public interface SecureDataContainer<E>{
    //typical element: un insieme S di triple S = {<owner(i), pass(i), datas(i)> : i = 1..n} 
    // dove n è la cardinalità dell'insieme, owner è la stringa del nome, pass è la stringa della password, 
    // datas è un insieme di dati di tipo E  datas = {elem(j) : j = 1..m}


    // Crea l’identità un nuovo utente della collezione
    void createUser(String Id, String passw) throws UserTakenException;
    //requires: Id != null, passw != passw, for all i = 1..n : owner i != Id
    //modifies: this
    //effects: aggiunge la triple <Id, passw, insieme vuoto> a this
    //throws: UserTakenException se esiste i = 1..n tale che owner i == Id
    //        NullPointerException se Id o passw == null


    // Restituisce il numero degli elementi di un utente presenti nella
    // collezione
    int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException;
    //requires: Owner != null, passw != null, esiste i = 1..n tale che owner i = Owner e pass i = passw ???????????????????????????????
    //effects: restituisce cardinalità di data i, per un i tale che owner i == Owner, pass i == passw
    //throws: IdNotFoundException se non esiste i = 1..n tale che owner i == Owner
    //        UnauthorizedException se esiste i = 1..n tale che owner i == Owner e pass i != passw
    //        NullPointerException se Owner == null || passw == null


    // Inserisce il valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: data(i)
    //effects: datas(i) = datas(i) U data  per i tale che owner(i) == Owner e pass(i) == passw, restituisce true
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner
    //        NullPointerException se Owner == null || passw == null || data == null


    // Ottiene una copia del valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: restituisce elem(j) presente in datas(i) per i tale che owner(i) == Owner e pass(i) == passw, per j = 1..m tale che elem(j) == data
    //         altrimenti resituisce null
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner
    //        NullPointerException se Owner == null || passw == null || data == null

    // Rimuove il dato nella collezione
    // se vengono rispettati i controlli di identità
    E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: datas(i) = datas(i) - data, per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner
    //        NullPointerException se Owner == null || passw == null || data == null


    // Crea una copia del dato nella collezione
    // se vengono rispettati i controlli di identità
    void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //modifies: datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: datas(i) = datas(i) U data, per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner
    //        NullPointerException se Owner == null || passw == null || data == null


    // Condivide il dato nella collezione con un altro utente
    // se vengono rispettati i controlli di identità
    void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, data != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw, esiste j = 1..n tale che owner(j) == Other
    //modifies: datas(j) per j = 1..n tale che owner(j) == Other
    //effects: datas(j) = datas(j) U data, per j = 1..n tale che owner(j) == Other
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner, se for all j = 1..n : owner(j) != Other
    //        NullPointerException se Owner == null || passw == null || Other == null || data == null



    // restituisce un iteratore (senza remove) che genera tutti i dati
    //dell’utente in ordine arbitrario
    // se vengono rispettati i controlli di identità
    Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException;
    //requires: Owner != null, passw != null, esiste i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //effects: restituisce l'iteratore di datas(i) per i = 1..n tale che owner(i) == Owner e pass(i) == passw
    //throws: UnauthorizedException se esiste i = 1..n tale che owner(i) == Owner e pass(i) != passw
    //        IdNotFoundException se for all i = 1..n : owner(i) != Owner
    //        NullPointerException se Owner == null || passw == null


    // … altre operazione da definire a scelta
}

class UserTakenException extends Exception {
    UserTakenException(){
        super();
    }

    UserTakenException(String s){
        super(s);
    }
}


class IdNotFoundException extends Exception {
    IdNotFoundException(){
        super();
    }

    IdNotFoundException(String s){
        super(s);
    }
}

class UnauthorizedException extends Exception{
    UnauthorizedException(){
        super();
    }

    UnauthorizedException(String s){
        super(s);
    }
}