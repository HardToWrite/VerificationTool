package indi.my.verification;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.junit.Test;

public class ValidatingTest {
	private String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] fontStyle ={"宋体","华文楷体","黑体","微软雅黑","楷体_GB2312"} ;
	private Color color = new Color(255,255,255);
	private String textString;
	private int w = 70;
	private int h = 35;
	private Random random = new Random();
	
	//随机颜色
	public Color randomColor(){
			int red = random.nextInt(150);
			int green = random.nextInt(150);
			int blue = random.nextInt(150);
			return new Color(red,green,blue);
	} 
	//随机字体样式
	public Font randomFont(){
		int index = random.nextInt(fontStyle.length);
		String name = fontStyle[index];
		int style = random.nextInt(4);
		int size = random.nextInt(4)+25;
		return new Font(name,style,size);
	}
	//随机截取code里面的一个字符
	public char randomchar(){
		int index = random.nextInt(code.length()-1);
		return code.charAt(index);
	} 
	//随机生成干扰线
	public void drawLine(BufferedImage image){
		int num = 3;
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setStroke(new BasicStroke(1.5F));
		g.setColor(Color.green);
		for (int i = 0; i < num ; i++) {
			int x1 = random.nextInt(w);
			int x2 = random.nextInt(w);
			int y1 = random.nextInt(h);
			int y2 = random.nextInt(h);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	//创建图片大小 以及背景
	public BufferedImage createImage(){
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setColor(this.color);
		g.fillRect(0, 0, w, h);
		return image;
	}
	//绘制字母在图片上以及绘制干扰线

	public BufferedImage createCode(){
		BufferedImage image = createImage();
		Graphics2D g = (Graphics2D)image.getGraphics();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String string = randomchar()+"";
			sb.append(randomchar());
			g.setColor(randomColor());
			g.setFont(randomFont());
			float f = i*1.0F*w/4;
			g.drawString(string,f,h-8);
		}
		drawLine(image);
		this.textString = sb.toString();
		return image;
	}
	//获取字符
	public String getTestString(){
		return this.textString;
	}
	//打印最终图片  输出
	public static void output(BufferedImage image,FileOutputStream file){
		try {
			ImageIO.write(image, "JPEG", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test() throws FileNotFoundException{
		ValidatingTest validatingCode = new ValidatingTest();
		BufferedImage bufferedImage = validatingCode.createCode();
		System.out.println(validatingCode.getTestString());
		validatingCode.output(bufferedImage, new FileOutputStream("F:/b.jpg"));
	}
}
