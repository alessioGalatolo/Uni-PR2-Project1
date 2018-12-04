import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        SecureDataContainer<String> mySecureDataContainer= new MySecureDataContainer<>();
        SecureDataContainer<String> mySecondSecureDataContainer = new MySecondSecureDataContainer<>();

        if(!test(mySecureDataContainer))
            throw new Error();
        if(!test(mySecondSecureDataContainer))
            throw new Error();
    }

    private static boolean test(SecureDataContainer<String> mySDC) {
        int unexpectedBehavior = 9; //Sono il numero di eccezioni che ci aspettiamo, se incontriamo un' eccezione diminuiamo il valore di 1

        for(int i = 0; i < 100; i++) {
            try {
                mySDC.createUser("User" + i % 98, i % 98 + "password");//2 eccezioni perchè uso operatore modulo 98
            } catch (Exception e) {
                unexpectedBehavior--;
            }
        }

        try {
            mySDC.getSize("User11", "11password"); //ok
            mySDC.getSize("User11", "1password");  //password mismatch
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        try {
            mySDC.copy("User11", "11password", ""); //data not found
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j <  2; j++) {
                try {
                    mySDC.put("User" + i, i + "password", "data" + i % (j + 1)); //Usiamo l'operazione di modulo in modo da avere dei duplicati
                } catch (Exception e) {
                    unexpectedBehavior--;   //Ho 4 eccezioni dovute a utente 98,99 non esistenti
                }
            }
        }

        try {
            mySDC.share("User59", "59password", "User10", "data1");//ok
            mySDC.share("User59", "59password", "Userx", "data1"); //user non esitente
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        try {
            mySDC.get("User10", "10password", "data1");//ok
        } catch (Exception e) {
            unexpectedBehavior--;
        }

        try {
            mySDC.getIterator("User12", "12password").remove();
        } catch (Exception e) {
            unexpectedBehavior--;
        }


        return unexpectedBehavior == 0;
    }
}
