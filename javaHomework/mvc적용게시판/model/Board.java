package mvc적용게시판.model;
/*====================DTO에 해당====================*/
public class Board {
	//1. 필드
	private int bno; 
	private String btitle;
	private String bcontent;
	private String bwriter;
	private String bpassword;
	
	//2. 생성자
	public Board() {}
	
	public Board(String btitle, String bcontent, String bwriter, String bpassword) {
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bwriter = bwriter;
		this.bpassword = bpassword;
	}

	//3. 메소드
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getBwriter() {
		return bwriter;
	}
	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}
	public String getBpassword() {
		return bpassword;
	}
	public void setBpassword(String bpassword) {
		this.bpassword = bpassword;
	}
	
	
	
	
}