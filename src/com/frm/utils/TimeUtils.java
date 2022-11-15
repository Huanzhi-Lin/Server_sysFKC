package com.frm.utils;

/**
 * @author LinHuanZhi
 * @time 2021年12月12日
 * @email lhz034069@163.com
 * @description 
 */
public class TimeUtils {

	/**
	 * @return
	 * @description
	 */
	public static long curMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * @param ms
	 * @return
	 * @description ms 差值 
	 */
	public static long MillisDiff(long ms) {
		return curMillis() - ms;
	}
	

}
