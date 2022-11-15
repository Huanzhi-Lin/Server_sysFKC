package com.frm.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.frm.utils.StringUtils;

public class DbMgr {
	private static Connection conn = null;

	public static void tryConn() {
		getConnection();
	}
	
	/**
	 * 清空表
	 * @description  DbMgr.truncateTb("usertb");
	 */
	public static void truncateTb(String tbName) {
		String sql = StringUtils.replaceToken("truncate table @;", tbName);
		try {
			mSql(sql).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void testInsert() {
//		for(int i=1; i< 100; i++) {			
//		String sql = "insert into usertb values(?, ?, ?, ?, ?, ?);";
//			String ph = i+"";
//			DbMgr.exe(sql, ph, ph, ph, ph, ph,null);
//		}
//	}

	public static Connection getConnection() {
		if (conn != null) {
			return conn;
		}
		try {
			Class.forName(IDbCfg.sqlDriverClz);
			conn = DriverManager.getConnection(IDbCfg.sqlUrl, IDbCfg.sqlAct, IDbCfg.sqlPwd);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("can't connected to database...");
		}
		return conn;
	}

	/**
	 * @return 如果第一个结果是ResultSet对象，则返回true；如果第一个结果是执行语句后更新的行数或者没有结果，则返回false
	 */
	public static boolean exe(String preSql, Object ...objs) {
//		String sql = "insert into timeTest value(?,?,?)";
//		PreparedStatement pstm = null;
		boolean b = false;
		try {
//			pstm = getConnection().prepareStatement(sql);
			b = mSql(preSql, objs).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("sql syntaxErrorException... [DbMgr.exe]");
		}
		return b;
	}

	/**
	 * @param sql
	 * @return 永不为null
	 */
	public static ResultSet query(String preSql, Object ...objs) {
		ResultSet rs = null;
		try {
//			Statement pstm = getConnection().prepareStatement(sql);
//			rs = pstm.executeQuery(sql);
			rs = mSql(preSql, objs).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("sql syntaxErrorException... [DbMgr.query]");
		}
		return rs;
	}

	/**
	 * @return 增删改
	 */
	public static int update(String preSql, Object ...objs) {
		int affectRow = 0;
		try {
//			PreparedStatement pstm = getConnection().prepareStatement(sql);
//			affectRow = pstm.executeUpdate();
			affectRow = mSql(preSql, objs).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectRow;
	}
	
	
	/**   
	 * @return 
	 */
	private static PreparedStatement mSql(String preSql, Object ...objs) {
		int len = objs.length;
		if(StringUtils.calcTokenCount(preSql, "?") != len) {
			throw new IllegalArgumentException("parameters count don't match...DbMgr.mSql(x,x)");
		}
		PreparedStatement pstm = null;
		try {
			pstm = getConnection().prepareStatement(preSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(objs.length > 0) {
			for(int fori = 0; fori < objs.length; fori++) {
				Object mParam = objs[fori];
				int sqlPlaceHolderIdx = fori+1;
				if(mParam == null) {
//					StringUtils.replaceSpecifiedToken("?", preSql, null);
//					StringBuffer sb = new StringBuffer(preSql);
//					List<Integer> l = StringUtils.findPitIdxList(preSql, "?");
//					int idx = l.get(fori);
//					preSql = sb.replace(idx, idx+1, "null").toString();
//					pstm.setNull(fori, idx);
					try {
						pstm.setNull(sqlPlaceHolderIdx, Types.NULL);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
				String simpleTName = mParam.getClass().getSimpleName();
				String methodName = null;
				try {
					methodName = mappingParser(simpleTName);
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Method m = null;
				try {					
					if(simpleTName.equals("Integer")) {
						m = pstm.getClass().getMethod(methodName, int.class, int.class);
					}else {						
						m = pstm.getClass().getMethod(methodName, int.class, mParam.getClass());
					}
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					m.invoke(pstm, sqlPlaceHolderIdx, mParam);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return pstm;
	}
	

	private static String mappingParser(String typeName) throws NoSuchMethodException {
		String str = "";
		switch(typeName) {
		case "Boolean":
				str = "setBoolean";
			break;
		case "Byte":
			str = "setByte";
			break;
		case "Short":
			str = "setShort";
			break;
		case "Integer":
			str = "setInt";
			break;
		case "Long":
			str = "setLong";
			break;
		case "Float":
			str = "setFloat";
			break;
		case "Double":
			str = "setDouble";
			break;
		case "Time":
			str = "setTime";
			break;
		case "String":
			str = "setString";
			break;
		case "TimeStamp":
			str = "setTimestamp";
			break;
		case "Date":
			str = "setDate";
			break;
		case "Object":
			str = "setObject";
			break;
//		case "":
//			str = "";
//			break;
		}
		if(str == "") {
			throw new NoSuchMethodException(StringUtils.replaceToken("can't found match method...【- @ -】", typeName));
		}
		return str;
		
//		setURL
//		setArray
//		setBytesNoEscapeNoQuotes
//		setNCharacterStream
//		setNCharacterStream
//		setSQLXML
//		setUnicodeStream
//		setBytesNoEscape
//		setClob
//		setClob
//		setClob
//		setBytes
//		setBytes
//		setBlob
//		setBlob
//		setBlob
//		setAsciiStream
//		setAsciiStream
//		setAsciiStream
//		setBigDecimal
//		setBinaryStream
//		setBinaryStream
//		setBinaryStream
//		setCharacterStream
//		setCharacterStream
//		setCharacterStream
//		setNString
//		setBigInteger
//		setRef
//		setNClob
//		setNClob
//		setNClob
//		setRowId
//		setAttribute
//		setQueryTimeout
//		setMaxRows
//		setMaxFieldSize
//		setCursorName
//		setFetchDirection
//		setFetchSize
//		setLargeMaxRows
//		setPoolable
//		setEscapeProcessing
//		resetCancelledState
//		setLocalInfileInputStream
//		setHoldResultsOpenOverClose
//		setClearWarningsCalled
//		setTimeoutInMillis
//		setExecuteTime
//		setPingTarget
//		setCancelStatus
//		setResultType
//		setCurrentDatabase
//		setResultFetchSize
	}
	
	
	/**
	 * 批处理更新 - 占位
	 */
	public static void batchUpdate() {
		
	}

}
