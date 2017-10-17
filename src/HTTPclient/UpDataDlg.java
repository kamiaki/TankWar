package HTTPclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class UpDataDlg extends JFrame implements ClientInit{

	private JPanel contentPane;
	
	private LoginDlg pLoginDlg;
	private JTextField textField_oldpassword;
	private JPasswordField textField_newpassword;
	private JPasswordField textField_newpassword2;
	
	private buttonlistener btl;
	
	private String userName = "";
	private String oldpassword = "";
	private String password = "";
	private String password2 = "";
	private JTextField textField_userName;
	/**
	 * Create the frame.
	 */
	public UpDataDlg(LoginDlg ld) {
		this.pLoginDlg = ld;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				pLoginDlg.setEnabled(true);
			}
		});
		btl = new buttonlistener();
		//**************************************************������
		setTitle("�޸�����");
		setSize(360, 202);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(73, 9, 208, 156);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{93, 87};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel_3 = new JLabel("�û���");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_userName = new JTextField();
		textField_userName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_userName = new GridBagConstraints();
		gbc_textField_userName.fill = GridBagConstraints.BOTH;
		gbc_textField_userName.insets = new Insets(0, 0, 5, 0);
		gbc_textField_userName.gridx = 1;
		gbc_textField_userName.gridy = 0;
		panel.add(textField_userName, gbc_textField_userName);
		textField_userName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ԭ����");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_oldpassword = new JTextField();
		textField_oldpassword.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_oldpassword = new GridBagConstraints();
		gbc_textField_oldpassword.fill = GridBagConstraints.BOTH;
		gbc_textField_oldpassword.insets = new Insets(0, 0, 5, 0);
		gbc_textField_oldpassword.gridx = 1;
		gbc_textField_oldpassword.gridy = 1;
		panel.add(textField_oldpassword, gbc_textField_oldpassword);
		textField_oldpassword.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("������");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_newpassword = new JPasswordField();
		textField_newpassword.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_newpassword = new GridBagConstraints();
		gbc_textField_newpassword.fill = GridBagConstraints.BOTH;
		gbc_textField_newpassword.insets = new Insets(0, 0, 5, 0);
		gbc_textField_newpassword.gridx = 1;
		gbc_textField_newpassword.gridy = 2;
		panel.add(textField_newpassword, gbc_textField_newpassword);
		textField_newpassword.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ȷ������");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_newpassword2 = new JPasswordField();
		textField_newpassword2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_newpassword2 = new GridBagConstraints();
		gbc_textField_newpassword2.fill = GridBagConstraints.BOTH;
		gbc_textField_newpassword2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_newpassword2.gridx = 1;
		gbc_textField_newpassword2.gridy = 3;
		panel.add(textField_newpassword2, gbc_textField_newpassword2);
		textField_newpassword2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("ȡ��");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 4;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.addActionListener(btl);
		btnNewButton_1.setActionCommand("ȡ��");
		
		JButton btnNewButton = new JButton("�޸�");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		panel.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(btl);
		btnNewButton.setActionCommand("�޸�");
		
		//**************************************************�ؼ�
	}

	/**
	 * ��ť��Ӧ�¼�
	 * @author Administrator
	 *
	 */
	public class buttonlistener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO �Զ����ɵķ������
			switch (e.getActionCommand()) {		
			case "�޸�":
				userName = textField_userName.getText();
				oldpassword	= textField_oldpassword.getText();
				password = textField_newpassword.getText();
				password2 = textField_newpassword2.getText();
				
				if(!userName.equals("")){
					if(!oldpassword.equals("")){
						if(!password.equals("")){
							if(!password2.equals("")){
								if(password2.equals(password)){
									
									//**********************************************��ʼ�޸�����
									HTTPclient httPclient = new HTTPclient(InitIP,InitPort);
									String returnSTR = httPclient.HTTPclientSendUpdata(userName,oldpassword,password);
									if(returnSTR.equals("�޸ĳɹ�")){
										JOptionPane.showMessageDialog(null, "�޸ĳɹ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);					
										pLoginDlg.setEnabled(true);
										UpDataDlg.this.dispose();	
									}else if(returnSTR.equals("������쳣")){
										JOptionPane.showMessageDialog(null, "�޸ķ�����쳣��","��ʾ",JOptionPane.INFORMATION_MESSAGE);	
									}else{
										JOptionPane.showMessageDialog(null, "�޸�ʧ�ܣ��û��� �� ԭ�����������","��ʾ",JOptionPane.INFORMATION_MESSAGE);					
									}								
									
								}else{
									JOptionPane.showMessageDialog(null, "�������벻һ��!","��ʾ",JOptionPane.ERROR_MESSAGE);
								}			
							}else{
								JOptionPane.showMessageDialog(null, "����д��ȷ��ȷ������!","��ʾ",JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, "����д��ȷ��������!","��ʾ",JOptionPane.ERROR_MESSAGE);
						}		
					}else{
						JOptionPane.showMessageDialog(null, "����д��ȷ��ԭ����!","��ʾ",JOptionPane.ERROR_MESSAGE);	
					}			
				}else{
					JOptionPane.showMessageDialog(null, "����д��ȷ���û���!","��ʾ",JOptionPane.ERROR_MESSAGE);	
				}
				
				break;
			case "ȡ��":
				pLoginDlg.setEnabled(true);
				UpDataDlg.this.dispose();
				break;					
			default:
				break;
			}
		}
		
	}
}
