package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
/**
 * ̹����
 *
 */
public class Tank implements InitValue{	
	private TankClient tankClient;						//��ܼ�
	public static final int tankX = 30, tankY = 30;		//̹�˴�С
	private int X, Y, xspeed, yspeed, oldX, oldY;		//̹��λ�� �ٶ�
	private boolean Up = false, Down = false,			//̹�˰�������
					Left = false, Right = false;
	private Direction FangXiang = Direction.d5;			//̹���ƶ�����
	private Direction ptDir = Direction.d4;				//�ӵ� �� ��Ͳ����
	private int TankType = type_player;					//̹������
	private Color tankColor;							//̹����ɫ
	private int TrackingDistance = 200;					//̹��׷���뾶
	private boolean live = true;						//̹���Ƿ����
	private int blood = 100;							//����ֵ
	private int bloodZong = 100;						//����ֵ����
	private BloodBar bloodBar = new BloodBar();			//Ѫ����
	//�����ӵ�����
	public int BaFangNumber = 0;						//�˷�������
	public int ZhuiZongNumber = 0;						//׷�ٵ�����
	
	/**
	 * ̹�˹��캯��
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
	public Tank(int x, int y, int type, Color Co , int xspeed, int yspeed, TankClient w){
		this.X = x;
		this.Y = y;
		this.TankType = type;
		this.tankColor = Co;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = w;
		this.live = true;
		switch (TankType) {
		case type_player:
			blood = 100;
			bloodZong = blood;
			break;
		case type_enemy:
			blood = 60;
			bloodZong = blood;
			break;
		default:
			break;
		}
		TankQD();
	}
	/**
	 * ��ȡѪ��
	 * @return
	 */
	public int getBlood() {
		return blood;
	}
	/**
	 * �ı�Ѫ��
	 * @param blood
	 */
	public void setBlood(int blood) {
		this.blood = blood;
	}
	public static Random random = new Random();			//�����
	/**
	 * �ж���ʲô̹��
	 * @return
	 */
	public int getTankType() {
		return TankType;
	}
	/**
	 * ������ʲô̹��
	 * @param good
	 */
	public void setTankType(int type) {
		TankType = type;
	}
	/**
	 * �ж�����
	 * @return
	 */
	public boolean isTankLive() {
		return live;
	}
	/**
	 * ��������
	 * @param tankLive
	 */
	public void setTankLive(boolean tankLive) {
		this.live = tankLive;
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
	 * ��̹��
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			TankPicture(g);	
		}	
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
		bloodBar.draw(g);
	}
	/**
	 * ��ȡ̹�˵ľ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, tankX, tankY);
	}
	/**
	 * ����Ͳ
	 * @param g
	 */
	private void paotong(Graphics g){
		Color c = g.getColor();
		int shenchu = 3;					//����ĳ���
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
	 * ����̹�����ݸ��³���
	 */
	private void TankQD(){
		if(this.TankType == type_player){
			MoveThread();
		}else{
			autoMove();
			ZhuiMove();
			autofire();
			MoveThread();
		}
	}
	/**
	 * �ƶ��߳�
	 */
	private void MoveThread(){
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
	 * ׷���ƶ��߳�
	 */
	private void ZhuiMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				while(live){
					//������̹�˻�����׷
					if( tankClient.myTank.isTankLive() ){
						tankx = tankClient.myTank.X;
						tanky = tankClient.myTank.Y;	
						if( Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2)) < TrackingDistance ){
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
					}
					try {Thread.sleep(10);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * �Զ��ƶ��߳�
	 */
	private void autoMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				int time = 0;
				while(live){
					time = random.nextInt(2000) + 500;
					tankx = tankClient.myTank.X;
					tanky = tankClient.myTank.Y;
					if(Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2))  >= TrackingDistance){
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
		if( Tank.this.ZhuangWalls(tankClient.walls) || Tank.this.ZhuangTank(tankClient.myTank) || Tank.this.ZhuangTanks(tankClient.enemyTanks) ) {
			this.stay();
		}else {
			//��һ���ƶ�������
			this.oldX = X;
			this.oldY = Y;		
			//��ʼ�ƶ�
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
			if(X < 0) this.stay();
			if(Y < 0) this.stay();
			if(X + Tank.tankX > WindowsXlength) this.stay();
			if(Y + Tank.tankY + 30 > WindowsYlength) this.stay();
		}
	}
	/**
	 * ������һ���ƶ���λ�� ������ֹͣ��
	 */
	private void stay() {
		this.X = oldX;
		this.Y = oldY;
	}
	/**
	 * �Զ�����
	 */
	public void autofire(){
		new Thread(new Runnable() {
			public void run() {
				int sleepInt = 0;
				int x = 0,y = 0;
				while(live){
					sleepInt = random.nextInt(2000) + 500;
					if(tankClient != null){
						x = Tank.this.X + Tank.tankX/2 - Missile.missileXlength/2;
						y = Tank.this.Y + Tank.tankY/2 - Missile.missileYlength/2;
						if(ptDir == Direction.d5)ptDir = Direction.d6;//�ڵ����ܲ���
						Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_enemy, 2, 2, tankClient);
						tankClient.missiles.add(missile);	
					}
					try {Thread.sleep(sleepInt);} catch (Exception e) {}
				}			
			}
		}).start();	
	}
	/**
	 * ��ҿ��� ����һ���ӵ�
	 * @return
	 */
	public void fire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			if(ptDir == Direction.d5)ptDir = Direction.d6;//�ڵ����ܲ���
			Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_player, 5, 5, tankClient);
			tankClient.missiles.add(missile);	
		}
	}		
	/**
	 * ��ҿ��� ����˷���
	 * @return
	 */
	public void BaFangfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			Direction[] directions = Direction.values();
			Direction direction = Direction.d1;
			for(int i = 0; i < 8; i++) {
				direction = directions[i];
				Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, tankClient);
				tankClient.missiles.add(missile);	
			}			
		}
	}
	/**
	 * ��ҿ��� ����һ��׷���ӵ�
	 * @return
	 */
	public void ZhuiZongfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			if(ptDir == Direction.d5)ptDir = Direction.d6;//�ڵ����ܲ���
			Missile missile = new Missile(x, y, Misslie_zhuizong, ptDir, type_player, 3, 3, tankClient);
			missile.ZhuiZongPD = true;
			tankClient.missiles.add(missile);	
		}
	}	
	/**
	 * ���°���
	 * @param e
	 */
	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD0:		
			break;
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
		case KeyEvent.VK_NUMPAD1:
			fire();
			break;
		case KeyEvent.VK_NUMPAD2:
			 
//			if(BaFangNumber > 7) {
				BaFangNumber -= 8;
				BaFangfire();
//			}
			break;
		case KeyEvent.VK_NUMPAD3:
//			if(ZhuiZongNumber > 0) {
				ZhuiZongNumber -= 1;
				ZhuiZongfire();
//			}
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
	 * ̹��ײǽ
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Tank.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * ̹��ײ����ǽ
	 * @param w
	 * @return
	 */
	public boolean ZhuangWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(ZhuangWall(walls.get(i))){
				return true;
			}
		}
		return false;
	}
	/**
	 * ̹����̹����ײ
	 * @param t
	 * @return
	 */
	public boolean ZhuangTank(Tank t) {
		if(this.live == true && t.live == true && this.getRect().intersects(t.getRect()) && this != t   ) {
			return true;
		}
		return false;
	}
	/**
	 * ̹����̹����ײ ��ȡ����
	 * @param t
	 * @return
	 */
	public boolean ZhuangTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if( ZhuangTank(tanks.get(i)) ) {
				return true;
			}	
		}
		return false;
	}
	/**
	 * ����Ʒ
	 * @param b
	 * @return
	 */
	private ItemsType eat(Item item) {
		ItemsType itemsType = ItemsType.NoItem;
		if(this.live == true && this.getRect().intersects(item.getRect())) {
			switch (item.getItemsType()) {
			case Blood:
				itemsType = ItemsType.Blood;
				break;
			case WeaponBaFang:
				itemsType = ItemsType.WeaponBaFang;
				break;
			case WeaponZhuiZong:
				itemsType = ItemsType.WeaponZhuiZong;
				break;
			default:
				itemsType = ItemsType.NoItem;
				break;
			}
			item.setLive(false);
			return itemsType;
		}
		return ItemsType.NoItem;
	}
	/**
	 * ����Ʒ����
	 * @param tanks
	 * @return
	 */
	public ItemsType eats(List<Item> items) {
		ItemsType itemsType = ItemsType.NoItem;
		for(int i = 0; i < items.size(); i++) {
			itemsType = eat(items.get(i));
			if(itemsType != ItemsType.NoItem) {
				items.remove(items.get(i));
				return itemsType;
			}
		}
		return ItemsType.NoItem;
	}
	/**
	 * Ѫ����
	 *
	 */
	private class BloodBar {
		//��Ѫ��
		public void draw(Graphics g) {
			BloodBarPicture(g);
		}
		//��Ѫ��ͼ
		private void BloodBarPicture(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(X, Y-12, tankX, 5);
			int w = tankX * blood/bloodZong;
			g.fillRect(X, Y-12, w, 5);
			g.setColor(c);
		}
	}
}
