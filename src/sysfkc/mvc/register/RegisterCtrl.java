package sysfkc.mvc.register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.frm.mvc.BaseCtrl;
import com.frm.proto.PDTO;
import com.frm.proto.PID;

import sysfkc.a_proto.apdto.PDTO_ZHUCE;

public class RegisterCtrl extends BaseCtrl{

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		this.view = new RegisterView();
		this.model = new RegisterModel();
	}

	@Override
	public PID[] registerPIDList() {
		// TODO Auto-generated method stub
		PID[] pIdList = {
				PID.PID_YZP_ZHUCE
		};
		return pIdList;
	}

	/**
	 *
	 */
	/**
	 *
	 */
	@Override
	public void updateModel(PDTO pdto) {
		// TODO Auto-generated method stub
		PDTO_ZHUCE info = (PDTO_ZHUCE) pdto;
		String tn = info.TeamName;
		String acc = info.Account;
		int pwd = Integer.parseInt(info.PassWord);
		boolean existAccount = false;
		
		//如果账号存在数据库 -主要这一步  =》 什么情况下把应该existAccount这个变量赋值为true
		String preSql1 = "select * from usertb where account =? ";
		ResultSet rs = this.sqlQuery(preSql1, acc);
			try {
				if (rs.next()) {
					existAccount=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(existAccount) {
			//那么isSucc为false
			info.isSucc = false;
			//返回协议给客户端
			response(PID.PID_YZP_ZHUCE, info);
			//阻止跑到下面去
			return;
		}
		//在下面这段代码之前做判断
		String preSql = "insert into usertb values(?, ?, ?, ?, ?, ?)";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(dt);
		int affectRow = this.sqlUpdate(preSql, null, tn, null, acc, pwd, date);
		//4、插入数据库
		if(affectRow > 0) {
			info.isSucc = true;
		}
		
		//5、响应客户端
		response(PID.PID_YZP_ZHUCE, info);
		
	}

	@Override
	public void updateView(PDTO pdto) {
		// TODO Auto-generated method stub
		
	}

}
