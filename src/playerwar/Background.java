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
	private int BeiJingID;												//����ID
	//��ͼ
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//���߰�
	private static Image BackGround1;									//����ͼƬ
	private static Image BackGround2;									//����ͼƬ
	static{
		/**
		 * ������
		 */	
		BackGround1 = toolkit.getImage(Background.class.getClassLoader().getResource("images/����2.png"));		//����ͼƬ
		BackGround1 = BackGround1.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
		BackGround2 = toolkit.getImage(Background.class.getClassLoader().getResource("images/����1.jpg"));		//����ͼƬ
		BackGround2 = BackGround2.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}

	/**
	 * ���캯��
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y,int beijingid, PlayerClient tc) {
		this.X = x;
		this.Y = y;
		this.BeiJingID = beijingid;
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
		switch (BeiJingID) {
		case 1:
			g.drawImage(BackGround1, X, Y, null);
			break;
		case 2:
			g.drawImage(BackGround2, X, Y, null);
			break;
		default:
			g.drawImage(BackGround1, X, Y, null);
			break;
		}
		
	}
}
