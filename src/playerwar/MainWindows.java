package playerwar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class MainWindows {
	//��ܼ�ָ��
	private PlayerClient PlayerClient;	
	//������
	public JFrame mFrame;					
	private JPanel MPanel;		
	private buttonlistener btr;
	//�ؼ�
	private JButton button_StartGame;	
	private JButton button_CZ;			
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
		button_StartGame = new JButton("��ʼ��Ϸ");
		button_StartGame.setBounds(30, 22, 128, 31);
		button_StartGame.setActionCommand("��ʼ��Ϸ");
		button_StartGame.addActionListener(btr);

		button_CZ = new JButton("����˵��");
		button_CZ.setBounds(30, 63, 128, 31);
		button_CZ.setActionCommand("����˵��");
		button_CZ.addActionListener(btr);	
		//�ؼ���������
		MPanel.add(button_StartGame);
		MPanel.add(button_CZ);
	}
	
	//��ť��Ӧ��
	private class buttonlistener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "��ʼ��Ϸ":
				PlayerClient = new PlayerClient(MainWindows.this);
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
