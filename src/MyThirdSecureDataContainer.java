import javafx.util.Pair;

import java.util.*;

public class MyThirdSecureDataContainer<E> implements SecureDataContainer<E>{
    //
    private Hashtable<String, Pair<String, List<E>>> table;

    public MyThirdSecureDataContainer(){
        table = new Hashtable<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        if(Id == null || passw == null)
            throw new NullPointerException();
        if(table.get(Id) == null)
            table.put(Id, new Pair<>(passw, new ArrayList<>()));
        else
            throw new UserTakenException();

    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw))
            return pair.getValue().size();
        else
            throw new UnauthorizedException();

    }

    @Override
    public boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw)) {
            pair.getValue().add(data);
            return true;
        }
        else
            throw new UnauthorizedException();
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw)){
            return pair.getValue().get(pair.getValue().indexOf(data));
        }
        else
            throw new UnauthorizedException();
    }

    @Override
    public E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw)){
            pair.getValue().remove(data);
            return data;
        }
        else
            throw new UnauthorizedException();
    }

    @Override
    public void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw))
            pair.getValue().add(data);
        else
            throw new UnauthorizedException();
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> ownerPair = table.get(Owner);
        Pair<String, List<E>> otherPair = table.get(Other);

        if(ownerPair == null || otherPair == null)
            throw new IdNotFoundException();

        if(ownerPair.getKey().equals(passw))
            otherPair.getValue().add(data);
        else
            throw new UnauthorizedException();
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException {
        Pair<String, List<E>> pair = table.get(Owner);
        if(pair == null)
            throw new IdNotFoundException();
        if(pair.getKey().equals(passw))
            return pair.getValue().iterator();
        else
            throw new UnauthorizedException();
    }


//    private class Credential{
//        private String id;
//        private String pass;
//
//        private Credential(String id, String pass){
//            if(id == null || pass == null)
//                throw new NullPointerException("id or pass are null");
//            this.id = id;
//            this.pass = pass;
//        }
//
//        @Override
//        public int hashCode() {
//            return id.hashCode();
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            Credential c = (Credential) obj;
//            return (c.id.equals(id) && c.pass.equals(pass));
//        }
//    }


}
