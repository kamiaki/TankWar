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
	
	private mainPanel mPanel;
	
	Tank myTank;
	List<Missile> missiles;
	List<Tank> enemyTanks; 
	/**
	 * ���캯��
	 */
	public TankClient(){
		initTank();
		launchFrame();
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
	 * ��ʼ��̹���ӵ��Ȳ���
	 */
	public void initTank(){	    
		myTank = new Tank(random(50, 750), random(50, 400), Tank.Tanktype_man, this);
		missiles = new ArrayList<Missile>();
		enemyTanks = new ArrayList<Tank>();
		CreateEnemyTank();
		new Thread(new Runnable() {		
			public void run() {	
				PDHitTank();
			}
		}).start();
	}
	
	/**
	 * ������ɵ���̹��
	 */
	public void CreateEnemyTank(){
		for(int i = 0; i < 5; i++){
		enemyTanks.add( new Tank(random(50, 750), random(50, 400), Tank.Tanktype_robot , this) );		
		}
	}
	
	/**
	 * �ж��Ƿ����̹��
	 */
	public void PDHitTank(){
		int tanknumber = 0;
		int missileN = 0;
		int tankN = 0;
		while(true){
			for(int i = 0; i < missiles.size(); i++){
				for(int j = 0; j < enemyTanks.size(); j++){
					if( missiles.get(i).hitTank(enemyTanks.get(j)) ){
							missiles.remove(i);
							enemyTanks.remove(j);
							enemyTanks.add( new Tank(random(50, 750), random(50, 400), Tank.Tanktype_robot , this) );	
							i = i - 1;
							j = j - 1;
							break;
					}
				}			
			}
			try {Thread.sleep(10);} catch (Exception e) {}
		}
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
		//���
		mPanel = new mainPanel(); 
		mPanel.setSize(WindowsXlength, WindowsYlength);
		this.setContentPane(mPanel);
		mPanel.setLayout(null);	
		new Thread(new PaintThread()).start();
				
		this.setVisible(true);
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
					Thread.sleep(1);
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
			Image image = mainPanel.this.createImage(WindowsXlength, WindowsYlength);
			Graphics goffScreenImage = image.getGraphics();
			Color c = goffScreenImage.getColor();		
			goffScreenImage.setColor(Color.GREEN);									//������
			goffScreenImage.fillRect(0, 0, WindowsXlength, WindowsYlength);
			goffScreenImage.setColor(c);
			
			myTank.draw(goffScreenImage);											//���Լ��� tank	
			for(int j = 0; j < enemyTanks.size(); j++){								//���˵�̹��
				enemyTanks.get(j).draw(goffScreenImage);
			}								
			for(int i = 0; i < missiles.size(); i++){								//���ڵ�
				missiles.get(i).draw(goffScreenImage);		
			}
			goffScreenImage.drawString("�ӵ�����:" + missiles.size(), 10, 20);
			goffScreenImage.drawString("̹��λ��: X." + myTank.getX() + " Y." + myTank.getY(), 10, 40);
			
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
