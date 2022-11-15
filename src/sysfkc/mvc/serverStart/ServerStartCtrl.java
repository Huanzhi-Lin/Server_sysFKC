package sysfkc.mvc.serverStart;

import com.frm.mvc.BaseCtrl;
import com.frm.proto.PDTO;
import com.frm.proto.PID;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class ServerStartCtrl extends BaseCtrl{

	public ServerStartCtrl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.view = new ServerStartView();
		this.model = new ServerStartModel();
	}

	@Override
	public PID[] registerPIDList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateModel(PDTO pdto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView(PDTO pdto) {
		// TODO Auto-generated method stub
		
	}

	

}
