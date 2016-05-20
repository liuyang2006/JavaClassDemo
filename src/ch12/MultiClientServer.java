package ch12;

import java.io.*;
import java.net.*;

public class MultiClientServer implements Runnable{
	
	static int SerialNum = 0; //ÿ��Client�����кš�
	Socket socket;
	
	public MultiClientServer(Socket ss){
		socket = ss;
	}
	
	public static void main (String args[]){
		
		int MaxClientNum = 5;  
		try{
			//����Server Socket��
			ServerSocket server = new ServerSocket(1680);
			
			for( int i = 0; i<MaxClientNum;i++){
				Socket socket=server.accept();

			//���ӽ���,����һ��Server���߳���Client��ͨ�š�
				Thread t = new Thread(new MultiClientServer(socket));
				t.start();
			}
		server.close();  //�ر�Server Socket��
		
		}catch(Exception e){
			System.out.println("Error:"+e);
		}
	    	    
	}
	
  //Server��ͨ���̵߳��߳��塣	
  public void run(){
  	    int myNum = ++SerialNum;

  		try{
  			//ͨ��Socket��ȡ�����ϵ�����/�������
  			BufferedReader in = new BufferedReader(
									new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			
			//������׼���������Ӽ��̽������ݡ�
			BufferedReader sin=new BufferedReader(
									new  InputStreamReader(System.in));

			/* �ȶ�ȡClient���͵����ݣ�Ȼ��ӱ�׼�����ȡ���ݷ��͸�Client��
			�����յ�byeʱ�ر����ӡ�*/
			String s;
			while(!(s=in.readLine()).equals("bye")){
				System.out.println("# Received from Client No."+myNum+": "+s);
				out.println(sin.readLine());
				out.flush();
			}
		    System.out.println("The connection to Client No."+
		    						myNum+" is closing... ... ");
		    
		    //�ر����ӡ�
			in.close();
			out.close();
			socket.close();
			
		}catch(Exception e){
			System.out.println("Error:"+e);
		}	
	
}
}
