import java.util.*;

public class MyThirdSecureDataContainer<E> implements SecureDataContainer<E>{

    private Hashtable<Credential, List<E>> table;

    private class Credential{
        private String id;
        private String pass;

        public Credential(String id, String pass){
            if(id == null || pass == null)
                throw new NullPointerException("id or pass are null");
            this.id = id;
            this.pass = pass;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            Credential c = (Credential) obj;
            return (c.id.equals(id) && c.pass.equals(pass));
        }
    }

    public MyThirdSecureDataContainer(){
        table = new Hashtable<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        if(Id == null || passw == null)
            throw new NullPointerException();
        table.put(new Credential(Id, passw), new ArrayList<>());

    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        return false;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        return null;
    }

    @Override
    public E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException {

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException {
        return null;
    }
}
