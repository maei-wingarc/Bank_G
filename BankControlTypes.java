enum BankControlType {
    EXIT_PROGRAM("銀行システムの終了", 0),
    OPEN_ACCOUNT("口座開設", 1),
    CHECK_BALANCE("残高照会", 2),
    DEPOSIT("お預入れ", 3),
    WITHDRAW("お引き出し", 4),
    DUMP_ACCOUNT("お客様情報の確認", 5),
    DUMP_ACCOUNT_PASSBOOK("通帳の確認", 6);

    private final String description;
    private final int id;

    private BankControlType(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + " : " + this.description;
    }
}
