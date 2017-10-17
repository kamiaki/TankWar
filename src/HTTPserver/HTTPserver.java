package HTTPserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import javafx.print.JobSettings;
import net.sf.json.JSONObject;

public class HTTPserver {
	private int PORT = 0;
	private int LINKNUMBER = 0;
	private HttpServer server = null;
	private HttpServerProvider provider = null;
	
	public HTTPserver(int port,int linknumber){
		this.PORT = port;
		this.LINKNUMBER = linknumber;
	}
	
	/**
	 * ����������
	 */
	public boolean HTTPserverStrat(){
		try {
			provider = HttpServerProvider.provider();
	        server = provider.createHttpServer(new InetSocketAddress(PORT), LINKNUMBER); 				//1.�˿�   2. ���ӵȴ���
	       
	        server.createContext("/join", new joinHttpHandler());   									//ע����Ϣ
	        server.createContext("/login",new loginHttpHandler());										//��¼��Ϣ
	        server.createContext("/updata",new updataHttpHandler());									//�޸���Ϣ
	        
			ExecutorService pool = Executors.newCachedThreadPool();										//�̳߳�		
	        server.setExecutor(pool);																	//nullΪ���߳�
	        server.start();	
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * �������ر�
	 */
	public boolean HTTPserverOff(){
		try {
			if(server != null){
		        server.stop(1);  //�����ֹͣ
		        return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * ע��
	 * @author Administrator
	 *
	 */
	class joinHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;
	        //*********************************************************��ȡ����
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************���ݶ�ȡ�����ݣ��������͵�����   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNameID(map)){
				PD = false;
			}else{
				PD = mysqlID.writePlayerGameID(map);
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************�������ݵ��ͻ���  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "ע��ɹ�".getBytes();
	        }else{
	        	writeBuffer = "ע��ʧ��".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush();  
	        out.close();
	        
	        //*********************************************************�ر�http       
	        HttpConn.close();           
	    } //handle����    
		
	}//DyanHttpHandler��

	/**
	 * ��¼
	 */
	class loginHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;       
	        //*********************************************************��ȡ����
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************���ݶ�ȡ�����ݣ��������͵�����   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);				
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNamePassWordID(map)){
				PD = true;
			}else{
				PD = false;
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************�������ݵ��ͻ���  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "��¼�ɹ�".getBytes();
	        }else{
	        	writeBuffer = "��¼ʧ��".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush();  
	        out.close();
	        
	        //*********************************************************�ر�http	        
	        HttpConn.close();         
	    } //handle����    	
	}//DyanHttpHandler��

	/**
	 * �޸�
	 */
	class updataHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;       
	        //*********************************************************��ȡ����
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************���ݶ�ȡ�����ݣ��������͵�����   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);				
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNamePassWordID(map)){
				if(mysqlID.updataPlayerGameID(map)){
					PD = true;
				}
			}else{
				PD = false;
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************�������ݵ��ͻ���  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "�޸ĳɹ�".getBytes();
	        }else{
	        	writeBuffer = "�޸�ʧ��".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush(); 
	        out.close();
	        //*********************************************************�ر�http
	        HttpConn.close();         
	    } //handle����    	
	}//DyanHttpHandler��

}
