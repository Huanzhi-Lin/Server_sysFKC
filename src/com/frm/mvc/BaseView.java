package com.frm.mvc;

import com.frm.proto.PDTO;
import com.frm.snet.SSocketMgr;
import com.frm.utils.PrintUtils;

/**
 * @author LinHuanZhi
 * @time 2021年11月24日
 * @email lhz034069@163.com
 * @description 
 */
public abstract class BaseView extends BaseMVCCom{
//	private JFrame jf = null;
	public BaseView() {
		// TODO Auto-generated constructor stub
//		PrintUtils.pr("constructor-BaseView...........");
//		initialize(); //TODO - 子类自动创建，交给子类了
//		initListener(); //TODO - giveup = 子类initialize后无法保证顺序，所以giveup
	}
	
	protected abstract void setVisible(boolean b);
//	protected abstract void initialize(); //TODO - 子类自动创建，交给子类了
//	protected abstract void initListener(); //TODO - giveup
	protected abstract void mClear();
	protected abstract void mClose();
//	protected abstract void mInject();

//	
//	protected void broadcase(PDTO pdto) {
//		SSocketMgr.ss.broadcast(pdto);
//	}
//	
//	protected void response(PDTO pdto) {
//		SSocketMgr.ss.response(pdto);
//	}

}

//DO_NOTHING_ON_CLOSE:无操作
//HIDE_ON_CLOSE:隐藏窗口
//DISPOSE_ON_CLOSE：隐藏当前窗口，并释放窗体占有的其他资源。在窗口被关闭的时候会dispose这个窗口。
//EXIT_ON_CLOSE：结束窗口所在的应用程序。在窗口被关闭的时候会退出JVM。 