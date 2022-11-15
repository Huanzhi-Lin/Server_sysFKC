package sysfkc.mvc.chat;

import com.frm.mvc.BaseCtrl;
import com.frm.proto.PDTO;
import com.frm.proto.PID;
import com.frm.snet.SSocketMgr;

public class ChatCtrl extends BaseCtrl{

	@Override
	public void init() { //暂不需要
		// TODO Auto-generated method stub
//		this.view = new ChatView();
		this.model = new ChatModel();
	}

	@Override
	public PID[] registerPIDList() {
		// TODO Auto-generated method stub
		PID[] pIdList = {
				PID.CHAT_INFO
		};
		return pIdList;
	}

	@Override
	public void updateModel(PDTO pdto) {
		// TODO Auto-generated method stub
		broadcase(PID.CHAT_INFO, pdto);
	}

	@Override
	public void updateView(PDTO pdto) {
		// TODO Auto-generated method stub
		
	}

}
