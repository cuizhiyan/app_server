
/*
 * �ͻ���
 * ʹ�ö��߳�ʵ������˫��ͨ�ţ�����д�����ֱ�ռ��һ���������߳�
 * ���ҷ�������ʵ�ֶ��̣߳�����ʵ�ֶ���ͻ��������������
 * ������������������Ȼ�������Ӧ���̻߳���ȥȥִ�з���
 * 2015.12.14
 * 
 * ����˵�½���ܣ�����֤��½����֤�ɹ����ٿ�ʼ˫��ͨ��
 * 2015.12.15
 */
package test_sever;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;  
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

//import Server.ConnectionThread;  
  
public class Sever {  
  
    public static final int PORT = 10000;//�����Ķ˿ں�     
    static int count=0;//��ʾ�ͻ��˵�����
    //�������ݿ����Ϣ
    private static final String URL="jdbc:mysql://localhost:3306/user";
	private static final String USER="root";
	private static final String PASSWORD="bianxingcui";
	private static boolean log=true;
	
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {    
    	System.out.println("sever begins");
    	
    	
    	Sever server = new Sever();   
    	
    	server.login();
        //server.connection();    
    }    
    
    
    //�����û���������
    public void login() throws IOException {
    	BufferedReader in_1=null;
    	ServerSocket serverSocket_1=null; 
    	Socket socket_1=null;
    	PrintWriter out_1=null;
    	try{
    		//��ʼ�������ݿ�
        	Class.forName("com.mysql.jdbc.Driver");
            //��ȡ���ݿ�����
            java.sql.Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
            //ͨ�����ݿ�����Ӳ������ݿ⣬�����ɾ�Ĳ�
            Statement stmt=(Statement) conn.createStatement();
            ResultSet rs=null;
            
            
            //�������˿�ʼ����ͨ��
        	serverSocket_1 = new ServerSocket(PORT);
        	socket_1=serverSocket_1.accept();
        	//������
        	out_1 = new PrintWriter(socket_1.getOutputStream());
        	//������
        	in_1 = new BufferedReader(new InputStreamReader(socket_1.getInputStream()));
    	    //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//�ӿ���̨��ȡ��������� 
//        	String username=in_1.readLine();
//        	System.out.println("username:"+username);
//        	out_1.println("login");
//        	out_1.flush();
        	
        	
        	
    	    while(log){
    	    	String username=in_1.readLine();
    		    String password=in_1.readLine();
    		    
    		    System.out.println("username:"+username);
    		    System.out.println("password:"+password);
    		    rs=stmt.executeQuery("select username,password from app_user");
    		    while(rs.next()){
    		    	if((username.equals(rs.getString("username")))&&(password.equals(rs.getString("password")))){
    		    		log=false;
    		    		break;	
    		    	}
    		    }
    		    if(log==true){
    		    	out_1.println("failed");
    		    	out_1.flush(); 
    		    	System.out.println("�û������������");
    		    }
    		    else{
    		    	out_1.println("success");
    		    	out_1.flush(); 
    		    	System.out.println("��¼�ɹ�");
    		    	break;
    		    }	
            } 
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}finally{
    		//in_1.close();
    	    //out_1.close();
    	    socket_1.close();
    	    serverSocket_1.close();
    	    
    	}
    	
	    
    }
    
    //�������Ӳ�ʵ��ͨ�ŵĺ���
    public void connection() {    
    	ServerSocket serverSocket = null;  
        try {    
            serverSocket = new ServerSocket(PORT);  
            
            while (true) {    
                Socket client = serverSocket.accept(); 
                ConnectionThread mythread=new ConnectionThread(client);
                mythread.start();
                count++;
                System.out.println("�ͻ�������Ϊ��"+count);
                //һ���ͻ������ӾͿ������̷ֱ߳������д    
                
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally{  
            try {  
                if(serverSocket != null){  
                    serverSocket.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }    
}    
  
