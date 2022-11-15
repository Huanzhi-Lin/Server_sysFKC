package com.frm.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.frm.snet.SPDTODispatchMgr;
import com.frm.utils.PrintUtils;

/**
 * @author LinHuanZhi
 * @time 2021年11月10日
 * @email lhz034069@163.com
 * @description 优化/注解
 */
public class CCenter/* <T extends BaseCtrl> */ {
	private static Map<Class<? extends BaseCtrl>, BaseCtrl> INSTANCES = new HashMap<>();
//	@SuppressWarnings("rawtypes")
	public static CCenter c = new CCenter(); 
	private CCenter() {}
	
	private <T extends BaseCtrl> BaseCtrl getIns(Class<T> clz, IModelArgsExt args) {
		BaseCtrl b = null;
		try {
			b = this.isContain(clz) ? INSTANCES.get(clz) : 
				(args == null) ? INSTANCES.put(clz, clz.getDeclaredConstructor().newInstance()) :
				INSTANCES.put(clz, clz.getDeclaredConstructor(IModelArgsExt.class).newInstance(args));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	private <T extends BaseCtrl> boolean isContain(Class<T> clz) {
		return INSTANCES.containsKey(clz);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BaseCtrl> T openCtrl(Class<T> clz, IModelArgsExt args) {
		return (T)c.getIns(clz, args);
	}
	@SuppressWarnings("unchecked")
	public <T extends BaseCtrl> T openCtrl(Class<T> clz) {
		return (T)c.getIns(clz, null);
	}
	
	public <T extends BaseCtrl> void closeCtrl(Class<T> clz) {
		if(this.isContain(clz)) {
			int len = INSTANCES.size();
			BaseCtrl ctrl = INSTANCES.remove(clz);
//			int after = INSTANCES.size();
//			PrintUtils.pr(len + " " + after);
			ctrl.mClearAll();
			SPDTODispatchMgr.sp.unRegister(ctrl);
//			PdtoDispatch.p.unRegister(ctrl);
			len = len - INSTANCES.size();
			if(len==1) {
				System.out.println("closed succeed.");
			}else{
				System.out.println("closed exception."); 
			}
		}else {			
			System.err.println("can't found class.");
		}
		this.showInsList();
	}
	
	public void showInsList() {
		PrintUtils.pEnter(this, "showInsList");
		INSTANCES.forEach((clz, ibc)->{
//			String cName = clz.getClass().getName();
			String cName = "";
			String iName = ibc.getClass().getName();
			System.out.println("cName：" + cName + " | iName：" + iName);
		});
	}
	
//	private void methodReflectInvoke() {
//		Clz p = new Clz();
//		Class clazz = p.getClass();
//		Method method = clazz.getMethod("methodName", null);
//		//123
//		method.invoke(p, null);
//	}
	
}
