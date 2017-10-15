package HTTPclient;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.httpclient.HttpClient;

import playerwar.Door;
import playerwar.Explode;
import playerwar.Item;
import playerwar.Missile;
import playerwar.Player;
import playerwar.Wall;
import playerwar.PlayerClient.mainPanel;

import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginDlg extends JFrame {
	//***************************************������
	private static int WindowsWidth = 800, WindowsHeigth = 450, side = 50;
	
	private Container container;			//����������		
	//***************************************�ؼ�
	private Login jPanelLogin;				//��¼���
	private JLabel jLabel_beijing;			//��¼����
	//***************************************��������
	private buttonlistener btl;				//��ť��Ӧ�¼�
	private playerwar.PlayerClient CD;		//��ܼ�ָ��
	private String initname = "";			//��ʼ���û���
	private String initpassword = "";		//��ʼ������
	private String initloginPD = "";		//��ʼ���ж��Ƿ��Զ���¼
	private String userName = "";			//�û���
	private String password = "";			//����
	//�˳�ͼƬ
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image imageExit = tk.getImage(LoginDlg.class.getClassLoader().getResource("images/ѯ���˳�.gif"));
	private static Image imageBeijing = tk.getImage(LoginDlg.class.getClassLoader().getResource("images/��¼����.jpeg"));
	static{
		imageExit = imageExit.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		imageBeijing = imageBeijing.getScaledInstance(WindowsWidth + side , WindowsHeigth + side , Image.SCALE_DEFAULT);
	}
	private static Icon iconExit = new ImageIcon(imageExit);
	private static Icon iconBeijing = new ImageIcon(imageBeijing);
	
	/**
	 * ������¼������
	 */
	public LoginDlg(playerwar.PlayerClient cd) {
		btl = new buttonlistener();
		this.CD = cd;
		//********************************************������
		setTitle("��ӭ�������ߵأ�");
		setSize(WindowsWidth, WindowsHeigth);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {		
				int i = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�˳���","�˳�",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,iconExit);
				if(i == JOptionPane.OK_OPTION){
					System.exit(0);
				}
			}
		});		
		//********************************************����
		container = getContentPane();	
		container.setLayout(null);
		//********************************************�ؼ�
		jPanelLogin = new Login();																	//��¼���
		jLabel_beijing = new JLabel(iconBeijing);													//������
		jLabel_beijing.setBounds(-side/2, -side/2, WindowsWidth + side, WindowsHeigth + side);		//���ñ���λ�ô�С
		//*********************************************�ؼ�˳��
		container.add(jPanelLogin);
		container.add(jLabel_beijing);
		//********************************************��ʼ������
		 init();
	}
	
	/**
	 * �ؼ���Ӧ�¼�
	 * @author KamiAki
	 *
	 */
	private class buttonlistener implements ActionListener{	
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "�޸�����":
				LoginDlg.this.setEnabled(false);
				UpDataDlg updatadlg = new UpDataDlg(LoginDlg.this);
				updatadlg.setVisible(true);
				break;
			case "��¼":				
				userName = jPanelLogin.textField_useName.getText();
				password = jPanelLogin.textField_password.getText();			
					if(!userName.equals("")){
						if(!password.equals("")){				
							//**************************************************************��ʼ��¼��֤
							HTTPclient httPclient = new HTTPclient("192.168.199.148","1234");
							String returnSTR = httPclient.HTTPclientSendLogin(userName, password);	
							if(returnSTR.equals("��¼�ɹ�")){
								JOptionPane.showMessageDialog(null, "��¼�ɹ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);					
								LOGIN();
							}else if(returnSTR.equals("������쳣")){
								JOptionPane.showMessageDialog(null, "��¼������쳣��","��ʾ",JOptionPane.INFORMATION_MESSAGE);	
							}else{
								JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û��� �� �������","��ʾ",JOptionPane.INFORMATION_MESSAGE);					
							}	
						}else{
							JOptionPane.showMessageDialog(null, "����δ��д��","��ʾ",JOptionPane.ERROR_MESSAGE);
						}			
					}else{
						JOptionPane.showMessageDialog(null, "�û���δ��д��","��ʾ",JOptionPane.ERROR_MESSAGE);
					}				
				break;
			case "�Զ���¼":

				if(jPanelLogin.CheckBox_password.isSelected() == false && jPanelLogin.CheckBox_login.isSelected() == true){
					jPanelLogin.CheckBox_password.setSelected(true);
				}else if(jPanelLogin.CheckBox_password.isSelected() == true && jPanelLogin.CheckBox_login.isSelected() == false){
					jPanelLogin.CheckBox_password.setSelected(false);
				}
				break;
			case "��ס����":
				if(jPanelLogin.CheckBox_password.isSelected() == false && jPanelLogin.CheckBox_login.isSelected() == true){
					jPanelLogin.CheckBox_login.setSelected(false);
				}
				break;
			case "ע��":
				LoginDlg.this.setEnabled(false);
				JoinDlg frame = new JoinDlg(LoginDlg.this);
				frame.setVisible(true);
				break;
			default:
				break;
			}
		}
	}
	/**
	 * ��¼���
	 * @author Administrator
	 *
	 */
	private class Login extends JPanel{
		//��Ա����
		public GridBagLayout gridBagLayout;					//���������
		public JTextField textField_useName;				//�û���
		public JPasswordField textField_password;			//����	
		public JLabel Label_name;							//�û���ͼ��
		public JLabel Label_pass;							//����ͼ��		
		public JButton Button_Login;						//��¼��ť
		public JCheckBox CheckBox_password;					//��������
		public JCheckBox CheckBox_login;					//�Զ���¼
		public JButton Button_join;							//ע�ᰴť
		public JButton Button_updata;						//�޸����밴ť
		/**
		 * ���캯��
		 */
		public Login(){
			gridBagLayout = new GridBagLayout();									//����gridbag����
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};		//���Ƿ���չ
			gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 0};				//��Ȩ��,��֮��ı���
			gridBagLayout.columnWeights = new double[]{0.0,0.0, 0.0};				//���Ƿ���չ
			gridBagLayout.columnWidths = new int[]{100,100, 100};					//��Ȩ��,��֮��ı���
			this.setLayout(gridBagLayout);											//������ò���Ϊgridbag
			this.setBounds(40, 180, 350, 190);										//�������λ�ã���С
			this.setOpaque(false); 													//����͸����
			
			//********************************************************�û���
			textField_useName = new JTextField();
			textField_useName.setForeground(Color.GRAY);
			textField_useName.setText("�û���");
			textField_useName.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(textField_useName.getText().equals("�û���")){
						textField_useName.setForeground(Color.BLACK);
						textField_useName.setText("");
					}
				}
				public void focusLost(FocusEvent e) {
					if(textField_useName.getText().equals("")){
						textField_useName.setForeground(Color.GRAY);
						textField_useName.setText("�û���");
					}
				}
			});
			textField_useName.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_textField_useName = new GridBagConstraints();
			gbc_textField_useName.gridwidth = 2;
			gbc_textField_useName.fill = GridBagConstraints.BOTH;
			gbc_textField_useName.insets = new Insets(0, 0, 5, 0);
			gbc_textField_useName.gridx = 0;
			gbc_textField_useName.gridy = 0;
			this.add(textField_useName, gbc_textField_useName);
			//********************************************************����
			textField_password = new JPasswordField();
			textField_password.setForeground(Color.GRAY);
			textField_password.setText("��¼����");
			textField_password.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(textField_password.getText().equals("��¼����")){
						textField_password.setForeground(Color.BLACK);
						textField_password.setText("");
					}
				}
				public void focusLost(FocusEvent e) {
					if(textField_password.getText().equals("")){
						textField_password.setForeground(Color.GRAY);
						textField_password.setText("��¼����");
					}
				}
			});
			textField_password.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_textField_password = new GridBagConstraints();
			gbc_textField_password.gridwidth = 2;
			gbc_textField_password.fill = GridBagConstraints.BOTH;
			gbc_textField_password.insets = new Insets(0, 0, 5, 0);
			gbc_textField_password.gridx = 0;
			gbc_textField_password.gridy = 1;
			this.add(textField_password, gbc_textField_password);	
			//********************************************************��ס����
			CheckBox_password = new JCheckBox("��ס����");
			CheckBox_password.setOpaque(false);
			CheckBox_password.addActionListener(btl);
			CheckBox_password.setActionCommand("��ס����");
			CheckBox_password.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = 2;
			this.add(CheckBox_password, gbc_chckbxNewCheckBox);
			//********************************************************�Զ���¼
			CheckBox_login = new JCheckBox("�Զ���¼");
			CheckBox_login.setOpaque(false);
			CheckBox_login.addActionListener(btl);
			CheckBox_login.setActionCommand("�Զ���¼");
			CheckBox_login.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_1.fill = GridBagConstraints.BOTH;
			gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox_1.gridx = 1;
			gbc_chckbxNewCheckBox_1.gridy = 2;
			this.add(CheckBox_login, gbc_chckbxNewCheckBox_1);
			//********************************************************��¼
			Button_Login = new JButton("��¼");
			Button_Login.addActionListener(btl);
			Button_Login.setActionCommand("��¼");
			GridBagConstraints gbc_Button_Login = new GridBagConstraints();
			gbc_Button_Login.fill = GridBagConstraints.BOTH;
			gbc_Button_Login.gridwidth = 2;
			gbc_Button_Login.insets = new Insets(0, 0, 5, 0);
			gbc_Button_Login.gridx = 0;
			gbc_Button_Login.gridy = 3;
			this.add(Button_Login, gbc_Button_Login);
			//********************************************************ע��
			Button_join = new JButton("ע��");
			Button_join.addActionListener(btl);
			Button_join.setActionCommand("ע��");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.gridy = 4;
			this.add(Button_join, gbc_btnNewButton);
			//********************************************************�޸�����
			Button_updata = new JButton("�޸�����");
			Button_updata.addActionListener(btl);
			Button_updata.setActionCommand("�޸�����");
			GridBagConstraints gbc_Button_updata = new GridBagConstraints();
			gbc_Button_updata.fill = GridBagConstraints.BOTH;
			gbc_Button_updata.gridx = 1;
			gbc_Button_updata.gridy = 4;
			this.add(Button_updata, gbc_Button_updata);
		}	
	}
	/**
	 * ��ʼ������
	 */
	public void init(){
		LoginDlg.this.setVisible(true);
		initname = FileRelevant.filepathread("logindata.jw",1);
		initpassword = FileRelevant.filepathread("logindata.jw",2);
		initloginPD = FileRelevant.filepathread("logindata.jw",3);	
		jPanelLogin.textField_useName.setForeground(Color.BLACK);
		jPanelLogin.textField_useName.setText(initname);								//�����û���
		
		if(!initpassword.equals("") && initloginPD.equals("����¼")){
			jPanelLogin.textField_password.setForeground(Color.BLACK);
			jPanelLogin.textField_password.setText(initpassword);						//������������˾Ͷ�ȡ
			jPanelLogin.CheckBox_password.setSelected(true);							//ѡ�б�������
		}else if(!initpassword.equals("") && initloginPD.equals("�Զ���¼")){
			jPanelLogin.textField_password.setForeground(Color.BLACK);
			jPanelLogin.textField_password.setText(initpassword);						//������������˾Ͷ�ȡ
			jPanelLogin.CheckBox_password.setSelected(true);							//ѡ�б�������
			jPanelLogin.CheckBox_login.setSelected(true);								//ѡ���Զ���¼
			LOGIN();
		}
	}
	
	/**
	 * ��¼����
	 */
	public void LOGIN(){																			//ˢ����֤��
		String SAVEneirong;
		if( jPanelLogin.CheckBox_password.isSelected() == true && jPanelLogin.CheckBox_login.isSelected() == false){
			//ѡ��������
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + jPanelLogin.textField_password.getText() + "\r\n����¼";
		}else if( jPanelLogin.CheckBox_login.isSelected() == true ){
			//ѡ�Զ���¼
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + jPanelLogin.textField_password.getText() + "\r\n�Զ���¼";
		}else {
			//ʲô��ûѡ
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + "" + "\r\n����¼";
		}	
		FileRelevant.filepathsave("logindata.jw", SAVEneirong); //����ı�
		initname = FileRelevant.filepathread("logindata.jw",1);	//��ȡ�û���
		
		CD.gamestart();											//��Ϸ����
		LoginDlg.this.dispose();								//�رյ�¼����
	}
}
