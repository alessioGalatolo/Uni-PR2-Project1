import java.util.Iterator;

public interface SecureDataContainer<E>{
    // Crea l’identità un nuovo utente della collezione
    void createUser(String Id, String passw) throws UserTakenException;
    // Restituisce il numero degli elementi di un utente presenti nella
    // collezione
    int getSize(String Owner, String passw) throws IdNotFoundException, NotAuthorizedException;
    // Inserisce il valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    boolean put(String Owner, String passw, E data) throws NotAuthorizedException, IdNotFoundException;
    // Ottiene una copia del valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    E get(String Owner, String passw, E data) throws NotAuthorizedException, IdNotFoundException;
    // Rimuove il dato nella collezione
    // se vengono rispettati i controlli di identità
    E remove(String Owner, String passw, E data);
    // Crea una copia del dato nella collezione
    // se vengono rispettati i controlli di identità
    void copy(String Owner, String passw, E data);
    // Condivide il dato nella collezione con un altro utente
    // se vengono rispettati i controlli di identità
    void share(String Owner, String passw, String Other, E data);
    // restituisce un iteratore (senza remove) che genera tutti i dati
    //dell’utente in ordine arbitrario
    // se vengono rispettati i controlli di identità
    Iterator<E> getIterator(String Owner, String passw);
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

class NotAuthorizedException extends Exception{
    NotAuthorizedException(){
        super();
    }

    NotAuthorizedException(String s){
        super(s);
    }
}