package com.drawPixel0819;

import java.awt.*;

import javax.swing.*;

/**
 * @author chen
 * ������
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
			if (effectName.equals("��ͼƬ")) {
				mouse.drawImg(mouse.getfPath(), g);
			}

			if (effectName.equals("������")) {
				mouse.drawPixel(mouse.getfPath(),g);
			}

			if (effectName.equals("�Ҷ�")) {
				mouse.drawGrayScale(mouse.getfPath(),g);
			}

			if (effectName.equals("ȥ����")) {
				mouse.drawRemoveBg(mouse.getfPath(),g);
			}

			if (effectName.equals("���ƻ�")) {
				mouse.drawGrid(mouse.getfPath(),g);
			}

			if (effectName.equals("�ڰ�")) {
				mouse.drawBw(mouse.getfPath(),g);
			}

			if (effectName.equals("�ͻ�Ч��")) {
				mouse.drawPainting(mouse.getfPath(),g);
			}
			if (effectName.equals("���")) {
				mouse.drawJJ(mouse.getfPath());
			}
		}
		if(path!=null){
			System.out.println("��ѡ��ͼ��");
		}

		if (effectName.equals("�ϲ�����ͼƬ")&&mouse.getfPath()!=null && mouse.getfPath2()!=null) {
			//mouse.drawtwo("C:\\Users\\Suver\\Documents\\1.jpg","C:\\Users\\Suver\\Documents\\3.jpg");+
				mouse.drawDouble(mouse.getfPath(), mouse.getfPath2(), g);
		}

	}


	/**
	 * ��ʾ����
	 */
	public void initUI(){
		JFrame jf = new JFrame();
		jf.setTitle("ͼ����");
		jf.setSize(1600, 1600);
		jf.setLocationRelativeTo(null);  //���þ���
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //�˳�����
		//��ǰ���ϱ߽粼����
		BorderLayout fl=new BorderLayout();
		this.setLayout(fl);
		jf.add(this,BorderLayout.CENTER);

		//�˵���
		JMenuBar jmb = new JMenuBar();
		//�˵�
		JMenu jm= new JMenu("�ļ�");
		JMenu jm2= new JMenu("ͼ��Ч��");
		//�˵�����Ŀ
		String name[] = {"������", "�Ҷ�", "ȥ����", "���ƻ�", "�ڰ�", "�ͻ�Ч��", "�ϲ�����ͼƬ","���"};
		for (int i=0; i<name.length; i++) {
			JMenuItem jmi= new JMenuItem(name[i]);
			jm2.add(jmi);
			jmi.addActionListener(mouse);
		}
		String name2[] = {"��ͼƬ", "����", "�˳�"};
		for (int i=0; i<name2.length; i++) {
			JMenuItem jmi2= new JMenuItem(name2[i]);
			jm.add(jmi2);
			//��Ӷ���������
			jmi2.addActionListener(mouse);
		}

		jmb.add(jm);
		jmb.add(jm2);
		jf.add(jmb,BorderLayout.NORTH);

		//���ÿɼ�
		jf.setVisible(true);

		//���ʣ�ͼ�λ����Ǹ�����ϣ����ʾʹӸ�����ϻ�ȡ
		//�Ӵ����ϻ�ȡ���ʶ���һ��Ҫ�ڴ�����ʾ�ɼ�֮��
		Graphics g = this.getGraphics();

		//���ó�ʼ��Ч�� ��ԭͼ
		mouse.setEffectName("��ʼ��");
		//���뻭��
		mouse.setGr(g);
		mouse.setdrawFrame(this);
	}
	
}
