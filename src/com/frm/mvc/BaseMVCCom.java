package com.frm.mvc;

import java.sql.ResultSet;

import com.frm.db.DbMgr;
import com.frm.proto.PDTO;
import com.frm.proto.PID;
import com.frm.snet.SSocketMgr;

public class BaseMVCCom {

	
	/**
	 * @param pdto 广播给所有客户端
	 */
	protected void broadcase(PID pId, PDTO pdto) {
		pdto.setPId(pId);
		SSocketMgr.ss.broadcast(pdto);
	}
	
	/**
	 * @param pdto 原包响应指定客户端
	 */
	protected void response(PID pId, PDTO pdto) {
		pdto.setPId(pId);
		SSocketMgr.ss.response(pdto);
	}
	/**
	 * @param pdto 新包响应指定客户端 需要传入旧包
	 */
	protected void responseWithNewPack(PID pId, PDTO pdto, PDTO rec) {
		pdto.setPId(pId);
		pdto.setsHsCode(rec.getsHsCode());
		SSocketMgr.ss.response(pdto);
	}
	/**
	 * @param pId
	 * @param pdto
	 * @param sHsCode 相应指定用户
	 */
	protected void responseWithSpecifiedUser(PID pId, PDTO pdto, int sHsCode) {
		pdto.setPId(pId);
		pdto.setsHsCode(sHsCode);
		SSocketMgr.ss.response(pdto);
	}
	
	
	
	/**
	 * @param preSql
	 * @param objs
	 * @return 执行sql语句-如果第一个结果是ResultSet对象，则返回true；如果第一个结果是执行语句后更新的行数或者没有结果，则返回false
	 */
	protected boolean sqlExe(String preSql, Object ...objs) {
		return DbMgr.exe(preSql, objs);
	}
	/**
	 * @param preSql
	 * @param objs
	 * @return 永不为null
	 * @description sql查询用这个方法
	 */
	protected ResultSet sqlQuery(String preSql, Object ...objs) {
		return DbMgr.query(preSql, objs);
	}
	/**
	 * @param preSql
	 * @param objs
	 * @return 返回影响记录的条数
	 * @description sql增删改用这个方法
	 */
	protected int sqlUpdate(String preSql, Object ...objs) {
		return DbMgr.update(preSql, objs);
	}

	/**
	 * @param v
	 * @return 可以用来判断 sqlUpdate() 是否成功
	 */
	protected boolean isUpSucc(int v) {
		return v > 0;
	}
}
