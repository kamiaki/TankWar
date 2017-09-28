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
import javax.swing.*;

public class TankClient extends JFrame implements InitValue{	
	private mainPanel mPanel;
	
	Tank myTank = new Tank(50, 50 ,this);
	List<Missile> missiles = new ArrayList<Missile>();
	
	/**
	 * ���캯��
	 */
	public TankClient(){
		launchFrame();
	}
	/**
	 * ��ʼ������
	 */
	public void launchFrame(){
		//��������
		this.setTitle("Tank");
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
			
			myTank.draw(goffScreenImage);											//��tank	
			for(int i = 0; i < missiles.size(); i++){								//���ڵ�
				Missile m = missiles.get(i);
				m.draw(goffScreenImage);		
			}
			goffScreenImage.drawString("�ӵ�����:" + missiles.size(), 10, 50);
			
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
