package com.frm.utils;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JLabel;

/**
 * @author LinHuanZhi
 * @time 2021年10月5日
 * @email lhz034069@163.com
 * @description 
 */
/**
 * @author LinHuanZhi
 * @time 2021年12月6日
 * @email lhz034069@163.com
 * @description 
 */
public class BrAdapterUtils {
//	private static int WW_1a = 0;
//	private static int WW_A = 0;
//	private static int WW_commaE = 0;
//	private static int WW_commaC = 0;
	private static int WW_commaC = 12;
	private static final int C = 0;
	
	private static int WW_A = 8;
	private static final int A = 1;
	
	private static int WW_1a = 7;
	private static final int a1 = 2;
	
	private static int WW_commaE = 3;
	private static final int commaE = 3;
	
	public static void init() {
		
	}
	
	private static int getWW(String x) {
//		if(SingleWordWidth==0) {
			JLabel jLb = new JLabel(x);
			Font f= jLb.getFont();
			FontMetrics fm = jLb.getFontMetrics(f);
			String str = jLb.getText();
			int width = fm.stringWidth(str);
//			SingleWordWidth = jLb.getWidth();
//			SingleWordWidth = width;
//		}
//		return SingleWordWidth;
//		return 8;
			return width;
	}
	private static int getStrWid(JLabel jLb, String str) {
//		if(SingleWordWidth==0) {
		Font f= jLb.getFont();
		FontMetrics fm = jLb.getFontMetrics(f);
		int width = fm.stringWidth(str);
		return width;
	}
	
	private static void testAllWidth() {
		PrintUtils.pr(getWW("1"));//7
		PrintUtils.pr(getWW("$"));//7
		PrintUtils.pr(getWW("a"));//7
		PrintUtils.pr(getWW("A"));//8
		PrintUtils.pr(getWW(","));//3
		
		PrintUtils.pr(getWW("，"));//12
		PrintUtils.pr(getWW("字"));
//		return SingleWordWidth;
	}
	
	private static int getWidthByType(int t) {
		int singleW = 0;
		switch(t) {
		case C:
			singleW = WW_commaC;
			break;
		case A:
			singleW = WW_A;
			break;
		case a1:
			singleW = WW_1a;
			break;
		case commaE:
			singleW = WW_commaE;
			break;
		}
		return singleW;
	}
	
	/**
	 * @param x
	 * @return
	 * @description http://ascii.911cha.com/
	 */
	private static int getType(char x) {
		int ascii = Integer.valueOf(x);
		int t = 0; //中文
		if(ascii < 127) {
			if(ascii > 40 && ascii <91) {
				t = A; //A
			}else if((ascii > 96 && ascii < 123) || (ascii > 47 && ascii < 58)) {
				t = a1; //a1
			}else {
				t = commaE; //符号
			}
//			t = commaE;
		}
		return t;
	}
	
	
//	private void calc() {
//		JLabel lab = new JLabel();
//		lab.setText("ddddddddddddddddddddddddddddddddddddddddddddddddddd");
//		Font f= lab.getFont();
//		FontMetrics fm = lab.getFontMetrics(f);
//		String str = lab.getText();
//		int width = fm.stringWidth(str);
//		//row width is idx x?
//		lab.setSize(width,30);
//	}
	
	
	public static void formatBr(JLabel c, String str) {
		testAllWidth();
		StringBuffer sb = new StringBuffer(str);//建立一个字符缓存区
//		System.out.println("原字符缓存区中的内容为："+sb);//输出原字符缓存区中的内容
//		System.out.println("原字符缓存区中的长度为："+sb.length() );//长度
//		System.out.println("原字符缓存区中的容量为："+sb.capacity() );//容量
		int contWid = c.getWidth();
//		int wordWid = getWW();
//		int wordWid = getWWByType();
//		int strWid = getStrWid(c, str);
//		int rowContWordNum = contWid / wordWid;
//		int strLen = sb.length();
//		int brRowNum = strLen / rowContWordNum;
//		int brRowNum = strWid / contWid;
		int posArr[] = getPosArr(sb, contWid);
		for(int x: posArr) {
			if(x==0) continue;
			sb.insert(x, "<br/>");
		}
//		for(int i=1; i < brRowNum; i++) {			
//			sb.charAt(i);
//			sb.insert((i*rowContWordNum), "<br/>");//给指定下标位置前的值赋新值
//		}
		c.setText("<html>" + sb + "</html>");
	}
	
	static int[] getPosArr(StringBuffer sb, int maxWid) {
		int strLen = sb.length();
		int tempAdd = 0;
		int t[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int c = 0;
		for(int i=0; i < strLen; i++) {
			char x = sb.charAt(i);
			tempAdd += getWidthByType(getType(x));
			if(tempAdd >= maxWid) {
//				t[t.length] = i-1;
				t[c] = i-1;
				c++;
				tempAdd = 0;
			}
		}
		return t;
	}
	
}
