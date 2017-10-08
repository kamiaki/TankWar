package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ������
 *
 */
public class Background implements InitValue{
	private TankClient tankClient;										//��ܼ�ָ��
	private int X,Y;													//������λ�úͳ���
	private String Path;												//��ַ
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//���߰�
	private Image BackGround;											//����ͼƬ
	
	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, String path, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.Path = path;
		tankClient = tc;
		HuaWall();
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
		g.drawImage(BackGround, X, Y, null);
	}
	/**
	 * ������
	 */
	public void HuaWall(){			
		BackGround = toolkit.getImage(Explode.class.getClassLoader().getResource(Path));		//����ͼƬ
		BackGround = BackGround.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}
}
