/*
 * 建立通信连接的线程，将读操作和写操作相统一的线程，使得程序可以双方可以任意发送信息
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
