package indi.my.verification;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.junit.Test;


public class ValidatingCode {
	private int w = 70;
	private int h = 35;
	private static Random random = new Random();
	private static String codeString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] fontNames = { "����", "���Ŀ���", "����", "΢���ź�", "����_GB2312" };
	private Color bgColor = new Color(255, 255, 255);
	private String testString;

	// �����ɫ
	public Color randomColor() {
		int red = random.nextInt(150);
		int green = random.nextInt(150);
		int blue = random.nextInt(150);
		return new Color(red, green, blue);
	}
	//�������
	public Font randomFont(){
		int index = random.nextInt(fontNames.length);
		String name = fontNames[index];
		//style Ϊ��� �мӴ�  б���
		int style =random.nextInt(4);
		int size = random.nextInt(4)+25;
		return new Font(name,style,size);
	}
	//��֤ʱ�ĸ���б��
	public void drawLine(BufferedImage image){

		//����б��Ϊ3��
		int num=3;
		Graphics2D graphics2d = (Graphics2D)image.getGraphics();
		for (int i = 0; i <num; i++) {
			//���û滭��ϸ
			graphics2d.setStroke(new BasicStroke(1.5F));
			graphics2d.setColor(Color.blue);
			
			int x1 =random.nextInt(w);
			int x2 =random.nextInt(w);
			int y1 =random.nextInt(h);
			int y2 =random.nextInt(h);
			
			graphics2d.drawLine(x1, y1, x2, y2);
		}
	}
	
	//�����ȡcodeString�����һ������ֵ
	public static char randomChar(){
		int index = random.nextInt(codeString.length()-1);
		return codeString.charAt(index);
	}
	//����ͼƬ����
	public BufferedImage createImage(){
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
		graphics2d.setColor(this.bgColor);
		graphics2d.fillRect(0, 0, w, h);
		return bufferedImage;
	}
	//����4����ĸ
	public BufferedImage getImage(){
		BufferedImage bufferedImage = createImage();
		Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
		StringBuilder stringBuilder = new StringBuilder();
		//���ĸ��ַ�
		for (int i = 0; i < 4; i++) {
			String string =randomChar()+"";
			stringBuilder.append(string);
			graphics2d.setFont(randomFont());
			graphics2d.setColor(randomColor());
			//ÿ����֮��ļ��
			float x = i*1.0F * w / 4;
			graphics2d.drawString(string,x, h-8);
		}
		this.testString = stringBuilder.toString();
		drawLine(bufferedImage);
		return bufferedImage;
		
	}
	//��ȡ4���ַ�
	public String getTestString(){
		return testString;
	}
	//���ͼƬ
	public static void output(BufferedImage bufferedImage,OutputStream out) throws IOException {
		ImageIO.write(bufferedImage, "JPEG", out);
	}
	@Test
	public void test() throws ClassNotFoundException, FileNotFoundException, IOException{
			ValidatingCode validatingCode = new ValidatingCode();
			BufferedImage bufferedImage = validatingCode.getImage();
			System.out.println(validatingCode.getTestString());
			validatingCode.output(bufferedImage, new FileOutputStream("F:/b.jpg"));
			
	}
}
