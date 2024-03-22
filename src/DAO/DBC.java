package DAO;

import java.sql.*;

public class DBC {
    public static Connection DBConnect(){
        // DB에 접속정보 저장을 위한 Connection 객체 con선언
        Connection con = null;

        // DB에 접속할 계정정보
        String user = "GI";
        String password = "1111";

        // DB에 접속할 주조정보
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        //String url = "jdbc:oracle:thin:@192.168.0.79:1521:XE";

        try {
            // (1) 오라클 데이터베이스 드라이버(ojdbc8)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // (2) 오라클 데이터베이스 접속 정보
            con = DriverManager.getConnection(url,user, password);

            System.out.println("DB접속 성공");

        } catch (ClassNotFoundException e) {
            System.out.println("DB접속 실패 : 드라이버 로딩 실패");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("DB접속 실패: 접속 정보 오류");
            throw new RuntimeException(e);
        }

        /*
            try-catch-finally(예외처리)
            예외처리 : 프로그램 실행중에 발생할 수 있는 예외적인 상황을 관리하고 대응하기 위한 방법

            try{
                // 예외가 발생할 수 있는 코드

            } catch (ExceptionType1 e){
                // ExceptionType1을 처리하는 코드

            } catch (ExceptionType2 e){
                // ExceptionType2을 처리하는 코드

            } finally {
                // 예외 발생 여부에 상관없이 항상 실해되는 코드(선택적)

            }
         */

        return con;
    }

}
