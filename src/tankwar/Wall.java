package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
/**
 * ǽ��
 *
 */
public class Wall {
	PlayerClient tankClient;
	private int x,y,w,h;
	private Image wallPicture;
	
	private static ImageIcon wallImageIcon; 
	static{
		/**
		 * ��ǽ��
		 */
		wallImageIcon = new ImageIcon(Wall.class.getClassLoader().getResource("images/ǽ.png"));
	}
	/**
	 * ���췽��
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param tankClient
	 */
	public Wall(int x, int y, int w, int h,PlayerClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
		HuaWall();
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
		g.drawImage(wallPicture, x, y, null);
	}
	/**
	 * ��ȡǽ�ľ���
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	/**
	 * ��ǽ��
	 */
	public void HuaWall(){			
		wallPicture = wallImageIcon.getImage();
		wallPicture = wallPicture.getScaledInstance(w, h, Image.SCALE_DEFAULT);
	}
}
