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
 * ʵ��(�̳�)�ӿڣ�implements ʵ�ֽӿ�һ��Ҫ��д�ӿ������еĳ��󷽷�
 * �¼�������
 * @author chen
 *
 */
public class DrawMouse implements  ActionListener {
	//����Graphics����gr���������洫�ݹ�������
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

	//����set��������ʼ��gr
	public void setGr(Graphics g){
		gr = g;
	}


	//�����¼�
	public void actionPerformed(ActionEvent e) {
		name = e.getActionCommand();
		//��ͼƬ
		if (name.equals("��ͼƬ")){
			openFile();
		}

		if (name.equals("�ϲ�����ͼƬ")){
			JFileChooser jfc = new JFileChooser();
			jfc.setAcceptAllFileFilterUsed(false);//ȡ����ʾ�����ļ�����ѡ��
			//��׺��������
			FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.jpg)", "jpg");
			jfc.setFileFilter(filter);
			jfc.setDialogTitle("���ļ�");
			//ѡ�����ļ�
			jfc.setMultiSelectionEnabled(true);
			jfc.showOpenDialog(null);
			File[] file = jfc.getSelectedFiles();
			if(file.length==2){
				fPath=file[0].getPath();
				fPath2=file[1].getPath();
			}
			else {
				System.out.println("��ѡ��2���ļ�");
			}
		}

		if (fPath!=null) {
			//ͼ��Ч��
			if (name.equals("������")){drawPixel(fPath, gr);}

			if (name.equals("�Ҷ�")){ drawGrayScale(fPath, gr ); }

			if (name.equals("ȥ����")){drawRemoveBg(fPath, gr); }

			if (name.equals("���ƻ�")){drawGrid(fPath, gr); }

			if (name.equals("�ڰ�")){drawBw(fPath, gr); }

			if (name.equals("�ͻ�Ч��")){ drawPainting(fPath, gr); }

			if (name.equals("���")){ drawJJ(fPath); }

			//�����ļ�
			if (name.equals("����")){
				saveFile();
			}

		}
		if(fPath!=null){
			System.out.println("��ѡ��ͼ��");
		}


		drawFrame.repaint();
	}



	/**
	 * ���������ص�Ķ�ά����
	 * @param path ͼƬ·��
	 */

	//ԭͼ
	public void drawImg(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		for(int i=0;i<img.length;i++){
			for(int j=0;j<img[i].length;j++){
				int pixel = img[i][j];
				//ԭͼ��ɫ����
				Color c = new Color(pixel);
				//ʹ��rectangle���ÿһ����
					gr.setColor(c);
					gr.fillRect(i, j, 1,1);
			}
		}
	}


	//������ʵ��
	public void drawPixel(String path, Graphics gr){
		int[][] pixelArray  = getImgPixel(path);
		buffG = new BufferedImage(pixelArray.length, pixelArray[0].length, 1);
		Graphics buff=buffG.getGraphics();
		//������������
		for(int i=0;i<pixelArray.length;i+=10){
			for(int j=0;j<pixelArray[0].length;j+=10){
				int pixel = pixelArray[i][j];
				Color color = new Color(pixel);
				buff.setColor(color);
				//�Ŵ����ص��С ��֤���ٵı�����Ŵ�����ͬ������������
				buff.fillRect(i, j, 10, 10);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//�Ҷ�ͼʵ��
	public void drawGrayScale(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		for(int i=0; i<img.length;i++){
			for(int j=0;j<img[i].length;j++) {
				int pixel =img[i][j];
				Color c = new Color (pixel);
				// ȡrgpƽ��ֵ
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

	//ȥ����ʵ��
	public void drawRemoveBg(String path, Graphics gr){
		int[][] img  = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();
		for(int i=0;i<img.length;i++){
			for(int j=0;j<img[i].length;j++){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				//ȥ������ֻ������ĳһֵ����ɫȥ����Ч��
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
	//����ʵ��
	public void drawGrid(String path, Graphics gr){
		int [][] img = getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		//������������
		for(int i=0;i<img.length;i+=10){
			for(int j=0;j<img[i].length;j+=10){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				int r=c.getRed(); int g=c.getGreen(); int b=c.getBlue();
				Color nc = new Color(r,g,b);
				buff.setColor(nc);
				//�Ŵ���������
				buff.fillOval(i, j, 8,8	);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//�ڰװ�ʵ��
	public void drawBw(String path, Graphics gr){
		int [][] img = getImgPixel(path);
		// �˴�buffGΪ�洢ͼƬ��׼��
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();

		for(int i=0; i<img.length; i++){
			for(int j=0; j<img[i].length; j++){
				int pixel =img[i][j];
				Color c = new Color(pixel);
				int r=c.getRed(); int g=c.getGreen(); int b=c.getBlue();
				//��С��ĳһֵ����ɫ��Ϊ�������ֺڰ�
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

	//�ͻ�Ч��
	public void drawPainting(String path, Graphics gr){
		int[][] img =getImgPixel(path);
		buffG = new BufferedImage(img.length, img[0].length, 1);
		Graphics buff=buffG.getGraphics();
		for(int i=0; i<img.length; i++){
			for(int j=0; j<img[i].length; j++){
				int pixel = img[i][j];
				Color c = new Color(pixel);
				buff.setColor(c);
				///��������С�mɫ��
				Random random =new Random();
				int r = random.nextInt(20)+5;
				buff.fillOval(i,j,r,r);
			}
		}
		gr.drawImage(buffG,0,0,null);
	}

	//˫ͼ
	public void drawDouble(String path1, String path2, Graphics gr ){

		//ѡȡa,b������Ƭ, ����ɫ��ͬȨֵ
		int[][] img1=getImgPixel(path1);
		int[][] img2=getImgPixel(path2);
		// ȡ���߰m��Сֵ��������Խ��
		int w =Math.min(img1.length,img2.length);
		int h =Math.min(img1[0].length, img2.length);
		buffG = new BufferedImage(w,h, 1);
		Graphics buff=buffG.getGraphics();
		//�����ͼƬ��ǰѭ��
		for(int i=0; i<w; i+=1){
			for(int j=0;j<h;j+=1){
				Color c1 = new Color(img1[i][j]);
				Color c2 = new Color(img2[i][j]);
				//͸������
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


	//���Ч��
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
				//�Ŵ����ص��С ��֤���ٵı�����Ŵ�����ͬ������������
				buff.fillRect(i, j, 10, 10);
			}
		}
		//�ڽ����ϻ���ia����ͼ�񣬼���������
		gr.drawImage(buffG,0,0,null);
	}

	//����˰m��ά���飺��Ե��
	float[][] kArray= {{-1,-1,-1,-1,-1},
					{-1,-1,-1,-1,-1},{-1,-1,25,-1,-1},
					{-1,-1,-1,-1,-1},{-1,-1,-1,-1,-1}};
	/**
	 * �������
	 * @param src :ͼƬ����
	 * @param filter :���������
	 * @return �����m����
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
	 * ����ͼƬ·������ȡ�ø�ͼƬ��ÿ�����ص㲢���浽��Ӧ�Ķ�ά������
	 * @param path  ͼƬ·��
	 * @return �������ص�Ķ�ά����
	 */
	public int[][] getImgPixel(String path){
		File file = new File(path);
		BufferedImage buffImg = null;  //����ͼƬ
		try {
			buffImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = buffImg.getWidth();
		int h = buffImg.getHeight();
		//�����ά���飬�������ص�
		int[][] pixelArray = new int[w][h];
		//��ȡÿ��λ�õ����ص�
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				int pixel = buffImg.getRGB(i, j);  //��ȡÿ��λ������ֵ
				pixelArray[i][j] = pixel;
			}
		}
		return pixelArray;
	}

	public void saveFile(){
		JFileChooser jfc = new JFileChooser();
		//��׺��������
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"(*.jpg)", "jpg");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("�����ļ�");
		jfc.showSaveDialog(null);
		File file = jfc.getSelectedFile();
		try {
			ImageIO.write(buffG,"jpg", file);
			System.out.print("����ɹ�" );
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void openFile(){
		JFileChooser jfc = new JFileChooser();
		jfc.setAcceptAllFileFilterUsed(false);//ȡ����ʾ�����ļ�����ѡ��
		//��׺��������
		FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.jpg)", "jpg");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("���ļ�");
		jfc.showOpenDialog(null);
		File file = jfc.getSelectedFile();
		if(file!=null){
			fPath=file.getPath();
			System.out.print(fPath);
			drawImg(fPath, gr);
		}else {
			System.out.println("δѡ���ļ�");
		}
	}


}
