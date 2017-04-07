package echo.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoClient extends JFrame{
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input;
	JButton bt_connect;
	
	//대화를 나눌 수 있는 소켓 (종이컵): 
	//말걸때: 소켓으로부터 출력스트림, 청취할때: 소켓으로부터 입력스터림
	Socket socket;
	BufferedReader buffr; //듣기동
	BufferedWriter buffw;//말하기용
	
	public EchoClient() {
		//생성
		area=new JTextArea();
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_connect=new JButton("접속");
	
		//설정
		area.setPreferredSize(new Dimension(300, 300));
		
		//부착
		p_south.add(t_input);
		p_south.add(bt_connect);
		add(scroll, BorderLayout.NORTH);
		add(p_south);
		
		//리스너연결
		bt_connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//엔터치면
				int key=e.getKeyCode();
				//클라이언트는 먼저 말을 해야한다. 
				if(key==KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		//윈도우설정
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//서버에 대화 보내기
	public void send(){
		//텍스트상장의 메시지 값을 얻기
		String msg=t_input.getText();
		//System.out.println(msg);
		//보낼려면 writer
		try {
			buffw.write(msg+"\n");  //스트림을 통해 출력
									  // 즉 서버측의 소켓에 데이터 전송
									//+"\n" 안하면 입력이 안됨
			buffw.flush();//버퍼에 남아 있을지도 모를 데어터를 대상으로 모두 출력시킴
			
			//서버에서 날아온 메세지를 area에 출력해보자
			String data=buffr.readLine();
			area.append(data+"\n");
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//서버에 접속하는 메서드
	public void connect(){
		try {
			socket=new Socket("localhost", 7777);
			
			//접속이 완료되었으니 스트림을 얻어놓자. 왜? 대화는 나누기 위해
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//	host 인터넷에 연결된 모든주소
		//port는 숫자
	}
	
	public static void main(String[] args) {
		new EchoClient();	
	}

}
