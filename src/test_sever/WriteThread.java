/*
 * ����д�������߳�
 */


package test_sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread implements Runnable{  
	    private Socket client;  
	  
	    public WriteThread(Socket client) {  
	        this.client = client;  
	    }  
	  
	    @Override  
	    public void run() {  
	        PrintWriter out=null;
			try {
				out = new PrintWriter(client.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//�ӿ���̨��ȡ��������� 
	        try{  
	            while(true){  
	                //��ͻ��˻ظ���Ϣ    
	            	//out.println("�������û���������");
	            	out.println(reader.readLine());
	            	out.flush();  
	            }  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }finally{  
	            if(client != null){  
				    client = null;  
				}  
	        }  
	    }  
	}  

