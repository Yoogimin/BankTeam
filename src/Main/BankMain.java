package Main;

import DAO.BankSQL;
import DTO.BankClient;
import java.util.Scanner;

public class BankMain {
    public static void main(String[] args) {

        // SQL(BankSQL) 객체 : sql
        BankSQL sql = new BankSQL(); // BankSQL 클래스의 객체 생성

        // client 객체 생성
        BankClient client = new BankClient(); // BankClient 클래스의 객체 생성

        // BankUtility 유틸리티를 사용하기 위한 객체 생성
        BankUtility util = new BankUtility();

        // 입력(Scanner)을 위한 객체 : sc
        Scanner sc = new Scanner(System.in); // Scanner 객체 생성

        // 프로그램 실행을 위한 변수 : run
        boolean run = true; // 프로그램 실행 여부를 나타내는 변수

        // 메뉴 선택을 위한 변수 : menu
        int menu = 0; // 사용자가 선택한 메뉴를 저장하는 변수


        System.out.println("=================================");
        System.out.println("    3조 은행에 오신 것을 환영합니다   ");
        System.out.println("=================================");

        // while문 돌리기 전에 DB접속 실행
        sql.connect(); // 데이터베이스에 접속

        while (run) {
            String loginId = null; // 로그인한 사용자의 ID를 저장하는 변수
            System.out.println("=================================");
            System.out.println("[1] 로그인 | [2] 회원가입 | [3]종료");
            System.out.println("=================================");
            System.out.print("번호를 선택해주세요 >> ");
            menu = sc.nextInt(); // 사용자가 선택한 메뉴 입력받기

            switch (menu) {
                case 1:
                    boolean run2 = false; // 로그인 성공 후 로그아웃할 때까지 반복할 변수
                    System.out.print("아이디 >> ");
                    String inId = sc.next();
                    System.out.print("비밀번호 >> ");
                    String inPw = sc.next();

                    run2 = sql.login(inId, inPw);

                    if (run2) { // 로그인 성공한 경우
                        loginId = inId; // 로그인한 사용자의 ID 저장
                    }

                    while (run2) { // 로그인 성공
                        System.out.println("================================");
                        System.out.println("[1]생성  |  [2]입급  |  [3]출금");
                        System.out.println("[4]송금  |  [5]조회  |  [6]로그아웃");
                        System.out.println("================================");

                        System.out.print("선택 >> ");
                        menu = sc.nextInt(); // 사용자가 선택한 메뉴 입력받기

                        switch (menu) {
                            case 1:
                                util.createAccount(sql, sc, loginId);
                                break;
                            case 2:
                                util.deposit(sql, sc); // 입금 유틸리티 메서드 호출
                                break;
                            case 3:
                                util.withdraw(sql, sc); // 출금 유틸리티 메서드 호출
                                break;
                            case 4:
                                util.transfer(sql, sc); // 송금 유틸리티 메서드 호출
                                break;
                            case 5:
                                util.checkBalance(sql, sc); // 잔액조회 유틸리티 메서드 호출
                                break;
                            case 6:
                                util.logout(); // 로그아웃 유틸리티 메서드 호출
                                run2 = false;
                                break;
                            default:
                                System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
                                break;
                        }
                    }
                    break;
                case 2:
                    util.joinMember(sc, sql); // 회원가입 유틸리티 메서드 호출
                    break;

                case 3:
                    util.closeProgram(sql); // 프로그램 종료 유틸리티 메서드 호출
                    break;

                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
                    break;
            }
        }
    }
}
