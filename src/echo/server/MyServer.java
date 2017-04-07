/*
 자바를 이용하여 서버측 프로그램을 작성한다. 

// 전화를 받는 사람 

 */

package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
	
public class MyServer {
	//대화를 나누기전에 접속을 알려주기 위한 객체! 즉 아직 대화는 못 나눈다. 
	//서버는 클라이언트가 찾아오기를 기다리므로 클라어언트가 약속한 포트번호만 보유하면 된다. 
	//원칙 : 포트번호는 자유롭게 정하면 된다. 
	//예외 1: 0~1023 이미 시스템이 점유하고 있다. 
	//예외 2: 유명한 프로그램들은 피하자. 오라클 1521, mysql 3306, web 80
	
	ServerSocket server;
	int port=8888;
	Socket socket;
	
	public MyServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("서버생성");
			
			//클라이언트가 접속을 기다린다
			//접속이 있을 때까지 무한대기 즉 지연에 따지
			//마치 스트림 read()계열과 같다. 읽어질때까지 무한 대기에 빠짐
			while(true){
				socket=server.accept();  //누군가 접속하면, accept에서 반환함
				System.out.println("접속자 발견~!");
				
				//소켓을 이용하여 데이터를 받고자 하는 경우엔 입력스트팀을
				//데이터를 보내고자하는 경우엔 출력스트림
				InputStream is=socket.getInputStream(); //바이트 기반의 입력스트림, 영문 한글자만 안깨짐, 한글을 깨질꺼니까 빨대를 끼우자
				InputStreamReader reader=null;
				reader = new InputStreamReader(is);
				
				
				int data;
			
				while(true){
					//data=is.read(); //1byte 읽어들임
					data=reader.read(); //2byte 읽어들임
					System.out.print((char)data);
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyServer();
	}
	
}
