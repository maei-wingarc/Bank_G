
import java.util.*;

/**
 * 銀行 (正確には「銀行の支店」)を表すクラス
 */
public class Bank {
    /** 各口座の残高の最大値を表す */
    public static final long MAX_BALANCE = 1_000_000_000;
    /** 口座番号の最大値を表す */
    public static final int MAX_ID = 9_999_999;
    
    /** この銀行(支店)に所属する口座の一覧 */
    private final Map<String, Account> accounts = new HashMap<>();

    /**
     * 次に発行する口座番号
     * 
     * 口座番号は作成順に連番で発行される
     */
    private int nextAccountId = 0;
    

    public Bank() {}

    /**
     * 口座を新規に開設する
     * 
     * @param name 口座名(名義人)
     * @return 作成した口座の口座番号
     * @throws IllegalArgumentException 引数が不正です (null or Empty)
     * @throws RuntimeException この銀行の口座数が最大値制限に達した
     */
    public String openAccount(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("口座名がnullです");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("口座名を入力してください");
        }
        if (this.nextAccountId > Bank.MAX_ID) {
            throw new RuntimeException("作成口座数が上限に達しています");
        }
        
        String id = String.format("%07d", this.nextAccountId++);
        Account account = new Account(id, name);
        accounts.put(id, account);

        return id;
    }

    /**
     * 指定の番号を持つ口座に預金を行う
     * 
     * @param id 口座番号
     * @param amount 預入金額
     * @return 操作後の口座残高
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     * @throws IllegalArgumentException 預入金額が0以下である
     * @throws RuntimeException 残高が「銀行が定める最大額 ({@link Bank.MAX_BALANCE})」を超過してしまう
     */
    public long deposit(String id, long amount) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("存在しない口座番号です");
        }
        return accounts.get(id).deposit(amount);
    }
    
    /**
     * 指定の番号を持つ口座から指定の金額を引き出す
     * 
     * @param id 口座番号
     * @param amount 引き出す金額
     * @return 操作後の口座残高
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     * @throws IllegalArgumentException 引き出す金額が0以下である
     * @throws RuntimeException 残高不足
     */
    public long withdraw(String id, long amount) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("存在しない口座番号です");
        }
        return accounts.get(id).withdraw(amount);
    }

    /**
     * 指定の番号を持つ口座の現在の口座残高を確認する
     * 
     * @param id 口座番号
     * @return 指定の口座の残高
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     */
    public long checkBalance(String id) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("存在しない口座番号です");
        }
        return accounts.get(id).getBalance();
    }

    /**
     * 指定の番号を持つ口座のインスタンスを取得する
     * 
     * @param id 口座番号
     * @return 指定の口座のインスタンス
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     */
    private Account searchAccount(String id)
    {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("存在しない口座番号です");
        }
        return accounts.get(id);
    }

    /**
     * 指定の番号を持つ口座の情報を標準出力に出力する
     * 
     * @param id 口座番号
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     */
    public void dumpAccount(String id){
        searchAccount(id).dump();
    }

    /**
     * 指定の番号を持つ口座の操作記録を標準出力に出力する
     * 
     * @param id 口座番号
     * @throws IllegalArgumentException 指定の口座番号がこの銀行(支店)に存在しない
     */
    public void dumpAccountPassbook(String id){
        searchAccount(id).dumpPassbook();
    }
}