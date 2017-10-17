package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import player.Explode;
import player.Item;
import player.Missile;
import player.Player;
import playerClient.InitValue;
import playerClient.PlayerClient;
import playerClient.PlayerClient.CreateEnemyPlayer;
import playerClient.PlayerClient.CreateItem;

/**
 * ����
 * @author Administrator
 *
 */
public class Door implements InitValue{
	private static int Width = 50, Height = 50;
	private int X, Y;
	private int DoorType = Door_city;
	private PlayerClient playerClient;
	private Map map;

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
		map = new Map(this.playerClient);
	}
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
			map.MapOff();
			map.CreateWoods1();
			break;
		case Door_city:
			map.MapOff();
			map.CreateCity();
			break;
		default:
			break;
		}
	}	
}
