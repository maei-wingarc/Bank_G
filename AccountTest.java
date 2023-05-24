public class AccountTest {
    public void testName(){
        Account account = new Account("0000001", "Fujita Kazunori");

        assert(account.getName().equals("Fujita Kazunori"));
    }

    public void testSetName(){
        Account account = new Account("0000001", "Fujita Kazunori");
        account.setName("Fujita Kazuhiko");

        assert(account.getName().equals("Fujita Kazuhiko"));
    }

    public void testDeposit(){
        Account account = new Account("0000001", "Fujita Kazunori");
        try{
            account.deposit(10000);
        }catch(Exception e){
            throw e;
        }

        assert(account.getBalance() == 10000);
    }

    public void testWithdraw(){
        Account account = new Account("0000001", "Fujita Kazunori");
        try{
            account.deposit(10000);
            account.withdraw(5000);
        }catch(Exception e){
            throw e;
        }

        assert(account.getBalance() == 5000);
    }
}