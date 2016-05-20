package ch12;

import java.io.*;
import java.net.*;

public class MyClient{
	public static void main (String args[]){
		try{
			Socket socket = new Socket("127.0.0.1",1680);  //������������
			
			//���ӽ�����ͨ��Socket��ȡ�����ϵ�����/�������
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(
									new InputStreamReader (socket.getInputStream()));
			
			//������׼���������Ӽ��̽������ݡ�
			BufferedReader sin = new BufferedReader(
									new InputStreamReader (System.in));
			
			//�ӱ�׼�����ж�ȡһ�У�����Server�ˣ����û�����byeʱ�������ӡ�
			String s;
			do{
				s=sin.readLine();
				out.println(s);
				out.flush();
				if (!s.equals("bye")){
					System.out.println("@ Server response:  "+in.readLine());
				}
				else{
					System.out.println("The connection is closing... ... "); 
				}
				
			}while(!s.equals("bye"));

			//�ر����ӡ�
			out.close();
			in.close();
			socket.close();

		}catch (Exception e) {
			System.out.println("Error"+e);
		}	
	}
}

