package com.frm.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {
	/**
	 * surround with 'str'
	 * 
	 * @param str
	 * @return
	 */
	public static String roundWithQuotes(String str) {
		return new String("'" + str + "'");
	}

	/**
	 * @param str
	 * @param token
	 * @return 符号出现次数
	 */
	public static int calcTokenCount(String str, String token) {
//		int num = 0; str.apennd1char(pre&&post);
//		String arr[] = str.split("\\"+token);
//		num = arr.length - 1;
//		return num;
		int num = 0;
		int i = str.indexOf(token);
		while (i != -1) {
			num++;
			i = str.indexOf(token, i + 1);
		}
		return num;
	}
	 
	/**
	 * @param t specified token 替换参数
	 * @param src
	 * @param objs
	 * @return
	 * @description 这个可以指定替换参数
	 */
	public static String replaceSpecifiedToken(String t, String src, Object ...objs) {
		token = t;
		String str = replaceToken(src, objs);
		token = "@";
		return str;
	}
	
	/**
	 * @param src 字符串
	 * @param objs 参考书列表
	 * @return
	 * @description 这个默认替换src中的 "@"
	 */
	public static String replaceToken(String src, Object ...objs) {
//		List<Integer> l = findPitIdx(src);
		StringBuffer sb = new StringBuffer(src);
//		for(Integer i: l) {
//			l.get
//			if() {				
//				sb.replace(i, i, );
//			}
//		}
		if(objs != null) {			
			for(int i=0; i<objs.length; i++) {
				Object o = objs[i];
//				int idx = l.get(i);
				int idx = findPitIdx(sb.toString());
				sb.replace(idx, idx+1, o.toString());
			}
		}
		return sb.toString();
	}
	public static String token = "@";
	private static int findPitIdx(String str) {
		return str.indexOf(token);
	}
	public static List<Integer> findPitIdxList(String str, String token) {
		List<Integer> l = new ArrayList<Integer>();
		int i = str.indexOf(token);
		while (i != -1) {
			l.add(i);
			i = str.indexOf(token, i + 1);
		}
		return l;
	}
	
	
	
}
