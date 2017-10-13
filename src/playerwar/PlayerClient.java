package playerwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
/**
 * ��ܼ���
 *
 */
public class PlayerClient extends JFrame implements InitValue{	
	//�̶�����
	public static final int setLift = 3;				//��ʼ�����������
	//�������
	public MainWindows mainWindows;					//������ָ��
	public JPanel MainPanel;							//���������
	public mainPanel GamePanel;						//��Ϸ���
	public boolean StartGame = true;					//��ʼ��Ϸ
	//����Ϸ����
	public Background background;						//����ͼ��
	public List<Item> Items;							//��Ʒ����
	public int ItemLength = 5;							//����Ʒ����
	public int ItemTime = 3000;							//��Ʒˢ��ʱ��
	public boolean CreateItemPD = true;					//�Ƿ�������Ʒ
	public Player myPlayer;								//�������
	public List<Player> enemyPlayers;					//��������
	public int EnemyLength = 5;							//����Ʒ����
	public int EnemyTime = 2000;						//��Ʒˢ��ʱ��
	public boolean CreateEnemyPlayersPD = true;			//�Ƿ����ɵ���
	public List<Missile> missiles;						//�ӵ�����
	public List<Explode> explodes;						//��ը����
	public List<Wall> walls;							//ǽ����
	public List<Door> doors;							//������
	public int killPlayerNumber = 0;					//ɱ��������
	public int rePlayerNumber = 0;						//�����������
	//�����
	public static Random random = new Random();			//���������
	//���߳�
	public Thread SXSjThread = null;					//ˢ�������߳�			
	
	/**
	 * ���캯��
	 * @param mainWindows //���봰��ָ��
	 */
	public PlayerClient(MainWindows mainWindows){
		this.mainWindows = mainWindows;							//������ָ�븳ֵ
		launchFrame();											//��������
		initObject();											//��ʼ��һЩ����
		launchGamePanel();										//��Ϸ������
		new Thread(new PaintThread()).start();					//������ͼ�߳�
		this.setVisible(true);									//��ʾ����
	}	
	//**********************************************************���촰�� ��ʼ��
	/**
	 * ��ʼ������
	 */
	public void launchFrame(){
		//��������
		this.setTitle("KamiAki's First JavaGame");
		this.setSize(WindowsXlength, WindowsYlength);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
				mainWindows.mFrame.setVisible(true);
			}
		});	
		//���̼���
		this.addKeyListener(new Keylistener());	
		//������壨��Ϸ��� �����У�
		MainPanel = new JPanel();
		MainPanel.setLayout(null);
		this.setContentPane(MainPanel);		
	}
	/**
	 * ��Ϸ������
	 */
	public void launchGamePanel() {
		GamePanel = new mainPanel(); 
		GamePanel.setLayout(null);
		GamePanel.setLocation(PanelX, PanelY);
		GamePanel.setSize(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2));			
		MainPanel.add(GamePanel);	
	}
	/**
	 * ��ʼ��̹���ӵ��Ȳ���
	 */
	public void initObject(){
		rePlayerNumber = setLift;			//������
		//���ر���
		if(background == null)background = new Background(0, 0, 2,PlayerClient.this);
		//�������̹��
		myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, this);
		//�����ӵ�
		if(missiles == null)missiles = new ArrayList<Missile>();
		//���ر�ը
		if(explodes == null)explodes = new ArrayList<Explode>();
		//����
		if(doors == null)doors = new ArrayList<Door>();
		doors.add(new Door(0, 450, Door_woods, PlayerClient.this));
		//************************************��������ˢ�� �߳�
		SXSjThread = new Thread(new ShuJuShuaXin());
		SXSjThread.start();
	}
	//**********************************************************�����߳�
	/**
	 * ����̹���߳�
	 * @author Administrator
	 *
	 */
	public class CreateEnemyPlayer implements Runnable{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while(CreateEnemyPlayersPD){
				if(CreateEnemyPlayersPD && enemyPlayers.size() < EnemyLength){
					Player enemyPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					while(enemyPlayer.ZhuangWalls(walls) || enemyPlayer.ZhuangTanks(enemyPlayers) || enemyPlayer.ZhuangTank(myPlayer)) {
						enemyPlayer.setPlayerLive(false);
						enemyPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					}
					enemyPlayers.add(enemyPlayer);
				}
				try {Thread.sleep(EnemyTime);} catch (Exception e) {}	//ˢ�¼��
			}
		}
	}	
	/**
	 * ������Ʒ�߳�
	 * @author Administrator
	 *
	 */
	public class CreateItem implements Runnable{
		@Override
		public void run() {	
			Item item = null;
			ItemsType[] itemsTypes = ItemsType.values();
			while(CreateItemPD){
				ItemsType itemsType = itemsTypes[random(0, itemsTypes.length)];
				if(Items.size() >= ItemLength && CreateItemPD == true) {
					//ץԽ���쳣
					try{
						if(Items.get(0) != null) {
							Items.remove(Items.get(0));
						}
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}		
				}
				if(Items.size() < ItemLength && CreateItemPD == true){
					switch (itemsType) {
					case Blood:
						//���Ѫ
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.Blood, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.Blood, PlayerClient.this);
						}
						Items.add(item);
						break;
					case WeaponBaFang:
						//���ǹ�˷���
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponBaFang, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponBaFang, PlayerClient.this);
						}
						Items.add(item);
						break;
					case WeaponZhuiZong:
						//���ǹ׷����
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponZhuiZong, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponZhuiZong, PlayerClient.this);
						}
						Items.add(item);
						break;
					default:
						break;
					}
				}
				try {Thread.sleep(ItemTime);} catch (Exception e) {}	//ˢ�¼��
			}
		}
	}
	/**
	 * �ػ��߳�
	 */
	public class PaintThread implements Runnable{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while(StartGame){
				GamePanel.repaint();
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	/**
	 * ����ˢ��
	 */
	public class ShuJuShuaXin implements Runnable{
		@Override
		public void run() {
			ItemsType itemsType = ItemsType.NoItem;								//�Ե��˺�����Ʒ
			while(StartGame){
				//ˢ���ӵ�����̹���¼�		
				Missile missile = null;
				if(missiles != null){
					for(int i = 0; i < missiles.size(); i++){					//�ڵ� �������	
						//ץԽ���쳣
						try {
							missile = missiles.get(i);	
							if(missile != null) {
								if(enemyPlayers != null)missile.hitTanks(enemyPlayers);
								if(myPlayer != null)missile.hitTank(myPlayer);
							}
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}			
					}	
				}
				//ˢ����ҳԵ���Ʒ�¼�
				itemsType = myPlayer.eats(Items);
				switch (itemsType) {
				case Blood:
					myPlayer.setBlood(myPlayer.getBlood() + 40);
					if(myPlayer.getBlood() >200){
						myPlayer.setBlood(200);
					}
					break;
				case WeaponBaFang:
					myPlayer.setMana(myPlayer.getMana() + 20);
					if(myPlayer.getMana() >200){
						myPlayer.setMana(200);
					}
					break;	
				case WeaponZhuiZong:
					myPlayer.setMana(myPlayer.getMana() + 40);
					if(myPlayer.getMana() >200){
						myPlayer.setMana(200);
					}
					break;
				default:
					break;
				}
				//ѭ��ˢ�¼��
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	//**********************************************************����Ϸ��� ��������
	/*
	 * ���������
	 */
	public class mainPanel extends JPanel{		
		@Override
		public void paint(Graphics g) {
			super.paint(g);			
			Image offScreenImage = Doublebuffer();			//�Ƚ����ݻ�����ͼƬ��
			g.drawImage(offScreenImage, 0, 0 , null);		//˫����
		}		
		/**
		 * ˫���� ����
		 * @return
		 */
		private Image Doublebuffer(){

			Image image = mainPanel.this.createImage(WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2));
			Graphics ImageG = image.getGraphics();
			//ץԽ���쳣
			try {
			//����ͼ�� �� ǽ �� ��
				if(background != null){
					background.draw(ImageG);	
				}
				if(walls != null){
					for(int i = 0; i < walls.size(); i++) {
						Wall wall = walls.get(i);
						if(wall != null)wall.draw(ImageG);
					}
				}
				if(doors != null){
					for(int i = 0; i < doors.size(); i++) {
						Door door = doors.get(i);
						if(door != null)door.draw(ImageG);
					}
				}
			//������Ϣ
				if(enemyPlayers != null){
					for(int i = 0; i < enemyPlayers.size(); i++){					//�����˵�̹��
						Player tank = enemyPlayers.get(i);
						if(tank != null)tank.draw(ImageG);	
					}
				}	
				if(myPlayer != null){
					if( myPlayer.isPlayerLive() )myPlayer.draw(ImageG);				//���Լ��� tank		
				}
				if(missiles != null){
					for(int i = 0; i < missiles.size(); i++){					//���ڵ�
						Missile missile = missiles.get(i);		
						if(missile != null)missile.draw(ImageG);
					}
				}
				if(explodes != null){
					for(int i = 0; i < explodes.size(); i++){						//����ը
						Explode e = explodes.get(i);
						try {
							if(e != null)e.draw(ImageG);
						} catch (NullPointerException e2) {
							e2.printStackTrace();
						}
					}
				}
				if(Items != null){
					for(int i = 0; i < Items.size(); i++){							//����ը
						Item item = Items.get(i);
						if(item != null)item.draw(ImageG);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
			//*************������Ϣ
			//��ɱ̹����
			ImageG.drawString("��ɱ̹������:" + killPlayerNumber, 10, 20);
			//��ʾ������
			if(rePlayerNumber < 0){
				ImageG.drawString("����ֵ����:0", 10, 40);
			}else{
				ImageG.drawString("����ֵ����:" + rePlayerNumber, 10, 40);
			}
			//̹��Ѫ��
			ImageG.drawString("̹��Ѫ��:" + myPlayer.getBlood(), 10, 60);
			//******��Ϸ���� ��ʾ�ܹ���ɱ��
			if(rePlayerNumber < 0){
				background.draw(ImageG);	
				ImageG.drawString("��Ϸ��������ɱ̹������:" + killPlayerNumber, 10, 120);		
			}
			return image;
		}
	}
	/**
	 * ���̼�����
	 * @author Administrator
	 *
	 */
	public class Keylistener extends KeyAdapter {
		//����
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myPlayer.KEY(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:	
				//������Ϸ����̹��
				if(!myPlayer.isPlayerLive() && rePlayerNumber > 0){
					rePlayerNumber--;
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setPlayerLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
				}
				//���¿�ʼ�µ�һ��
				if(rePlayerNumber < 0){
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setPlayerLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
					rePlayerNumber = setLift;
					killPlayerNumber = 0;
				}
				break;
			case KeyEvent.VK_Q:				
				break;
			case KeyEvent.VK_E:	
				break;
			default:
				break;
			}
		}
		//̧��
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			myPlayer.noKEY(e);
		}
	}
	//**********************************************************һЩ��������
	/**
	 * �����Χ�� ֻ��������
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max){
		int jieguo = random.nextInt(max)%(max-min+1) + min;	
		return jieguo;
	}
	/**
	 * ��
	 */
	public void ZhenDong(){
		new Thread(new Runnable() {
			public void run() {
				int windowsXz = 0;								//����X
				int windowsYz = 0;								//����Y
				int ZFX = 1;									//x��������+1-1
				int ZFPDX =0;									//���������Ǹ�PD
				int ZFY = 1;									//y��������+1-1
				int ZFPDY =0;									//���������Ǹ�PD
				
				for(int i = 0; i < 10; i++){					
					ZFPDX = random(1, 10);						//���������Ǹ�
					ZFPDY = random(1, 10);						//���������Ǹ�
					windowsXz = random(1, PanelX * (-1));		//����
					windowsYz = random(1, PanelY * (-1));		//����
					if(ZFPDX <= 5){ZFX = -1;}else{ZFX = 1;}		//�� +1 or -1
					if(ZFPDY <= 5){ZFY = -1;}else{ZFY = 1;}		//�� +1 or -1				
					GamePanel.setLocation(PanelX + ZFX * windowsXz, PanelY + ZFY * windowsYz);
					try {Thread.sleep(20);} catch (Exception e) {}	
				}
				GamePanel.setLocation(PanelX,PanelY);	
			}
		}).start();	
	}	
}
