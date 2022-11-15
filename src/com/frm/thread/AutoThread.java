package com.frm.thread;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public abstract class AutoThread extends AbsThread{
	public AutoThread() {
		// TODO Auto-generated constructor stub
		super();
		this.start();
		System.out.println("AutoThread start...");
	}
	public AutoThread(String name) {
		// TODO Auto-generated constructor stub
		super(name);
		this.start();
	}
}
