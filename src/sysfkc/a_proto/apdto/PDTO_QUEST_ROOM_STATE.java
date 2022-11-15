package sysfkc.a_proto.apdto;

import java.util.ArrayList;

import com.frm.proto.PDTO;

public class PDTO_QUEST_ROOM_STATE extends PDTO{
	/**
	 * 房间是否满
	 */
	public boolean isRoomFull = false;
	
	/**
	 * 名字了列表
	 */
	public ArrayList<String> nList = new ArrayList<String>();
	
//	/**
//	 * isPrepared. 暂客户端判断
//	 */
//	public ArrayList<Boolean> pList = null;
}
