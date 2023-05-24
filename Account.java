import java.util.*;
import java.time.*;
import java.time.format.*;

/**
 * 口座を表すクラス
 */
public class Account {
    /**
     * 口座番号 (一般に7桁)
     * UNIQUEであることが期待されるが、このクラスではUNIQUEであることを保証しない
     * コンストラクタで初期化される
     * 
     * この変数は書き換え不可であるため、口座番号の変更を行う際は新しく口座を作成する必要がある。
     * */
    private final String id;

    /**
     * 口座名　(口座の名義人)
     * コンストラクタで初期化される
     */
    private String name;

    /**
     * 預金残高
     * 初期値は0である
     * 
     * 理論上、0以上 9,223,372,036,854,775,807以下 (約922京)
     */
    private long balance;

    /** balanceのロックに使用するオブジェクト */
    private final Object balance_lockobj;

    /** 通帳 */
    private final List<String> passbook = new ArrayList<>();
    
    /**
     * 口座オブジェクトを初期化します
     * 
     * @param id 口座番号 呼び出し元でUNIQUE性を保証してください
     * @param name 口座名
     * @return 作成した口座オブジェクト
     */
    public Account(String id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0;
        this.balance_lockobj = new Object();
        this.logToPassbook("OPEN");
    }

    private void logToPassbook(String op) { logToPassbook(op, 0); }
    private void logToPassbook(String op, long amount)
    {
        LocalDateTime dt = LocalDateTime.now();
        String logStr = String.format(
            "[%s] %s\tID:%s\tNAME:%s\tBALANCE:%10s%s",
            dt.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")),
            op,
            this.id,
            this.name,
            this.balance,
            0 < amount ? String.format("\tAMOUNT:%10d", amount) : ""
        );

        passbook.add(logStr);
    }

    /**
     * 口座からお金を引き出す
     * 
     * @param amount 引き出す金額
     * @return 操作後の残高
     * @throws IllegalArgumentException 引数が0以下である
     * @throws RuntimeException 残高不足
     */
    public long withdraw(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("マイナスの金額を引き出すことはできません");
        }

        synchronized (balance_lockobj) {
            if (this.balance < amount) {
                throw new RuntimeException("口座残高が足りません");
            }

            this.balance -= amount;

            this.logToPassbook("WITHDRAW", amount);
            return this.balance;
        }
    }
    
    /**
     * 口座にお金を入れる
     * 
     * @param amount 入金する金額
     * @return 操作後の残高
     * @throws IllegalArgumentException 引数が0以下である
     * @throws RuntimeException 残高が「銀行が定める最大額 ({@link Bank.MAX_BALANCE})」を超過してしまう
     */
    public long deposit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("マイナス金額を預け入れることはできません");
        }

        synchronized (balance_lockobj) {
            if (Bank.MAX_BALANCE < this.balance + amount) {
                throw new RuntimeException("預入限度額を超えて預け入れいれることはできません");
            }

            this.balance += amount;

            this.logToPassbook("DEPOSIT", amount);
            return this.balance;
        }
    }

    public void dump(){
        System.out.println(this.toString());
    }

    public void dumpPassbook(){
        System.out.println("--- BEGIN LOG ---");
        passbook.forEach(System.out::println);
        System.out.println("---  END LOG  ---");
    }

    /**
     * 口座番号を取得する
     * @return この口座の口座名
     * */
    public String getId() {
        return this.id;
    }
    
    /** 
     * 口座名 (口座の名義人) を取得する
     * @return この口座の名義人
     * */
    public String getName() {
        return this.name;
    }

    /**
     * 口座名 (口座の名義人) を変更する
     * @param name 新しい名義人
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 現在の口座残高を取得する
     * @return 現在の口座残高
     */
    public long getBalance() {
        return this.balance;
    }
    
    /**
     * この口座の文字列表現を取得する
     * @return この口座の文字列表現
     */
    @Override
    public String toString() {
        return "id: " + id + "\tname: " + name + "\tbalance: " + balance;
    }
}
