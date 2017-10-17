package HTTPclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPclient {
	private String Addr = "";
	private String PORT = "";
	/**
	 * ���캯��
	 * @param addr
	 * @param port
	 */
	public HTTPclient(String addr,String port){
		this.Addr = addr;
		this.PORT = port;
	}
	/**
	 * ע��
	 * @param userName
	 * @param name
	 * @param password
	 * @return
	 */
	public String HTTPclientSendJoin(String userName, String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/join");			//URL��ַ
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP��URL����               
		    conn.setRequestMethod("POST");										//����URL����ķ���       
		    conn.setDoInput(true);												//���� ����POST�����������
		    conn.setDoOutput(true);												//���  ����POST�����������      
		    conn.setUseCaches(true);											//�ͻ�����ʹ�û���
		    
			String SENDstr = "";												//���ַ���
			SENDstr = writeuserpassword(userName, password);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];									//���ֽ���
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//���ӶϿ�
			
			return resultSTR;
		} catch (Exception e) {
			// TODO: handle exception
			return "������쳣";
		}		
	}	
	/**
	 * ��¼
	 */
	public String HTTPclientSendLogin(String userName,String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/login");		//URL��ַ
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP��URL����              
		    conn.setRequestMethod("POST");										//����URL����ķ���       
		    conn.setDoInput(true);												//���� ����POST�����������
		    conn.setDoOutput(true);												//���  ����POST�����������      
		    conn.setUseCaches(true);											//�ͻ�����ʹ�û���
		    conn.connect();
		    
			String SENDstr = "";												//���ַ���
			SENDstr = writeLogin(userName,password);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];										//���ֽ���
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();													//���ӶϿ�
			
			return resultSTR;
		} catch (Exception e) {
			return "������쳣";
		}		
	}	
	/**
	 * �޸�����
	 */
	public String HTTPclientSendUpdata(String userName,String oldPassword,String newPassword){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/updata");		//URL��ַ
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP��URL����              
		    conn.setRequestMethod("POST");										//����URL����ķ���       
		    conn.setDoInput(true);												//���� ����POST�����������
		    conn.setDoOutput(true);												//���  ����POST�����������      
		    conn.setUseCaches(true);											//�ͻ�����ʹ�û���
		    
			String SENDstr = "";												//���ַ���
			SENDstr = writeUpdata(userName, oldPassword, newPassword);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];									//���ֽ���
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//���ӶϿ�
			
			return resultSTR;
		} catch (Exception e) {
			// TODO: handle exception
			return "������쳣";
		}		
	}
	//************************************************************************************����
	/**
	 * ���� ע�� ����
	 */
	public String writeuserpassword(String userName,String password){
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\",\"����\":\"%s\"}",
				userName,password);
		return data;
	}
	/**
	 * ���� ��¼ ����
	 */
	public String writeLogin(String userName,String password){
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\",\"����\":\"%s\"}",
				userName,password);
		return data;
	}
	/**
	 * ���� �޸����� ����
	 */
	public String writeUpdata(String userName,String oldPassword,String newPassword){
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\",\"����\":\"%s\",\"������\":\"%s\"}",
				userName,oldPassword,newPassword);
		return data;
	}

}
