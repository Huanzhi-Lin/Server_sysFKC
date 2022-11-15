package com.frm.utils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;

/**
 * @author LinHuanZhi
 * @time 2021年11月24日
 * @email lhz034069@163.com
 * @description 
 */
public class PrintUtils {
	/**
	 * global ctrl
	 */
	private final static boolean D = true;
	public PrintUtils() {
		//TODO transplants another PUtils
	}
	/**
	 * @param obj
	 * @description 正常输出 
	 */
	public static void pr(Object obj) {
		System.out.println(obj);
	}
	
	/**
	 * 
	 * @description 行
	 */
	public static void pDLine() {
		System.out.println("==========================");
	}
	public static void pLine() {
		System.out.println("--------------------------");
	}
	
	
	/**
	 * @param thix
	 * @param mthName
	 * @param postfix
	 * @description enter... 
	 */
	public static void pEnter(Object thix, String mthName/*, String... postfix*/) {
		if(D) {
//			boolean b = postfix==null;
//			pr(postfix + "没有参数也不会为空 " + b);
			System.out.println("[" + thix.getClass().getName() + "=>" + mthName + "()]： enter..." 
					/*+ moreBind( b ? "空-unreachable" : new Object[] {postfix})*/);
		}
	}
	
	/**
	 * @param obj
	 * @description 多个toString()输出
	 */
	public static /* <T extends Object> */ void pMore(/*T*/Object... obj) {
		if(D) {
			System.out.println(moreBind(new Object[] {obj}));			
		}
	}
	/**
	 * @param <T>
	 * @param obj
	 * @return
	 * @description 连接
	 */
	private static /* <T extends Object> */ String moreBind(/*T*/Object... obj) {
		String strBind = "";
		for(Object o: obj) {
//			System.out.println(o.getClass().getName());
			strBind += " | " + o.toString();
		}
		return strBind;
	}
	
	
	public static void printMethods(Object obj) {
		Method arr[] = obj.getClass().getMethods();
		for(Method m: arr) {
			String name = m.getName();
//			if(name.contains("xxx")) {
				System.out.println(name);
//			}
		}
	}

}
