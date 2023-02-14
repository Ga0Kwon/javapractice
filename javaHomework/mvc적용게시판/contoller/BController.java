package mvc적용게시판.contoller;

import mvc적용게시판.model.Board;
import mvc적용게시판.model.Dao;

public class BController {
	//* 싱글톤
	private static BController bc = new BController();
	private BController() {};
	public static BController getInstance() {
		return bc;
	}
	
	
	/*게시글 쓰기*/
	public boolean write(String title, String content, String writer, String password) {
		Board board = new Board(title, content, writer, password);
		
		boolean result = Dao.getInstance().write(board);
		
		return result;
	}
	
	/*상세 조회*/
	public Board selectView(int index) {
		Board board = Dao.getInstance().selectView(index);
		return board;
	}
	
	/*게시글 수정*/
	public boolean changeUpdate(String title, String content, int index) {
		Board board = Dao.getInstance().selectView(index);
		board.setBtitle(title); //제목 바꿈
		board.setBcontent(content); //내용 바꿈
		
		boolean result = Dao.getInstance().changeUpdate(board);
		
		return result;
		
	}
	
	/*게시글 삭제*/
	public boolean delete(int index) {
		boolean result = Dao.getInstance().delete(index);
		return result;
	}
}
