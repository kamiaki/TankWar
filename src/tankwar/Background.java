package tankwar;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ������
 *
 */
public class Background {
	private TankClient tankClient;			//��ܼ�ָ��
	private int X,Y,width,height;			//������λ�úͳ���
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, int width, int height, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.width = width;
		this.height = height;
		tankClient = tc;
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
		g.setColor(Color.BLACK);
		g.fillRect(X, Y, width, height);
		g.setColor(c);
	}
}
