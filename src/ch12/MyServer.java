package ch12;

import java.io.*;
import java.net.*;

public class MyServer{
	public static void main (String args[]){
		try{
			//����Server Socket���ȴ���������
			ServerSocket server = new ServerSocket(1680);
			Socket socket=server.accept();

			//���ӽ�����ͨ��Socket��ȡ�����ϵ�����/�������
			BufferedReader in = new BufferedReader(
									new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			
			//������׼���������Ӽ��̽������ݡ�
			BufferedReader sin=new BufferedReader(
									new  InputStreamReader(System.in));

			//�ȶ�ȡClient���͵����ݣ�Ȼ��ӱ�׼�����ȡ���ݷ��͸�Client�������յ�byeʱ�ر����ӡ�
			String s;
			while(!(s=in.readLine()).equals("bye")){
				System.out.println("# Received from Client:  "+s);
				out.println(sin.readLine());
				out.flush();
			}
		    System.out.println("The connection is closing... ... ");
		    
		    //�ر����ӡ�
			in.close();
			out.close();
			socket.close();
			server.close();

		}catch(Exception e){
			System.out.println("Error:"+e);
		}
	}
}
