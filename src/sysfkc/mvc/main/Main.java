package sysfkc.mvc.main;

import com.frm.db.DbMgr;
import com.frm.mvc.CCenter;
import com.frm.utils.StringUtils;

import sysfkc.mvc.answer.AnswerCtrl;
import sysfkc.mvc.chat.ChatCtrl;
import sysfkc.mvc.login.LoginCtrl;
import sysfkc.mvc.register.RegisterCtrl;
import sysfkc.mvc.serverStart.ServerStartCtrl;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class Main {

	public Main() {
		DbMgr.tryConn();
		CCenter.c.openCtrl(ServerStartCtrl.class);
		CCenter.c.openCtrl(ChatCtrl.class);
		CCenter.c.openCtrl(RegisterCtrl.class);
		CCenter.c.openCtrl(LoginCtrl.class);
		CCenter.c.openCtrl(AnswerCtrl.class);
	}

	public static void main(String[] args) {
		new Main();
	}
}
