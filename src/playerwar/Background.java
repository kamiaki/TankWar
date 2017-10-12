package playerwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ������
 *
 */
public class Background implements InitValue{
	private PlayerClient tankClient;										//��ܼ�ָ��
	private int X,Y;													//������λ�úͳ���
	//��ͼ
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//���߰�
	private static Image BackGround;									//����ͼƬ
	static{
		/**
		 * ������
		 */	
		BackGround = toolkit.getImage(Background.class.getClassLoader().getResource("images/����2.png"));		//����ͼƬ
		BackGround = BackGround.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}

	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, PlayerClient tc) {
		this.X = x;
		this.Y = y;
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
		g.drawImage(BackGround, X, Y, null);
	}
}
