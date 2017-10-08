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
public class TankClient extends JFrame implements InitValue{	
	//�������
	private MainWindows mainWindows;					//������ָ��
	private JPanel MainPanel;							//���������
	private mainPanel GamePanel;						//��Ϸ���
	public boolean StartGame = true;					//��ʼ��Ϸ
	//����Ϸ����
	public Background background;						//����ͼ��
	public List<Item> Items;							//����̹������
	public boolean CreateItemPD = true;					//�Ƿ����ɵ���̹��
	public Tank myTank;									//���̹��
	public static final int setLift = 3;				//��Ϸ���λ��
	public List<Tank> enemyTanks;						//����̹������
	public boolean CreateEnemyTanksPD = true;			//�Ƿ����ɵ���̹��
	public List<Missile> missiles;						//�ӵ�����
	public List<Explode> explodes;						//��ը����
	public List<Wall> walls;							//ǽ����
	public int killTankNumber = 0;						//ɱ��̹����
	public int reTankNumber = 0;						//�����������
	//�������
	public static Random random = new Random();			//�������
	
	/**
	 * ���캯��1
	 */
	public TankClient(){
		launchFrame();											//��������
		initObject();											//��ʼ��һЩ����
		launchGamePanel();										//��Ϸ������
		new Thread(new PaintThread()).start();					//������ͼ�߳�
		this.setVisible(true);									//��ʾ����
	}	
	/**
	 * ���캯��2
	 */
	public TankClient(MainWindows mainWindows){
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
		background = new Background(0, 0, "images/����2.png", this);
		walls = new ArrayList<Wall>();
		walls.add(new Wall(200, 100, 100, 50, "images/ǽ.png", TankClient.this));
		walls.add(new Wall(400, 300, 50, 100, "images/ǽ.png", TankClient.this));
		//������Ʒ
		Items = new ArrayList<Item>();
		new Thread(new CreatItem()).start();
		//�������̹��
		myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, this);
		while(myTank.ZhuangWalls(walls)) {
			myTank.setTankLive(false);
			myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, this);
		}
		//���ص���̹��
		enemyTanks = new ArrayList<Tank>();
		new Thread(new CreatEnemyTank()).start();
		//�����ӵ�
		missiles = new ArrayList<Missile>();
		//���ر�ը
		explodes = new ArrayList<Explode>();
		//************************************��������ˢ�� �߳�
		new Thread(new ShuJuShuaXin()).start();			
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
				if(enemyTanks.size() < 5){
					Tank enemyTank = new Tank(random(100, 650), random(100, 300), type_enemy, 1, 1, TankClient.this);
					while(enemyTank.ZhuangWalls(walls) || enemyTank.ZhuangTanks(enemyTanks) || enemyTank.ZhuangTank(myTank)) {
						enemyTank.setTankLive(false);
						enemyTank = new Tank(random(100, 650), random(100, 300), type_enemy, 1, 1, TankClient.this);
					}
					enemyTanks.add(enemyTank);
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
				if(Items.size() < 3){
					switch (itemsType) {
					case Blood:
						//���Ѫ
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.Blood, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.Blood, TankClient.this);
						}
						Items.add(item);
						break;
					case WeaponBaFang:
						//���ǹ�˷���
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponBaFang, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponBaFang, TankClient.this);
						}
						Items.add(item);
						break;
					case WeaponZhuiZong:
						//���ǹ׷����
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponZhuiZong, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponZhuiZong, TankClient.this);
						}
						Items.add(item);
						break;
					default:
						break;
					}
				}
				try {Thread.sleep(10000);} catch (Exception e) {}	//ˢ�¼��
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
				//ˢ���ӵ�����̹���¼�											
				for(int i = 0; i < missiles.size(); i++){						//�ڵ� �������
					Missile missile = missiles.get(i);	
					if(missile != null) {
						missile.hitTanks(enemyTanks);
						missile.hitTank(myTank);
					}
				}
				//ˢ����ҳԵ���Ʒ�¼�
				itemsType = myTank.eats(Items);
				switch (itemsType) {
				case Blood:
					myTank.setBlood(100);
					break;
				case WeaponBaFang:
					myTank.BaFangNumber += 20;
					break;	
				case WeaponZhuiZong:
					myTank.ZhuiZongNumber += 5;
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
			if( myTank.isTankLive() )myTank.draw(ImageG);					//���Լ��� tank	
			for(int i = 0; i < enemyTanks.size(); i++){						//�����˵�̹��
				Tank tank = enemyTanks.get(i);
				tank.draw(ImageG);	
			}											
			for(int i = 0; i < missiles.size(); i++){						//���ڵ�
				Missile missile = missiles.get(i);		
				missile.draw(ImageG);
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
			//******������Ϣ
//			ImageG.drawString("���̹��λ��: X." + myTank.getX() + " Y." + myTank.getY(), 300, 20);
//			ImageG.drawString("�ӵ�����:" + missiles.size(), 300, 40);	
//			ImageG.drawString("��ը����:" + explodes.size(), 300, 60);
//			ImageG.drawString("̹������:" + enemyTanks.size(), 300, 80);
			//******�����Ϣ
			//��ɱ̹����
			ImageG.drawString("��ɱ̹������:" + killTankNumber, 10, 20);
			//��ʾ������
			if(reTankNumber < 0){
				ImageG.drawString("����ֵ����:0", 10, 40);
			}else{
				ImageG.drawString("����ֵ����:" + reTankNumber, 10, 40);
			}
			//̹��Ѫ��
			ImageG.drawString("̹��Ѫ��:" + myTank.getBlood(), 10, 60);
			//���ӵ���
			ImageG.drawString("AOE������:" + myTank.BaFangNumber, 710, 20);
			ImageG.drawString("׷�ٵ�����:" + myTank.ZhuiZongNumber, 710, 40);
			//******��Ϸ���� ��ʾ�ܹ���ɱ��
			if(reTankNumber < 0){
				background.draw(ImageG);	
				ImageG.drawString("��Ϸ��������ɱ̹������:" + killTankNumber, 320, 225);		
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
			myTank.KEY(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:	
				//������Ϸ����̹��
				if(!myTank.isTankLive() && reTankNumber > 0){
					reTankNumber--;
					myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					while(myTank.ZhuangWalls(walls) || myTank.ZhuangTanks(enemyTanks) ) {
						myTank.setTankLive(false);
						myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					}
				}
				//���¿�ʼ�µ�һ��
				if(reTankNumber < 0){
					myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					while(myTank.ZhuangWalls(walls) || myTank.ZhuangTanks(enemyTanks) ) {
						myTank.setTankLive(false);
						myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
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
			myTank.noKEY(e);
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
