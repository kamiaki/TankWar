package tankwar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindows {
	private TankClient tankClient;
	public JFrame frame;
	private JPanel mainPanel;
	private JButton button_StartGame;
	private JButton button_CZ;
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindows window = new MainWindows();
		window.frame.setVisible(true);
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
		//������
		frame = new JFrame();
		frame.setSize(194, 153);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�ؼ�		
		button_StartGame = new JButton("��ʼ��Ϸ");
		button_StartGame.setBounds(30, 22, 128, 31);
		button_StartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tankClient = new TankClient(MainWindows.this);
				frame.dispose();
			}
		});
		button_CZ = new JButton("����˵��");
		button_CZ.setBounds(30, 63, 128, 31);
		button_CZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String xx = "��-w\r\n��-S\r\n��-A\r\n��-D\r\n��ͨ��-Num1\r\nAOE��-Num2\r\n׷����-Num3";
				JOptionPane.showMessageDialog(null, xx);
			}
		});
		//�ؼ���������
		mainPanel.add(button_StartGame);
		mainPanel.add(button_CZ);
	}
}
