package DB버전게시판;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class noticeBoard_DB {//class s
	@SuppressWarnings("resource")
	public static void main(String[] args) {//main s
		Scanner scanner = new Scanner(System.in); //입력 객체
		
		Connection conn = null;
		
		try {//try s
			Class.forName("oracle.jdbc.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.219.113:1521/orcl",
					"java", //사용자
					"oracle" //비밀번호
			);
			System.out.println("연결 성공!");
			
			 while(true) {
				 /*게시물 [전체]목록 출력*/
				System.out.println("-------------------게시판 목록------------------");
	            System.out.printf("%-10s%-25s%-18s\n", "번호" , "제목", "작성자");
	            
	            /*board 테이블 가져오기*/
	            String sql = "" + "SELECT bno, btitle, bcontent, bwriter, bpassword" 
	        				+ " FROM boards" 
	        				+ " ORDER BY bno ASC"; //오름차순
	            
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery();
	            
	            while(rs.next()) { // 다음 데이터가 있으면
	            	Board board = new Board(); //board객체 생성
	            	board.setBno(rs.getInt("bno"));
	            	board.setTitle(rs.getString("btitle"));
	            	board.setContent(rs.getString("bcontent"));
	            	board.setWriter(rs.getString("bwriter"));
	            	board.setPassword(rs.getString("bpassword"));
	            	
	            	System.out.printf("%-9s%-25s%-18s\n",
	            			board.getBno(),
	            			board.getTitle(),
	            			board.getWriter());
	            }
	            
	            System.out.println("---------------------------------------------");
                System.out.println("1.게시물 입력 \t 2. 게시물 상세 조회"); //1. 게시물 입력 2. 게시물 상세 조회
                System.out.print("선택(번호) : "); int boardChoice = scanner.nextInt();
                
                if(boardChoice == 1) { //1.게시물 입력 선택시
                	//1. 입력받기
                	Board board = new Board(); //board 객체 생성
                	scanner.nextLine();
                	System.out.print("제목 : ");
                	board.setTitle(scanner.nextLine());
                	System.out.print("내용 : ");
                	board.setContent(scanner.nextLine());
                	System.out.print("작성자 : ");
                	board.setWriter(scanner.nextLine());
                	System.out.print("비밀번호 : ");
                	board.setPassword(scanner.nextLine());
                	
                	//매개변수화된 SQL 문 작성
                    sql = "" + "INSERT INTO boards (bno, btitle, bcontent, bwriter, bpassword)" + 
                    			"VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, ?)";
                    
                    //PreparedStatement 얻기 및 값 지정
                    pstmt = conn.prepareStatement(sql);
                    
                    pstmt.setString(1,  board.getTitle());
                    pstmt.setString(2,  board.getContent());
                    pstmt.setString(3,  board.getWriter());
                    pstmt.setString(4,  board.getPassword());
                	pstmt.executeUpdate();
                	
                }else if(boardChoice == 2) { //2. 게시물 상세 조회 선택시
                	 
                	 System.out.println("------------------게시판 상세 조회-----------------");
                	//해당 인덱스의 값을 상세 조회 => 뒤로가기, 수정, 삭제
                	 System.out.print("조회할 인덱스 : "); int boardIndex = scanner.nextInt(); 
                	 
                	 sql = "" +"SELECT bno, btitle, bcontent, bwriter, bpassword" 
                			  +" FROM boards" 
                			  +" WHERE bno = ?";
                	 
                	 pstmt = conn.prepareStatement(sql);
                	 pstmt.setInt(1, boardIndex); //첫번째 물음표
                	 rs = pstmt.executeQuery();
                	 
                	 if(rs.next()) { //데이터값이 있으면 => 해당 인덱스의 읽어올 값이 있으면
                		 Board board = new Board(); //board 객체 생성
                		 board.setBno(rs.getInt("bno"));
                		 board.setTitle(rs.getString("btitle"));
                		 board.setContent(rs.getString("bcontent"));
                		 board.setPassword(rs.getString("bpassword"));
                		 board.setWriter(rs.getString("bwriter"));
                		 
                		 System.out.println("---------------------------------------------");
                		 System.out.println("번호 : " + board.getBno());
                		 System.out.println("제목 : " + board.getTitle());
                		 System.out.println("내용 : " + board.getContent());
                		 System.out.println("작성자 : " + board.getWriter());
                		 System.out.println("---------------------------------------------");
                		 
                		 System.out.println("1. 게시물 조회[뒤로가기]  2. 게시물 수정  3. 게시물 삭제");
                         System.out.print("선택(번호) : "); int choice2 = scanner.nextInt();
                         
                         if(choice2 == 1) { //1. 게시물 조회[뒤로가기] 선택했을 경우
                        	 continue; 
                         }else if(choice2 == 2) { //2. 게시물 수정을 선택했을 경우
                        	 String[] changeNumber = new String[0]; //수정할 요소를 저장할 배열
                        	 scanner.nextLine();
                        	 System.out.print("비밀번호 : "); String inputPassword = (scanner.nextLine()).trim();
                        	 if(board.getPassword().equals(inputPassword)) { //비밀번호가 일치하면 수정하도록
                        		 
                        		 System.out.println("수정할 것 : 1.제목, 2.내용(둘다 선택가능/공백이나 반점으로 구분) ");
                                 String inputNumber = scanner.nextLine(); //수정할 번호 입력 받음
                                 
                                 if(inputNumber.length() > 1){ //1개 이상 선택했을 때
                                     if(inputNumber.contains(",")){
                                         changeNumber = inputNumber.split(","); //반점으로 구분했을 경우.

                                     } else if (inputNumber.contains(" ")) {
                                         changeNumber = inputNumber.split(" "); //공백으로 구분했을 경우
                                     }
                                     
                                     // 배열에 중복된 숫자를 제거
                                     LinkedHashSet<String> hashArray =
                                             new LinkedHashSet<>( Arrays.asList(changeNumber) );

                                     // 중복된 숫자를 제거하고 배열로 다시 전환
                                     String[] hashedChangeNumber =
                                             hashArray.toArray(new String[] {});
                                     for(int index = 0; index < hashedChangeNumber.length; index++){
                                    	 if(hashedChangeNumber[index].equals("1")){ //제목을 수정
                                    		 System.out.print("수정할 제목 : "); board.setTitle(scanner.nextLine());
                                    		 
                                    		 sql ="UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bpassword = ?" 
                                    				 + "WHERE bno = ?";
                                    		 
                                    		 pstmt = conn.prepareStatement(sql);
                                    		 pstmt.setString(1, board.getTitle());
                                    		 pstmt.setString(2, board.getContent());
                                    		 pstmt.setString(3, board.getWriter());
                                    		 pstmt.setString(4, board.getPassword());
                                    		 pstmt.setInt(5, board.getBno());
                                    		
                                    		 pstmt.executeUpdate();
                                    		 
                                    		 
                                    	 }
                                    	 
                                    	 if(hashedChangeNumber[index].equals("2")) {// 내용을 수정
                                    		 System.out.print("수정할 내용 : "); board.setContent(scanner.nextLine());
                                    		 
                                    		 sql ="UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bpassword = ?" 
                                    				 + "WHERE bno = ?";
                                    		 
                                    		 pstmt = conn.prepareStatement(sql);
                                    		 pstmt.setString(1, board.getTitle());
                                    		 pstmt.setString(2, board.getContent());
                                    		 pstmt.setString(3, board.getWriter());
                                    		 pstmt.setString(4, board.getPassword());
                                    		 pstmt.setInt(5, board.getBno());
                                    		
                                    		 pstmt.executeUpdate();
                                    	 }
                                     }
                                 }else { //한개만 선택했을 경우
                                	 if(inputNumber.equals("1")){//제목을 수정할 것이면
                                		 System.out.print("수정할 제목 : "); board.setTitle(scanner.nextLine());
                                		 
                                		 sql ="UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bpassword = ?" 
                                				 + "WHERE bno = ?";
                                		 
                                		 pstmt = conn.prepareStatement(sql);
                                		 pstmt.setString(1, board.getTitle());
                                		 pstmt.setString(2, board.getContent());
                                		 pstmt.setString(3, board.getWriter());
                                		 pstmt.setString(4, board.getPassword());
                                		 pstmt.setInt(5, board.getBno());
                                		
                                		 pstmt.executeUpdate();
                                	 }else if(inputNumber.equals("2")) { //내용을 수정할 것이면
                                		 System.out.print("수정할 내용 : "); board.setContent(scanner.nextLine());
                                		 
                                		 sql ="UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bpassword = ?" 
                                				 + "WHERE bno = ?";
                                		 
                                		 pstmt = conn.prepareStatement(sql);
                                		 pstmt.setString(1, board.getTitle());
                                		 pstmt.setString(2, board.getContent());
                                		 pstmt.setString(3, board.getWriter());
                                		 pstmt.setString(4, board.getPassword());
                                		 pstmt.setInt(5, board.getBno());
                                		
                                		 pstmt.executeUpdate();
                                	 }else { //그외
                                		 System.err.println("[안내]숫자는 1번과 2번 중에 선택해주세요.");
                                	 }//else e
                                 }//한개만 선택했을 경우 else e
                        	 }else { //비밀번호가 올바르지 않을경우
                        		 System.err.println("[안내] 비밀번호가 올바르지 않습니다.");
                        	 }
                         }else if(choice2 == 3){ //3. 게시물 삭제를 눌렀을 경우
                        	 scanner.nextLine();
                        	 
                        	 System.out.print("비밀번호 : "); String inputPassword = (scanner.nextLine()).trim();
                        	 if(board.getPassword().equals(inputPassword)) { //비밀번호가 일치하면 삭제하도록
                        		sql = "DELETE FROM boards WHERE bno=?";
                        		pstmt = conn.prepareStatement(sql);
                        		pstmt.setInt(1, boardIndex); //해당 조회한 인덱스의 값을 삭제해주면 됨.
                        		pstmt.executeUpdate();
                        	 }else { //비밀번호가 올바르지 않을경우
                        		 System.err.println("[안내] 비밀번호가 올바르지 않습니다.");
                        	 }
                         }else {//else 상세조회 부분 s
                        	 System.err.println("[안내] 1 ~ 3번중에 선택해주세요.");
                         } //else e
                	 }
                	 
                	 
                }else { //그외
                	System.err.println("[안내]1.게시물 입력  2. 게시물 상세 조회 중에 선택해주세요.");
                }

			 }
			
		}catch(ClassNotFoundException e) { //예외 처리
			
			e.printStackTrace();
			
		}catch(SQLException e) { //예외 처리
			
			e.printStackTrace();
			
		}finally {
			
			if(conn != null) {
		
			try { //연결 끊기
				conn.close(); //연결 끊기
				System.out.println("연결 끊기!");
				
			}catch(SQLException ex) {}
			
		  }
	   }
	}//main e

}//class e
