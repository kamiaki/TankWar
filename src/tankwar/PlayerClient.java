package tankwar;

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
	//�������
	private MainWindows mainWindows;					//������ָ��
	private JPanel MainPanel;							//���������
	private mainPanel GamePanel;						//��Ϸ���
	public boolean StartGame = true;					//��ʼ��Ϸ
	//����Ϸ����
	public Background background;						//����ͼ��
	public List<Item> Items;							//����̹������
	public boolean CreateItemPD = true;					//�Ƿ����ɵ���̹��
	public Player myPlayer;									//���̹��
	public static final int setLift = 3;				//��Ϸ���λ��
	public List<Player> enemyPlayers;						//����̹������
	public boolean CreateEnemyTanksPD = true;			//�Ƿ����ɵ���̹��
	public List<Missile> missiles;						//�ӵ�����
	public List<Explode> explodes;						//��ը����
	public List<Wall> walls;							//ǽ����
	public int killTankNumber = 0;						//ɱ��̹����
	public int reTankNumber = 0;						//�����������
	//�������
	public static Random random = new Random();			//�������
	//���߳�
	public Thread SXSjThread = null;					//ˢ�������߳�			
	
	/**
	 * ���캯��1
	 */
	public PlayerClient(){
		launchFrame();											//��������
		initObject();											//��ʼ��һЩ����
		launchGamePanel();										//��Ϸ������
		new Thread(new PaintThread()).start();					//������ͼ�߳�
		this.setVisible(true);									//��ʾ����
	}	
	/**
	 * ���캯��2
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
				mainWindows.frame.setVisible(true);
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
		//������
		reTankNumber = setLift;
		//���ر��� �ϰ�
		background = new Background(0, 0, this);
		walls = new ArrayList<Wall>();
		walls.add(new Wall(500, 400, 400, 50, PlayerClient.this));
		walls.add(new Wall(1200, 200, 50, 500, PlayerClient.this));
		//������Ʒ
		Items = new ArrayList<Item>();
		new Thread(new CreatItem()).start();
		//�������̹��
		myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, this);
		while(myPlayer.ZhuangWalls(walls)) {
			myPlayer.setTankLive(false);
			myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, this);
		}
		//���ص���̹��
		enemyPlayers = new ArrayList<Player>();
		new Thread(new CreatEnemyTank()).start();
		//�����ӵ�
		missiles = new ArrayList<Missile>();
		//���ر�ը
		explodes = new ArrayList<Explode>();
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
	private class CreatEnemyTank implements Runnable{
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			while(CreateEnemyTanksPD){
				if(enemyPlayers.size() < 5){
					Player enemyTank = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					while(enemyTank.ZhuangWalls(walls) || enemyTank.ZhuangTanks(enemyPlayers) || enemyTank.ZhuangTank(myPlayer)) {
						enemyTank.setTankLive(false);
						enemyTank = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					}
					enemyPlayers.add(enemyTank);
				}
				try {Thread.sleep(2000);} catch (Exception e) {}	//ˢ�¼��
			}
		}
	}	
	/**
	 * ������Ʒ�߳�
	 * @author Administrator
	 *
	 */
	private class CreatItem implements Runnable{
		@Override
		public void run() {	
			Item item = null;
			ItemsType[] itemsTypes = ItemsType.values();
			while(CreateItemPD){
				ItemsType itemsType = itemsTypes[random(0, itemsTypes.length)];
				if(Items.size() >= 3) {
					if(Items.get(0) != null) {
						Items.remove(Items.get(0));
					}				
				}
				if(Items.size() < 5){
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
				try {Thread.sleep(3000);} catch (Exception e) {}	//ˢ�¼��
			}
		}
	}
	/**
	 * �ػ��߳�
	 */
	private class PaintThread implements Runnable{
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
			ItemsType itemsType = ItemsType.NoItem;				//�Ե��˺�����Ʒ
			while(StartGame){
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
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	//**********************************************************����Ϸ��� ��������
	/*
	 * ���������
	 */
	private class mainPanel extends JPanel{		
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
			//����ͼ�� �� ǽ
			background.draw(ImageG);	
			for(int i = 0; i < walls.size(); i++) {
				Wall wall = walls.get(i);
				wall.draw(ImageG);
			}
			//������Ϣ
			for(int i = 0; i < enemyPlayers.size(); i++){					//�����˵�̹��
				Player tank = enemyPlayers.get(i);
				tank.draw(ImageG);	
			}
			
			if( myPlayer.isTankLive() )myPlayer.draw(ImageG);				//���Լ��� tank	
			
			for(int i = 0; i < missiles.size(); i++){						//���ڵ�
				Missile missile = missiles.get(i);		
				if(missile != null)missile.draw(ImageG);
			}
			for(int i = 0; i < explodes.size(); i++){						//����ը
				Explode e = explodes.get(i);
				e.draw(ImageG);
			}			
			for(int i = 0; i < Items.size(); i++){							//����ը
				Item item = Items.get(i);
				item.draw(ImageG);
			}
			//*************������Ϣ
			//��ɱ̹����
			ImageG.drawString("��ɱ̹������:" + killTankNumber, 10, 20);
			//��ʾ������
			if(reTankNumber < 0){
				ImageG.drawString("����ֵ����:0", 10, 40);
			}else{
				ImageG.drawString("����ֵ����:" + reTankNumber, 10, 40);
			}
			//̹��Ѫ��
			ImageG.drawString("̹��Ѫ��:" + myPlayer.getBlood(), 10, 60);
			//******��Ϸ���� ��ʾ�ܹ���ɱ��
			if(reTankNumber < 0){
				background.draw(ImageG);	
				ImageG.drawString("��Ϸ��������ɱ̹������:" + killTankNumber, 10, 120);		
			}
			return image;
		}
	}
	/**
	 * ���̼�����
	 * @author Administrator
	 *
	 */
	private class Keylistener extends KeyAdapter {
		//����
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myPlayer.KEY(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:	
				//������Ϸ����̹��
				if(!myPlayer.isTankLive() && reTankNumber > 0){
					reTankNumber--;
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setTankLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
				}
				//���¿�ʼ�µ�һ��
				if(reTankNumber < 0){
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setTankLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
					reTankNumber = setLift;
					killTankNumber = 0;
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
