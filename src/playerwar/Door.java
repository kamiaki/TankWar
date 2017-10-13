package playerwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import playerwar.PlayerClient.CreateEnemyPlayer;
import playerwar.PlayerClient.CreateItem;

/**
 * ����
 * @author Administrator
 *
 */
public class Door implements InitValue{
	private static int Width = 50, Height = 50;
	private int X, Y;
	private int DoorType = Door_city;
	
	/**
	 * ��ȡ��ʲô��
	 * @return
	 */
	public int getDoorType() {
		return DoorType;
	}
	/**
	 * ������ʲô��
	 * @param doorType
	 */
	public void setDoorType(int doorType) {
		DoorType = doorType;
	}
	private PlayerClient playerClient;
	
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param playerClient
	 */
	public Door(int x, int y, int doorType,PlayerClient playerClient) {
		this.X = x;
		this.Y = y;
		this.DoorType = doorType;
		this.playerClient = playerClient;
	}
	/**
	 * ����
	 */
	public void draw(Graphics g){
		doorPicture(g);
	}
	/**
	 * ����ͼ
	 * @param g
	 */
	private void doorPicture(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillRect(X, Y, Width, Height);
		g.setColor(c);
	}
	/**
	 * ��ȡ����ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, Width, Height);
	}
	/**
	 * ������֮���ͺδ�
	 */
	public void ChuanSong(){
		switch (this.DoorType) {
		case Door_woods:
			MapOff();
			MapWoodsStart();
			break;
		case Door_city:
			MapOff();
			playerClient.myPlayer.setX(100);
			playerClient.myPlayer.setY(400);	
			//���ر���
			if(playerClient.background == null)playerClient.background = new Background(0, 0, 2,playerClient);
			if(playerClient.doors == null)playerClient.doors = new ArrayList<Door>();
			playerClient.doors.add(new Door(0, 450, Door_woods, playerClient));		
			break;
		default:
			break;
		}
	}	
	/**
	 * ������ͼ����
	 */
	public void MapWoodsStart(){
		//�������λ��
		playerClient.myPlayer.setX(1400);
		playerClient.myPlayer.setY(400);
		//���ر���
		if(playerClient.background == null)playerClient.background = new Background(0, 0, 1,playerClient);
		//����ǽ
		if(playerClient.walls == null)playerClient.walls = new ArrayList<Wall>();
		playerClient.walls.add(new Wall(100, 100, 100, 50, playerClient));
		playerClient.walls.add(new Wall(300, 300, 50, 100, playerClient));
		//������
		if(playerClient.doors == null)playerClient.doors = new ArrayList<Door>();
		playerClient.doors.add(new Door(1500, 450, Door_city,playerClient));
		//������Ʒ
		if(playerClient.Items == null)playerClient.Items = new ArrayList<Item>();
		playerClient.CreateItemPD = true;
		new Thread(playerClient.new CreateItem()).start();
		//���ص���̹��
		if(playerClient.enemyPlayers == null)playerClient.enemyPlayers = new ArrayList<Player>();
		playerClient.CreateEnemyPlayersPD = true;
		new Thread(playerClient.new CreateEnemyPlayer()).start();
		//�����ӵ�
		if(playerClient.missiles == null)playerClient.missiles = new ArrayList<Missile>();
		//���ر�ը
		if(playerClient.explodes == null)playerClient.explodes = new ArrayList<Explode>();
	}
	/**
	 * �رյ�ͼ����
	 */
	public void MapOff(){
		//������� 
		if(playerClient.background != null){
			playerClient.background = null;
		}
		//���ǽ
		if(playerClient.walls != null){
			for(int i = 0; i < playerClient.walls.size(); i++){
				playerClient.walls.remove(i);
			}
			playerClient.walls.clear();
			playerClient.walls = null;
		}	
		//�����
		if(playerClient.doors != null){
			for(int i = 0; i < playerClient.doors.size(); i++){
				playerClient.doors.remove(i);
			}
			playerClient.doors.clear();
			playerClient.doors = null;
		}	
		//�����Ʒ
		if(playerClient.Items != null){
			playerClient.CreateItemPD = false;
			for(int i = 0; i < playerClient.Items.size(); i++){
				playerClient.Items.get(i).setLive(false);
				playerClient.Items.remove(i);
				i--;
			}
			playerClient.Items.clear();
			playerClient.Items = null;
		}
		//�������̹��
		if(playerClient.enemyPlayers != null){
			playerClient.CreateEnemyPlayersPD = false;
			for(int i = 0; i < playerClient.enemyPlayers.size(); i++){
				playerClient.enemyPlayers.get(i).setPlayerLive(false);
				playerClient.enemyPlayers.remove(i);
				i--;
			}
			playerClient.enemyPlayers.clear();
			playerClient.enemyPlayers = null;
		}
		//����ӵ�
		if(playerClient.missiles != null){
			for(int i = 0; i < playerClient.missiles.size(); i++){
				playerClient.missiles.get(i).setLive(false);
				playerClient.missiles.remove(i);
				i--;
			}
			playerClient.missiles.clear();
			playerClient.missiles = null;
			playerClient.missiles = new ArrayList<Missile>();		
		}
		//�����ը
		if(playerClient.explodes != null){
			for(int i = 0; i < playerClient.explodes.size(); i++){
				playerClient.explodes.get(i).setLive(false);
				playerClient.explodes.remove(i);
				i--;
			}
			playerClient.explodes.clear();
			playerClient.explodes = null;
			playerClient.explodes = new ArrayList<Explode>();
		}
	}

}
