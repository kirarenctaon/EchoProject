/*
 �ڹٸ� �̿��Ͽ� ������ ���α׷��� �ۼ��Ѵ�. 

// ��ȭ�� �޴� ��� 

 */

package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
	
public class MyServer {
	//��ȭ�� ���������� ������ �˷��ֱ� ���� ��ü! �� ���� ��ȭ�� �� ������. 
	//������ Ŭ���̾�Ʈ�� ã�ƿ��⸦ ��ٸ��Ƿ� Ŭ����Ʈ�� ����� ��Ʈ��ȣ�� �����ϸ� �ȴ�. 
	//��Ģ : ��Ʈ��ȣ�� �����Ӱ� ���ϸ� �ȴ�. 
	//���� 1: 0~1023 �̹� �ý����� �����ϰ� �ִ�. 
	//���� 2: ������ ���α׷����� ������. ����Ŭ 1521, mysql 3306, web 80
	
	ServerSocket server;
	int port=8888;
	Socket socket;
	
	public MyServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("��������");
			
			//Ŭ���̾�Ʈ�� ������ ��ٸ���
			//������ ���� ������ ���Ѵ�� �� ������ ����
			//��ġ ��Ʈ�� read()�迭�� ����. �о��������� ���� ��⿡ ����
			while(true){
				socket=server.accept();  //������ �����ϸ�, accept���� ��ȯ��
				System.out.println("������ �߰�~!");
				
				//������ �̿��Ͽ� �����͸� �ް��� �ϴ� ��쿣 �Է½�Ʈ����
				//�����͸� ���������ϴ� ��쿣 ��½�Ʈ��
				InputStream is=socket.getInputStream(); //����Ʈ ����� �Է½�Ʈ��, ���� �ѱ��ڸ� �ȱ���, �ѱ��� �������ϱ� ���븦 ������
				InputStreamReader reader=null;
				reader = new InputStreamReader(is);
				
				
				int data;
			
				while(true){
					//data=is.read(); //1byte �о����
					data=reader.read(); //2byte �о����
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
