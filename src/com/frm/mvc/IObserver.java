package com.frm.mvc;

import com.frm.proto.PDTO;
import com.frm.proto.PID;

/**
 * @author LinHuanZhi
 * @time 2021年11月10日
 * @email lhz034069@163.com
 * @description
 * Ctrl is observer.  
 */
public interface IObserver {
	public abstract void init();
	/**
	 * @return
	 * @description register pid. 
	 */
	public PID[] registerPIDList();
	/**
	 * @param vType
	 * @description update entrance...with PDTO. 
	 */
	public void updateModel(PDTO pdto);
	public void updateView(PDTO pdto);
}
