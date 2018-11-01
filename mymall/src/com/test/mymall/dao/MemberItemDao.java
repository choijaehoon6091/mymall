package com.test.mymall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MemberItemDao {
	public void deleteMemberItem(Connection conn, int no) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("");
	}
	// Member INNER JOIN item
	public ArrayList<HashMap<String, Object>> getMemberItemList(int memberNo) throws SQLException{
		ResultSet resultSet = null;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT mi.no, mi.order_date, mi.item_no, i.name, i.price FROM member_item mi INNER JOIN item i ON mi.item_no = i.no where mi.member_no =1 ";
		while(resultSet.next()){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("memberItemNO", resultSet.getInt("mi.no"));
			map.put("ItemPrice", resultSet.getInt("i.price"));
			list.add(map);
		}
	return list;
	}
public void deleteMemberItem(int no) {
		
	}
	
}

