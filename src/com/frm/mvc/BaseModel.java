package com.frm.mvc;

/**
 * @author LinHuanZhi
 * @time 2021年11月24日
 * @email lhz034069@163.com
 * @description 
 */
public abstract class BaseModel extends BaseMVCCom{

	public BaseModel() {
		// TODO Auto-generated constructor stub
		this.init();
	}
	
	protected abstract void init();
	protected abstract void mClear();

	
//	protected void broadcase(PDTO pdto) {
//		SSocketMgr.ss.broadcast(pdto);
//	}
//	
//	protected void response(PDTO pdto) {
//		SSocketMgr.ss.response(pdto);
//	}
}
