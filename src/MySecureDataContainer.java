import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //implementato con un'unica hashtable di user, password e data

    private Hashtable<String, User> container;

    public MySecureDataContainer(){
        container = new Hashtable<>(11, 0.5f);
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        if(Id == null || passw == null)
            throw new NullPointerException("Cannot create user: Id or passw are null");
        if(container.containsKey(Id + passw))
            throw new UserTakenException();
        else
            container.put(Id + passw, new User(Id, passw, new ArrayList<>()));

    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, UnauthorizedException {
        if(Owner == null || passw == null)
            throw new NullPointerException("Cannot return size: Owner or passw are null");
        boolean found = false;
        int size = -1;
        for(User user : container)
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)){
                    found = true;
                    size = user.getData().size();
                    break;
                }else
                    throw new UnauthorizedException("Owner-Password mismatch");
        if(!found)
            throw new IdNotFoundException();
        return size;
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null || data == null)
            throw new NullPointerException("Cannot add data: Owner or passw or data are null");
        boolean found = false;
        for(User user : container)
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)){
                    found = true;
                    user.addData(data);
                    break;
                }else
                    throw new UnauthorizedException("Cannot add data: Owner-Password mismatch");
        if(!found)
            throw new IdNotFoundException();
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null)
            throw new NullPointerException("Cannot return data: Owner or passw are null");
            for(User user: container){
            if(user.getId().equals(Owner))
                if(user.samePassw(passw))
                    return user.getData().get(0);
                else
                    throw new UnauthorizedException("Cannot return data: Owner-Password mismatch");
        }
        throw new IdNotFoundException();
    }

    @Override
    public E remove(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null || data == null)
            throw new NullPointerException("Cannot remove data: Owner, passw or data are null");
        for(User user: container){
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)) {
                    user.getData().remove(data);
                    return data;
                }
                else
                    throw new UnauthorizedException("Owner-Password mismatch: cannot remove data");
        }
        throw new IdNotFoundException("Got to end of collection without finding the Owner string as id");
    }

    @Override
    public void copy(String Owner, String passw, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null || data == null)
            throw new NullPointerException("Cannot copy data: Owner, passw or data are null");
        for(User user: container){
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)) {
                    user.getData().add(data);
                    return;
                }
                else
                    throw new UnauthorizedException("Owner-Password mismatch: cannot remove data");
        }
        throw new IdNotFoundException("Got to end of collection without finding the Owner string as id");
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws UnauthorizedException, IdNotFoundException {
        if(Owner == null || passw == null || data == null)
            throw new NullPointerException("Cannot share data: Owner, passw or data are null");
        User tmp = null; //conterrà l'utente corrispondente a Other se lo troviamo prima di aver fatto il controllo sull'identità
        boolean check = false; //true se abbiamo già controllato l'identità di Owner
        for(User user: container){
            if(user.getId().equals(Other))
                if(check) { //rispetta i controlli di identità, copiamo data nei dati di Other
                    user.getData().add(data);
                    return;
                }
                else //non abbiamo ancora controllato l'identità, salviamo l'utente per aggiungere data in seguito
                    tmp = user;
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)) {
                    check = true; //abbiamo controllato l'identità
                    if(tmp != null){ //se abbiamo già trovato Other, aggiungiamo data
                        tmp.getData().add(data);
                        return;
                    }
                }
                else
                    throw new UnauthorizedException("Owner-Password mismatch: cannot share data");
        }
        throw new IdNotFoundException("Got to end of collection without finding Owner or Other");
    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws UnauthorizedException, IdNotFoundException {
        for(User user: container){
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)) {
                    return user.getData().iterator();
                }
                else
                    throw new UnauthorizedException("Owner-Password mismatch: cannot iterate data");
        }
        throw new IdNotFoundException("Got to end of collection without finding the Owner string as id");
    }


    private class User{
        private String id;
        private String passw;
        private List<E> data;

        User(String id, String passw, List<E> data){
            this.id = id;
            this.passw = passw;
            this.data = data;
        }


        String getId() {
            return id;
        }

        boolean samePassw(String passw) {
            return passw.equals(this.passw);
        }

        List<E> getData() {
            return data;
        }

        void addData(E newData) {
            data.add(newData);
        }
    }


}