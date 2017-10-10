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
	private int TrackingDistance = 1000;					//̹��׷���뾶
	//�����
	public static Random random = new Random();			//�����
	//�����ӵ�����
	public int BaFangNumber = 0;						//�˷�������
	public int ZhuiZongNumber = 0;						//׷�ٵ�����
	//��ͼ
	private int step;														//��������
	private int stepfireX;													//��������
	private int stepfireY;													//��������
	private boolean AtkKey;													//���������
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//���߰�
	private static Image Player1Picture;									//����ͼƬ1
	public static final int Player1X = 79;									//�����С1
	public static final int Player1Y = 108;									//�����С1
	private static Image Player2Picture;									//����ͼƬ2
	public static final int Player2X = 79;									//�����С2
	public static final int Player2Y = 108;									//�����С2
	private static Image FirePicture;										//fire
	public static final int FireX = 120;									//fire
	public static final int FireY = 120;									//fire
	static{	
			Player1Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/KG1.png"));	//����1ͼƬ
			Player1Picture = Player1Picture.getScaledInstance(Player1X * 8, Player1Y * 8, Image.SCALE_DEFAULT);
		
			Player2Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/KG2.png"));	//����2ͼƬ
			Player2Picture = Player2Picture.getScaledInstance(Player2X * 8, Player2Y * 8, Image.SCALE_DEFAULT);
	
			FirePicture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/ʹ�ü���.png"));	//����ͼƬ
			FirePicture = FirePicture.getScaledInstance(FireX * 5, FireY * 4, Image.SCALE_DEFAULT);
	}

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
		PlayerMoveDongHua();
		PlayerFireDongHua();
		TankQD();
	}
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
	//******************************************************************̹�˲�������
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
	 * ��Ҷ��� �˶�֡��
	 */
	private void PlayerMoveDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(Up || Down || Left || Right || TankType != type_player){
						if(step > 7)step = 0;
					}else{
						step = 0;
					}
					if(AtkKey){
						if(stepfireY > 3) stepfireY = 0;
						if(stepfireX > 4) stepfireX = 0;
					}else{
						stepfireX = 0;
						stepfireY = 0;
					};
					try {Thread.sleep(50);} catch (Exception e) {}
					step += 1;
					stepfireX += 1;
					if(stepfireX > 4){
						stepfireY += 1;
					}
				}			
			}
		}).start();
	}	
	/**
	 * ��Ҷ��� ������ʽ֡��
	 */
	private void PlayerFireDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(AtkKey){
						if(stepfireY > 3) stepfireY = 0;
						if(stepfireX > 4) stepfireX = 0;
					}else{
						stepfireX = 0;
						stepfireY = 0;
					};
					try {Thread.sleep(20);} catch (Exception e) {}
					stepfireX += 1;
					if(stepfireX > 4){
						stepfireY += 1;
					}
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
			PlayerPicture(g);	
		}	
	}
	/**
	 * ������ͼ��ͼ
	 * @param g
	 */
	private void PlayerPicture(Graphics g){
		switch (TankType) {
		case type_player:
			HuaPlayer(g,Player1Picture,Player1X,Player1Y);
			Huafire(g,FirePicture,FireX,FireY);
			bloodBar.draw(g);
			break;
		default:
			HuaPlayer(g,Player2Picture,Player2X,Player2Y);
			bloodBar.draw(g);
			break;
		}
	}
	/**
	 * �����1
	 * @param g
	 */
	private void HuaPlayer(Graphics g , Image player , int playerX, int playerY){
		switch (ptDir) {
		case d4:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 1, (step+1) * Player1X, Player1Y * 2, null);
			break;
		case d7:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 6, (step+1) * Player1X, Player1Y * 7, null);
			break;
		case d8:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 3, (step+1) * Player1X, Player1Y * 4, null);
			break;
		case d9:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 7, (step+1) * Player1X, Player1Y * 8, null);
			break;
		case d6:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 2, (step+1) * Player1X, Player1Y * 3, null);
			break;
		case d3:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 5, (step+1) * Player1X, Player1Y * 6, null);
			break;
		case d2:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 0, (step+1) * Player1X, Player1Y * 1, null);
			break;
		case d1:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 4, (step+1) * Player1X, Player1Y * 5, null);
			break;
		default:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 0, (step+1) * Player1X, Player1Y * 1, null);
			break;
		}
	}
	/**
	 * ������
	 * @param g
	 */
	private void Huafire(Graphics g , Image fire , int fireX, int fireY){
		if(AtkKey){
			g.drawImage(fire  , X - 35, Y - 15, X + fireX, Y + fireY, stepfireX * fireX, stepfireY * fireY, (stepfireX+1) * fireX, (stepfireY+1) * fireY, null);
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
			rectangle = new Rectangle(X + 25, Y + 25, Player1X - 50, Player1Y - 30);
			break;
		default:
			rectangle = new Rectangle(X + 25, Y + 25, Player2X - 50, Player2Y - 30);
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
			if(X < 0 || Y < 0 || X + Tank.Player1X > WindowsXlength || Y + Tank.Player1Y + 30 > WindowsYlength) {
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
						x = Tank.this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
						y = Tank.this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
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
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
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
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
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
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
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
			if(BaFangNumber > 7) {
				BaFangNumber -= 8;
				BaFangfire();
			}
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD3:
			if(ZhuiZongNumber > 0) {
				ZhuiZongNumber -= 1;
				ZhuiZongfire();
			}
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
			g.setColor(Color.BLUE);
			g.drawRect(X, Y-12, Player1X, 5);
			int w = Player1X * blood/bloodZong;
			if(blood>50){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.RED);
			}
			g.fillRect(X, Y-11, w, 4);
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
				if(items.get(i) != null)items.remove(items.get(i));
				return itemsType;
			}
		}
		return ItemsType.NoItem;
	}
	
}
