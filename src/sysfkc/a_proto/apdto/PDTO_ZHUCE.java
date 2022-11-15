package sysfkc.a_proto.apdto;

import com.frm.proto.PDTO;
import com.frm.proto.PID;

public class PDTO_ZHUCE extends PDTO {
	
	public String TeamName; // 队伍名
	public String PassWord;// 密码
	public String Account; // 账号
	
	/**
	 * 服务器端返回是否注册成功
	 */
	public boolean isSucc = false;

//	@Override
//	public PID getPId() {
//		// TODO Auto-generated method stub
//		return PID.PID_YZP_ZHUCE;
//	}

}
