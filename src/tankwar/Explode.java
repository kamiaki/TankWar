package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ��ը��
 *
 */
public class Explode {
	private TankClient tankClient;		//��ܼ�ָ��
	private int X, Y;					//��ը X Y ����
	private boolean live = true;		//��ը�Ƿ���
	private int x1,y1,x2,y2;			//��ȡͼƬλ��
	//��ͼ
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//���߰�								
	private static Image ExplodePicture1 = toolkit.getImage(Explode.class.getClassLoader().getResource("images/��ը.png"));	//����ͼƬ
	private static Image ExplodePicture2 = toolkit.getImage(Explode.class.getClassLoader().getResource("images/��ը2.png"));	//����ͼƬ
	private static int ExplodeXY1 = 50; 
	private static int ExplodeXY2 = 100;
	static{
		ExplodePicture1 = ExplodePicture1.getScaledInstance(ExplodeXY1 * 4, ExplodeXY1 * 4, Image.SCALE_DEFAULT);
		ExplodePicture2 = ExplodePicture2.getScaledInstance(ExplodeXY2 * 8, ExplodeXY2    , Image.SCALE_DEFAULT);
	}
	
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
//		BaoZhaQD1();
		BaoZhaQD2();
	}
	/**
	 * ����ը
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
//			ExplodePicture1(g);
			ExplodePicture2(g);
		}
	}
	//*********************************************************************************��ը1
	/**
	 * ����ըͼ ��ͼƬ
	 * @param g
	 */
	private void ExplodePicture1(Graphics g) {
		g.drawImage(ExplodePicture1, X, Y, X + ExplodeXY1, Y + ExplodeXY1, x1, y1, x2, y2, null);
	}
	/**
	 * ������ը�߳� ��ͼƬ
	 */
	private void BaoZhaQD1(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC1();
				}
			}
		}).start();
	}
	/**
	 * ��ը��ͼ���ݸ��� ��ͼƬ
	 */
	private void ExplodeXC1(){			
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				x1 = j * ExplodeXY1;
				y1 = i * ExplodeXY1;
				x2 = (j + 1) * ExplodeXY1;
				y2 = (i + 1) * ExplodeXY1;
				try {Thread.sleep(30);} catch (Exception e) {}
			}
		}
		live = false;
		tankClient.explodes.remove(Explode.this);
	}	
	//*********************************************************************************��ը2
	/**
	 * ����ըͼ ��ͼƬ
	 * @param g
	 */
	private void ExplodePicture2(Graphics g) {
		g.drawImage(ExplodePicture2, X, Y, X + ExplodeXY2, Y + ExplodeXY2, x1, y1, x2, y2, null);
	}
	/**
	 * ������ը�߳� ��ͼƬ
	 */
	private void BaoZhaQD2(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC2();
				}
			}
		}).start();
	}
	/**
	 * ��ը��ͼ���ݸ��� ��ͼƬ
	 */
	private void ExplodeXC2(){			
		for(int i = 0; i < 1; i++){
			for(int j = 0; j < 8; j++){
				x1 = j * ExplodeXY2;
				y1 = i * ExplodeXY2;
				x2 = (j + 1) * ExplodeXY2;
				y2 = (i + 1) * ExplodeXY2;
				try {Thread.sleep(30);} catch (Exception e) {}
			}
		live = false;
		tankClient.explodes.remove(Explode.this);
		}
	}
}
