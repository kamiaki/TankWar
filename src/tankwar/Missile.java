package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile implements InitValue{
	TankClient TC = null;
	int X, Y, xspeed = 3, yspeed = 3;
	public static final int missileX = 10, missileY = 10;
	private boolean live = true;
	Direction MissileFangXiang;
	/**
	 * �����ӵ����״̬
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * �ж��ӵ��Ƿ���
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param missileFangXiang
	 * @param tc
	 */
	public Missile(int x, int y, Direction missileFangXiang,TankClient tc) {
		this.X = x;
		this.Y = y;
		MissileFangXiang = missileFangXiang;
		this.TC = tc;
		this.live = true;
		MissileQD();
	}
	/**
	 * ���ӵ�
	 * @param g
	 */
	public void draw(Graphics g) {
		MissilePicture(g);
	}
	/**
	 * �ӵ���ͼ
	 * @param g
	 */
	private void MissilePicture(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(X, Y, 10, 10);
		g.setColor(c);
	}
	/**
	 * �����ӵ��ƶ��߳�
	 */
	private void MissileQD(){
		new Thread(new Runnable() {
			public void run() {	
				while(live){
					move();
					try {Thread.sleep(10);} catch (Exception e) {}
				}	
			}
		}).start();
	}
	/**
	 * �ƶ��ӵ�
	 */
	private void move() {	
		switch (MissileFangXiang) {
		case d4:
			X = X - xspeed;
			break;
		case d7:
			X = X - xspeed;
			Y = Y - yspeed;
			break;
		case d8:
			Y = Y - yspeed;
			break;
		case d9:
			X = X + xspeed;
			Y = Y - yspeed;
			break;
		case d6:
			X = X + xspeed;
			break;
		case d3:
			X = X + xspeed;
			Y = Y + yspeed;
			break;
		case d2:
			Y = Y + yspeed;
			break;
		case d1:
			X = X - xspeed;
			Y = Y + yspeed;
			break;
		case d5:
			break;
		default:
			break;
		}
		
		if(TC != null){
			if(X < 0 || Y < 0 || X > WindowsXlength || Y > WindowsYlength){
				this.live = false;			//�ӵ������ж�Ϊ��		
				TC.missiles.remove(this);	//�ڶ������Ƴ��ӵ�
			}
		}
	}	
	/**
	 * ��ȡ�ӵ��ľ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, missileX, missileY);
	}
	/**
	 * ����̹��
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(  this.getRect().intersects( t.getRect() )  && t.isTankLive()){			
			this.live = false;									//�ӵ������ж�Ϊ��
			TC.missiles.remove(this);							//�ڶ������Ƴ��ӵ�
			
			TC.ZhenDong();										//��ܼ���			
			t.setTankLive(false);								//̹�������ж�Ϊ��
			
			Explode e = new Explode(this.X, this.Y, this.TC);	//���һ����ը
			TC.explodes.add(e);
			
			return true;
		}
		return false;
	}
	/**
	 * ����̹������
	 * @param enemyTanks
	 */
	public boolean hitTanks(List<Tank> enemyTanks) {
		for(int i = 0; i < enemyTanks.size(); i++){
			if(hitTank(enemyTanks.get(i))){
				enemyTanks.remove(i);
				return true;
			}			
		}
		return false;
	}
}
