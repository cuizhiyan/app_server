/*
 * ����ͨ�����ӵ��̣߳�����������д������ͳһ���̣߳�ʹ�ó������˫���������ⷢ����Ϣ
 */
package test_sever;

import java.net.Socket;

public class ConnectionThread extends Thread {
	Socket socket=null;
	
	public  ConnectionThread(Socket socket){
		this.socket=socket;
	}
	
	public void run(){
		new Thread(new ReadThread(socket)).start();    
        new Thread(new WriteThread(socket)).start();   
	}
	
}
