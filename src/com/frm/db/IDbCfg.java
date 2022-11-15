package com.frm.db;

/**
 * @author LinHuanZhi
 * @time 2021年10月25日
 * @email lhz034069@163.com
 * @description 
 * 【【预处理SQL语句-> 一次解析，多次执行】】——力推使用
 */
public interface IDbCfg {
	String sqlDriverClz = "com.mysql.cj.jdbc.Driver";
	String sqlUrl = "jdbc:mysql://localhost:3306/competition";
	String sqlAct = "root";
	String sqlPwd = "123";
	

	//--------------------------------------------------数据库使用示例---------------------------------------
//	String sql = "insert into usertb values(?, ?, ?, ?, ?, ?);";
//	String ph = "这里的类型是对应 ? 号所在sql语句的类型";
//	xxView.sqlExe(sql, ph, ph, ph, ph, ph,null); //执行
//	xxView.sqlQuery(sql, ph, ph, ph, ph, ph,null); //查询
//	xxView.sqlUpdate(sql, ph, ph, ph, ph, ph,null); //增删改
	//--------------------------------------------------数据库使用示例---------------------------------------
}
