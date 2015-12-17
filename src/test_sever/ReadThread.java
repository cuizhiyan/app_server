
/*
 * 处理读操作的线程
 */
package test_sever;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ReadThread implements Runnable{  
    private Socket client=null;  
    
    public ReadThread(Socket client) {  
        this.client = client;  
    }  
  
    @Override  
    public void run() {  
    	BufferedReader in = null;
    	try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {  
            while(true){ 
            	System.out.println("客户端说："+in.readLine());    
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{              
                if(client != null){  
                    client = null;  
                }  
        }  
        Sever.count--;
         
    }  
}  
  