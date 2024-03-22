package DTO;

public class BankAccount {
    String tAccount;
    String tId;
    int tNUm;
    int tBalance;

    public BankAccount() {
    }

    public void settNum(int acType) {
    }

    public String gettAccount() {
        return tAccount;
    }

    public void settAccount(String tAccount) { this.tAccount = tAccount; }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public int gettNUm() {
        return tNUm;
    }

    public void settNUm(int tNUm) {
        this.tNUm = tNUm;
    }

    public int gettBalance() {
        return tBalance;
    }

    public void settBalance(int tBalance) {
        this.tBalance = tBalance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "tAccount='" + tAccount + '\'' +
                ", tId='" + tId + '\'' +
                ", tNUm=" + tNUm +
                ", tBalance=" + tBalance +
                '}';
    }
}
