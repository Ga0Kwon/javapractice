package 기본게시판;
/*
 	[비회원 게시판]
 		1. 내용 : 번호, 작성자, 제목 ->
 		2. 기능 : 글입력, 글 조회
 			글을 입력할 때 : 제목, 내용, 작성자, 비밀번호 입력
 			글을 조회할 때
 					1. 선택 목록으로 돌아가기
 					2. 글 삭제
 					3. 글 수정
 */

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

/*---------------자바 비회원제 게시판---------------*/
public class noticeBoard { //class start
    public static void main(String[] args) { //main start


        //입력 객체
        Scanner scanner = new Scanner(System.in);

        //게시판 추가 --> 2차원 배열 최대 [row][col] col은  제목(0), 내용(1), 작성자(2), 비밀번호(3)
        String[][] boardList = new String[100][4]; //0~3 인덱스 => 총 4개

        String title; //제목
        String contents; //내용
        String writer; //작성자
        String password; //비밀번호

        while (true){
            //게시판 목록 출력
            System.out.println("-------------------------게시판 목록-------------------------");
            System.out.println("인덱스 번호 \t\t 작성자 \t\t\t\t\t\t 제목");
            //게시판 내용이 저장되어있는 모든 목록을 출력해야함.
            for(int index = 0; index < boardList.length; index++){
                //게시판에 있는 모든 내용을 출력
                if(boardList[index][0] != null){
                    System.out.printf("\t%d \t\t\t %s \t\t\t\t\t\t%s", index, boardList[index][2], boardList[index][0] );
                    System.out.println();
                }
            }

            System.out.println("-----------------------------------------------------------");
            System.out.println("1. 게시물 입력 \t 2. 게시물 조회");
            System.out.print("선택(숫자로입력해주세요.) >>"); int choiceNumber = scanner.nextInt();

            /*---------------------------1. 게시물 입력-------------------------------*/
            if(choiceNumber == 1){
                System.out.println("-------------------------게시물 입력-------------------------");
                //제목
                System.out.print("제목 : "); title = scanner.next(); scanner.nextLine();//엔터를 누를 경우 그것까지 포함해서 작성되어 넣음
                //내용
                System.out.print("내용 : "); contents = scanner.nextLine();
                //작성자
                System.out.print("작성자 : "); writer = scanner.next(); scanner.nextLine();
                //비밀번호
                System.out.print("비밀번호 : "); password = scanner.next(); scanner.nextLine();

                for(int index = 0; index < boardList.length; index++){ //boardList배열의 크기만큼 반복문을 돌음.
                    if(boardList[index][0] == null){ //만약 인덱스의 제목이 비어있다면
                        boardList[index][0] = title; //입력받은 title 비어있는 boardList의 인덱스 0번(=title)에 넣음
                        boardList[index][1] = contents; //입력받은 contents 비어있는 boardList의 인덱스 1번(=contents)에 넣음
                        boardList[index][2] = writer; //입력받은 writer 비어있는 boardList의 인덱스 2번(=writer)에 넣음
                        boardList[index][3] = password; //입력받은 password 비어있는 boardList의 인덱스 3번(=password)에 넣음
                        System.out.println("게시물이 등록되었습니다:)");
                        break;
                    } else if (index > 100) {
                        System.out.println("정원 100이 꽉찼습니다:(");
                    }
                }
                System.out.println("-----------------------------------------------------------");

            }
            /*-----------------------------------2. 글 조회--------------------------------------*/
            else if (choiceNumber == 2) { //글 조회를 선택했을 경우
                System.out.println("------------------보고싶은 번호를 입력해주세요.------------------");
                System.out.print("번호 입력 >>"); int searchIndex = scanner.nextInt(); scanner.nextLine();//보고싶은 게시물의 인덱스 값을 입력.

                if(!boardList[searchIndex][0].isEmpty()){ //보고싶은 번호의 정보가 비어있지 않으면
                    System.out.println("---------------------검색한 번호의 내용-----------------------");
                    System.out.println("제목 : " + boardList[searchIndex][0]); //제목 출력
                    System.out.println("내용 : " + boardList[searchIndex][1]); //내용 출력
                    System.out.println("작성자 : " + boardList[searchIndex][2]); //작성자 출력
                    System.out.println("-----------------------------------------------------------");

                    //1. 게시판 목록 조회(첫 부분으로) / 2. 게시판 글 수정 / 3. 게시판 글 삭제
                    System.out.println("1. 게시판 목록 조회[뒤로가기]  2. 게시물 수정  3. 게시물 삭제");
                    System.out.print("선택 >> "); int selectNumber = scanner.nextInt(); //사용자로부터 번호를 입력받음.

                    if(selectNumber == 1){ //1. 게시판 목록 조회
                        continue; //뒤로 가기 -> 게시판 목록으로 조회하기. (맨 처음 화면)
                    }
                    /*--------------------------------2. 게시판 글 수정------------------------------------*/
                    else if (selectNumber == 2) {
                        //게시판을 수정하고 싶으면 올바른 비밀번호를 입력해야함.
                        System.out.print("비밀번호 : "); String checkPassword = scanner.next(); scanner.nextLine();

                        if(checkPassword.equals(boardList[searchIndex][3])){ //비밀번호가 맞다면
                            System.out.println("----------------------수정할 게시물의 정보---------------------");
                            System.out.println("수정할 것 : 1.제목, 2.내용(둘다 선택가능/공백이나 반점으로 구분) ");
                            String inputChange = scanner.nextLine(); // 수정할 번호 입력받음

                            if(!inputChange.isEmpty()){  //만약 입력 받은 정보가 비어있지 않는 경우
                                if(inputChange.contains(",")){ //반점으로 선택한 숫자를 구분했다면
                                    // ,으로 문자열을 잘라서 배열의 요소로 저장
                                    String[] changeNumber = inputChange.split(",");

                                    // 배열에 중복된 숫자를 제거
                                    LinkedHashSet<String> hashArray =
                                            new LinkedHashSet<>( Arrays.asList(changeNumber) );

                                    // 중복된 숫자를 제거하고 배열로 다시 전환
                                    String[] hashedChangeNumber =
                                            hashArray.toArray(new String[] {});

                                    for(int num = 0; num < hashedChangeNumber.length; num++){ //배열의 크기만큼 반복문
                                        int check = Integer.parseInt(hashedChangeNumber[num]); //배열에 있는 문자열을 숫자로 바꿈
                                        if(check == 1){ //제목을 수정할 것이면
                                            System.out.print("수정할 제목 : "); boardList[searchIndex][0] = scanner.nextLine();
                                            System.out.println("번호 : " + searchIndex+ "의 제목 수정 완료하였습니다:)");

                                        }
                                        if (check == 2) { //내용을 수정할거면
                                            System.out.print("수정할 내용 : "); boardList[searchIndex][1] = scanner.nextLine();
                                            System.out.println("번호 : " + searchIndex+ "의 내용 수정 완료하였습니다:)");
                                        }
                                        if(check != 2 && check != 1){ //1번 or 2번 중에 선택하지 않았을 경우
                                            System.out.println("1번, 2번 중에 선택해주세요:(");
                                        }
                                    }
                                } else if (inputChange.contains(" ")) { //공백으로 구분했을 경우
                                    String[] changeNumber = inputChange.split(" "); //공백으로 문자열을 나눠 배열의 요소로 저장

                                    // 배열에 중복된 숫자를 제거
                                    LinkedHashSet<String> hashArray =
                                            new LinkedHashSet<>( Arrays.asList(changeNumber) );
                                    // 중복된 숫자를 제거하고 배열로 다시 전환
                                    String[] hashedChangeNumber =
                                            hashArray.toArray(new String[] {});

                                    for(int num = 0; num < hashedChangeNumber.length; num++){
                                        int check = Integer.parseInt(hashedChangeNumber[num]);
                                        if(check == 1){ //제목을 수정할 것이면
                                            System.out.print("수정할 제목 : "); boardList[searchIndex][0] = scanner.nextLine();
                                            System.out.println("번호 : " + searchIndex+ "의 제목 수정 완료하였습니다:)");
                                        }
                                        if (check == 2) { //내용을 수정할거면
                                            System.out.print("수정할 내용 : "); boardList[searchIndex][1] = scanner.nextLine();
                                            System.out.println("번호 : " + searchIndex+ "의 내용 수정 완료하였습니다:)");
                                        }
                                        if(check != 2 && check != 1){ //1번 or 2번 중에 선택하지 않았을 경우
                                            System.out.println("1번, 2번 중에 선택해주세요:(");
                                        }
                                    }

                                }else{ //숫자를 구분하지 않아도 되는 수정할 내용이 1개이거나 구분할 수 없을때.
                                    if(Integer.parseInt(inputChange) == 1){//제목을 수정할 것이면
                                        System.out.print("수정할 제목 : "); boardList[searchIndex][0] = scanner.nextLine();
                                        System.out.println("번호 : " + searchIndex+ "의 제목 수정 완료하였습니다:)");
                                    }
                                    if(Integer.parseInt(inputChange) == 2){//내용을 수정할거면
                                        System.out.print("수정할 내용 : "); boardList[searchIndex][1] = scanner.nextLine();
                                        System.out.println("번호 : " + searchIndex+ "의 내용 수정 완료하였습니다:)");
                                    }
                                    if(Integer.parseInt(inputChange) != 2 && Integer.parseInt(inputChange) != 1){ //1번 or 2번 중에 선택하지 않았을 경우
                                        System.out.println("1번, 2번 중에 선택해주세요:(");
                                    }
                                }

                            }else{ //번호를 입력하지 않은 경우 
                                System.out.println("번호를 입력해주시겠어요?");
                            }

                            System.out.println("-----------------------------------------------------------");

                        }else{ //비밀번호를 올바르게 클릭하지 않았을 경우
                            System.out.println("비밀번호가 틀렸습니다:<");
                        }

                    }
                    /*--------------------------------3. 게시물 삭제------------------------------------*/
                    else if (selectNumber == 3) {
                        System.out.print("비밀번호 : "); String checkPassword = scanner.next(); scanner.nextLine();

                        if(checkPassword.equals(boardList[searchIndex][3])){ //비밀번호가 맞다면
                            /*해당 인덱스 값의 정보들을 삭제.*/
                            boardList[searchIndex][0] = null; // 해당 인덱스의 제목을 null값으로 바꿈 (삭제니까)
                            boardList[searchIndex][1] = null; // 해당 인덱스의 내용을 null값으로 바꿈 (삭제니까)
                            boardList[searchIndex][2] = null; // 해당 인덱스의 작성자를 null값으로 바꿈 (삭제니까)
                            boardList[searchIndex][3] = null; // 해당 인덱스의 비밀번호 null값으로 바꿈 (삭제니까)

                            for(int row = 0; row < boardList.length-1; row++){

                                if(boardList[row][0] != null){ //해당 인덱스가 비어있지 않다면 다시 반복문 처음으로,,,
                                    continue;
                                }
                                /*만약 앞 배열은 비어있는데 뒤의 배열은 비어있지 않다면*/
                                else if(boardList[row][0] == null && boardList[row + 1][0] != null) {
                                    //배열 요소 값들을 모두 앞으로 당겨준다.
                                    boardList[row][0] = boardList[row + 1][0]; //제목
                                    boardList[row][1] = boardList[row + 1][1]; //내용
                                    boardList[row][2] = boardList[row + 1][2]; //작성자
                                    boardList[row][3] = boardList[row + 1][3]; //비밀번호

                                    //뒤에 있는 요소 값들 삭제
                                    boardList[row+1][0] = null; //제목
                                    boardList[row+1][1] = null; //내용
                                    boardList[row+1][2] = null; //작성자
                                    boardList[row+1][3] = null; //비밀번호

                                }
                                else if (row > 99) { //게시물은 100개까지만,,,
                                    break; //row가 99까지 간거면 게시물 100개를 넘긴 거기때문에 반복문을 나간다.
                                }
                            }
                            System.out.println("게시물 삭제가 성공되었습니다:)");
                        }
                        else{ //비밀번호를 올바르게 클릭하지 않았을 경우
                            System.out.println("비밀번호가 틀렸습니다:<");
                        }

                    }else{ //1. 뒤로가기 2. 게시물 수정 3. 게시물 삭제 중에 선택하지 않았을 경우
                        System.out.println("1번, 2번, 3번중에 선택해주세요;<");
                    }

                }
                else{ //해당 번호에 정보가 비어있을 때
                    System.out.println("해당 인덱스의 데이터가 없습니다;<");
                }

            }else { //번호를 1번과 2번중에 선택하지 않으면
                System.out.println("번호는 1번과 2번중에 선택해주세요;<");
            }
        }
    } //main end
}//class end