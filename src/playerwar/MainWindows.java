package playerwar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HTTPclient.LoginDlg;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class MainWindows {
	//������������
	LoginDlg loginDlg;
	PlayerClient playerClient;
	//������
	public JFrame mFrame;					
	private JPanel MPanel;	
	private JLabel label_main;
	//��������
	private buttonlistener btr;
		
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
		btr = new buttonlistener();
		//������
		mFrame = new JFrame();
		mFrame.setSize(194, 153);
		mFrame.setLocationRelativeTo(null);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//������
		MPanel = new JPanel();
		MPanel.setLayout(null);
		mFrame.setContentPane(MPanel);
		//�ؼ�		
		label_main = new JLabel("������..");
		label_main.setBounds(38, 45, 112, 34);
		//�ؼ���������
		MPanel.add(label_main);;
	}
	
	private void LOGIN(){
		new Thread(new Runnable() {
			public void run() {
				for(int i = 2; i >= 0; i--){
					label_main.setText("������.." + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}	
				playerClient = new PlayerClient(MainWindows.this);
				loginDlg = new LoginDlg(playerClient);	
				loginDlg.setVisible(true);
			}
		}).start();
		
	}
	
	
	
	//��ť��Ӧ��
	private class buttonlistener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "��ʼ��Ϸ":
				PlayerClient PlayerClient = new PlayerClient(MainWindows.this);
				mFrame.dispose();
				break;
			case "����˵��":
				String xx = "��-w\r\n��-S\r\n��-A\r\n��-D\r\n��ͨ��-Num1\r\nAOE��-Num2\r\n׷����-Num3";
				JOptionPane.showMessageDialog(null, xx);
				break;
			default:
				break;
			}
			
		}
	}
}
