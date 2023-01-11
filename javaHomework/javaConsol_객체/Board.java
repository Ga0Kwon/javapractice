package javaConsol_객체;

public class Board { //Board 객체
    //멤버
    String Title;
    String Content;
    String Writer;
    String Password;


    Board(){} //매개변수가 없는 생성자

    Board(String Title, String Content, String Writer, String Password){ //매개변수를 갖는 생성자
        this.Title =  Title;
        this.Content = Content;
        this.Writer = Writer;
        this.Password = Password;
    }

}
