package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * ̹����
 *
 */
public class Tank implements InitValue{	
	//��ͻ�ָ��
	private TankClient tankClient;						//��ܼ�
	//̹������
	private boolean live = true;						//̹���Ƿ����
	private int TankType = type_player;					//̹������
	private int blood = 100;							//����ֵ
	private int bloodZong = 100;						//����ֵ����
	private BloodBar bloodBar = new BloodBar();			//Ѫ����
	private int X, Y, xspeed, yspeed, oldX, oldY;		//̹��λ�� �ٶ�
	private boolean Up = false, Down = false,Left = false, Right = false;//̹�˰�������
	private Direction FangXiang = Direction.d5;			//̹���ƶ�����
	private Direction ptDir = Direction.d4;				//�ӵ� �� ��Ͳ����
	private int TrackingDistance = 200;					//̹��׷���뾶
	//�����
	public static Random random = new Random();			//�����
	//�����ӵ�����
	public int BaFangNumber = 0;						//�˷�������
	public int ZhuiZongNumber = 0;						//׷�ٵ�����
	//��ͼ
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//���߰�
	private Image Player1Picture;											//����ͼƬ1
	private static final int Player1XY = 50;								//�����С1
	private Image Player2Picture;											//����ͼƬ2
	private static final int Player2XY = 50;								//�����С2
	private int step;														//��������
	private boolean AtkKey;													//���������

	/**
	 * ̹�˹��캯��
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
 	public Tank(int x, int y, int type, int xspeed, int yspeed, TankClient w){
		this.X = x;
		this.Y = y;
		this.TankType = type;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = w;
		this.live = true;
		SetTypeBlood(this.TankType);
		PlayerDongHua();
		HuaPlayer1Picture();
		HuaPlayer2Picture();
		TankQD();
	}
	//******************************************************************̹�˲�������
	/**
	 * ����̹����������Ѫ��
	 */
	public void SetTypeBlood(int Type){
		switch (Type) {
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
	//******************************************************************��������
	/**
	 * ��Ҷ��� ֡��
	 */
	private void PlayerDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(Up || Down || Left || Right || AtkKey){
						step += 1;
						if(step > 20)step = 1;
					}else{
						step = 0;
					}
					try {Thread.sleep(10);} catch (Exception e) {}
				}			
			}
		}).start();
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
		switch (TankType) {
		case type_player:
			HuaPlayer1(g);
			bloodBar.draw(g);
			break;
		default:
			HuaPlayer2(g);
			bloodBar.draw(g);
			break;
		}
	}
	/**
	 * ������1
	 */
	public void HuaPlayer1Picture(){			
		Player1Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/����1.png"));	//����1ͼƬ
		Player1Picture = Player1Picture.getScaledInstance(Player1XY * 4, Player1XY * 4, Image.SCALE_DEFAULT);
	}
	/**
	 * ������2
	 */
	public void HuaPlayer2Picture(){			
		Player2Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/����2.png"));	//����2ͼƬ
		Player2Picture = Player2Picture.getScaledInstance(Player1XY * 4, Player1XY * 4, Image.SCALE_DEFAULT);
	}
	/**
	 * �����1
	 * @param g
	 */
	private void HuaPlayer1(Graphics g){
		switch (ptDir) {
		case d4:
			if(AtkKey){
				if(step < 5){
					g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 0, Player1XY, Player1XY * 1, Player1XY * 2, null);
				}else if(step < 10){
					g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 1, Player1XY, Player1XY * 2, Player1XY * 2, null);
				}else if(step < 15){
					g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 2, Player1XY, Player1XY * 3, Player1XY * 2, null);
				}else{
					g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 3, Player1XY, Player1XY * 4, Player1XY * 2, null);
				}
			}
			if(step == 0){
				g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 0, Player1XY, Player1XY * 1, Player1XY * 2, null);
			}else if(step < 5){
				g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 0, Player1XY, Player1XY * 1, Player1XY * 2, null);
			}else if(step < 10){
				g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 1, Player1XY, Player1XY * 2, Player1XY * 2, null);
			}else if(step < 15){
				g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 2, Player1XY, Player1XY * 3, Player1XY * 2, null);
			}else{
				g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, Player1XY * 3, Player1XY, Player1XY * 4, Player1XY * 2, null);
			}
			break;
		case d7:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY, Player1XY, Player1XY * 2, null);
			break;
		case d8:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY * 3, Player1XY, Player1XY * 4, null);
			break;
		case d9:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY * 2, Player1XY, Player1XY * 3, null);
			break;
		case d6:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY * 2, Player1XY, Player1XY * 3, null);
			break;
		case d3:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY * 2, Player1XY, Player1XY * 3, null);
			break;
		case d2:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, 0, Player1XY, Player1XY, null);
			break;
		case d1:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY, Player1XY, Player1XY * 2, null);
			break;
		default:
			g.drawImage(Player1Picture, X, Y, X + Player1XY, Y + Player1XY, 0, Player1XY, Player1XY, Player1XY * 2, null);
			break;
		}
	}
	/**
	 * �����1
	 * @param g
	 */
	private void HuaPlayer2(Graphics g){
		switch (ptDir) {
		case d4:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY, Player2XY, Player2XY * 2, null);
			break;
		case d7:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY, Player2XY, Player2XY * 2, null);
			break;
		case d8:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY * 3, Player2XY, Player2XY * 4, null);
			break;
		case d9:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY * 2, Player2XY, Player2XY * 3, null);
			break;
		case d6:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY * 2, Player2XY, Player2XY * 3, null);
			break;
		case d3:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY * 2, Player2XY, Player2XY * 3, null);
			break;
		case d2:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, 0, Player2XY, Player2XY, null);
			break;
		case d1:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY, Player2XY, Player2XY * 2, null);
			break;
		default:
			g.drawImage(Player2Picture, X, Y, X + Player2XY, Y + Player2XY, 0, Player2XY, Player2XY, Player2XY * 2, null);
			break;
		}
	}
	/**
	 * ��ȡ̹�˵ľ���
	 * @return
	 */
	public Rectangle getRect(){
		Rectangle rectangle = null;
		switch (TankType) {
		case type_player:
			rectangle = new Rectangle(X, Y, Player1XY, Player1XY);
			break;
		default:
			rectangle = new Rectangle(X, Y, Player2XY, Player2XY);
			break;
		}
		return rectangle;
	}
	//******************************************************************�����̸߳���
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
			//�ӵ���ʼ���� ��Ͳ��ʼ����  //���û�ж��Ͳ��ı䷽����
			if( FangXiang != Direction.d5 ) this.ptDir = this.FangXiang; 
			//̹�˲��ܳ���
			if(X < 0 || Y < 0 || X + Tank.Player1XY > WindowsXlength || Y + Tank.Player1XY + 30 > WindowsYlength) {
				this.stay();
			}
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
	 * �Զ������߳�
	 */
	public void autofire(){
		new Thread(new Runnable() {
			public void run() {
				int sleepInt = 0;
				int x = 0,y = 0;
				while(live){
					sleepInt = random.nextInt(2000) + 500;
					if(tankClient != null){
						x = Tank.this.X + Tank.Player1XY/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
						y = Tank.this.Y + Tank.Player1XY/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
						if(ptDir == Direction.d5)ptDir = Direction.d6;				//�ڵ����ܲ���
						//new ��һ���ӵ�
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
			int x = this.X + Tank.Player1XY/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1XY/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
			if(ptDir == Direction.d5)ptDir = Direction.d6;				//�ڵ����ܲ���
			//new ��һ���ӵ�
			Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_player, 5, 5, tankClient);
			missile.ZhuiZongPD = false;
			tankClient.missiles.add(missile);	
		}
	}		
	/**
	 * ��ҿ��� ����˷���
	 * @return
	 */
	public void BaFangfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.Player1XY/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1XY/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
			Direction[] directions = Direction.values();
			Direction direction = Direction.d1;
			for(int i = 0; i < 8; i++) {
				direction = directions[i];
				Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, tankClient);
				missile.ZhuiZongPD = false;
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
			int x = this.X + Tank.Player1XY/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1XY/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
			if(ptDir == Direction.d5)ptDir = Direction.d6;				//�ڵ����ܲ���
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
		case KeyEvent.VK_NUMPAD1:
			AtkKey = true;
			break;
		case KeyEvent.VK_NUMPAD2:
			AtkKey = true;
			break;
		case KeyEvent.VK_NUMPAD3:
			AtkKey = true;
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
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD2:		 
//			if(BaFangNumber > 7) {
				BaFangNumber -= 8;
				BaFangfire();
//			}
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD3:
//			if(ZhuiZongNumber > 0) {
				ZhuiZongNumber -= 1;
				ZhuiZongfire();
//			}
			AtkKey = false;
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
			g.drawRect(X, Y-12, Player1XY, 5);
			int w = Player1XY * blood/bloodZong;
			g.fillRect(X, Y-12, w, 5);
			g.setColor(c);
		}
	}	
	//******************************************************************̹�����������󻥶�
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
	
}
