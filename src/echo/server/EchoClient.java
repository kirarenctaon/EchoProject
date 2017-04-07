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
	
	//��ȭ�� ���� �� �ִ� ���� (������): 
	//���ɶ�: �������κ��� ��½�Ʈ��, û���Ҷ�: �������κ��� �Է½��͸�
	Socket socket;
	BufferedReader buffr; //��⵿
	BufferedWriter buffw;//���ϱ��
	
	public EchoClient() {
		//����
		area=new JTextArea();
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_connect=new JButton("����");
	
		//����
		area.setPreferredSize(new Dimension(300, 300));
		
		//����
		p_south.add(t_input);
		p_south.add(bt_connect);
		add(scroll, BorderLayout.NORTH);
		add(p_south);
		
		//�����ʿ���
		bt_connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//����ġ��
				int key=e.getKeyCode();
				//Ŭ���̾�Ʈ�� ���� ���� �ؾ��Ѵ�. 
				if(key==KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		//�����켳��
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//������ ��ȭ ������
	public void send(){
		//�ؽ�Ʈ������ �޽��� ���� ���
		String msg=t_input.getText();
		//System.out.println(msg);
		//�������� writer
		try {
			buffw.write(msg+"\n");  //��Ʈ���� ���� ���
									  // �� �������� ���Ͽ� ������ ����
									//+"\n" ���ϸ� �Է��� �ȵ�
			buffw.flush();//���ۿ� ���� �������� �� �����͸� ������� ��� ��½�Ŵ
			
			//�������� ���ƿ� �޼����� area�� ����غ���
			String data=buffr.readLine();
			area.append(data+"\n");
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//������ �����ϴ� �޼���
	public void connect(){
		try {
			socket=new Socket("localhost", 7777);
			
			//������ �Ϸ�Ǿ����� ��Ʈ���� ������. ��? ��ȭ�� ������ ����
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//	host ���ͳݿ� ����� ����ּ�
		//port�� ����
	}
	
	public static void main(String[] args) {
		new EchoClient();	
	}

}
