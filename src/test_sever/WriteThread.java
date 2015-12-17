/*
 * 处理写操作的线程
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
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//从控制台获取输入的内容 
	        try{  
	            while(true){  
	                //向客户端回复信息    
	            	//out.println("请输入用户名和密码");
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

