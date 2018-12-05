import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        SecureDataContainer<String> mySecureDataContainer= new MySecureDataContainer<>();//prima implementazione
        SecureDataContainer<String> mySecondSecureDataContainer = new MySecondSecureDataContainer<>();//seconda implementazione

        if(notCompliant(mySecureDataContainer)) //se non  conforme lancia un errore
            throw new Error("Implementazione non conforme");
        if(notCompliant(mySecondSecureDataContainer))
            throw new Error("Implementazione non conforme");
        System.out.println("Successo!");
    }

    private static boolean notCompliant(SecureDataContainer<String> mySDC) {
        int unexpectedBehavior = 12; //Sono il numero di eccezioni che ci aspettiamo, se incontriamo un' eccezione diminuiamo il valore di 1


        //aggiungi 98 utenti
        for(int i = 0; i < 100; i++) {
            try {
                mySDC.createUser("User" + i % 98, i % 98 + "password");//2 eccezioni perchè uso operatore modulo 98
            } catch (Exception e) {
                unexpectedBehavior--;
            }
        }


        //prendiamo la dimensione dei dati(doverbbe essere 0)
        try {
            mySDC.getSize("User11", "11password"); //ok
            mySDC.getSize("User11", "1password");  //password mismatch
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        //copiamo un dato
        try {
            mySDC.copy("User11", "11password", ""); //data not found
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        //aggiungiamo dei dati a tutti gli utenti
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j <  2; j++) {
                try {
                    mySDC.put("User" + i, i + "password", "data" + i % (j + 1)); //Usiamo l'operazione di modulo in modo da avere dei duplicati
                } catch (Exception e) {
                    unexpectedBehavior--;   //Ho 4 eccezioni dovute a utente 98,99 non esistenti
                }
            }
        }


        //condividiamo un dato
        try {
            mySDC.share("User59", "59password", "User10", "data1");//ok
            mySDC.share("User59", "59password", "Userx", "data1"); //user non esitente
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        //rimuoviamo un dato condiviso
        try {
            mySDC.remove("User10", "10password", "data1");//ok
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        //vediamo se il dato rimosso in precedenza è stato eliminato anche dal condivisore
        try {
            mySDC.get("User59", "59password", "data1"); //ok
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        //prendiamo un iteratore e facciamo un paio di iterazioni e poi usiamo un'operazione non supportata
        try {
            Iterator<String> iterator = mySDC.getIterator("User12", "12password");
            iterator.next();    //ok
            iterator.next();    //ok
            iterator.remove();  //Unsupported operation
        } catch (UnsupportedOperationException e) {
            unexpectedBehavior--;
        }catch (Exception e){
            unexpectedBehavior++; //ci aspettiamo che non entri in questo catch
        }

        //rimuoviamo un dato non esistente
        try {
            mySDC.remove("User1", "1password", "Data non esistente");   //data non trovata
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        //creiamo un utente con un puntatore a null
        try {
            mySDC.createUser(null, "password"); //nullPointer
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        return unexpectedBehavior != 0;// se diverso da 0 non è conforme
    }
}
