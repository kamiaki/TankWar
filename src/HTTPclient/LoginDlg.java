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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.httpclient.HttpClient;

import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class LoginDlg extends JFrame {
	
	private Container container;			//����һ������
	private JPanel jPanelLogin;
	
	private GridBagLayout gridBagLayout;
	
	private JTextField textField_useName;
	private JPasswordField textField_password;
	private JLabel Label_useName;
	private JLabel Label_password;
	private JButton Button_Login;
	private JLabel Label_readCode;
	private JCheckBox CheckBox_password;
	private JCheckBox CheckBox_login;
	private JButton Button_join;
	private JTextField textField_Code;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	
	private buttonlistener btl;
	
	playerwar.PlayerClient CD;
	
	String initname = "";
	String initpassword = "";
	String initloginPD = "";
	
	String StrCode = "";
	String INStrCode = "";
	String userName = "";
	String password = "";

	/**
	 * ������¼������
	 */
	public LoginDlg(playerwar.PlayerClient cd) {
		btl = new buttonlistener();
		this.CD = cd;
		//********************************************������
		setTitle("�������ϴ�����¼");
		setSize(437, 246);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = getContentPane();	
		container.setLayout(null);
		
		//********************************************�ؼ�	
		gridBagLayout = new GridBagLayout();										//����gridbag����
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};			//���Ƿ���չ
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30};					//��Ȩ��,��֮��ı���
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};						//���Ƿ���չ
		gridBagLayout.columnWidths = new int[]{120, 120};							//��Ȩ��,��֮��ı���
		jPanelLogin = new JPanel();													//�������
		jPanelLogin.setLayout(gridBagLayout);										//������ò���Ϊgridbag
		jPanelLogin.setBounds(83, 12, 265, 173);									//�������λ�ã���С
		container.add(jPanelLogin);
		
		Label_useName = new JLabel("�� �� ��");
		Label_useName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_Label_useName = new GridBagConstraints();
		gbc_Label_useName.fill = GridBagConstraints.BOTH;
		gbc_Label_useName.insets = new Insets(0, 0, 5, 5);
		gbc_Label_useName.gridx = 0;
		gbc_Label_useName.gridy = 0;
		jPanelLogin.add(Label_useName, gbc_Label_useName);
		
		textField_useName = new JTextField();
		textField_useName.setForeground(Color.GRAY);
		textField_useName.setText("�ֻ���/����");
		textField_useName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_useName.getText().equals("�ֻ���/����")){
					textField_useName.setForeground(Color.BLACK);
					textField_useName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_useName.getText().equals("")){
					textField_useName.setForeground(Color.GRAY);
					textField_useName.setText("�ֻ���/����");
				}
			}
		});
		textField_useName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_useName = new GridBagConstraints();
		gbc_textField_useName.fill = GridBagConstraints.BOTH;
		gbc_textField_useName.insets = new Insets(0, 0, 5, 0);
		gbc_textField_useName.gridx = 1;
		gbc_textField_useName.gridy = 0;
		jPanelLogin.add(textField_useName, gbc_textField_useName);
		
		Label_password = new JLabel("��    ��");
		Label_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_Label_password = new GridBagConstraints();
		gbc_Label_password.fill = GridBagConstraints.BOTH;
		gbc_Label_password.insets = new Insets(0, 0, 5, 5);
		gbc_Label_password.gridx = 0;
		gbc_Label_password.gridy = 1;
		jPanelLogin.add(Label_password, gbc_Label_password);
		
		textField_password = new JPasswordField();
		textField_password.setForeground(Color.GRAY);
		textField_password.setText("��¼����");
		textField_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_password.getText().equals("��¼����")){
					textField_password.setForeground(Color.BLACK);
					textField_password.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_password.getText().equals("")){
					textField_password.setForeground(Color.GRAY);
					textField_password.setText("��¼����");
				}
			}
		});
		textField_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.fill = GridBagConstraints.BOTH;
		gbc_textField_password.insets = new Insets(0, 0, 5, 0);
		gbc_textField_password.gridx = 1;
		gbc_textField_password.gridy = 1;
		jPanelLogin.add(textField_password, gbc_textField_password);
		
		Label_readCode = new JLabel("��֤��");
		Label_readCode.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		jPanelLogin.add(Label_readCode, gbc_lblNewLabel_1);
		Label_readCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				readCode();
			}
		});
		
		textField_Code = new JTextField();
		textField_Code.setForeground(Color.GRAY);
		textField_Code.setText("��֤��");
		textField_Code.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_Code.getText().equals("��֤��")){
					textField_Code.setForeground(Color.BLACK);
					textField_Code.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_Code.getText().equals("")){
					textField_Code.setForeground(Color.GRAY);
					textField_Code.setText("��֤��");
				}
			}
		});
		textField_Code.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		jPanelLogin.add(textField_Code, gbc_textField);
		textField_Code.setColumns(10);
		
		CheckBox_password = new JCheckBox("��ס����");
		CheckBox_password.addActionListener(btl);
		CheckBox_password.setActionCommand("��ס����");
		CheckBox_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.SOUTHEAST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 3;
		jPanelLogin.add(CheckBox_password, gbc_chckbxNewCheckBox);
		
		CheckBox_login = new JCheckBox("�Զ���¼");
		CheckBox_login.addActionListener(btl);
		CheckBox_login.setActionCommand("�Զ���¼");
		CheckBox_login.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 3;
		jPanelLogin.add(CheckBox_login, gbc_chckbxNewCheckBox_1);
		

		Button_join = new JButton("ע��");
		Button_join.addActionListener(btl);
		Button_join.setActionCommand("ע��");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		jPanelLogin.add(Button_join, gbc_btnNewButton);
		
		Button_Login = new JButton("��¼");
		Button_Login.addActionListener(btl);
		Button_Login.setActionCommand("��¼");
		GridBagConstraints gbc_Button_Login = new GridBagConstraints();
		gbc_Button_Login.anchor = GridBagConstraints.SOUTHWEST;
		gbc_Button_Login.gridx = 1;
		gbc_Button_Login.gridy = 4;
		jPanelLogin.add(Button_Login, gbc_Button_Login);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("����");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("�޸�����");
		mntmNewMenuItem.addActionListener(btl);
		mntmNewMenuItem.setActionCommand("�޸�����");
		mnNewMenu.add(mntmNewMenuItem);
				
		//********************************************��ʼ������
		 init();
	}
	
	/**
	 * �ؼ���Ӧ�¼�
	 * @author KamiAki
	 *
	 */
	public class buttonlistener implements ActionListener{	
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "�޸�����":
				LoginDlg.this.setEnabled(false);
				UpDataDlg updatadlg = new UpDataDlg(LoginDlg.this);
				updatadlg.setVisible(true);
				break;
			case "��¼":				
				INStrCode = textField_Code.getText();
				userName = textField_useName.getText();
				password = textField_password.getText();
				
				if( INStrCode.equals(StrCode) ){
					if(!userName.equals("")){
						if(!password.equals("")){
							
							//**************************************************************��ʼ��¼��֤
							HTTPclient httPclient = new HTTPclient("123","123");
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
							JOptionPane.showMessageDialog(null, "�������","��ʾ",JOptionPane.ERROR_MESSAGE);
						}			
					}else{
						JOptionPane.showMessageDialog(null, "�û�������","��ʾ",JOptionPane.ERROR_MESSAGE);
					}	
				}else{
					JOptionPane.showMessageDialog(null, "��֤�벻��Ŷ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "�Զ���¼":

				if(CheckBox_password.isSelected() == false && CheckBox_login.isSelected() == true){
					CheckBox_password.setSelected(true);
				}else if(CheckBox_password.isSelected() == true && CheckBox_login.isSelected() == false){
					CheckBox_password.setSelected(false);
				}
				break;
			case "��ס����":
				if(CheckBox_password.isSelected() == false && CheckBox_login.isSelected() == true){
					CheckBox_login.setSelected(false);
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
	 * ��ʼ������
	 */
	public void init(){
		readCode();															//������֤��
		LoginDlg.this.setVisible(true);
		initname = FileRelevant.filepathread("logindata.jw",1);
		initpassword = FileRelevant.filepathread("logindata.jw",2);
		initloginPD = FileRelevant.filepathread("logindata.jw",3);	
		textField_useName.setForeground(Color.BLACK);
		textField_useName.setText(initname);								//�����û���
		
		if(!initpassword.equals("") && initloginPD.equals("����¼")){
			textField_password.setForeground(Color.BLACK);
			textField_password.setText(initpassword);						//������������˾Ͷ�ȡ
			CheckBox_password.setSelected(true);							//ѡ�б�������
		}else if(!initpassword.equals("") && initloginPD.equals("�Զ���¼")){
			textField_password.setForeground(Color.BLACK);
			textField_password.setText(initpassword);						//������������˾Ͷ�ȡ
			CheckBox_password.setSelected(true);							//ѡ�б�������
			CheckBox_login.setSelected(true);								//ѡ���Զ���¼
			LOGIN();
		}
	}
	
	/**
	 * ��¼����
	 */
	public void LOGIN(){
		readCode();																				//ˢ����֤��
		String SAVEneirong;
		if( CheckBox_password.isSelected() == true && CheckBox_login.isSelected() == false){
			SAVEneirong = textField_useName.getText() + "\r\n" + textField_password.getText() + "\r\n����¼";
		}else if( CheckBox_login.isSelected() == true ){
			SAVEneirong = textField_useName.getText() + "\r\n" + textField_password.getText() + "\r\n�Զ���¼";
		}else {
			SAVEneirong = textField_useName.getText() + "\r\n" + "" + "\r\n����¼";
		}	
		FileRelevant.filepathsave("logindata.jw", SAVEneirong);
		initname = FileRelevant.filepathread("logindata.jw",1);
		
		CD.setVisible(true);
		LoginDlg.this.dispose();
	}
	
	/**
	 * ������֤��
	 */
	public void readCode(){
		StrCode = String.valueOf(  (int)((Math.random()*9+1)*1000)  );
		createImage(StrCode,new Font("����", Font.BOLD, 14),Label_readCode);		
	}
	/**
	 * ����תͼƬ
	 */
	public void createImage(String str,Font font,JLabel codelabel) {
		//��ȡfont����ʽӦ����str�ϵ���������
		Rectangle2D r=font.getStringBounds(str, new FontRenderContext(AffineTransform.getScaleInstance(1, 1),false,false));
		int width=(int)Math.round(r.getWidth())+1;		//��ȡ����str����font��ʽ�Ŀ�����������������+1��֤��Ⱦ�������������ַ�����ΪͼƬ�Ŀ��
		int height=(int)Math.floor(r.getHeight())+3;		//�ѵ����ַ��ĸ߶�+3��֤�߶Ⱦ����������ַ�����ΪͼƬ�ĸ߶�
		//����ͼƬ
		BufferedImage CreateImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics g = CreateImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);//���ð�ɫ�������ͼƬ,Ҳ���Ǳ���
		g.setColor(Color.black);//�ڻ��ɺ�ɫ
		g.setFont(font);//���û�������
		g.drawString(str, 0, font.getSize());//�����ַ���
		g.dispose();
		//���ͼƬ
		ImageIcon imageicon = new ImageIcon(CreateImage);
		//���ñ�ǩ
		Label_readCode.setText("");
		Label_readCode.setIcon(imageicon);
	}
}
