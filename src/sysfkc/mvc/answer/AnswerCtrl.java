package sysfkc.mvc.answer;

import com.frm.mvc.BaseCtrl;
import com.frm.proto.PDTO;
import com.frm.proto.PID;

public class AnswerCtrl extends BaseCtrl {

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		this.view = new AnswerView();
		this.model = new AnswerModel();
	}

	@Override
	public PID[] registerPIDList() {
		// TODO Auto-generated method stub
		PID[] pIdList = {
			PID.PID_QUESTION_ENTER_ROOM, //进入c
//			PID.PID_QUESTION_ENTER_STATE, //进入房间收到的信息s
			PID.PID_QUESTION_REQ_START, //请求开始答题c （就绪检测c）
//			PID.PID_QUESTION_PREPARE_START, //倒计时开始s
//			PID.PID_QUESTION_INFO_UPDATE, //题目信息s  tag
			PID.PID_QUESTION_COMMIT_ANSWER, //提交答案c
//			PID.PID_QUESTION_SHOW_RESULT, //显示结果s  进入倒计时c  定时跳到tag||结束
//			PID.PID_QUESTION_END, //结束答题比赛
		};
		return pIdList;
	}

	@Override
	public void updateModel(PDTO pdto) {
		// TODO Auto-generated method stub
		AnswerModel am = (AnswerModel) this.model;
		switch(pdto.getPId()) {
		case PID_QUESTION_ENTER_ROOM:
				am.handleEnterRoom(pdto);
			break;
//		case PID_QUESTION_ENTER_STATE:
//			break;
		case PID_QUESTION_REQ_START:
				am.handleReqStart(pdto);
			break;
//		case PID_QUESTION_PREPARE_START:
//			break;
//		case PID_QUESTION_INFO_UPDATE:
//			break;
		case PID_QUESTION_COMMIT_ANSWER:
				am.handleCommit(pdto);
			break;
//		case PID_QUESTION_SHOW_RESULT:
//			break;
//		case PID_QUESTION_END:
//			break;
		default:
			System.err.println("unknow QUESTION_PID__________");
			break;
		}
	}

	@Override
	public void updateView(PDTO pdto) {
		// TODO Auto-generated method stub
		
	}

}
