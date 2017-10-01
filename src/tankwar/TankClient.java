package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class TankClient extends JFrame implements InitValue{	
	
	public static final int PanelX = -5,PanelY = -5;
	
	private mainPanel mPanel;
	private JPanel Mmpanel;
	Background background;
	Tank myTank;
	List<Tank> enemyTanks;
	boolean enemytanksPD = true;
	List<Missile> missiles;
	List<Explode> explodes;
	
	/**
	 * ���캯��
	 */
	public TankClient(){
		launchFrame();
		//������ͼ�߳�
		new Thread(new PaintThread()).start();	
		initObject();
		this.setVisible(true);
	}	
	/**
	 * �����Χ��
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max){
		Random random = new Random();	
		int jieguo = random.nextInt(max)%(max-min+1) + min;	
		return jieguo;
	}	
	/**
	 * ����̹���߳�
	 * @author Administrator
	 *
	 */
	private class CreatTank implements Runnable{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while(enemytanksPD){
				if(enemyTanks.size() < 5){
					Tank enemyTank = new Tank(random(50, 750), random(50, 400), false, Color.GRAY, TankClient.this);
					enemyTanks.add(enemyTank);
				}
				try {Thread.sleep(2000);} catch (Exception e) {}	//ˢ�¼��
			}
		}
	}
	/**
	 * ��ʼ��̹���ӵ��Ȳ���
	 */
	public void initObject(){	   
		enemyTanks = new ArrayList<Tank>();
		missiles = new ArrayList<Missile>();
		explodes = new ArrayList<Explode>();
		background = new Background(0, 0, WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2), this);
		myTank = new Tank(random(50, 750), random(50, 400), true, Color.RED, this);
		new Thread(new CreatTank()).start();	
	}
	/**
	 * ��ʼ������
	 */
	public void launchFrame(){
		//��������
		this.setTitle("KamiAki's First JavaGame");
		this.setSize(WindowsXlength, WindowsYlength);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}		
		});		
		//���̼���
		this.addKeyListener(new Keylistener());	
		//��Ϸ���
		mPanel = new mainPanel(); 
		mPanel.setLocation(PanelX, PanelY);
		mPanel.setSize(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2));
		mPanel.setLayout(null);			
		//������壨��Ϸ��� �����У�
		Mmpanel = new JPanel();
		Mmpanel.setLayout(null);
		Mmpanel.add(mPanel);		
		//���������ӽ�������
		this.setContentPane(Mmpanel);			
	}	
	/**
	 * ��
	 */
	public void ZhenDong(){
		new Thread(new Runnable() {
			public void run() {
				int windowsXz = 0;
				int windowsYz = 0;
				int ZFX = 1;
				int ZFPDX =0;
				int ZFY = 1;
				int ZFPDY =0;
				
				for(int i = 0; i < 10; i++){
					
					ZFPDX = random(1, 10);
					ZFPDY = random(1, 10);
					windowsXz = random(1, PanelX * (-1));		//����
					windowsYz = random(1, PanelY * (-1));		//����
					if(ZFPDX <= 5){ZFX = -1;}else{ZFX = 1;}		//�� + -
					if(ZFPDY <= 5){ZFY = -1;}else{ZFY = 1;}		//�� + -
					
					mPanel.setLocation(PanelX + ZFX * windowsXz, PanelY + ZFY * windowsYz);
					try {Thread.sleep(20);} catch (Exception e) {}	
				}
				mPanel.setLocation(PanelX,PanelY);	
			}
		}).start();
		
	}	
	/**
	 * �ػ��߳�
	 * @author Administrator
	 *
	 */
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while(true){
				mPanel.repaint();
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
	}
	/*
	 * ���������
	 */
	private class mainPanel extends JPanel{
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);			
			Image offScreenImage = Doublebuffer();
			g.drawImage(offScreenImage, 0, 0 , null);		
		}
		
		/**
		 * ˫���� ����
		 * @return
		 */
		private Image Doublebuffer(){
			Image image = mainPanel.this.createImage(WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2));
			Graphics goffScreenImage = image.getGraphics();
			
			//����ͼ��
			background.draw(goffScreenImage);
			
			//������Ϣ
			myTank.draw(goffScreenImage);											//���Լ��� tank	
			for(int i = 0; i < enemyTanks.size(); i++){								//�����˵�̹��
				Tank tank = enemyTanks.get(i);
				tank.draw(goffScreenImage);		
			}											
			for(int i = 0; i < missiles.size(); i++){								//���ڵ�
				Missile m = missiles.get(i);		
				m.draw(goffScreenImage);
				m.hitTanks(enemyTanks);
			}
			for(int i = 0; i < explodes.size(); i++){
				Explode e = explodes.get(i);
				e.draw(goffScreenImage);
			}
			
			//������Ϣ
			goffScreenImage.drawString("���̹��λ��: X." + myTank.getX() + " Y." + myTank.getY(), 10, 20);
			goffScreenImage.drawString("�ӵ�����:" + missiles.size(), 10, 40);	
			goffScreenImage.drawString("��ը����:" + explodes.size(), 10, 60);
			goffScreenImage.drawString("̹������:" + enemyTanks.size(), 10, 80);
			return image;
		}
	}
	/**
	 * ���̼�����
	 * @author Administrator
	 *
	 */
	private class Keylistener extends KeyAdapter {
		//����
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myTank.KEY(e);
		}
		//̧��
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			myTank.noKEY(e);
		}
	}
}
