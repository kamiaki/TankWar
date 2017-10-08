package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
/**
 * ��ը��
 *
 */
import java.awt.Toolkit;
public class Explode {
	private TankClient tankClient;		//��ܼ�ָ��
	private int X, Y;					//��ը X Y ����
	private boolean live = true;		//��ը�Ƿ���
	//��ͼ
	private int step;							//�����˵ڼ���
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//���߰�
	private static Image[] images = {
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	}; 							
	/**
	 * ��ը�Ƿ���
	 * @return
	 */
	public boolean isLive() {
		return live;
	
	}
	/**
	 * ���ñ�ը����
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param tc
	 */
	public Explode(int x,int y,TankClient tc){
		this.X = x;
		this.Y = y;
		this.tankClient = tc;
		BaoZhaQD();
	}
	/**
	 * ����ը
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			ExplodePicture(g);
		}
	}
	/**
	 * ����ըͼ
	 * @param g
	 */
	private void ExplodePicture(Graphics g) {
		g.drawImage(images[step], X, Y, null);
	}
	/**
	 * ������ը�߳�
	 */
	private void BaoZhaQD(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC();
				}
			}
		}).start();
	}
	/**
	 * ��ը��ͼ���ݸ���
	 */
	private void ExplodeXC(){				
		for(step = 0; step < images.length; step++){
			try {Thread.sleep(10);} catch (Exception e) {}
		}
		live = false;
		tankClient.explodes.remove(Explode.this);
	}
}
