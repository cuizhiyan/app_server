
/*
 * 客户端
 * 使用多线程实现任意双向通信，读和写操作分别占用一个单独的线程
 * 并且服务器端实现多线程，可以实现多个客户端与服务器连接
 * 主程序，启动服务器，然后调用响应的线程或功能去去执行服务
 * 2015.12.14
 * 
 * 添加了登陆功能，先验证登陆，验证成功后再开始双向通信
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
  
    public static final int PORT = 10000;//监听的端口号     
    static int count=0;//显示客户端的数量
    //关于数据库的信息
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
    
    
    //检验用户名，密码
    public void login() throws IOException {
    	BufferedReader in_1=null;
    	ServerSocket serverSocket_1=null; 
    	Socket socket_1=null;
    	PrintWriter out_1=null;
    	try{
    		//开始连接数据库
        	Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            java.sql.Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
            //通过数据库的连接操纵数据库，完成增删改查
            Statement stmt=(Statement) conn.createStatement();
            ResultSet rs=null;
            
            
            //服务器端开始建立通信
        	serverSocket_1 = new ServerSocket(PORT);
        	socket_1=serverSocket_1.accept();
        	//输入流
        	out_1 = new PrintWriter(socket_1.getOutputStream());
        	//输入流
        	in_1 = new BufferedReader(new InputStreamReader(socket_1.getInputStream()));
    	    //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//从控制台获取输入的内容 
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
    		    	System.out.println("用户名或密码错误");
    		    }
    		    else{
    		    	out_1.println("success");
    		    	out_1.flush(); 
    		    	System.out.println("登录成功");
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
    
    //建立连接并实现通信的函数
    public void connection() {    
    	ServerSocket serverSocket = null;  
        try {    
            serverSocket = new ServerSocket(PORT);  
            
            while (true) {    
                Socket client = serverSocket.accept(); 
                ConnectionThread mythread=new ConnectionThread(client);
                mythread.start();
                count++;
                System.out.println("客户端数量为："+count);
                //一个客户端连接就开两个线程分别处理读和写    
                
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
  
