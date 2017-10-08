package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * ǽ��
 *
 */
public class Wall {
	TankClient tankClient;
	private int x,y,w,h;
	
	/**
	 * ���췽��
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param tankClient
	 */
	public Wall(int x, int y, int w, int h, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
	}
	/**
	 * ��ǽ
	 * @param g
	 */
	public void draw(Graphics g) {
		WallPicture(g);
	}
	/**
	 * ��ǽͼƬ
	 * @param g
	 */
	public void WallPicture(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	/**
	 * ��ȡǽ�ľ���
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
