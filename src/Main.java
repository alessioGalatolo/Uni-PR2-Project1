import java.util.Iterator;

public class Main {

    private static final int N = 100;
    private static final int MOD = N / 100 * 98;

    public static void main(String[] args) {
        SecureDataContainer<String> mySecureDataContainer= new MySecureDataContainer<>();
        SecureDataContainer<String> mySecondSecureDataContainer = new MySecondSecureDataContainer<>();

        if(!test(mySecureDataContainer))
            throw new Error();
//        if(!test(mySecondSecureDataContainer))
//            throw new Error();
    }

    private static boolean test(SecureDataContainer<String> mySDC) {
        for(int i = 0; i < N; i++) {
            try {
                mySDC.createUser("User" + i % MOD, i % MOD + "password");
            } catch (UserTakenException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.printf("Data size of user 11: %d\n", mySDC.getSize("User11", "11password"));
            System.out.printf("Data size of user 11: %d\n", mySDC.getSize("User11", "1password"));
            System.out.printf("Data size of user 11: %d\n", mySDC.getSize("User", "35password"));
        } catch (IdNotFoundException | UnauthorizedException e) {
            e.printStackTrace();
        }

        try {
            mySDC.copy("User11", "11password", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j <  2; j++) {
                try {
                    mySDC.put("User" + i, i + "password", "data" + i % (j + 1)); //Usiamo l'operazione di modulo in modo da avere dei duplicati
                } catch (UnauthorizedException | IdNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            mySDC.share("User59", "59password", "Userx", "data1"); //user non esitente
            mySDC.share("User59", "59password", "User10", "data1");//doverebbe condividere i dati
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
