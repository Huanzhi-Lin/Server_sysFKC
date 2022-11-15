package sysfkc.a_proto.apdto;

import com.frm.proto.PDTO;
import com.frm.proto.PID;

public class PDTO_DENGLU extends PDTO{
	
	public String passWord;// 密码
	public String account; // 账号
	public String nickName;
	
	
	/**
	 * 服务器端返回是否登录成功
	 */
	public boolean isSucc = false;
	
//	@Override
//	public PID getPId() {
//		// TODO Auto-generated method stub
//		return PID.PID_YZP_DNEGLU;
//	}

}
