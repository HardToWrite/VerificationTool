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
	private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
	private Color bgColor = new Color(255, 255, 255);
	private String testString;

	// 随机颜色
	public Color randomColor() {
		int red = random.nextInt(150);
		int green = random.nextInt(150);
		int blue = random.nextInt(150);
		return new Color(red, green, blue);
	}
	//随机字体
	public Font randomFont(){
		int index = random.nextInt(fontNames.length);
		String name = fontNames[index];
		//style 为风格 有加粗  斜体等
		int style =random.nextInt(4);
		int size = random.nextInt(4)+25;
		return new Font(name,style,size);
	}
	//验证时的干扰斜线
	public void drawLine(BufferedImage image){

		//干扰斜线为3个
		int num=3;
		Graphics2D graphics2d = (Graphics2D)image.getGraphics();
		for (int i = 0; i <num; i++) {
			//设置绘画粗细
			graphics2d.setStroke(new BasicStroke(1.5F));
			graphics2d.setColor(Color.blue);
			
			int x1 =random.nextInt(w);
			int x2 =random.nextInt(w);
			int y1 =random.nextInt(h);
			int y2 =random.nextInt(h);
			
			graphics2d.drawLine(x1, y1, x2, y2);
		}
	}
	
	//随机截取codeString里面的一个索引值
	public static char randomChar(){
		int index = random.nextInt(codeString.length()-1);
		return codeString.charAt(index);
	}
	//创建图片方法
	public BufferedImage createImage(){
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
		graphics2d.setColor(this.bgColor);
		graphics2d.fillRect(0, 0, w, h);
		return bufferedImage;
	}
	//绘制4个字母
	public BufferedImage getImage(){
		BufferedImage bufferedImage = createImage();
		Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
		StringBuilder stringBuilder = new StringBuilder();
		//画四个字符
		for (int i = 0; i < 4; i++) {
			String string =randomChar()+"";
			stringBuilder.append(string);
			graphics2d.setFont(randomFont());
			graphics2d.setColor(randomColor());
			//每个字之间的间隔
			float x = i*1.0F * w / 4;
			graphics2d.drawString(string,x, h-8);
		}
		this.testString = stringBuilder.toString();
		drawLine(bufferedImage);
		return bufferedImage;
		
	}
	//获取4个字符
	public String getTestString(){
		return testString;
	}
	//输出图片
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
