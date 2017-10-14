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
	//***************************************************************************mysql����ʾ��
	//���캯��
	public MySQLRelevant(){
	}
	//�������ݿ�
	public void connectionSQL(String addr, String port, String SQLname, String name, String password){
		this.addr = addr;
		this.port = port;
		this.SQLname = SQLname;
		this.name = name;
		this.password = password;
		
		String url = null;
//		url = "jdbc:mysql://localhost:3306/hello?characterEncoding=UTF-8"; 
		url = String.format("jdbc:mysql://%s:%s/%s", addr,port,SQLname);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,name,password);
			JOptionPane.showMessageDialog(null,"�������ݿ�" + SQLname + "�ɹ���");
		}catch ( ClassNotFoundException cnfex ) {    
	        JOptionPane.showMessageDialog(null,"//����������������쳣  װ�� JDBC/ODBC ��������ʧ�ܡ�" + cnfex);
	        cnfex.printStackTrace();  
	    }   
	    catch ( SQLException sqlex ) {  
	        JOptionPane.showMessageDialog(null,"//�����������ݿ��쳣  �޷��������ݿ�" + sqlex);
	        sqlex.printStackTrace(); 
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
	//д�����ݿ�
	public void writeSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String insert = "insert into qandrtable(����,A,B,����) values('wenti','aaaa','bbbb',123)";  
				pst = con.prepareStatement(insert);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"���ݿ�δ���ӣ�");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"�������ݿ�ʱ����" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"����ʱ����" + e);
            e.printStackTrace();  
        }  
	}
	//�������ݿ�
	public void updataSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String update = "update qandrtable set C = '123' where ���� = 'wenti'";
				pst = con.prepareStatement(update);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"���ݿ�δ���ӣ�");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"�������ݿ�ʱ����" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"����ʱ����" + e);
            e.printStackTrace();  
        }  
	}
	//ɾ�����ݿ�
	public void deleteSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String delete = "delete from qandrtable where ���� = 'wenti'";  
				pst = con.prepareStatement(delete);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"���ݿ�δ���ӣ�");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"ɾ�����ݿ�ʱ����" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"ɾ��ʱ����" + e);
            e.printStackTrace();  
        }  
	}
	//��ѯ���ݿ�
	public void selectSQL(String table, List<Map<String, String>> maplist){
		ResultSet rs = null;  
		try {
			if(con != null){
				String select = "select * from qandrtable";
				String str = null;
				int zonging = 0;
				//������ѯ
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				while (rs.next()) {  
					str = " ����:" + rs.getInt("����") + " A:" + rs.getString("A") + " B:" + rs.getString("B"); 
					JOptionPane.showMessageDialog(null,"��ѯ���ݣ�" + str);
				} 
				//����
				select = "select count(*) totalCount from qandrtable";
				rs = pst.executeQuery(select);//ResultSet�࣬������Ż�ȡ�Ľ��������
				while(rs.next()) {   
	       		 zonging = rs.getInt("totalCount");
				}  	   
				JOptionPane.showMessageDialog(null,"��ѯ������" + zonging);
				rs.close();
			}else{
				JOptionPane.showMessageDialog(null,"���ݿ�δ���ӣ�");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"��ѯ���ݿ�ʱ����" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"��ѯʱ����" + e);
            e.printStackTrace();  
        }  
	}
		
	//*************************************************************************** �û�ע�� �޸������
	//�����û�ID
	public void connectionPlayerGameID(){
		String url = null;
		url = String.format("jdbc:mysql://rm-2ze354j79ce6ky541o.mysql.rds.aliyuncs.com:3306/playerwarid");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,"beijingjiuwei","jiuwei123-"); 		 
		}catch ( Exception e ) {    
	        e.printStackTrace();  
	    }   		
	}
	//д�����û���Ϣ
	public boolean writePlayerGameID(Map<String, String> map){
		String insert = null;
		String userName = null,name = null,password = null,joinDate = null,IPLocation = null;
		try {
			if(con != null){											
				userName = map.get("�û���");
				name = map.get("����");
				password = map.get("����");
				joinDate = map.get("ע������");
				IPLocation = map.get("IP��ַ");
				insert = String.format("insert into userid (�û���,����,����,ע������,IP��ַ)"
									+ " values(?,?,?,?,?)");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, password);
				pst.setString(3, name);
				Date dateXD = new Date();  
				dateXD = sdf.parse(joinDate); 
				pst.setDate(4,new java.sql.Date(dateXD.getTime())); 				
				pst.setString(5, IPLocation);			
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
						"userid", userName);
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
						"userid", userName);
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
	//�û�������¼
	public boolean PlayerGameuserRecord(Map<String, String> map){
		String insert = "";
		String userName = "";
		String IP = "";
		String NeiRong = "";
		String Date = "";		
		ResultSet rs = null;  			
		userName = map.get("�û���");
		IP = map.get("IP��ַ");
		NeiRong = map.get("����");
		Date = map.get("����");	
		try {
			if(con != null){											

				insert = String.format("insert into userrecord (�û���,IP��ַ,����,����)"
									+ " values(?,?,?,?)");
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, IP);
				pst.setString(3, NeiRong);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
				Date dateXD = new Date();  
				dateXD = sdf.parse(Date); 
				Timestamp tt = new Timestamp(dateXD.getTime());			
				pst.setTimestamp(4,tt); 
				
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
	//�û���ȡ��ʷ��¼
	public boolean PlayerGameuserHistory(Map<String, String> map,JSONObject object){
		int xuhao = 0;
		String xuhaoSTR = "";
		String select = "";
		String userName = "";	
		String NeiRong = "";
		ResultSet rs = null;  	
		
		userName = map.get("�û���");
		
		try {
			if(con != null){
				select = String.format("select * from %s where �û���  = '%s'", 
						"userrecord", userName);
				//������ѯ
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				while(rs.next()){
					xuhao++;
					xuhaoSTR = String.valueOf(xuhao);
					NeiRong = rs.getString("����") + "-" + rs.getDate("����");
					object.put(xuhaoSTR, NeiRong);		
				}			
				rs.close();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
