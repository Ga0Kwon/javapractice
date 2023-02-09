package DB버전게시판;

public class Board {
	private int bno; 
	private String btitle;
	private String bcontent;
	private String bwriter;
	private String bpassword;
	

	public void setTitle(String string) {
		// TODO Auto-generated method stub
		btitle = string;
	}

	public void setContent(String string) {
		// TODO Auto-generated method stub
		bcontent = string;
	}

	public void setWriter(String string) {
		// TODO Auto-generated method stub
		bwriter = string;
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		bpassword = string;
	}


	public String getTitle() {
		// TODO Auto-generated method stub
		return btitle;
	}

	public String getWriter() {
		// TODO Auto-generated method stub
		return bwriter;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return bcontent;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return bpassword;
	}

	public void setBno(int int1) {
		// TODO Auto-generated method stub
		bno = int1;
	}

	public int getBno() {
		// TODO Auto-generated method stub
		return bno;
	}
}
