package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * �ӵ���
 *
 */
public class Missile implements InitValue{
	private TankClient tankClient = null;								//��ܼ�ָ��
	public static final int missileXlength = 10, missileYlength = 10;	//�ӵ��Ĵ�С
	private int X, Y, xspeed, yspeed, oldX, oldY;						//�ӵ�λ�� �� �ٶ�
	private int MissileType = type_player;								//�ӵ�����
	Direction MissileFangXiang;											//�ӵ�����
	private boolean live = true;										//�ӵ��Ƿ����
	//�����ӵ�
	private int MissileZhongLei = Misslie_putong;						//�ڵ�����
	public boolean ZhuiZongPD = false;									//׷�ٵ��Ƿ�����
	
	/**
	 * ��ȡ�ӵ�����
	 * @return
	 */
	public int getMissileType() {
		return MissileType;
	}
	/**
	 * �����ӵ�����
	 * @param tankType
	 */
	public void setMissileType(int tankType) {
		MissileType = tankType;
	}
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
	public Missile(int x, int y,int MissileZhongLei, Direction missileFangXiang, int tankType, int xspeed,int yspeed, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.MissileZhongLei = MissileZhongLei;
		MissileFangXiang = missileFangXiang;
		this.MissileType = tankType;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = tc;
		this.live = true;
		MissileQD();
	}
	/**
	 * ���ӵ�
	 * @param g
	 */
	public void draw(Graphics g) {
		if(live) {
			MissilePicture(g);
		}
	}
	/**
	 * �ӵ���ͼ
	 * @param g
	 */
	private void MissilePicture(Graphics g){
		Color c = g.getColor();
		if(MissileType == type_player) {
			switch (MissileZhongLei) {
			case Misslie_putong:
				g.setColor(Color.BLACK);
				break;
			case Misslie_bafang:
				g.setColor(Color.BLUE);
				break;
			case Misslie_zhuizong:
				g.setColor(Color.MAGENTA);
				break;
			default:
				break;
			}
		}else {
			g.setColor(Color.GRAY);
		}
		g.fillOval(X, Y, missileXlength, missileYlength);
		g.setColor(c);
	}
	/**
	 * ��ȡ�ӵ��ľ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, missileXlength, missileYlength);
	}
	/**
	 * �����ӵ����ݸ��³���
	 */
	private void MissileQD(){
		if(MissileType == type_player)Follow();  	//����׷�ٵ��߳�
		MissileMove();						  		//�ӵ��ƶ��߳�
		TimeMissileDead();								//�ӵ������߳�
	}
	/**
	 * �ӵ��ƶ��߳�
	 */
	private void MissileMove(){
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
	 * ׷�ٵ��߳�
	 */
	private void Follow(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				double Distance = 0;
				List<Tank> enemytanks = tankClient.enemyTanks;
				//������̹�˻�����׷
				while(live){
					//�Ե�׷�ٵ���׷��
					while(ZhuiZongPD) {
						for(int i = 0; i < enemytanks.size(); i++) {
							Tank enemytank = enemytanks.get(i);
							Distance = Math.sqrt(Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2) + Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2));
							if(Distance < 100) {
								tankx = enemytank.getX();
								tanky = enemytank.getY();	
								if(Missile.this.X < tankx && Missile.this.Y == tanky){
									MissileFangXiang = Direction.d6;
								}else if(Missile.this.X < tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d3;
								}else if(Missile.this.X == tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d2;
								}else if(Missile.this.X > tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d1;
								}else if(Missile.this.X > tankx && Missile.this.Y == tanky){
									MissileFangXiang = Direction.d4;
								}else if(Missile.this.X > tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d7;
								}else if(Missile.this.X == tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d8;
								}else if(Missile.this.X < tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d9;
								}							
							}					
						}
						try {Thread.sleep(10);} catch (Exception e) {}	
					}
					try {Thread.sleep(10);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * �ӵ���ʱ�������߳�
	 */
	private void TimeMissileDead(){
		new Thread(new Runnable() {
			public void run() {	
				try {Thread.sleep(5000);} catch (Exception e) {}
				if(Missile.this.live){
					Missile.this.live = false;											//�ӵ������ж�Ϊ��
					if(Missile.this != null) {
						tankClient.missiles.remove(Missile.this);						//�ڶ������Ƴ��ӵ�
					}
				}	
			}
		}).start();
	}
	/**
	 * �ƶ��ӵ�
	 */
	private void move() {	
		if( Missile.this.hitWalls(tankClient.walls) ) {
			this.missileDead();
		}else {
			//��һ���ƶ�������
			this.oldX = X;
			this.oldY = Y;	
			//��ʼ�ƶ�
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
			if(tankClient != null){
				if(X < 0 || Y < 0 || X > WindowsXlength || Y > WindowsYlength){
					this.live = false;						//�ӵ������ж�Ϊ��
					if(this != null) {
						tankClient.missiles.remove(this);	//�ڶ������Ƴ��ӵ�		
					}
					
				}
			}
		}
	}
	/**
	 * ���ӵ�ֱ������
	 */
	private void missileDead() {
		this.live = false;
		if(this != null) {
			tankClient.missiles.remove(this);	
		}
	}
	/**
	 * ����̹��
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(this.getRect().intersects( t.getRect()) && t.isTankLive() && Missile.this.getMissileType() != t.getTankType() ){			
			Explode e = new Explode(this.X, this.Y, this.tankClient);	//���һ����ը
			tankClient.explodes.add(e);
			
			tankClient.ZhenDong();										//��ܼ� �𶯷���			
			
			this.live = false;											//�ӵ������ж�Ϊ��
			if(this != null) {
				tankClient.missiles.remove(this);						//�ڶ������Ƴ��ӵ�
			}
				
			if(t.getTankType() == type_player) {
				t.setBlood(t.getBlood() - 20);
				if(t.getBlood() <= 0) {
					t.setTankLive(false);								//���̹�������ж�Ϊ��	
					if(tankClient.reTankNumber == 0){
						tankClient.reTankNumber -= 1;
					}
				}
			}else if(t.getTankType() == type_enemy){	
				//ʲô�����ӵ���ʲô����Ѫ
				if( this.MissileZhongLei == Misslie_bafang ) {
					t.setBlood(t.getBlood() - Misslie_bafangX);
				}else if( this.MissileZhongLei == Misslie_zhuizong ) {
					t.setBlood(t.getBlood() - Misslie_zhuizongX);
				}else {
					t.setBlood(t.getBlood() - Misslie_putongX);
				}	
				//ɱ�����˺� ���� + 1
				if(t.getBlood() <= 0) {
					t.setTankLive(false);								//����̹�������ж�Ϊ��
					tankClient.killTankNumber += 1;
					return true;
				}else {
					return false;
				}			
			}
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
				if(enemyTanks.get(i) != null) {
					enemyTanks.remove(enemyTanks.get(i));
				}
				return true;
			}			
		}
		return false;
	}
	/**
	 * ������ǽ
	 * @param w
	 * @return
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * ������ǽ����
	 * @param w
	 * @return
	 */
	public boolean hitWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(hitWall(walls.get(i))) {
				return true;
			}
		}
		return false;
	}
}
