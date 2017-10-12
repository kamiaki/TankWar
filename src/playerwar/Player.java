package playerwar;

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
 * ��ɫ��
 *
 */
public class Player implements InitValue{	
	//��ͻ�ָ��
	private PlayerClient playerClient;										//��ܼ�
	//��ɫ����
	private boolean live = true;											//��ɫ�Ƿ����
	private int playerType = type_player;									//��ɫ���
	private int blood = 100;												//����ֵ
	private int bloodZong = 100;											//����ֵ����
	private int Mana = 100;													//����ֵ
	private int ManaZong = 100;												//����ֵ����
	private Bar bar = new Bar();								//Ѫ����
	private int X, Y, oldX, oldY, xspeed, yspeed;							//��ɫλ�� �ٶ�
	private boolean Up = false, Down = false,Left = false, Right = false;	//��ҽ�ɫ ��������
	private Direction MoveFangXiang = Direction.d5;							//��ɫ �ƶ�����
	private Direction DrawFangXiang = Direction.d4;							//�ӵ� �� ��Ͳ����
	private int FollowDistance = 1000;										//��ɫ׷������
	private int XuLi = 0;													//��ɫ����
	private int XuLiZhi = 30;												//��ɫ����ֵ
	//�����
	public static Random random = new Random();								//�����
	//��ͼ
	private int step;														//�ƶ���������
	private int stepfireX;													//���ж������� ͼƬx����
	private int stepfireY;													//���ж������� ͼƬy����
	private boolean AtkKey;													//��������� �ж�
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//���߰�
	private static Image Player1Picture;									//����ͼƬ1
	public static final int Player1X = 79;									//����X��С1
	public static final int Player1Y = 108;									//����Y��С1
	private static Image Player2Picture;									//����ͼƬ2
	public static final int Player2X = 79;									//����X��С2
	public static final int Player2Y = 108;									//����Y��С2
	private static Image FirePicture;										//������ͼ
	public static final int FireX = 120;									//���д�СX
	public static final int FireY = 120;									//���д�СY
	static{	
		//����1ͼƬ
		Player1Picture = toolkit.getImage(Player.class.getClassLoader().getResource("images/KG1.png"));	
		Player1Picture = Player1Picture.getScaledInstance(Player1X * 8, Player1Y * 8, Image.SCALE_DEFAULT);
		//����2ͼƬ
		Player2Picture = toolkit.getImage(Player.class.getClassLoader().getResource("images/KG2.png"));	
		Player2Picture = Player2Picture.getScaledInstance(Player2X * 8, Player2Y * 8, Image.SCALE_DEFAULT);
		FirePicture = toolkit.getImage(Player.class.getClassLoader().getResource("images/ʹ�ü���.png"));	
		//����ͼƬ
		FirePicture = FirePicture.getScaledInstance(FireX * 5, FireY * 4, Image.SCALE_DEFAULT);
	}

	/**
	 * ��ɫ���캯��
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
 	public Player(int x, int y, int type, int xspeed, int yspeed, PlayerClient w){
		this.X = x;
		this.Y = y;
		this.playerType = type;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.playerClient = w;
		this.live = true;
		SetTypeBar(this.playerType);	//����Ѫ�� ħ��ֵ
		PlayerMoveDongHua();			//������ɫ�ƶ�����
		PlayerFireDongHua();			//������ɫ���ж���
		PlayerQD();						//������ɫ�̳߳���
	}
 	/**
	 * ���ݽ�ɫ��������Ѫ��
	 */
	public void SetTypeBar(int Type){
		switch (Type) {
		case type_player:
			blood = 200;
			bloodZong = blood;
			Mana = 200;													
			ManaZong = Mana;
			break;
		case type_enemy:
			blood = 60;
			bloodZong = blood;
			break;
		case type_boss:
			blood = 2000;
			bloodZong = blood;
			break;
		default:
			break;
		}
	}
	//******************************************************************��ɫ��������
	/**
	 *  * ��ȡħ��ֵ
	 * @return
	 */
	public int getMana() {
		return Mana;
	}
	/**
	 *  * �ı�ħ��ֵ
	 * @return
	 */
	public void setMana(int mana) {
		Mana = mana;
	}
	/**
	 *  * ��ȡѪ��
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
	 * �ж���ʲô��ɫ
	 * @return
	 */
	public int getPlayerType() {
		return playerType;
	}
	/**
	 * ������ʲô��ɫ
	 * @param good
	 */
	public void setPlayerType(int type) {
		playerType = type;
	}
	/**
	 * �ж�����
	 * @return
	 */
	public boolean isPlayerLive() {
		return live;
	}
	/**
	 * ��������
	 * @param tankLive
	 */
	public void setPlayerLive(boolean tankLive) {
		this.live = tankLive;
	}	
	/**
	 * ��ɫxλ��
	 * @return
	 */
	public int getY() {
		return Y;
	}
	/**
	 * ��ɫYλ��
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
					if(Up || Down || Left || Right || playerType != type_player){
						if(step > 7)step = 0;
					}else{
						step = 0;
					}
					try {Thread.sleep(50);} catch (Exception e) {}
					step += 1;
				}			
			}
		}).start();
	}	
	/**
	 * ��Ҷ��� ������ʽ֡�� ����
	 */
	private void PlayerFireDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(AtkKey){
						if(XuLi < XuLiZhi)XuLi++;
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
	 * ����ɫ
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
		switch (playerType) {
		case type_player:
			HuaPlayer(g,Player1Picture,Player1X,Player1Y);
			Huafire(g,FirePicture,FireX,FireY);
			bar.draw(g);
			break;
		default:
			HuaPlayer(g,Player2Picture,Player2X,Player2Y);
			bar.draw(g);
			break;
		}
	}
	/**
	 * �����1
	 * @param g
	 */
	private void HuaPlayer(Graphics g , Image player , int playerX, int playerY){
		switch (DrawFangXiang) {
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
	 * ��ȡ��ɫ�ľ���
	 * @return
	 */
	public Rectangle getRect(){
		Rectangle rectangle = null;
		switch (playerType) {
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
	 * ������ɫ���ݸ��³���
	 */
	private void PlayerQD(){
		if(this.playerType == type_player){
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
					//�����ҽ�ɫ������׷
					if( playerClient.myPlayer.isPlayerLive() ){
						tankx = playerClient.myPlayer.X;
						tanky = playerClient.myPlayer.Y;	
						if( Math.sqrt(Math.pow(Math.abs(Player.this.X - tankx), 2) + Math.pow(Math.abs(Player.this.Y - tanky), 2)) < FollowDistance ){
							if(Player.this.X < tankx && Player.this.Y == tanky){
								MoveFangXiang = Direction.d6;
							}else if(Player.this.X < tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d3;
							}else if(Player.this.X == tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d2;
							}else if(Player.this.X > tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d1;
							}else if(Player.this.X > tankx && Player.this.Y == tanky){
								MoveFangXiang = Direction.d4;
							}else if(Player.this.X > tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d7;
							}else if(Player.this.X == tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d8;
							}else if(Player.this.X < tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d9;
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
					tankx = playerClient.myPlayer.X;
					tanky = playerClient.myPlayer.Y;
					if(Math.sqrt(Math.pow(Math.abs(Player.this.X - tankx), 2) + Math.pow(Math.abs(Player.this.Y - tanky), 2))  >= FollowDistance){
						Direction[] dirs = Direction.values();
						MoveFangXiang = dirs[ random.nextInt(dirs.length) ];
					}
					try {Thread.sleep(time);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * ��ɫ�ƶ�
	 */
	private void move() {
		if( Player.this.ZhuangWalls(playerClient.walls) || Player.this.ZhuangTank(playerClient.myPlayer) || Player.this.ZhuangTanks(playerClient.enemyPlayers) ) {
			this.stay();
		}else {
			//��һ���ƶ�������
			this.oldX = X;
			this.oldY = Y;		
			//��ʼ�ƶ�
			switch (MoveFangXiang) {
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
			if( MoveFangXiang != Direction.d5 ) this.DrawFangXiang = this.MoveFangXiang; 
			//��ɫ���ܳ���
			if(X < 0 || Y < 0 || X + Player.Player1X > WindowsXlength || Y + Player.Player1Y + 30 > WindowsYlength) {
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
					if(playerClient != null){
						x = Player.this.X + Player.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
						y = Player.this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
						if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//�ڵ����ܲ���
						//new ��һ���ӵ�
						Missile missile = new Missile(x, y, Misslie_putong, DrawFangXiang, type_enemy, 2, 2, playerClient);
						playerClient.missiles.add(missile);	
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
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;				//�Ӷ������ķ����ӵ�
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;				//�Ӷ������ķ����ӵ�
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//�ڵ����ܲ���
			//new ��һ���ӵ�
			Missile missile = new Missile(x, y, Misslie_putong, DrawFangXiang, type_player, 5, 5, playerClient);
			missile.ZhuiZongPD = false;
			playerClient.missiles.add(missile);	
		}
	}	
	/**
	 * ��ҿ��� ����һ��������ͨ�ӵ�
	 * @return
	 */
	public void Superfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;				//�Ӷ������ķ����ӵ�
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;				//�Ӷ������ķ����ӵ�
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//�ڵ����ܲ���
			//new ��һ���ӵ�
			Missile missile = new Missile(x, y, Misslie_bafang, DrawFangXiang, type_player, 5, 5, playerClient);
			missile.ZhuiZongPD = false;
			playerClient.missiles.add(missile);	
		}
	}		
	/**
	 * ��ҿ��� ����˷���
	 * @return
	 */
	public void BaFangfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			new Thread(new Runnable() {
				public void run() {		
					for(int i = 0; i < 8; i++) {
						int x = Player.this.X + Player.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
						int y = Player.this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
						Direction[] directions = Direction.values();
						Direction direction = Direction.d1;
						direction = directions[i];
						Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, playerClient);
						missile.ZhuiZongPD = true;
						playerClient.missiles.add(missile);	
						try {Thread.sleep(50);} catch (Exception e) {}	
					}				
				}
			}).start();
					
		}
	}
	/**
	 * ��ҿ��� ����һ��׷���ӵ�
	 * @return
	 */
	public void ZhuiZongfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;	//�Ӷ������ķ����ӵ�
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//�Ӷ������ķ����ӵ�
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//�ڵ����ܲ���
			Missile missile = new Missile(x, y, Misslie_zhuizong, DrawFangXiang, type_player, 3, 3, playerClient);
			missile.ZhuiZongPD = true;
			playerClient.missiles.add(missile);	
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
			if(XuLi >= XuLiZhi){
				Superfire();
			}else{
				fire();	
			}
			XuLi = 0;
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD2:
			if(XuLi >= XuLiZhi && Mana >= 20){
				BaFangfire();
				Mana -= 20;
			}		
			XuLi = 0;
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD3:
			if(XuLi >= XuLiZhi && Mana >= 10){
				ZhuiZongfire();
				Mana -= 10;
			}	
			XuLi = 0;
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
		if(!Up && !Down && Left && !Right)MoveFangXiang = Direction.d4;
		else if(Up && !Down && Left && !Right)MoveFangXiang = Direction.d7;
		else if(Up && !Down && !Left && !Right)MoveFangXiang = Direction.d8;
		else if(Up && !Down && !Left && Right)MoveFangXiang = Direction.d9;
		else if(!Up && !Down && !Left && Right)MoveFangXiang = Direction.d6;
		else if(!Up && Down && !Left && Right)MoveFangXiang = Direction.d3;
		else if(!Up && Down && !Left && !Right)MoveFangXiang = Direction.d2;
		else if(!Up && Down && Left && !Right)MoveFangXiang = Direction.d1;
		else if(!Up && !Down && Left && Right)MoveFangXiang = Direction.d5;
		else if(Up && Down && !Left && !Right)MoveFangXiang = Direction.d5;
		else if(!Up && !Down && !Left && !Right)MoveFangXiang = Direction.d5;
		else if(Up && Down && Left && Right)MoveFangXiang = Direction.d5;
	}
	/**
	 * ����
	 *
	 */
	private class Bar {
		//����
		public void draw(Graphics g) {
			BarPicture(g);
		}
		//����ͼ
		private void BarPicture(Graphics g) {
			int BarLength = 0;
			int BarHeight = 5;
			int offset = -120;
			int offsetXuLi = -10;
			//���滭����ɫ
			Color c = g.getColor();
			//Ѫ��
			g.setColor(Color.BLACK);
			g.drawRect(X, Y-(offset + BarHeight * 2), Player1X, BarHeight);
			BarLength = Player1X * blood / bloodZong;
			if( blood > bloodZong / 5 ){g.setColor(Color.GREEN);}
			else{g.setColor(Color.RED);}
			g.fillRect(X + 1, Y-(offset + BarHeight * 2) + 1, BarLength - 1, BarHeight - 1);
			//��������
			if(playerType == type_player){
				//ħ����
				g.setColor(Color.BLACK);
				g.drawRect(X, Y-(offset + BarHeight * 1), Player1X, BarHeight);
				BarLength = Player1X * Mana / ManaZong;
				if( Mana > ManaZong / 5 ){g.setColor(Color.BLUE);}
				else{g.setColor(Color.RED);}
				g.fillRect(X + 1, Y-(offset + BarHeight * 1) + 1, BarLength - 1, BarHeight - 1);
				//������
				BarLength = Player1X * XuLi / XuLiZhi;
				g.setColor(Color.YELLOW);
				g.fillRect(X + 1, Y-(offsetXuLi + BarHeight * 0) + 1, BarLength - 1, BarHeight - 1);
			}
			//��ԭ������ɫ
			g.setColor(c);
		}
	}	
	//******************************************************************��ɫ���������󻥶�
	/**
	 * ��ɫײǽ
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Player.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * ��ɫײ����ǽ
	 * @param w
	 * @return
	 */
	public boolean ZhuangWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			try {
				if(ZhuangWall(walls.get(i))){
					return true;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
		}
		return false;
	}
	/**
	 * ��ɫ���ɫ��ײ
	 * @param t
	 * @return
	 */
	public boolean ZhuangTank(Player t) {
		if(this.live == true && t.live == true && this.getRect().intersects(t.getRect()) && this != t   ) {
			return true;
		}
		return false;
	}
	/**
	 * ��ɫ���ɫ��ײ ��ȡ����
	 * @param t
	 * @return
	 */
	public boolean ZhuangTanks(List<Player> Players) {
		if(Players != null){
			for(int i = 0; i < Players.size(); i++) {
				try {
					if( ZhuangTank(Players.get(i)) ) {
						return true;
					}	
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}			
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
			try {
				itemsType = eat(items.get(i));
				if(itemsType != ItemsType.NoItem) {
					if(items.get(i) != null)items.remove(items.get(i));
					return itemsType;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
		}
		return ItemsType.NoItem;
	}
	
}
