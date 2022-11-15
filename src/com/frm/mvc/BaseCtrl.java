package com.frm.mvc;

import com.frm.proto.PDTO;
import com.frm.proto.PID;
import com.frm.snet.SPDTODispatchMgr;

/**
 * @author LinHuanZhi
 * @time 2021年11月24日
 * @email lhz034069@163.com
 * @description 
 */
public abstract class BaseCtrl extends BaseMVCCom implements IObserver{
	protected BaseView view;
	protected BaseModel model;
	public BaseCtrl() {
		this(null);
	}
	/**
	 * @return abstract to par
	 */
	public boolean isView() {
		return this.view != null;
	}
	public BaseCtrl(IModelArgsExt args) {
		this.init();
		if(this.isView()) {			
			if(args == null) {			
				this.view.setVisible(true);
			}else {			
				this.view.setVisible(args.isViewVis());
			}
		}
		PID[] pIdList = this.registerPIDList();
		if(pIdList != null) {			
			for(PID pId: pIdList) {
				SPDTODispatchMgr.sp.nonRepeatRegister(pId, this);
//				PdtoDispatch.p.nonRepeatRegister(pId, this);
			}
		}
	}
	protected void mClearAll() {
		this.view.mClear();
		this.view.mClose();
		this.model.mClear();
		this.view = null;
		this.model = null;
	}
	
//	/**
//	 * enhance for sequence. //提升 为了子类addImpl时此方法的摆放顺序
//	 */
//	protected abstract void init();
	
	public void updateSignal(PDTO pdto) {
		if(this.model != null) {
			this.updateModel(pdto);
		}
		if(this.view != null) {
			this.updateView(pdto);
		}
	}
	

}
