package sysfkc.mvc.answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.frm.mvc.BaseModel;
import com.frm.proto.PDTO;
import com.frm.proto.PID;

import sysfkc.a_proto.apdto.PDTO_BASE;
import sysfkc.a_proto.apdto.PDTO_QUEST_COMMIT_ANSWER;
import sysfkc.a_proto.apdto.PDTO_QUEST_ENTER_ROOM;
import sysfkc.a_proto.apdto.PDTO_QUEST_INFO_UPDATE;
import sysfkc.a_proto.apdto.PDTO_QUEST_PREPARE_START;
import sysfkc.a_proto.apdto.PDTO_QUEST_ROOM_STATE;
import sysfkc.a_proto.apdto.PDTO_QUEST_SHOW_RESULT;
import sysfkc.b_po.Bisaitimu;

public class AnswerModel extends BaseModel{
	/**
	 * 房间上限
	 */
	public final static int ROOM_CAPACITY = 2;
	/**
	 * 竞赛成员
	 */
	private static Vector<Integer> vMembers = new Vector<Integer>();
	public static void removeVMembers(int sHsCode) {
		int idx = vMembers.indexOf(sHsCode);
		if(idx != -1) {
			socketBreakClearByIdx(idx);
		}
	}
	private static Map<Integer, Boolean> isPreparedMap = new HashMap<Integer, Boolean>();
	private static ArrayList<String> aMemName = new ArrayList<String>();
	/**
	 * 答题轮数
	 */
	private final static int COMP_TURN = 6;
	private static int COMP_TURN_COUNTER = 0;
	/**
	 * 答题间隔
	 */
	private final static int INTERVAL_NEXT = 6000;
	
	/**
	 * 随机题库源
	 */
	private ArrayList<Bisaitimu> qList = new ArrayList<Bisaitimu>();
	/**
	 * 随机题库副本
	 */
	private ArrayList<Bisaitimu> rmvList = new ArrayList<Bisaitimu>();
	/**
	 * 抽出的题目
	 */
	private ArrayList<Bisaitimu> stackList = new ArrayList<Bisaitimu>();

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String preSql = "select * from bisaitimu";
				ResultSet rs = sqlQuery(preSql);
				if(rs != null) {					
					try {
						ListconvertList(rs);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					System.err.println("AnswerModel extract question failed....");
				}
			}
		}).start();
	}

	@Override
	protected void mClear() {
		// TODO Auto-generated method stub
		//TODO
		COMP_TURN_COUNTER = 0;
		vMembers.clear();
		isPreparedMap.clear();
		aMemName.clear();
		rmvList.clear();
		stackList.clear();
	}
	private static void socketBreakClearByIdx(int idx) {
		int sHsCode = vMembers.remove(idx);
		isPreparedMap.remove(sHsCode);
		aMemName.remove(idx);
	}
	
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @description 数据库拷贝 - 从数据库
	 */
	private void ListconvertList(ResultSet rs) throws SQLException{
//		List list =new ArrayList();
//		ResultSetMetaData md = rs.getMetaData();//获取键名
//		int columnCount = md.getColumnCount();//获取行的数量
		while(rs.next()) {
			Bisaitimu item = new Bisaitimu();
//			for(int i = 1; i <= columnCount; i++){
//				item.setId(rs.getInt(i));
//			}
			item.setId(rs.getInt(1));
			item.setTimu(rs.getString(2));
			item.setChoiceA(rs.getString(3));
			item.setChoiceB(rs.getString(4));
			item.setChoiceC(rs.getString(5));
			item.setChoiceD(rs.getString(6));
			item.setDaan(rs.getString(7));
			qList.add(item);
		}
//		return (ArrayList) list;
	}
	/**
	 * @param pId
	 * @param pdto
	 * @description 房间广播
	 */
	private void rspRoom(PID pId, PDTO pdto) {
		for(int sHsCode: vMembers) {			
			responseWithSpecifiedUser(pId, pdto, sHsCode);
		}
	}
	
	/**
	 * 请求进入房间处理
	 */
	public void handleEnterRoom(PDTO pdto) {
		if(isRoomFull()) {
			//TODO 返回提示信息“房间满了，进不去了~”
//			PID_QUESTION_ROOM_FULL
			return;
		}
		PDTO_QUEST_ENTER_ROOM info = (PDTO_QUEST_ENTER_ROOM)pdto;
		vMembers.add(info.getsHsCode());
		aMemName.add(info.name);
		isPreparedMap.put(info.getsHsCode(), false);
		PDTO_QUEST_ROOM_STATE rsp = new PDTO_QUEST_ROOM_STATE();
		rsp.isRoomFull = isRoomFull();
		rsp.nList.addAll(aMemName);
		rspRoom(PID.PID_QUESTION_ENTER_STATE, rsp);
	}
	private boolean isRoomFull() {
		return this.vMembers.size() >= ROOM_CAPACITY;
	}
	/**
	 * 请求开始处理
	 */
	public void handleReqStart(PDTO pdto) {
		isPreparedMap.put(pdto.getsHsCode(), true);
		if(isAllPrepared()) {		
			rmvList.clear();
			rmvList.addAll(qList);
//			PDTO rsp = new PDTO_BASE();
			PDTO_QUEST_PREPARE_START rsp = new PDTO_QUEST_PREPARE_START();
			rsp.nList.addAll(aMemName);
			rspRoom(PID.PID_QUESTION_PREPARE_START, rsp);
			startCd();
		}
	}
	private boolean isAllPrepared() {
		boolean isPrepared = true;
		for(int v: vMembers) {
			if(!isPreparedMap.get(v)) {
				isPrepared = false;
				break;
			}
		}
		return isPrepared;
	}
	/**
	 * 请求提交处理
	 */
	public void handleCommit(PDTO pdto) {
		PDTO_QUEST_COMMIT_ANSWER info = (PDTO_QUEST_COMMIT_ANSWER) pdto;
		String topAnswer = stackList.get(stackList.size()-1).getDaan();
		PDTO_QUEST_SHOW_RESULT rsp = new PDTO_QUEST_SHOW_RESULT();
		
		rsp.isCorrect = info.commitAnswer.equals(topAnswer);
		rsp.showRst = topAnswer;
		rspRoom(PID.PID_QUESTION_SHOW_RESULT, rsp);
		startCd();
	}
	
	
//	---------------------------------------------
	
	private void startCd() {
		this.startNext(INTERVAL_NEXT);
	}
	
	
	public void startNext(long delay) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				//TODO
//				if(qList.size() > 0) {
				if(++COMP_TURN_COUNTER <= COMP_TURN) {
//					Random r=new Random();
//					int rVal = r.nextInt(rmvList.size());					
					int rVal = (int) (rmvList.size()*Math.random());
					int adjust = Math.min(0, rVal);
					Bisaitimu item = rmvList.remove(adjust);
//					if(rmvList.size() < 0) rmvList.addAll(stackList); //无题循环利用
					stackList.add(item);
					PDTO_QUEST_INFO_UPDATE rsp = new PDTO_QUEST_INFO_UPDATE();
					rsp.answerName = COMP_TURN_COUNTER%2 == 1 ? aMemName.get(0) : aMemName.get(1);
//					rsp.item = item;
					rsp.Id = item.getId();
					rsp.timu = item.getTimu();
					rsp.choiceA = item.getChoiceA();
					rsp.choiceB = item.getChoiceB();
					rsp.choiceC = item.getChoiceC();
					rsp.choiceD = item.getChoiceD();
					rsp.daan = item.getDaan();
					rspRoom(PID.PID_QUESTION_INFO_UPDATE, rsp);
				}else {
					PDTO_BASE rsp = new PDTO_BASE();
					rspRoom(PID.PID_QUESTION_END, rsp);
					mClear();
				}
			}
		}, delay);
	}

}
