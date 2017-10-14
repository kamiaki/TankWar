package HTTPclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONObject;

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
	public String HTTPclientSendJoin(String userName,String name,String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/join");			//URL��ַ
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP��URL����             
//			conn.setConnectTimeout(30000);										//��������������ʱ����λ�����룩 
//			conn.setReadTimeout(30000); 										//���ô�������ȡ���ݳ�ʱ����λ�����룩       
		    conn.setRequestMethod("POST");										//����URL����ķ���       
		    conn.setDoInput(true);												//���� ����POST�����������
		    conn.setDoOutput(true);												//���  ����POST�����������      
		    conn.setUseCaches(true);											//�ͻ�����ʹ�û���
		    
			String SENDstr = "";												//���ַ���
			SENDstr = writeuserpassword(userName,name,password);
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
//			conn.setConnectTimeout(30000);										//��������������ʱ����λ�����룩 
//			conn.setReadTimeout(30000); 										//���ô�������ȡ���ݳ�ʱ����λ�����룩       
		    conn.setRequestMethod("POST");										//����URL����ķ���       
		    conn.setDoInput(true);												//���� ����POST�����������
		    conn.setDoOutput(true);												//���  ����POST�����������      
		    conn.setUseCaches(true);											//�ͻ�����ʹ�û���
		    
			String SENDstr = "";												//���ַ���
			SENDstr = writeLogin(userName,password);
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
	 * �޸�����
	 */
	public String HTTPclientSendUpdata(String userName,String oldPassword,String newPassword){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/updata");		//URL��ַ
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP��URL����             
//			conn.setConnectTimeout(30000);										//��������������ʱ����λ�����룩 
//			conn.setReadTimeout(30000); 										//���ô�������ȡ���ݳ�ʱ����λ�����룩       
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

	/**
	 * �����û�������Ϣ
	 * @param userName
	 * @param NeiRong
	 */
	public void UserMessage(String userName,String NeiRong){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/userMessage");		
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(true);
			
			String sendSTR = "";											//���ַ���
			sendSTR = writeUserMessage(userName,NeiRong);
			byte[] FSwz = sendSTR.getBytes();
			OutputStream ots = conn.getOutputStream();
			ots.write(FSwz);
			ots.flush();
			ots.close();
			
			byte[] buffer = new byte[1024];									//���ֽ���
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//���ӶϿ�
			System.out.println(resultSTR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� �û���ʷ��Ϣ
	 * @param userName
	 * @param v
	 */
	public void UserHistory(String userName,Vector<String> v){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/userHistory");		
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(true);
			
			String sendSTR = "";											//���ַ���
			sendSTR = writeUserHistory(userName);
			byte[] FSwz = sendSTR.getBytes();
			OutputStream ots = conn.getOutputStream();
			ots.write(FSwz);
			ots.flush();
			ots.close();
					
			InputStream in = conn.getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inr);         
			String line = "";
			StringBuilder builder = new StringBuilder();
			while((line = br.readLine())!=null){
				builder.append(line);
			}	
			in.close();     
			conn.disconnect();										//���ӶϿ�
			
			String resultSTR = null;        
			resultSTR = builder.toString();													
			JSONObject jsonObject = new JSONObject();
			Map<String, String> map = new HashMap<String,String>();
			jsonObject = JSONObject.fromObject(resultSTR);
			map.putAll(jsonObject);
			for (int i = 0; i < jsonObject.size(); i++) {
				v.add(map.get(String.valueOf(i+1)));				//������ �� 1 ��ʼ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//************************************************************************************����
	/**
	 * ���� ע�� ����
	 */
	public String writeuserpassword(String userName,String name,String password){
		//*************************************************************��������
		String datetime = "";		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");		//�������ڸ�ʽ
		datetime = df.format(new Date());								// new Date()Ϊ��ȡ��ǰϵͳʱ��
		
		//*************************************************************����IP��ַMAC��
		String IpMac = "";
		try {
			String Ip = InetAddress.getLocalHost().toString();
			
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<mac.length; i++) {
				if(i!=0) {
					sb.append("-");
				}	
				int temp = mac[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			String MacStr = sb.toString().toUpperCase();	
			IpMac = Ip + "@" + MacStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\",\"����\":\"%s\",\"����\":\"%s\",\"ע������\":\"%s\",\"IP��ַ\":\"%s\"}",
				userName,name,password,datetime,IpMac);
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
	/**
	 * д�û�������Ϣ
	 * @param userName
	 * @param NeiRong
	 * @return
	 */
	public String writeUserMessage(String userName,String NeiRong){
		
		//*************************************************************��������
		String datetime = "";		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		//�������ڸ�ʽ
		datetime = df.format(new Date());										// new Date()Ϊ��ȡ��ǰϵͳʱ��
		
		//*************************************************************����IP��ַMAC��
		String IpMac = "";
		try {
			String Ip = InetAddress.getLocalHost().toString();
			
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<mac.length; i++) {
				if(i!=0) {
					sb.append("-");
				}	
				int temp = mac[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			String MacStr = sb.toString().toUpperCase();	
			IpMac = Ip + "@" + MacStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\",\"����\":\"%s\",\"����\":\"%s\",\"IP��ַ\":\"%s\"}",
				userName,NeiRong,datetime,IpMac);
		return data;
	}
	/**
	 * ���� �û���ʷ��Ϣ
	 * @param userName
	 * @return
	 */
	public String writeUserHistory(String userName){
		//*************************************************************����map
		String data = String.format("{\"�û���\":\"%s\"}",
				userName);
		return data;
	}
}
