package sysfkc.a_proto.apdto;

import com.frm.proto.PDTO;
import com.frm.proto.PID;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class PDTO_TICKER extends PDTO{
	public String info = "";

	@Override
	public PID getPId() {
		// TODO Auto-generated method stub
//		System.out.println(this.info);
		return super.getPId();
	}

}
