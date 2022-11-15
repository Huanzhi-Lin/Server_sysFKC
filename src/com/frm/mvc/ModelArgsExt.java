package com.frm.mvc;

/**
 * @author LinHuanZhi
 * @time 2021年11月24日
 * @email lhz034069@163.com
 * @description 
 */
public class ModelArgsExt implements IModelArgsExt{
	private boolean viewVis = true;
	
	public ModelArgsExt() {
		// TODO Auto-generated constructor stub
	}

	public boolean isViewVis() {
		return viewVis;
	}

	public void setViewVis(boolean viewVis) {
		this.viewVis = viewVis;
	}
	
	
}
