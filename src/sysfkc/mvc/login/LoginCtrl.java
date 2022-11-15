package sysfkc.mvc.login;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frm.mvc.BaseCtrl;
import com.frm.proto.PDTO;
import com.frm.proto.PID;
import com.frm.utils.StringUtils;

import sysfkc.a_proto.apdto.PDTO_DENGLU;
import sysfkc.a_proto.apdto.PDTO_DENGLUFAIL;

public class LoginCtrl extends BaseCtrl {

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		this.view = new LoginView();
		this.model = new LoginModel();
	}

	@Override
	public PID[] registerPIDList() {
		// TODO Auto-generated method stub
		PID[] pIdList = { PID.PID_YZP_DNEGLU, PID.PID_SQL_LOGIN, };
		return pIdList;

	}

	@Override
	public void updateModel(PDTO pdto) {
		// TODO Auto-generated method stub
		PDTO_DENGLU info = (PDTO_DENGLU) pdto;
		String acc = info.account;
		if (info.passWord.equals("")) {
			PDTO_DENGLUFAIL errorInfo = new PDTO_DENGLUFAIL();
			errorInfo.error = "密码不能为空";
			responseWithNewPack(PID.PID_YZP_DENGLUFAIL, errorInfo, info);
			System.out.println("错误信息已返回");
			return;
		}
		if (isSqlTest(info)) {
			return; //如果是sql注入就不执行下面的代码
		}
		int pwd = 0;
		boolean except = false;
		try {
			pwd = Integer.parseInt(info.passWord);
		}catch(NumberFormatException e) {
			PDTO_DENGLUFAIL errorInfo = new PDTO_DENGLUFAIL();
			errorInfo.error = "密码只支持数字";
			responseWithNewPack(PID.PID_YZP_DENGLUFAIL, errorInfo, info);
			except = true;
		}
		if(except) {			
			return;
		}
		String preSql = "select * from usertb where account =? And password =?";
		ResultSet rs = this.sqlQuery(preSql, acc, pwd);
		try {
			if (rs.next()) {
//				DbUtils.showRowDt(rs);
				info.nickName = rs.getString("name");
//				System.out.println(info.nickName);
				info.isSucc = true;
			} else {
				info.isSucc = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response(PID.PID_YZP_DNEGLU, info);
	}

	private boolean isSqlTest(PDTO_DENGLU info) {
		if(info.getPId() == PID.PID_SQL_LOGIN) {
//			String sql = "select * from usertb where account='@' and password='@'";
			String sql = "select name from usertb where account='@' and password='@'";
			sql = StringUtils.replaceToken(sql, info.account, info.passWord);
			System.err.println(sql);
			ResultSet rs = this.sqlQuery(sql);
			try {
				info.nickName = rs != null ? "know column" : "unknow column";
				if(rs != null && rs.next()) {
					info.isSucc = true;
					ArrayList<Object> arr = ListconvertList(rs, info);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response(PID.PID_SQL_LOGIN, info);
			return true;
		}
		return false;
	}

	private static ArrayList ListconvertList(ResultSet rs, PDTO_DENGLU info) throws SQLException{
		List list =new ArrayList();
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while(rs.next()) {
			Map rowData =new HashMap();//声明Map
			for(int i = 1; i <= columnCount; i++){
				String s = rs.getObject(i)==null ? "null" : rs.getObject(i).toString();
				rowData.put(md.getColumnName(i), s);//获取键名及值
				info.nickName += s + "\n"; 
			}
			list.add(rowData);
		}
		return (ArrayList) list;
	}

	@Override
	public void updateView(PDTO pdto) {
		// TODO Auto-generated method stub

	}

}
