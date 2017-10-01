package tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Background {
	private int X,Y,width,height;
	private TankClient TC;
	
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param tC
	 */
	public Background(int x, int y, int width, int height, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.width = width;
		this.height = height;
		TC = tc;
	}
	/**
	 * ������
	 * @param g
	 */
	public void draw(Graphics g){
		BackgroundPicture(g);
	}
	/**
	 * ������ͼ
	 * @param g
	 */
	private void BackgroundPicture(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect(X, Y, width, height);
		g.setColor(c);
	}
}
