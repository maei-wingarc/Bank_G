import java.util.*;

public class Main {

    public static void main(String... args) {
        Bank bank = new Bank();
        String idDoi = bank.openAccount("土井");
        String idSuzuki = bank.openAccount("鈴木");
        
        bank.deposit(idDoi, 100000);
        bank.deposit(idDoi, 10000);
        bank.withdraw(idDoi, 100);
        bank.deposit(idSuzuki, 100);
        bank.withdraw(idDoi, 9999);
        bank.deposit(idDoi, 1);
        bank.withdraw(idDoi, 50000);
        try {
            bank.deposit(idSuzuki, (long)1e8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bank.dumpAccountPassbook(idDoi);
        bank.dumpAccountPassbook(idSuzuki);

        // Scanner sc = new Scanner(System.in);
    }

}
