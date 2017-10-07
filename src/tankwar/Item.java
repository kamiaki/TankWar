package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

/**
 * ��Ʒ��
 */
public class Item {
	private TankClient tankClient;				//��ܼ���
	private int x, y, w, h;						//x y���� ������Ʒ
	private ItemsType itemsType; 				//��Ʒ����
	private int[][] GuiJi = new int[3][2];		//����ζ�λ��
	private boolean live;						//��Ʒ����
	int step = 0;								//��ƷС��Χ�ƶ�������һ��
	
	/**
	 * ��ȡ��Ʒ����
	 * @return
	 */
	public ItemsType getItemsType() {
		return itemsType;
	}
	/**
	 * ������Ʒ����
	 * @param itemsType
	 */
	public void setItemsType(ItemsType itemsType) {
		this.itemsType = itemsType;
	}
	/**
	 * ��ȡ��Ʒ����
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * ������Ʒ����
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param Item_type
	 * @param tankClient
	 */
	public Item(int x, int y, int w, int h,ItemsType Item_type,TankClient tankClient) {
		this.live = true;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.itemsType = Item_type;
		this.tankClient = tankClient;		
		GuiJi[0][0] = x;
		GuiJi[0][1] = y;
		GuiJi[1][0] = x + 2;
		GuiJi[1][1] = y;
		GuiJi[2][0] = x + 1;
		GuiJi[2][1] = y + 2;		
		ItemQD();
	}	
	/**
	 * ����Ʒ
	 * @param g
	 */
	public void draw(Graphics g) {
		if(live) {
			ItemPicture(g);
		}
	}
	/**
	 * ����ƷͼƬ
	 * @param g
	 */
	private void ItemPicture(Graphics g) {
		Color c = g.getColor();
		switch (itemsType) {
		case Blood:
			g.setColor(Color.RED);
			break;
		case WeaponBaFang:
			g.setColor(Color.BLUE);
			break;		
		case WeaponZhuiZong:
			g.setColor(Color.MAGENTA);
			break;
		default:
			g.setColor(Color.BLACK);
			break;
		}
		g.fillOval(x, y, w, h);
		g.setColor(c);
	}
	/**
	 * ��ײ������
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
	/**
	 * ������Ʒ���ݸ��³���
	 */
	private void ItemQD(){
		ItemXianCheng();
	}
	/**
	 * ��Ʒ�߳�
	 */
	private void ItemXianCheng() {
		new Thread(new Runnable() {
			public void run() {
				while(live) {			
					move();
					try {Thread.sleep(10);} catch (Exception e) {}	
				}			
			}
		}).start();
	}
	//��Ʒ����ζ�
	private void move() {
		step++;
		if(step == GuiJi.length) {
			step = 0;
		}
		x = GuiJi[step][0];
		y = GuiJi[step][1];
	}
	/**
	 * ��Ʒײǽ
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Item.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * ��Ʒײ����ǽ
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
}
