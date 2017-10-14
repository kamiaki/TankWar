package HTTPserver;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MainServer {
	//�ؼ�
	private JFrame frame;
	private JLabel Label_IpPort;
	private JLabel Label_FanKui;
	private JScrollPane scrollPane;
	private JTextArea textArea_XinXi;
	private JLabel label_ID;
	//�˵�
	private JMenuBar menuBar;
	private JMenu Menu_CaoZuo;
	private JMenuItem MenuItem_Start;
	private JMenuItem MenuItem_Stop;
	private JMenuItem MenuItem_Exit;
	private JMenu Menu_Set;
	private JMenuItem MenuItem_Set;
	//��������
	private HTTPserver httPserver;
	private int PORT;
	private int LINKNUMBER;
	private ButtonListener btl;
	/**
	 * ������
	 * @param args //��������
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainServer window = new MainServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * ���캯��
	 */
	public MainServer() {
		initialize();
	}
	/**
	 * ��ʼ��
	 */
	private void initialize() {
		btl = new ButtonListener();
		//**************************************************************������
		frame = new JFrame();
		frame.setTitle("�����ļ������");
		frame.setSize(419, 262);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);									//����
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		//**************************************************************�ؼ�
		Label_IpPort = new JLabel("IpPort");
		Label_IpPort.setHorizontalAlignment(SwingConstants.RIGHT);
		Label_IpPort.setBounds(10, 10, 389, 15);
		
		Label_FanKui = new JLabel("���з���");
		Label_FanKui.setBounds(10, 46, 54, 15);
		
		label_ID = new JLabel("������״̬���ر�");
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		label_ID.setBounds(177, 46, 222, 15);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 389, 132);	
		textArea_XinXi = new JTextArea();
		scrollPane.setViewportView(textArea_XinXi);
		textArea_XinXi.setText("������Ϣ..");	
		
		menuBar = new JMenuBar();	
		Menu_CaoZuo = new JMenu("����");	
		Menu_Set = new JMenu("����");	
		
		MenuItem_Start = new JMenuItem("����������");
		MenuItem_Start.addActionListener(btl);
		MenuItem_Start.setActionCommand("����������");	
		
		MenuItem_Stop = new JMenuItem("�رշ�����");
		MenuItem_Stop.addActionListener(btl);
		MenuItem_Stop.setActionCommand("�رշ�����");	
		MenuItem_Stop.setEnabled(false);
		
		MenuItem_Exit = new JMenuItem("�˳�");
		MenuItem_Exit.addActionListener(btl);
		MenuItem_Exit.setActionCommand("�˳�");	
			
		MenuItem_Set = new JMenuItem("����..");	
		MenuItem_Set.addActionListener(btl);
		MenuItem_Set.setActionCommand("����");	

		//**************************************************************�ؼ�˳��
		//�˵���
		frame.setJMenuBar(menuBar);
		menuBar.add(Menu_CaoZuo);
		Menu_CaoZuo.add(MenuItem_Start);
		Menu_CaoZuo.add(MenuItem_Stop);
		Menu_CaoZuo.add(MenuItem_Exit);
		menuBar.add(Menu_Set);
		Menu_Set.add(MenuItem_Set);	
		//���ؼ�
		frame.getContentPane().add(Label_IpPort);
		frame.getContentPane().add(Label_FanKui);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(label_ID);
		
		//**************************************************************���¿ؼ�
		updataServer();
	}
	//**************************************************************��ť��Ӧ�¼�
	public class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (arg0.getActionCommand()) {
			case "����������":
				String portstr = JOptionPane.showInputDialog(null,"�˿ںţ�","��������",JOptionPane.PLAIN_MESSAGE);  
				String linkStr = JOptionPane.showInputDialog(null,"��������","��������",JOptionPane.PLAIN_MESSAGE);
				PORT = Integer.parseInt(portstr);
				LINKNUMBER = Integer.parseInt(linkStr);
				
				httPserver = new HTTPserver(PORT, LINKNUMBER);
				if(httPserver.HTTPserverStrat()){
					MenuItem_Start.setEnabled(false);	
					MenuItem_Stop.setEnabled(true);
					label_ID.setText("������״̬������");
				}else{
					JOptionPane.showMessageDialog(null, "������������ʧ�ܣ�");	
				}
				updataServer();
				break;
			case "�رշ�����":
				if(httPserver.HTTPserverOff()){
					label_ID.setText("������״̬���ر�");
					MenuItem_Start.setEnabled(true);	
					MenuItem_Stop.setEnabled(false);
				}else{
					JOptionPane.showMessageDialog(null, "�������˹ر�ʧ�ܣ�");	
				}		
				updataServer();
				break;
			case "����":
				JOptionPane.showMessageDialog(null, "����������..");	
				break;	
			case "�˳�":
				System.exit(0); //�����˳�0���������˳�1������try..catch��			
				break;		
			default:
				break;
			}
			
		}
	}
	/**
	 * ���²���
	 */
	public void updataServer(){
		String ip = "";
		try {
			ip = ( (InetAddress)InetAddress.getLocalHost() ).getHostAddress().toString();  
		} catch (Exception e) {
			e.printStackTrace();
		}	
		String port = String.valueOf(PORT);
		String HeBing1 = String.format("IP��ַ : %s   �˿ں� : %s", ip,port);
		Label_IpPort.setText(HeBing1);
	}
}
