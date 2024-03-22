package DAO;

import DTO.BankClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankSQL {

    // SQL클래스에서 CRUD를 사용하기 위한 3가지 객체

    // DB에 접속하기 위한 연결(Connection) 객체 : con
    Connection con;

    // SQL 쿼리문 전달을 위한 (PreparedStatement) 객체 : pstmt
    PreparedStatement pstmt;

    // DB에서 조회(select)한 결과(ResultSet)를 담을 객체 : rs
    ResultSet rs;

    // [1] DB접속
    public void connect() {
        con = DBC.DBConnect();
    }

    // [2] 접속해제
    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // [3] 로그인 결과 확인 메소드
    public boolean login(String inId, String inPw) {
        boolean loginResult = false;

        try {
            String sql = "SELECT TNAME FROM CLIENT WHERE TID = ? AND TPW = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, inId);
            pstmt.setString(2, inPw);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 조회된 결과가 있으면 로그인 성공
                System.out.println("로그인 중..");
                System.out.println(rs.getString(1) + "님 환영합니다");
                loginResult = true;
            } else {
                // 조회된 결과가 없으면 로그인 실패
                System.out.println("로그인 실패..");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginResult;
    }

    // [4] 가입된 고객의 마지막 번호를 조회하는 메서드
    public int clientNumber() {
        int tNum = 0; // 고객 번호를 저장할 변수 초기화
        String sql = "SELECT MAX(TNUM) FROM CLIENT"; // 가장 큰 고객 번호를 조회하는 SQL 쿼리문 생성
        try {
            pstmt = con.prepareStatement(sql); // SQL 쿼리를 실행하기 위한 PreparedStatement 생성
            rs = pstmt.executeQuery(); // SQL 쿼리 실행 후 결과를 ResultSet에 저장
            if (rs.next()) {
                tNum = rs.getInt(1); // 결과에서 고객 번호를 가져와 변수에 저장
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // SQL 예외가 발생하면 런타임 예외로 처리하여 상위 호출자에게 전파
        }
        return tNum; // 조회된 가장 큰 고객 번호를 반환
    }

    // [5] 회원가입을 처리하는 메서드
    public void joinMember(BankClient client) {
        String sql = "INSERT INTO CLIENT VALUES (?,?,?,?,?,?)"; // 고객 정보를 삽입하는 SQL 쿼리문 생성
        try {
            int clientNum = clientNumber() + 1; // 새로운 고객 번호를 생성하기 위해 가장 큰 고객 번호에 1을 더함
            pstmt = con.prepareStatement(sql); // SQL 쿼리를 실행하기 위한 PreparedStatement 생성
            // PreparedStatement에 각 파라미터를 설정하여 SQL 쿼리 실행 준비
            pstmt.setInt(1, clientNum);
            pstmt.setString(2, client.gettId());
            pstmt.setString(3, client.gettPw());
            pstmt.setString(4, client.gettName());
            pstmt.setString(5, client.gettPhone());
            pstmt.setString(6, client.gettAddr());

            int result = pstmt.executeUpdate(); // SQL 쿼리 실행 및 삽입된 행의 수 반환
            if (result > 0) { // 삽입된 행의 수가 0보다 크면
                System.out.println("회원가입 성공"); // 회원가입 성공 메시지 출력
                System.out.println("ID : " + client.gettId()); // 가입한 고객의 ID 출력
            } else {
                System.out.println("회원가입 실패"); // 회원가입 실패 메시지 출력
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // SQL 예외가 발생하면 런타임 예외로 처리하여 상위 호출자에게 전파
        }
    }

    // [6] 계좌가 있는지 확인하는 메서드
    public boolean checkAccount(String tAccount) {
        // 계좌 유효성을 검사한 결과를 저장할 변수를 선언합니다.
        boolean checked = false;
        // SQL 문장을 준비합니다.
        String sql = "SELECT * FROM ACCOUNT WHERE TACCOUNT = ?";
        try {
            // PreparedStatement 객체를 생성합니다.
            pstmt = con.prepareStatement(sql);
            // SQL 문장의 매개변수를 설정합니다.
            pstmt.setString(1, tAccount);
            // SQL 문장을 실행하고 결과 집합을 가져옵니다.
            rs = pstmt.executeQuery();
            // 결과 집합에 데이터가 있는지 확인합니다.
            checked = rs.next();
        } catch (SQLException e) {
            // SQL 예외가 발생한 경우 런타임 예외로 변환하여 던집니다.
            throw new RuntimeException(e);
        }
        // 계좌 유효성 검사 결과를 반환합니다.
        return checked;
    }

    // [7] 계좌 생성 메소드
    public String addAccount(String loginId, int acType) {
        String newAccount = addAccountNumber();

        // 계좌번호를 생성하고 생성된 계좌번호를 ACCOUNT 테이블에 저장하는 SQL 문
        String sql = "INSERT INTO ACCOUNT (TACCOUNT, TID, CODENUM, BALANCE) VALUES (?, ?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newAccount);
            pstmt.setString(2, loginId);
            pstmt.setInt(3, acType);
            pstmt.setInt(4, 0); // 계좌를 생성할 때 초기 잔액을 0으로 설정했습니다.

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("계좌번호 생성 및 저장 완료: " + newAccount);
                return newAccount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // [8] 계좌번호 생성 메소드
    public String addAccountNumber() {
        String newAccount = "3333-";
        for (int i = 0; i < 4; i++) {
            int ac1 = (int) ((Math.random() * 9) + 1);
            newAccount += ac1;
        }
        newAccount += "-";

        for (int i = 0; i < 5; i++) {
            int ac2 = (int) ((Math.random() * 9) + 1);
            newAccount += ac2;
        }
        return newAccount;
    }

    // [9] 계좌 갯수 확인 메소드
    public int cAccount(String loginId) {
        String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE TID = ?";
        int accountCount = 0;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                accountCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return accountCount;
    }

    // [10] 입금 메소드
    public boolean deposit(String depositAccount, int amount) {
        boolean depositResult = false;

        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE + ? WHERE TACCOUNT = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, depositAccount);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("입금 완료: " + amount + "원");
                depositResult = true;
            } else {
                System.out.println("입금 실패. 유효하지 않은 계좌번호입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return depositResult;
    }

    // [11] 출금 메소드
    public boolean withdraw(String withdraw, int amount2) {
        boolean withdrawResult = false;

        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE TACCOUNT = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, amount2);
            pstmt.setString(2, withdraw);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("출금 완료: " + amount2 + "원");
                withdrawResult = true;
            } else {
                System.out.println("출금 실패. 유효하지 않은 계좌번호입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return withdrawResult;
    }

    // [12] 계좌번호를 이용한 잔액조회
    public int checkBalance(String accountNumber) {
        int balance = -1; // 초기값은 유효하지 않은 잔액을 나타내는 값으로 설정합니다.

        String sql = "SELECT BALANCE FROM ACCOUNT WHERE TACCOUNT = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                balance = rs.getInt(1); // 조회된 잔액을 가져옵니다.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }

    // [13] 송금 메소드
    public boolean transfer(String senderAccount, String receiverAccount, int amount) {
        // 보내는 계좌와 받는 계좌가 유효한지 확인
        boolean validSender = checkAccount(senderAccount);
        boolean validReceiver = checkAccount(receiverAccount);

        // 보내는 계좌와 받는 계좌가 유효한 경우에만 송금 처리
        if (validSender && validReceiver) {
            // 보내는 계좌의 잔액 확인
            int senderBalance = checkBalance(senderAccount);
            // 잔액이 충분한 경우에만 송금 처리
            if (senderBalance >= amount) {
                // 보내는 계좌에서 돈을 출금
                withdraw(senderAccount, amount);
                // 받는 계좌에 돈을 입금
                deposit(receiverAccount, amount);
                return true; // 송금 성공
            }
        }
        return false; // 송금 실패
    }

}


        /*
            Array(배열), List(목록) => 데이터를 저장하는데 사용되는 구조

            - Array : 크기가 고정되어 있다.
                    : 기본타입(정수형, 실수형, 문자형, 논리형)


            - List : 크기가 변한다.
                   : 참조형
        */