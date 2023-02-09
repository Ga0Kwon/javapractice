package javaConsol_객체;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class userInsertExample {//class s
	public static void main(String[] args) {//main s
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.219.113:1521/orcl",
					"java", //사용자
					"oracle" //비밀번호
			);
			System.out.println("연결 성공!");
			 
			/*게시물 목록 출력*/
			System.out.println("-------------------게시판 목록------------------");
            System.out.printf("%-6d-12%s-8%s", "번호" , "제목", "작성자");
          
             
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally { //끝날때
			if(conn != null) {
				try { //연결 끊기
					conn.close();
					System.out.println("연결 끊기!");
				}catch(SQLException e) {}
			}
		}
	}//main e
}//class e
