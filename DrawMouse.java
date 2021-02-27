package com.drawPixel0819;

import org.w3c.dom.css.RGBColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 实现(继承)接口：implements 实现接口一定要重写接口中所有的抽象方法
 * 事件处理类
 * @author chen
 *
 */
public class DrawMouse implements  ActionListener {
	//定义Graphics变量gr，用来保存传递过来画笔
	private Graphics gr;
	private ImageIcon img;
	private BufferedImage buffG;
	private String fPath,fPath2;
	private DrawFrame drawFrame;

	public void setdrawFrame(DrawFrame drawFrame){ this.drawFrame=drawFrame; }

	private String name;
	public void setEffectName(String name){ this.name=name; }
	public String getEffectName(){ return name; }


	public String getfPath(){ return fPath; }
	public String getfPath2(){ return fPath2; }

	//定义set方法，初始化gr
	public void setGr(Graphics g){
		gr = g;
	}


	//动作事件
	public void actionPerformed(ActionEvent e) {
		name = e.getActionCommand();
		//打开图片
		if (name.equals("打开图片")){
			openFile();
		}

		if (name.equals("合并两张图片")){
			JFileChooser jfc = new JFileChooser();
			jfc.setAcceptAllFileFilterUsed(false);//取消显示所有文件过滤选项
			//后缀名过滤器
			FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.jpg)", "jpg");
			jfc.setFileFilter(filter);
			jfc.setDialogTitle("打开文件");
			//选择多个文件
			jfc.setMultiSelectionEnabled(true);
			jfc.showOpenDialog(null);
			File[] file = jfc.getSelectedFiles();
			if(file.length==2){
				fPath=file[0].getPath();
				fPath2=file[1].getPath();
			}
			else {
				System.out.println("请选择2个文件");
			}
		}

		if (fPath!=null) {
			//图像效果
			if (name.equals("马赛克")){drawPixel(fPath, gr);}

			if (name.equals("灰度")){ drawGrayScale(fPath, gr ); }

			if (name.equals("去背景")){drawRemoveBg(fPath, gr); }

			if (name.equals("珠纹化")){drawGrid(fPath, gr); }

			if (name.equals("黑白")){drawBw(fPath, gr); }

			if (name.equals("油画效果")){ drawPainting(fPath, gr); }

			if (name.equals("卷积")){ drawJJ(fPath); }

			//保存文件
			if (name.equals("保存")){
				saveFile();
			}

		}
		if(fPath!=null){
			System.out.println("请选择图像");
		}


		drawFrame.repaint();
	}



	/**
	 * 处理保存像素点的二维数组
	 * @param path 图片路径
	 */

	//原图
	public void drawImg(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		for(int i=0;i<img.length;i++){
			for(int j=0;j<img[i].length;j++){
				int pixel = img[i][j];
				//原图颜色不变
				Color c = new Color(pixel);
				//使用rectangle填充每一个点
					gr.setColor(c);
					gr.fillRect(i, j, 1,1);
			}
		}
	}


	//马赛克实现
	public void drawPixel(String path, Graphics gr){
		int[][] pixelArray  = getImgPixel(path);
		buffG = new BufferedImage(pixelArray.length, pixelArray[0].length, 1);
		Graphics buff=buffG.getGraphics();
		//减少像素数量
		for(int i=0;i<pixelArray.length;i+=10){
			for(int j=0;j<pixelArray[0].length;j+=10){
				int pixel = pixelArray[i][j];
				Color color = new Color(pixel);
				buff.setColor(color);
				//放大像素点大小 保证减少的倍数与放大倍数相同，否则是网格化
				buff.fillRect(i, j, 10, 10);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//灰度图实现
	public void drawGrayScale(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		for(int i=0; i<img.length;i++){
			for(int j=0;j<img[i].length;j++) {
				int pixel =img[i][j];
				Color c = new Color (pixel);
				// 取rgp平均值
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int sum = (r+g+b)/3;
				Color newc= new Color(sum,sum,sum);
				buff.setColor(newc);
				buff.fillRect(i,j,1,1);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//去背景实现
	public void drawRemoveBg(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();
		for(int i=0;i<img.length;i++){
			for(int j=0;j<img[i].length;j++){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				//去背景，只画大于某一值的颜色去背景效果
				int r=c.getRed();int g=c.getGreen();int b=c.getBlue();
				if(g>150){
					Color nc=new Color(r,g,b);
					buff.setColor(nc);
					buff.fillRect(i, j, 1,1);
				}
			}
		}
		gr.drawImage(buffG,0,0,null);
	}
	//网格化实现
	public void drawGrid(String path, Graphics gr){
		int [][] img = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		//减少像素数量
		for(int i=0;i<img.length;i+=10){
			for(int j=0;j<img[i].length;j+=10){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				int r=c.getRed(); int g=c.getGreen(); int b=c.getBlue();
				Color nc = new Color(r,g,b);
				buff.setColor(nc);
				//放大像素数量
				buff.fillOval(i, j, 8,8	);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//黑白版实现
	public void drawBw(String path, Graphics gr){
		int [][] img = getImgPixel(path);
		// 此处buffG为存储图片做准备
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		for(int i=0; i<img.length; i++){
			for(int j=0; j<img[i].length; j++){
				int pixel =img[i][j];
				Color c = new Color(pixel);
				int r=c.getRed(); int g=c.getGreen(); int b=c.getBlue();
				//将小于某一值的颜色作为界限区分黑白
				if(b<100){
					buff.setColor(Color.BLACK);
				}else {
					buff.setColor(Color.white);
				}
				buff.fillRect(i,j,3,3);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//油画效果
	public void drawPainting(String path, Graphics gr){
		int[][] img =getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();
		for(int i=0; i<img.length; i++){
			for(int j=0; j<img[i].length; j++){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				buff.setColor(c);
				///填充随机大小癿色块
				Random random =new Random();
				int r = random.nextInt(20)+5;
				buff.fillOval(i,j,r,r);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//双图
	public void drawDouble(String path1, String path2, Graphics gr ){

		//选取a,b两张照片, 给颜色不同权值
		int[][] img1=getImgPixel(path1);
		int[][] img2=getImgPixel(path2);
		// 取其宽高癿最小值，防数组越界
		int w =Math.min(img1.length,img2.length);
		int h =Math.min(img1[0].length, img2.length);
		buffG = new BufferedImage(w,h, 1);
		Graphics buff=buffG.getGraphics();
		//更大的图片在前循环
		for(int i=0; i<w; i+=1){
			for(int j=0;j<h;j+=1){
				Color c1 = new Color(img1[i][j]);
				Color c2 = new Color(img2[i][j]);
				//透明处理
				int r=(int)(c1.getRed()*0.7+c2.getRed()*0.3);
				int g=(int)(c1.getGreen()*0.3+c2.getGreen()*0.7);
				int b=(int)(c1.getBlue()*0.3+c2.getBlue()*0.7);
				Color cn = new Color(r,g,b);
				buff.setColor(cn);
				buff.drawLine(i,j,i,j);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}


	//卷积效果
	public void drawJJ(String path){
		DrawMouse mouse= new DrawMouse();
		int[][] ia= mouse.getImgPixel(path);
		ia=valide(ia, kArray);
		buffG = new BufferedImage(ia.length, ia[0].length, 1);
		Graphics buff=buffG.getGraphics();
		for(int i=0;i<ia.length;i++){
			for(int j=0;j<ia[0].length;j++){
				int pixel = ia[i][j];
				Color color = new Color(pixel);
				buff.setColor(color);
				//放大像素点大小 保证减少的倍数与放大倍数相同，否则是网格化
				buff.fillRect(i, j, 10, 10);
			}
		}
		//在界面上画出ia数组图像，即卷积结果：
		gr.drawImage(buffG,0,0,null);
	}

	//卷积核癿二维数组：边缘化
	float[][] kArray= {{-1,-1,-1,-1,-1},
					{-1,-1,-1,-1,-1},{-1,-1,25,-1,-1},
					{-1,-1,-1,-1,-1},{-1,-1,-1,-1,-1}};
	/**
	 * 卷积计算
	 * @param src :图片数组
	 * @param filter :卷积核数组
	 * @return 计算后癿数组
	 */
	public static int[][] valide(int[][] src,float[][] filter){
		int[][]tem = new int[filter.length][filter[0].length];
		int valideWidth = src[0].length - filter[0].length+1;
		int valideheight = src.length - filter.length+1;
		int[][] valide = new int[valideheight][valideWidth];
		for(int i=0;i<valideheight;i+=1){
			for(int j=0;j<valideWidth;j+=1){
				for(int y=0;y<filter.length;y++){
					for(int z=0;z<filter[0].length;z++){
						tem[y][z] =(int)((src[i+y][j+z])*(filter[y][z]));
					}
				}
				int kk=0;
				for(int y=0;y<filter.length;y++){
					for(int z=0;z<filter[0].length;z++){
						kk += tem[y][z];
					}
				}
				if(kk<0)kk=0; if(kk>255)kk=255;
				valide[i][j]=(byte)kk;
			}
		}
		return valide;
	}

	/**
	 * 根据图片路径，获取该该图片的每个像素点并保存到对应的二维数组中
	 * @param path  图片路径
	 * @return 保存像素点的二维数组
	 */
	public int[][] getImgPixel(String path){
		File file = new File(path);
		BufferedImage buffImg = null;  //缓冲图片
		try {
			buffImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = buffImg.getWidth();
		int h = buffImg.getHeight();
		//定义二维数组，保存像素点
		int[][] pixelArray = new int[w][h];
		//读取每个位置的像素点
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				int pixel = buffImg.getRGB(i, j);  //获取每个位置像素值
				pixelArray[i][j] = pixel;
			}
		}
		return pixelArray;
	}

	public void saveFile(){
		JFileChooser jfc = new JFileChooser();
		//后缀名过滤器
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"(*.jpg)", "jpg");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("保存文件");
		jfc.showSaveDialog(null);
		File file = jfc.getSelectedFile();
		try {
			ImageIO.write(buffG,"jpg", file);
			System.out.print("保存成功" );
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void openFile(){
		JFileChooser jfc = new JFileChooser();
		jfc.setAcceptAllFileFilterUsed(false);//取消显示所有文件过滤选项
		//后缀名过滤器
		FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.jpg)", "jpg");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("打开文件");
		jfc.showOpenDialog(null);
		File file = jfc.getSelectedFile();
		if(file!=null){
			fPath=file.getPath();
			System.out.print(fPath);
			drawImg(fPath, gr);
		}else {
			System.out.println("未选择文件");
		}
	}


}
