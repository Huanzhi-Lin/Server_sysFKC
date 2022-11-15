package com.frm.thread;

/**
 * @author LinHuanZhi
 * @time 2021年12月11日
 * @email lhz034069@163.com
 * @description 基线程（socket、... 用到） 
 */
public abstract class AbsThread extends Thread{

	public AbsThread() {
		// TODO Auto-generated constructor stub
		super();
	}
	public AbsThread(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
	public abstract void run();

}
