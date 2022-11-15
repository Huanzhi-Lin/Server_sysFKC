package com.frm.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DbUtils {
	public static void showRowDt(ResultSet rs) {
		int columns;
		try {
			ResultSetMetaData rsmd=rs.getMetaData();  //获取结果集的元数据
			columns = rsmd.getColumnCount();
			while(rs.next()){
				for(int i=1;i<=columns;i++){
					System.out.print(rs.getString(i)+"\t");					
				}				
				System.out.println();				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //获取结果集的列数
	}
}
