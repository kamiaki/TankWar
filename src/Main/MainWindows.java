package Main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HTTPclient.LoginDlg;
import playerClient.PlayerClient;

public class MainWindows {
	//������������
	LoginDlg loginDlg;
	//������
	public JFrame mFrame;					
	private JPanel MPanel;	
	private JLabel label_main;
	//��������
		
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindows window = new MainWindows();
		window.mFrame.setVisible(true);
	}
	/**
	 * ���캯��
	 */
	public MainWindows() {
		initialize();
		LOGIN();
	}
	/**
	 * ��ʼ��������
	 */
	private void initialize() {
		//������
		mFrame = new JFrame();
		mFrame.setSize(200, 100);
		mFrame.setLocationRelativeTo(null);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//������
		MPanel = new JPanel();
		MPanel.setLayout(null);
		mFrame.setContentPane(MPanel);
		//�ؼ�		
		label_main = new JLabel("������...");
		label_main.setBounds(70, 28, 54, 15);
		//�ؼ���������
		MPanel.add(label_main);;
	}
	
	private void LOGIN(){
		new Thread(new Runnable() {
			public void run() {
				String xx = "��-w\r\n��-S\r\n��-A\r\n��-D\r\n��ͨ��-Num1\r\nAOE��-Num2\r\n׷����-Num3\r\n����-R";
				JOptionPane.showMessageDialog(null, xx);
				
				loginDlg = new LoginDlg();								//���ص�¼����
				loginDlg.setVisible(true);                              //��ʾ��¼����
				
				mFrame.dispose();										//�ر��������
			}
		}).start();		
	}
}