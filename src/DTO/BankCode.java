package DTO;

public class BankCode {
    String tId;
    String tPw;
    String tName;

    public BankCode() {}

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettPw() {
        return tPw;
    }

    public void settPw(String tPw) {
        this.tPw = tPw;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    @Override
    public String toString() {
        return "BankCode{" +
                "tId='" + tId + '\'' +
                ", tPw='" + tPw + '\'' +
                ", tName='" + tName + '\'' +
                '}';
    }
}
