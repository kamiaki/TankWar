package playerwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * �ӵ���
 *
 */
public class Missile implements InitValue{
	//��ܼ�ָ��
	private PlayerClient tankClient = null;								//��ܼ�ָ��
	//�ӵ�����
	private boolean live = true;										//�ӵ��Ƿ����
	private int X, Y, xspeed, yspeed, oldX, oldY;						//�ӵ�λ�� �� �ٶ�
	private int MissileType = type_player;								//�ӵ�����
	private Direction MissileFangXiang;									//�ӵ�����
	//�����ӵ�
	private int MissileZhongLei = Misslie_putong;						//�ڵ�����
	public boolean ZhuiZongPD = false;									//׷�ٵ��Ƿ�����
	private static int ZhuiJiDistance = 100;								//׷������
	//************************************************************************************************��ͼ
	private int stepBF;																//�˷��ӵ�����
	private int stepPTX;															//��ͨ�ӵ�����
	private int stepPTY;															//��ͨ�ӵ�����
	private int stepZZX;															//zz�ӵ�����
	private int stepZZY;															//zz�ӵ�����
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	public static final int missileXlength = 80;									//�ӵ��Ĵ�С
	public static final int missileYlength = 80;									//�ӵ��Ĵ�С
	//�����ӵ�
	private int step;																//����
	private static Image[] MissileImages = new Image[10];							//�˸�ͼƬ
	private static Map<String, Image> MapImage = new HashMap<String, Image>();		//��ϣ��
	static{																			// ���ӵ�
		MissileImages[4] = tk.getImage(Player.class.getClassLoader().getResource("images/D4.png"));	
		MissileImages[4] = MissileImages[4].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[7] = tk.getImage(Player.class.getClassLoader().getResource("images/D7.png"));	
		MissileImages[7] = MissileImages[7].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[8] = tk.getImage(Player.class.getClassLoader().getResource("images/D8.png"));	
		MissileImages[8] = MissileImages[8].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[9] = tk.getImage(Player.class.getClassLoader().getResource("images/D9.png"));	
		MissileImages[9] = MissileImages[9].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[6] = tk.getImage(Player.class.getClassLoader().getResource("images/D6.png"));	
		MissileImages[6] = MissileImages[6].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[3] = tk.getImage(Player.class.getClassLoader().getResource("images/D3.png"));	
		MissileImages[3] = MissileImages[3].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[2] = tk.getImage(Player.class.getClassLoader().getResource("images/D2.png"));	
		MissileImages[2] = MissileImages[2].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MissileImages[1] = tk.getImage(Player.class.getClassLoader().getResource("images/D1.png"));	
		MissileImages[1] = MissileImages[1].getScaledInstance(missileXlength * 3, missileXlength, Image.SCALE_DEFAULT);
		MapImage.put("D4", MissileImages[4]);
		MapImage.put("D7", MissileImages[7]);
		MapImage.put("D8", MissileImages[8]);
		MapImage.put("D9", MissileImages[9]);
		MapImage.put("D6", MissileImages[6]);
		MapImage.put("D3", MissileImages[3]);
		MapImage.put("D2", MissileImages[2]);
		MapImage.put("D1", MissileImages[1]);
	}
	//��ͨ�ӵ�
	private static Image missilePT = tk.getImage(Missile.class.getClassLoader().getResource("images/ʯͷ.png"));
	static{
		missilePT = missilePT.getScaledInstance(missileXlength * 5, missileXlength * 2, Image.SCALE_DEFAULT);
	}	
	//�˷��ӵ�
	private static Image missileBF = tk.getImage(Missile.class.getClassLoader().getResource("images/��.png"));
	static{
		missileBF = missileBF.getScaledInstance(missileXlength * 4, missileXlength, Image.SCALE_DEFAULT);
	}	
	//׷���ӵ�
	private static Image missileZZ = tk.getImage(Missile.class.getClassLoader().getResource("images/������.png"));
	static{
		missileZZ = missileZZ.getScaledInstance(missileXlength * 5, missileXlength * 2, Image.SCALE_DEFAULT);
	}	
	
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param missileFangXiang
	 * @param tc
	 */
	public Missile(int x, int y,int MissileZhongLei, Direction missileFangXiang, int tankType, int xspeed,int yspeed, PlayerClient tc) {
		this.X = x;
		this.Y = y;
		this.MissileZhongLei = MissileZhongLei;
		this.MissileFangXiang = missileFangXiang;
		this.MissileType = tankType;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = tc;
		this.live = true;
		MissileDongHua();
		MissileQD();
	}
	//*****************************************************************************�ӵ���������
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
	//*****************************************************************************���ӵ�
	/**
	 * �ӵ����� ֡��
	 */
	private void MissileDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){				
					if(step > 2)step = 0;
					if(stepBF > 3)stepBF = 0;
					if(stepPTY > 1) stepPTY = 0;
					if(stepPTX > 4)	stepPTX = 0;
					if(stepZZY > 1) stepZZY = 0;
					if(stepZZX > 4)	stepZZX = 0;
					try {Thread.sleep(50);} catch (Exception e) {}
					
					step += 1;
					stepBF += 1;
					stepPTX += 1;
					if(stepPTX > 4){
						stepPTY += 1;
					}
					stepZZX += 1;
					if(stepZZX > 4){
						stepZZY += 1;
					}
				}				
					
			}
		}).start();
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
		switch (MissileType) {
		case type_player:
			switch (MissileZhongLei) {
			case Misslie_putong:
				HuaMissilePT(g,missileXlength,missileYlength);
				break;
			case Misslie_bafang:
				HuaMissileBF(g,missileXlength,missileYlength);
				break;
			case Misslie_zhuizong:
				HuaMissileZZ(g,missileXlength,missileYlength);
				break;
			default:
				break;
			}		
			break;
		default:
			HuaMissile(g,MapImage,missileXlength,missileYlength);
			break;
		}
	}
	/**
	 * �������ӵ�
	 * @param g
	 */
	private void HuaMissile(Graphics g ,  Map<String, Image> mapImage , int missileX, int missileY){
		switch (MissileFangXiang) {
		case d4:
			g.drawImage(MapImage.get("D4"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d7:
			g.drawImage(MapImage.get("D7"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d8:
			g.drawImage(MapImage.get("D8"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d9:
			g.drawImage(MapImage.get("D9"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d6:
			g.drawImage(MapImage.get("D6"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d3:
			g.drawImage(MapImage.get("D3"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d2:
			g.drawImage(MapImage.get("D2"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		case d1:
			g.drawImage(MapImage.get("D1"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		default:
			g.drawImage(MapImage.get("D4"), X, Y, X + missileXlength, Y + missileYlength, step * missileXlength, missileYlength * 0, (step+1) * missileXlength, missileYlength * 1, null);
			break;
		}
	}	
	/**
	 * ����ͨ�ӵ�
	 * @param g
	 */
	private void HuaMissilePT(Graphics g , int missileX, int missileY){
		g.drawImage(missilePT, X, Y, X + missileXlength, Y + missileYlength, stepPTX * missileXlength, stepPTY * missileYlength, (stepPTX+1) * missileXlength, (stepPTY+1) * missileYlength, null);
	}
	/**
	 * ���˷��ӵ�
	 * @param g
	 */
	private void HuaMissileBF(Graphics g , int missileX, int missileY){
		g.drawImage(missileBF, X, Y, X + missileXlength, Y + missileYlength, stepBF * missileXlength, 0 * missileYlength, (stepBF+1) * missileXlength, 1 * missileYlength, null);
	}
	/**
	 * ׷���ӵ�
	 * @param g
	 */
	private void HuaMissileZZ(Graphics g , int missileX, int missileY){
		g.drawImage(missileZZ, X, Y, X + missileXlength, Y + missileYlength, stepZZX * missileXlength, stepZZY * missileYlength, (stepZZX+1) * missileXlength, (stepZZY+1) * missileYlength, null);
	}
	/**
	 * ��ȡ�ӵ��ľ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X + missileXlength/2, Y + missileXlength/2, missileXlength/2, missileYlength/2);
	}
	//*****************************************************************************�ӵ������߳�
	/**
	 * �����ӵ����ݸ��³���
	 */
	private void MissileQD(){
		switch (MissileType) {
		case type_player:
			Follow();  				//����׷�ٵ��߳�
			MissileMove();			//�ӵ��ƶ��߳�
			TimeMissileDead();		//�ӵ������߳�
			break;
		default:
			MissileMove();			//�ӵ��ƶ��߳�
			TimeMissileDead();		//�ӵ������߳�
			break;
		}
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
				List<Player> enemytanks = tankClient.enemyPlayers;
				Player enemytank = null;
				//������̹�˻�����׷
				while(live){
					//�Ե�׷�ٵ���׷��
					while(ZhuiZongPD) {
						for(int i = 0; i < enemytanks.size(); i++) {
							try {
								enemytank = enemytanks.get(i);
							} catch (IndexOutOfBoundsException e) {
								e.printStackTrace();
							}
							try {
								Distance = Math.sqrt(Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2) + Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2));
							} catch (NullPointerException e) {
								e.printStackTrace();
							}
							if(Distance < ZhuiJiDistance) {
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
					this.missileDead();	
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
	//*****************************************************************************�ӵ����������� ����
	/**
	 * ����̹��
	 * @param t
	 * @return
	 */
	public boolean hitTank(Player t){
		if(this.getRect().intersects( t.getRect()) && t.isPlayerLive() && Missile.this.getMissileType() != t.getPlayerType() ){			
			Explode e = new Explode(this.X, this.Y, this.tankClient);	//���һ����ը
			tankClient.explodes.add(e);
			
			tankClient.ZhenDong();										//��ܼ� �𶯷���			
			
			this.live = false;											//�ӵ������ж�Ϊ��
			if(this != null) {
				tankClient.missiles.remove(this);						//�ڶ������Ƴ��ӵ�
			}
				
			if(t.getPlayerType() == type_player) {
				t.setBlood(t.getBlood() - 20);
				if(t.getBlood() <= 0) {
					t.setPlayerLive(false);								//���̹�������ж�Ϊ��	
					if(tankClient.rePlayerNumber == 0){
						tankClient.rePlayerNumber -= 1;
					}
				}
			}else if(t.getPlayerType() == type_enemy){	
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
					t.setPlayerLive(false);								//����̹�������ж�Ϊ��
					tankClient.killPlayerNumber += 1;
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
	public boolean hitTanks(List<Player> enemyTanks) {
		for(int i = 0; i < enemyTanks.size(); i++){
			try {
				if(hitTank(enemyTanks.get(i))){
					if(enemyTanks.get(i) != null) {
						enemyTanks.remove(enemyTanks.get(i));
					}
					return true;			
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
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
			Explode e = new Explode(this.X, this.Y, this.tankClient);	//���һ����ը
			tankClient.explodes.add(e);
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
			try {
				if(hitWall(walls.get(i))) {
					return true;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
