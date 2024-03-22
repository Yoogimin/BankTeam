package DTO;

public class BankClient {
    int tNUm;
    String tId;
    String tPw;
    String tName;
    String tPhone;
    String tAddr;

    public BankClient() {}

    public int gettNUm() {
        return tNUm;
    }

    public void settNUm(int tNUm) {
        this.tNUm = tNUm;
    }

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

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettAddr() {
        return tAddr;
    }

    public void settAddr(String tAddr) {
        this.tAddr = tAddr;
    }

    @Override
    public String toString() {
        return "BankClient{" +
                "tNUm=" + tNUm +
                ", tId='" + tId + '\'' +
                ", tPw='" + tPw + '\'' +
                ", tName='" + tName + '\'' +
                ", tPhone='" + tPhone + '\'' +
                ", tAddr='" + tAddr + '\'' +
                '}';
    }

    public void settNum(int i) {
    }
}
