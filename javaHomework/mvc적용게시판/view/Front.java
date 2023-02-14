package mvc적용게시판.view;

import java.util.ArrayList;
import java.util.Scanner;

import mvc적용게시판.contoller.BController;
import mvc적용게시판.model.Board;
import mvc적용게시판.model.Dao;

public class Front {
	//입력 객체는 front에서만 사용하기 때문에 private
	private Scanner scanner = new Scanner(System.in);
	
	//*싱글톤
	private static Front front = new Front();
	//생성자
	private Front() {}
	//객체 반환 함수
	public static Front getInstance() {
		return front;
	}
	
	//메인 페이지
	public void index() {
		while(true) {
			ArrayList<Board> boardList = Dao.getInstance().print();
			
			System.out.println("-------------------게시판 목록------------------");
			System.out.println("번호\t제목\t\t\t작성자");
			
			for(Board b : boardList) {
				System.out.printf("%-7d %-10s \t %10s\n", 
						b.getBno(),
						b.getBtitle(),
						b.getBwriter());
			}
			/*게시물 출력 후 메뉴*/
			System.out.println("---------------------------------------------");
            System.out.println("1.게시물 입력 \t 2. 게시물 상세 조회");
            System.out.print("선택(번호) : "); int choice = scanner.nextInt();
            
            if(choice == 1) { //1. 게시물 입력
            	write();
            }else if(choice == 2) { //2. 게시물 상세 조회
            	selectView();
            }else { //그 외
            	System.err.println("[안내] 1번과 2번중에 선택해주세요.");
            }
		}
	}
	
	/*게시글 쓰기*/
	public void write() {
		System.out.print("제목 : "); String title = scanner.next(); scanner.nextLine();
		System.out.print("내용 : "); String content = scanner.nextLine();
		System.out.print("작성자 : "); String writer = scanner.next();
		System.out.print("비밀번호 : "); String password = scanner.next();
		
		boolean result = BController.getInstance().write(title, content, writer, password);
		
		if(result) {
			System.out.println("[안내] 게시물이 등록되었습니다");
		}else{
			System.err.println("[안내] 게시물 등록 실패하였습니다.");
		}
	}
	
	/*게시글 상세 조회*/
	public void selectView() {
		System.out.print("볼 게시물 번호 : "); int index = scanner.nextInt();
		Board board = BController.getInstance().selectView(index);
		
		if(board == null) {
			System.err.println("해당 번호를 가진 게시물 번호가 없습니다.");
			return; //다시 메인 페이지 while문으로 돌아감
		}
		
		System.out.println("---------------------------------------------");
		System.out.println("번호 : " + board.getBno());
		System.out.println("제목 : " + board.getBtitle());
		System.out.println("내용 : " + board.getBcontent());
		System.out.println("작성자 : " + board.getBwriter());
		System.out.println("---------------------------------------------");
		 
		System.out.println("1. 게시물 조회[뒤로가기]  2. 게시물 수정  3. 게시물 삭제");
	    System.out.print("선택(번호) : "); int choice = scanner.nextInt();	
	    
	    if(choice == 1) { //1. 게시물 조회[뒤로가기]
	    	return;
	    }else if(choice == 2) {//2. 게시물 수정
	    	changeUpdate(index);
	    }else if(choice == 3) { //3. 게시물 삭제
	    	deleteBoard(index);
	    }else {
	    	System.err.println("[안내] 1,2,3번 중에 선택해주세요.");
	    }
	    
	}
	
	/*비밀번호 체크*/
	public boolean checkPassword(int index) {
		System.out.print("비밀번호 : "); String password = scanner.next();
		if(!password.equals(BController.getInstance().selectView(index).getBpassword())) {
			return false; //비밀번호 틀릴 경우
		}
		return true;
	}
	
	/*게시물 수정*/
	public void changeUpdate(int index) {
    	boolean pCheck = checkPassword(index);
    	if(pCheck) { //비밀번호 유효성 검사가 true[통과]
    		/*수정 할 것은 제목과 내용만*/
    		System.out.print("제목 : "); String title = scanner.next(); scanner.nextLine();
    		System.out.print("내용 : "); String content = scanner.nextLine();
    		
    		boolean result = BController.getInstance().changeUpdate(title, content, index);
    		
    		if(result) {
    			System.out.println("[안내] 해당 게시물이 수정 완료되었습니다.");
    		}else {
    			System.err.println("[안내] 해당 게시물이 수정 실패하였습니다.");
    		}
    	}else {
    		System.err.println("[안내] 비밀번호가 올바르지 않습니다.");
    	}
		
	}
	
	/*게시물 삭제*/
	public void deleteBoard(int index) {
		boolean pCheck = checkPassword(index);
		if(pCheck) {
			boolean result = BController.getInstance().delete(index);
			
			if(result) {
    			System.out.println("[안내] 해당 게시물 삭제 완료되었습니다.");
    		}else {
    			System.err.println("[안내] 해당 게시물 삭제 실패하였습니다.");
    		}
		}else {
    		System.err.println("[안내] 비밀번호가 올바르지 않습니다.");
    	}
	}
}
