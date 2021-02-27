package com.drawPixel0819;

import java.awt.*;

import javax.swing.*;

/**
 * @author chen
 * 启动类
 */
public class DrawFrame extends JPanel{

	private DrawMouse mouse=new DrawMouse();

	public static void main(String[] args){
		DrawFrame frame = new DrawFrame();
		frame.initUI();
	}


	public void paint(Graphics g){
		super.paint(g);
		String effectName=mouse.getEffectName();
		String path=mouse.getfPath();
		//mouse.drawtwo("C:\\Users\\Suver\\Documents\\1.jpg","C:\\Users\\Suver\\Documents\\3.jpg");
		if (path!=null) {
			if (effectName.equals("打开图片")) {
				mouse.drawImg(mouse.getfPath(), g);
			}

			if (effectName.equals("马赛克")) {
				mouse.drawPixel(mouse.getfPath(),g);
			}

			if (effectName.equals("灰度")) {
				mouse.drawGrayScale(mouse.getfPath(),g);
			}

			if (effectName.equals("去背景")) {
				mouse.drawRemoveBg(mouse.getfPath(),g);
			}

			if (effectName.equals("珠纹化")) {
				mouse.drawGrid(mouse.getfPath(),g);
			}

			if (effectName.equals("黑白")) {
				mouse.drawBw(mouse.getfPath(),g);
			}

			if (effectName.equals("油画效果")) {
				mouse.drawPainting(mouse.getfPath(),g);
			}
			if (effectName.equals("卷积")) {
				mouse.drawJJ(mouse.getfPath());
			}
		}
		if(path!=null){
			System.out.println("请选择图像");
		}

		if (effectName.equals("合并两张图片")&&mouse.getfPath()!=null && mouse.getfPath2()!=null) {
			//mouse.drawtwo("C:\\Users\\Suver\\Documents\\1.jpg","C:\\Users\\Suver\\Documents\\3.jpg");+
				mouse.drawDouble(mouse.getfPath(), mouse.getfPath2(), g);
		}

	}


	/**
	 * 显示界面
	 */
	public void initUI(){
		JFrame jf = new JFrame();
		jf.setTitle("图像处理");
		jf.setSize(1600, 1600);
		jf.setLocationRelativeTo(null);  //设置居中
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //退出进程
		//提前加上边界布局器
		BorderLayout fl=new BorderLayout();
		this.setLayout(fl);
		jf.add(this,BorderLayout.CENTER);

		//菜单条
		JMenuBar jmb = new JMenuBar();
		//菜单
		JMenu jm= new JMenu("文件");
		JMenu jm2= new JMenu("图像效果");
		//菜单子项目
		String name[] = {"马赛克", "灰度", "去背景", "珠纹化", "黑白", "油画效果", "合并两张图片","卷积"};
		for (int i=0; i<name.length; i++) {
			JMenuItem jmi= new JMenuItem(name[i]);
			jm2.add(jmi);
			jmi.addActionListener(mouse);
		}
		String name2[] = {"打开图片", "保存", "退出"};
		for (int i=0; i<name2.length; i++) {
			JMenuItem jmi2= new JMenuItem(name2[i]);
			jm.add(jmi2);
			//添加动作监听器
			jmi2.addActionListener(mouse);
		}

		jmb.add(jm);
		jmb.add(jm2);
		jf.add(jmb,BorderLayout.NORTH);

		//设置可见
		jf.setVisible(true);

		//画笔：图形画在那个组件上，画笔就从该组件上获取
		//从窗体上获取画笔对象，一定要在窗体显示可见之后
		Graphics g = this.getGraphics();

		//设置初始化效果 即原图
		mouse.setEffectName("初始化");
		//传入画笔
		mouse.setGr(g);
		mouse.setdrawFrame(this);
	}
	
}
