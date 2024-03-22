package Main;

import DAO.BankSQL;
import DTO.BankClient;

import java.util.Scanner;

public class BankUtility {

    // [2] 회원가입 메서드
    public void joinMember(Scanner sc, BankSQL sql) {
        BankClient client = new BankClient();
        System.out.print("아이디 >> ");
        client.settId(sc.next());
        System.out.print("비밀번호 >> ");
        client.settPw(sc.next());
        System.out.print("이름 >> ");
        client.settName(sc.next());
        System.out.print("연락처 >> ");
        client.settPhone(sc.next());
        System.out.print("주소 >> ");
        client.settAddr(sc.next());

        client.settNum(sql.clientNumber() + 1);

        sql.joinMember(client);
    }

    // [2.1]계좌 생성 유틸리티 메서드
    public void createAccount(BankSQL sql, Scanner sc, String loginId) {
        int accountCount = sql.cAccount(loginId);
        if (accountCount == 3) {
            System.out.println("3개를 초과한 계좌생성은 불가능합니다");
        } else {
            System.out.println("================계좌 종류================");
            System.out.println("[1]일반    [2]청약    [3]주식    [4]적금");
            System.out.println("========================================");
            System.out.print("종류선택 > ");
            int acType = sc.nextInt();

            String accNumber = sql.addAccount(loginId, acType);

            System.out.println("생성된 계좌번호: " + accNumber);
        }
    }

    // [2.2]입금 유틸리티 메서드
    public void deposit(BankSQL sql, Scanner sc) {
        System.out.print("입금할 계좌번호: ");
        String depositAccount = sc.next();
        System.out.print("입금할 금액: ");
        int amount = sc.nextInt();

        boolean depositResult = sql.deposit(depositAccount, amount);
        if (depositResult) {
            System.out.println(amount + "원이 " + depositAccount + " 계좌에 입금되었습니다.");
        } else {
            System.out.println("입금 실패. 유효하지 않은 계좌번호입니다.");
        }
    }

    // [2.3] 출금 유틸리티 메서드
    public void withdraw(BankSQL sql, Scanner sc) {
        System.out.print("출금할 계좌번호: ");
        String withdraw = sc.next();
        System.out.print("출금할 금액: ");
        int amount = sc.nextInt();

        boolean withdrawResult = sql.withdraw(withdraw, amount);
        if (withdrawResult) {
            System.out.println(amount + "원이 " + withdraw + " 계좌에 출금되었습니다.");
        } else {
            System.out.println("출금 실패. 유효하지 않은 계좌번호이거나 잔액이 부족합니다.");
        }
    }

    // [2.4] 송금 유틸리티 메서드
    public void transfer(BankSQL sql, Scanner sc) {
        System.out.print("출금 계좌 > ");
        String senderAccount = sc.next();
        boolean validSenderAccount = sql.checkAccount(senderAccount);
        if (!validSenderAccount) {
            System.out.println("유효하지 않은 출금 계좌입니다.");
            return;
        }

        System.out.print("입금 계좌 > ");
        String receiverAccount = sc.next();
        boolean validReceiverAccount = sql.checkAccount(receiverAccount);
        if (!validReceiverAccount) {
            System.out.println("유효하지 않은 입금 계좌입니다.");
            return;
        }

        if (senderAccount.equals(receiverAccount)) {
            System.out.println("동일한 계좌로의 송금은 진행할 수 없습니다.");
            return;
        }

        System.out.print("송금할 금액 > ");
        int transferAmount = sc.nextInt();

        boolean transferResult = sql.transfer(senderAccount, receiverAccount, transferAmount);
        if (transferResult) {
            System.out.println(transferAmount + "원이 " + senderAccount + "에서 " + receiverAccount + "로 송금되었습니다.");
        } else {
            System.out.println("송금 실패. 잔액이 부족하거나 유효하지 않은 계좌입니다.");
        }
    }

    // [2.5] 잔액 조회 유틸리티 메서드
    public void checkBalance(BankSQL sql, Scanner sc) {
        System.out.print("잔액 조회할 계좌번호 >> ");
        String accountNumber = sc.next();
        int balance = sql.checkBalance(accountNumber);
        if (balance >= 0) {
            System.out.println("계좌번호 " + accountNumber + "의 잔액은 " + balance + "원 입니다.");
        } else {
            System.out.println("잔액 조회 실패. 유효하지 않은 계좌번호입니다.");
        }
    }

    // [2.6] 로그아웃 유틸리티 메서드
    public void logout() {
        System.out.println("로그아웃 중...");
    }

    // [3]프로그램 종료 유틸리티 메서드
    public void closeProgram(BankSQL sql) {
        System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
        sql.conClose();
        System.exit(0); // 정상 종료
    }
}
