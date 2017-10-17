package HTTPserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;
import java.sql.Timestamp;

public class MySQLRelevant {
	
	public String addr = "";
	public String port = "";
	public String SQLname = "";
	public String name = "";
	public String password = "";	
	public Connection con = null;
	public PreparedStatement pst = null;

	//*************************************************************************** �û�ע�� �޸������
	//�����û�ID
	public void connectionPlayerGameID(){
		String url = null;
		url = String.format("jdbc:mysql://rm-2ze354j79ce6ky541o.mysql.rds.aliyuncs.com:3306/aki");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,"beijingjiuwei","jiuwei123-"); 		 
		}catch ( Exception e ) {    
	        e.printStackTrace();  
	    }   		
	}
	//�ر����ݿ�
	public void deconnectionSQL(){
		try {
			if(con != null){
				con.close();
			}else{
//				JOptionPane.showMessageDialog(null,"���ݿ�δ���ӣ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
//			JOptionPane.showMessageDialog(null,"���ݿ�ر�ʧ�ܣ�" + e);
			e.printStackTrace();
		}
	}
	//д�����û���Ϣ
	public boolean writePlayerGameID(Map<String, String> map){
		String insert = null;
		String userName = null,password = null;
		try {
			if(con != null){											
				userName = map.get("�û���");
				password = map.get("����");
				insert = String.format("insert into playerwarid (�û���,����)"
									+ " values(?,?)");
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, password);		
				pst.executeUpdate(); //ִ�и���	
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {  
            e.printStackTrace();  
            return false;
        } 
	}
	//�޸��û���Ϣ
	public boolean updataPlayerGameID(Map<String, String> map){	
		String update = "";
		String userName = "",newPassWord = "";	
		userName = map.get("�û���");
		newPassWord = map.get("������");
		try {
			if(con != null){		
				update = String.format("update %s set ���� = '%s' where �û���  = '%s'", 
						"userid", newPassWord, userName);
				pst = con.prepareStatement(update);		
				pst.executeUpdate();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {  
            e.printStackTrace(); 
            return false;
		}
	}
	//��ѯ�û����Ƿ����
	public boolean selectPlayerGameUserNameID(Map<String, String> map){	
		String select = "";
		String userName = "";
		
		ResultSet rs = null;  	
		
		userName = map.get("�û���");
		try {
			if(con != null){
				select = String.format("select * from %s where �û��� = '%s'", 
						"playerwarid", userName);
				//������ѯ
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				if(rs.next()){
					rs.close();
					return true;
				}else{
					rs.close();
					return false;
				}
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//��ѯ�û���,�����Ƿ���ȷ
	public boolean selectPlayerGameUserNamePassWordID(Map<String, String> map){	
		String select = "";
		String userName = "";
		String passWord = "";
		String SQLpassword = "";
		
		ResultSet rs = null;  	
		
		userName = map.get("�û���");
		passWord = map.get("����");
		try {
			if(con != null){
				select = String.format("select * from %s where �û��� = '%s'", 
						"playerwarid", userName);
				//������ѯ
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				if(rs.next()){
					SQLpassword = rs.getString("����");
					if( passWord.equals( SQLpassword ) ){
						rs.close();
						return true;
					}else{
						rs.close();
						return false;
					}	
				}else{
					rs.close();
					return false;
				}
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
