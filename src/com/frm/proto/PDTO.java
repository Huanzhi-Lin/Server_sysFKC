package com.frm.proto;

import java.io.Serializable;

/**
 * @author LinHuanZhi
 * @time 2021年12月2日
 * @email lhz034069@163.com
 * @description transferObj 
 */
public abstract class PDTO implements Serializable{
	private int sHsCode = 0; 
//	private PID pId = PID.PID_ZERO;
	private PID pId = PID.PID_ZERO;
	
	public PDTO() {
//		this.pId = getPId();
	}
	
	public PDTO(PID pId) {
		this.pId = pId;
	}
	
	public PID getPId() {
		return this.pId;
	}
	public void setPId(PID p) {
		this.pId = p;
	}
	public int getPIdx() {
		return this.pId.ordinal();
	}

	public int getsHsCode() {
		return sHsCode;
	}

	public void setsHsCode(int sHsCode) {
		this.sHsCode = sHsCode;
	}
	
	
}
