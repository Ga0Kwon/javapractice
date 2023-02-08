package javaConsol_객체;

import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class noticeBoard {//class start

    public static void main(String[] args) { //main start
        Scanner scanner = new Scanner(System.in); //입력 객체
        Board[] boardList = new Board[100]; //board 객체가 100개 들어갈 수 있는 boardList

        //무한 반복
        while (true){ //while start
            //게시판 목록 출력
            System.out.println("-------------------게시판 목록------------------");
            System.out.println("인덱스 번호 \t\t 작성자 \t\t 제목");

           for(int index = 0; index < boardList.length; index++){
                if(boardList[index] != null){ //해당 인덱스가 비어있지 않으면
                    System.out.printf("%d \t\t %s \t\t %s", index, boardList[index].Writer, boardList[index].Title ); //인덱스 번호, 작성자, 제목 출력
                    System.out.println();
                }
            }
            System.out.println("----------------------------------------------");
            System.out.println("1.게시물 입력 \t 2. 게시물 조회"); //1. 게시물 입력 2. 게시물 조회
            System.out.print("선택(번호) : "); int boardChoice = scanner.nextInt();

            if(boardChoice == 1){ //첫번째 if문 start -> 1. 게시물 입력 선택했을 경우
                System.out.println("-------------------게시판 입력------------------");
                System.out.print("게시물 제목 :"); String title = scanner.next(); scanner.nextLine();
                System.out.print("게시물 내용 :"); String content = scanner.nextLine();
                System.out.print("게시물 작성자 :"); String writer = scanner.next();
                System.out.print("비밀번호 : "); String password = scanner.next();

                //해당 값들을 매개변수로 넣은 board 객체 생성
                Board board = new Board(title, content, writer, password);

                for(int index = 0; index < boardList.length; index++){ //1 for start

                    if(boardList[index] == null){ //if start -> 해당 인덱스의 boardList가 비어있다면
                        boardList[index] = board; //값을 넣은 borad 객체를 boradList에 저장
                        System.out.println("게시물이 등록되었습니다.");
                        break; //for문을 빠져나옴.
                    } //if end

                    if(index > 100){ // if start -> 객체는 딱 100개까지만 저장 할 수 있음.
                        System.out.println("게시판이 꽉 찼습니다.");
                        break; //for문을 빠져나옴.
                    } //if end

                } //1 for end

            }//첫번째 if문 end
            else if(boardChoice == 2){//첫번째 else-if start -> 2. 게시물 조회 선택했을 경우
                System.out.println("------------보고싶은 번호를 입력해주세요.-----------");
                System.out.print("번호 입력 :"); int searchIndex = scanner.nextInt(); scanner.nextLine();//보고싶은 게시물의 인덱스 값을 입력.
                
                //조회
                if(boardList[searchIndex]!=null){ //입력받은 번호의 해당 값이 존재할 경우 if start
                    System.out.println("제목 : " + boardList[searchIndex].Title );
                    System.out.println("내용 : " + boardList[searchIndex].Content );
                    System.out.println("작성자 : " + boardList[searchIndex].Writer );
                    System.out.println("----------------------------------------------");

                    System.out.println("1. 게시물 조회[뒤로가기]  2. 게시물 수정  3. 게시물 삭제");
                    System.out.print("선택(번호) : ");
                    int selectNumber = Integer.parseInt(scanner.nextLine().trim()); //공백을 제거해서 변수에 저장.

                    if(selectNumber == 1){ //if 1. 게시물 조회[뒤로가기] start
                        continue;
                    } //if 1. 게시물 조회[뒤로가기] end
                    else if (selectNumber == 2) { //else-if 2. 게시물 수정 start
                        String[] changeNumber = new String[0];

                        System.out.print("비밀번호 : "); String inputPassword = (scanner.nextLine()).trim();
                        System.out.println("-------------------게시물 수정창-----------------");
                        if(boardList[searchIndex].Password.equals(inputPassword)){ // 입력한 비밀번호가 맞으면
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

                                    if(hashedChangeNumber[index].equals("1")){ //1. 제목을 선택했으면
                                        System.out.print("수정할 제목 : "); boardList[searchIndex].Title= scanner.nextLine();
                                        System.out.println("제목이 수정되었습니다.");
                                    }
                                    if (hashedChangeNumber[index].equals("2")){ //2. 내용 선택했으면
                                        System.out.print("수정할 내용 : "); boardList[searchIndex].Content= scanner.nextLine();
                                        System.out.println("내용이 수정되었습니다.");
                                    }
                                }
                            }else{ //한개 선택했을 때
                                if(Integer.parseInt(inputNumber) == 1){//제목을 수정할 것이면
                                    System.out.print("수정할 제목 : "); boardList[searchIndex].Title = scanner.nextLine();
                                    System.out.println("제목이 수정되었습니다.");
                                }
                                if(Integer.parseInt(inputNumber) == 2){//내용을 수정할거면
                                    System.out.print("수정할 내용 : "); boardList[searchIndex].Content = scanner.nextLine();
                                    System.out.println("내용 수정되었습니다.");
                                }
                                if(Integer.parseInt(inputNumber) > 2 || Integer.parseInt(inputNumber) < 0){
                                    System.out.println("번호는 1번, 2번 중에 선택해주세요.");
                                }
                            }


                        } else if (inputPassword == "") { //비밀번호 입력을 안했을 경우
                            System.out.println("비밀번호를 입력해주세요.");
                        }
                        else{ //비밀번호가 틀릴 경우
                            System.out.println("잘못된 비밀번호 입니다.");
                        }
                        System.out.println("-----------------------------------------------");


                    } //else-if 2. 게시물 수정 end
                    else if (selectNumber == 3) { //else-if 3. 게시물 삭제 start

                        System.out.print("비밀번호 : "); String inputPassword = (scanner.nextLine()).trim();
                        System.out.println("-------------------게시물 삭제창-----------------");
                        if(boardList[searchIndex].Password.equals(inputPassword)){ // 입력한 비밀번호가 맞으면
                           boardList[searchIndex] = null;

                            for(int index = 0; index < boardList.length - 1; index++){ //0 ~ 99이기 때문에 길이에서 -1
                                //앞의 내용은 비어있는데, 뒤의 내용은 비어있지 않은 경우 앞으로 당겨줘야한다.
                                if(boardList[searchIndex] == null && boardList[searchIndex + 1] != null){

                                    //앞으로 당기고
                                    boardList[searchIndex] = boardList[searchIndex+1];

                                    //뒤의 내용은 삭제!
                                    boardList[searchIndex + 1] = null;
                                }
                                else if (index >= 100) { //객체는 100개까지만 생성할 수 있어서 넘어가면  반복문 그만 돌아야함.
                                    break;
                                }
                            }
                            System.out.println("게시물 삭제를 성공하였습니다.");

                        } //if end [입력한 비밀번호가 맞으면]
                        else if (inputPassword == "") { //비밀번호가 공백일 경우 -> 입력안했을 경우 else-if start
                            System.out.println("비밀번호가 틀렸습니다.");
                        } //else-if end
                        else{ //비밀번호가 틀리면 else start
                            System.out.println("비밀번호가 틀렸습니다.");
                        }
                        System.out.println("-----------------------------------------------");
                    } //else-if 3. 게시물 삭제 end
                    else{ //else 1, 2, 3번 중에 선택하지 않았을 경우
                        System.out.println("1. 게시물 조회[뒤로가기]  2. 게시물 수정  3. 게시물 삭제 중에 선택해주세요. (번호로)");
                    } //else end

                } //if end
                else{ //입력받은 번호의 해당 값이 존재하지 않을 경우 else start
                    System.out.println("해당 번호에는 데이터가 존재하지 않습니다.");
                } //else end
            }//첫번째 else-if end
            else{
                System.out.println("1. 게시물 입력 2. 게시물 조회 중에 선택해주세요. (1, 2)번 중에 골라주세요.");
            }
        } //while end
    }//main end
}//class end
