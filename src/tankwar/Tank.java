package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank implements InitValue{
	public static final int TANK_player = 0;
	public static final int TANK_enemy = 1;
	public static final int TANK_boss = 2;
	
	public static Random random = new Random();	
	private int X, Y, xspeed = 3, yspeed = 3;	
	private TankClient tankClient = null;
	public static final int tankX = 30, tankY = 30;
	private boolean Up = false, Down = false, Left = false, Right = false;
	private Direction FangXiang = Direction.d5;
	private Direction ptDir = Direction.d4;				//�ӵ� �� ��Ͳ����
	private int Type;
	private Color tankColor;
	private boolean tankLive = true;
	private boolean auto = false;
	/**
	 * �ж���ʲô̹��
	 * @return
	 */
	public int isType() {
		return Type;
	}
	/**
	 * ������ʲô̹��
	 * @param good
	 */
	public void setType(int type) {
		Type = type;
	}
	/**
	 * �ж�����
	 * @return
	 */
	public boolean isTankLive() {
		return tankLive;
	}
	/**
	 * ��������
	 * @param tankLive
	 */
	public void setTankLive(boolean tankLive) {
		this.tankLive = tankLive;
	}	
	/**
	 * ̹��xλ��
	 * @return
	 */
	public int getY() {
		return Y;
	}
	/**
	 * ̹��Yλ��
	 * @return
	 */
	public int getX() {
		return X;
	}
	/**
	 * ̹�˹��캯��
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
	public Tank(int x, int y, int type, Color Co, TankClient w){
		this.X = x;
		this.Y = y;
		this.Type = type;
		this.tankColor = Co;
		this.tankClient = w;
		this.tankLive = true;
		TankQD();
	}
	/**
	 * ��̹��
	 * @param g
	 */
	public void draw(Graphics g){	
		TankPicture(g);		
	}
	/**
	 * ̹����ͼ
	 * @param g
	 */
	private void TankPicture(Graphics g){
		Color c = g.getColor();
		g.setColor(tankColor);
		g.fillOval(X, Y,tankX, tankY);
		g.setColor(c);
		paotong(g);
	}
	/**
	 * ����Ͳ
	 * @param g
	 */
	private void paotong(Graphics g){
		Color c = g.getColor();
		int shenchu = 3;
		g.setColor(Color.BLACK);
		switch (ptDir) {
		case d4:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X - shenchu, Y + Tank.tankY/2);
			break;
		case d7:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y);
			break;
		case d8:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y - shenchu);
			break;
		case d9:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y);
			break;
		case d6:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX + shenchu, Y + Tank.tankY/2);
			break;
		case d3:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y + Tank.tankY);
			break;
		case d2:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y + Tank.tankY + shenchu);
			break;
		case d1:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y + Tank.tankY);
			break;
		default:
			break;
		}
		g.setColor(c);
	}
	/**
	 * ����̹���߳�
	 */
	private void TankQD(){
		if(this.Type == Tank.TANK_player){
			PlayerMoveThread();
		}else{
			ZhuiMove();
			autoMove();
			PlayerMoveThread();
		}
	}
	/**
	 * ̹���ƶ��߳�
	 */
	private void PlayerMoveThread(){
		new Thread(new Runnable() {
			public void run() {
				while(tankLive){
					move();	
					try {Thread.sleep(10);} catch (Exception e) {}
				}
			}
		}).start();
	}
	/**
	 * ׷���ƶ�
	 */
	private void ZhuiMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				while(tankLive){	
					xspeed = 1;
					yspeed = 1;
					tankx = tankClient.myTank.X;
					tanky = tankClient.myTank.Y;	
					if( Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2)) < 100 ){
						if(Tank.this.X < tankx && Tank.this.Y == tanky){
							FangXiang = Direction.d6;
						}else if(Tank.this.X < tankx && Tank.this.Y < tanky){
							FangXiang = Direction.d3;
						}else if(Tank.this.X == tankx && Tank.this.Y < tanky){
							FangXiang = Direction.d2;
						}else if(Tank.this.X > tankx && Tank.this.Y < tanky){
							FangXiang = Direction.d1;
						}else if(Tank.this.X > tankx && Tank.this.Y == tanky){
							FangXiang = Direction.d4;
						}else if(Tank.this.X > tankx && Tank.this.Y > tanky){
							FangXiang = Direction.d7;
						}else if(Tank.this.X == tankx && Tank.this.Y > tanky){
							FangXiang = Direction.d8;
						}else if(Tank.this.X < tankx && Tank.this.Y > tanky){
							FangXiang = Direction.d9;
						}		
					}
					try {Thread.sleep(10);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * �Զ��ƶ�
	 */
	private void autoMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				int time = 0;
				while(tankLive){
					time = random.nextInt(2000);
					xspeed = 1;
					yspeed = 1;
					tankx = tankClient.myTank.X;
					tanky = tankClient.myTank.Y;
					if(Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2))  >= 100){
						auto = true;
						Direction[] dirs = Direction.values();
						FangXiang = dirs[ random.nextInt(dirs.length) ];
					}
					try {Thread.sleep(time);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * ̹���ƶ�
	 */
	private void move() {
		switch (FangXiang) {
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
		//�ӵ���ʼ���� ��Ͳ��ʼ����
		if( FangXiang != Direction.d5 ) this.ptDir = this.FangXiang;
		//̹�˲��ܳ���
		if(X < 0) X = 0;
		if(Y < 0) Y = 0;
		if(X + Tank.tankX > WindowsXlength) X = WindowsXlength - Tank.tankX;
		if(Y + Tank.tankY + 30 > WindowsYlength) Y = WindowsYlength - Tank.tankY - 30;
	}
	/**
	 * ���°���
	 * @param e
	 */
	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_W:
			Up = true;
			break;
		case KeyEvent.VK_S:
			Down = true;
			break;
		case KeyEvent.VK_A:
			Left = true;
			break;
		case KeyEvent.VK_D:
			Right = true;
			break;
		case KeyEvent.VK_O:
			xspeed++;
			yspeed++;
			break;
		case KeyEvent.VK_P:
			xspeed--;
			yspeed--;
			break;
		default:
			break;
		}
		PDFangXiang();
	}
	/**
	 * ̧�𰴼�
	 * @param e
	 */
	public void noKEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD0:
			fire();
			break;
		case KeyEvent.VK_W:
			Up = false;
			break;
		case KeyEvent.VK_S:
			Down = false;
			break;
		case KeyEvent.VK_A:
			Left = false;
			break;
		case KeyEvent.VK_D:
			Right = false;
			break;
		default:
			break;
		}
		PDFangXiang();
	}	
	/**
	 * �жϷ���
	 */
	private void PDFangXiang(){
		if(!Up && !Down && Left && !Right)FangXiang = Direction.d4;
		else if(Up && !Down && Left && !Right)FangXiang = Direction.d7;
		else if(Up && !Down && !Left && !Right)FangXiang = Direction.d8;
		else if(Up && !Down && !Left && Right)FangXiang = Direction.d9;
		else if(!Up && !Down && !Left && Right)FangXiang = Direction.d6;
		else if(!Up && Down && !Left && Right)FangXiang = Direction.d3;
		else if(!Up && Down && !Left && !Right)FangXiang = Direction.d2;
		else if(!Up && Down && Left && !Right)FangXiang = Direction.d1;
		else if(!Up && !Down && Left && Right)FangXiang = Direction.d5;
		else if(Up && Down && !Left && !Right)FangXiang = Direction.d5;
		else if(!Up && !Down && !Left && !Right)FangXiang = Direction.d5;
		else if(Up && Down && Left && Right)FangXiang = Direction.d5;
	}
	/**
	 * ����һ���ӵ�
	 * @return
	 */
	public void fire(){
		if(tankClient != null){
			int x = this.X + Tank.tankX/2 - Missile.missileX/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileY/2;
			Missile missile = new Missile(x, y, ptDir,tankClient);
			tankClient.missiles.add(missile);	
		}
	}	
	/**
	 * ��ȡ̹�˵ľ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, tankX, tankY);
	}
}
