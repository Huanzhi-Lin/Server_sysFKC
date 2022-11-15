package com.frm.proto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.frm.mvc.BaseCtrl;
import com.frm.utils.PrintUtils;

/**
 * @author LinHuanZhi
 * @time 2021年12月9日
 * @email lhz034069@163.com
 * @description 
 */
@Deprecated
public class PdtoDispatch {
	public static PdtoDispatch p = new PdtoDispatch(); 
	private PdtoDispatch() {}
	/**
	 * update
	 */
	private static Map<Integer, HashSet<BaseCtrl>> PID_2_CTRL_UPD = new HashMap<>();
	private static Map<BaseCtrl, HashSet<Integer>> CTRL_2_PID_DEL = new HashMap<>();
	
	
//	private <T extends BaseCtrl> boolean isContain(int pId) {
//		return PID_2_CTRL.containsKey(pId);
//	}
	
	
	/**
	 * @param pId
	 * @param ctrl
	 * @description invoke with open/new.
	 */
	public void nonRepeatRegister(PID pId, BaseCtrl ctrl) {
		int pIdx = pId.ordinal();
		System.err.println("===============" + pIdx + "===========");
		HashSet<BaseCtrl> hsUpCtrlSet = PID_2_CTRL_UPD.get(pIdx);
		HashSet<Integer> hsDeIdSet = CTRL_2_PID_DEL.get(ctrl);
		if(hsUpCtrlSet == null) {
			hsUpCtrlSet = new HashSet<BaseCtrl>();
			PID_2_CTRL_UPD.put(pIdx, hsUpCtrlSet);
		}
		if(hsDeIdSet == null) {
			hsDeIdSet = new HashSet<Integer>();
			CTRL_2_PID_DEL.put(ctrl, hsDeIdSet);
		}
		hsUpCtrlSet.add(ctrl);
		hsDeIdSet.add(pIdx);
	}
	
	/**
	 * @param ctrl
	 * @description invoke with close. 
	 */
	public void unRegister(BaseCtrl ctrl) {
		HashSet<Integer> hsDePIdSet = CTRL_2_PID_DEL.remove(ctrl);
		for(int pId: hsDePIdSet) {
			HashSet<BaseCtrl> hsUpCtrlSet = PID_2_CTRL_UPD.get(pId);
			hsUpCtrlSet.remove(ctrl);
			if(hsUpCtrlSet.size() == 0) {
				PID_2_CTRL_UPD.remove(pId);
				PrintUtils.pr("PID_2_CTRL_UPD.hsUpCtrlSet is empty, remove it...");
			}
		}
	}
	
	
	
	/**
	 * @param pdto
	 * @description 派发PDTO 
	 */
	public void dispatch(PDTO pdto) {
		System.out.println(pdto.getPIdx() + "协议号更新");
		@SuppressWarnings("unlikely-arg-type")
		HashSet<BaseCtrl> hsUpCtrlSet = PID_2_CTRL_UPD.get(pdto.getPIdx());
		for(BaseCtrl ctrl: hsUpCtrlSet) {
			ctrl.updateSignal(pdto);
		}
	}
	
	
	
}
