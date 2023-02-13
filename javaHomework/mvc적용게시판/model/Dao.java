package mvc적용게시판.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB버전게시판.Board;

public class Dao {
	private Connection conn ;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/*연결*/
	public Dao() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.219.113:1521/orcl",
					"java", //사용자
					"oracle" //비밀번호
			);
			System.out.println("연결 성공!");
			
		}catch(ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("연결 실패!");
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			System.out.println("연결 실패!");
		}
	}
	
	/*전체 글 조회*/
	public ArrayList<Board> print(){
		ArrayList<Board> boardList = new ArrayList<>();
		/*board 테이블 가져오기*/
		/*board 테이블 가져오기*/
        String sql = "" + "SELECT bno, btitle, bcontent, bwriter, bpassword" 
    				+ " FROM boards" 
    				+ " ORDER BY bno ASC"; //오름차순
        try {
        	pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            

        	while(rs.next()) { // 다음 데이터가 있으면
            	Board board = new Board(); //board객체 생성
            	board.setBno(rs.getInt("bno"));
            	board.setTitle(rs.getString("btitle"));
            	board.setContent(rs.getString("bcontent"));
            	board.setWriter(rs.getString("bwriter"));
            	board.setPassword(rs.getString("bpassword"));
            	boardList.add(board);
        	}
        	
        	return boardList;
        	
		}catch(Exception e) {
			return null;
		}
	}
	
	/*게시글 쓰기*/
	public Boolean write(Board board) {
		try {
			//매개변수화된 SQL 문 작성
	       String sql = "" + "INSERT INTO boards (bno, btitle, bcontent, bwriter, bpassword)" + 
	        			"VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, ?)";
	        
	       //PreparedStatement 얻기 및 값 지정
           pstmt = conn.prepareStatement(sql);
           
           pstmt.setString(1,  board.getTitle());
           pstmt.setString(2,  board.getContent());
           pstmt.setString(3,  board.getWriter());
           pstmt.setString(4,  board.getPassword());
           pstmt.executeUpdate();
           return true; //성공시
       	
		}catch(Exception e){
			return false; 
		}
	}
	
	/*게시글 상세 조회*/
	public Board selectView(int index) {
		try {
			String sql = "" +"SELECT bno, btitle, bcontent, bwriter, bpassword" 
		   			  +" FROM boards" 
		   			  +" WHERE bno = ?";
			
			 pstmt = conn.prepareStatement(sql);
        	 pstmt.setInt(1, index); //첫번째 물음표
        	 rs = pstmt.executeQuery();
        	 
        	 if(rs.next()) { //데이터값이 있으면 => 해당 인덱스의 읽어올 값이 있으면
        		 Board board = new Board(); //board 객체 생성
        		 board.setBno(rs.getInt("bno"));
        		 board.setTitle(rs.getString("btitle"));
        		 board.setContent(rs.getString("bcontent"));
        		 board.setPassword(rs.getString("bpassword"));
        		 board.setWriter(rs.getString("bwriter"));
        		 return board; //데이터값이 있다면 전부 넣고, board를 반환
        	 }
        	 
			} catch (Exception e) {//에러를 발생할 때도
				/* return null; => 밑에 return을 해줬기 때문에 의미없는 코드*/
			} 
			return null;//여기서 이게 실행된다는 것은, 차피 앞에서 데이터값을 찾지 못했다는 것
	}	
	
	/*게시글 수정 => 수정할 때 제목 따로, 내용따로 선택해서 그것만 수정받는 것이 아니라 
	  그냥, 다 같이 수정하도록하게 바꿈.*/
	public Boolean changeUpdate(int index) {
		try {
			String sql ="UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bpassword = ?" 
					 + "WHERE bno = ?";
			
			 pstmt = conn.prepareStatement(sql);
			 
//			 pstmt.setString(1, board.getTitle());
//    		 pstmt.setString(2, board.getContent());
//    		 pstmt.setString(3, board.getWriter());
//    		 pstmt.setString(4, board.getPassword());
//    		 pstmt.setInt(5, board.getBno());
    		 
    		 pstmt.executeUpdate();
		}catch(Exception e) {
			return false;
		}
		
	}
	/*게시글 삭제*/
	public Boolean delete(int index) {
		try {
			String sql = "DELETE FROM boards WHERE bno=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, index); //해당 조회한 인덱스의 값을 삭제해주면 됨.
    		pstmt.executeUpdate();
    		return true;
    		
		}catch(Exception e) {
			return false;
		}
	}
	
}
