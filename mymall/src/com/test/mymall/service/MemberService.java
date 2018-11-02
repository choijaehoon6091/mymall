package com.test.mymall.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.test.mymall.commons.DBHelper;
import com.test.mymall.dao.MemberDao;
import com.test.mymall.dao.MemberItemDao;
import com.test.mymall.vo.Member;

public class MemberService {
	private MemberDao memberDao;
	private MemberItemDao memberItemDao;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public void updateMember(Member member) {
		System.out.println("updateMember 메서드... MemberService.java");
		try {
			connection = DBHelper.getConnection();		
			memberDao = new MemberDao();
			memberDao.updateMember(connection, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(connection, preparedStatement, resultSet);
		}
	}

	public Member selectMember(String id) {		
		System.out.println("selectMember 메서드... MemberService.java");
		Member selectMember = new Member();
		try {
			connection = DBHelper.getConnection();
			
			memberDao = new MemberDao();
			selectMember = memberDao.selectMember(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(connection, preparedStatement, resultSet);
		}	
		return selectMember;
	}

	public boolean deleteMember(Member member) {
		System.out.println("deleteMember 메서드... MemberService.java");
		boolean check= false;
		try {
			connection = DBHelper.getConnection();
			// 자동커밋false
			connection.setAutoCommit(false);
			memberDao = new MemberDao();
			memberItemDao = new MemberItemDao();
			check = memberDao.deleteCheckMember(connection, member);
			//비밀번호 틀릴시 false 로 실행되지않음
			if(check) {
				memberItemDao.deleteMemberItem(connection, member);
				memberDao.deleteMember(connection, member);
			}
			//commit
			connection.commit();
		} catch(Exception e) {
			try {
				// 문제 발생시 롤백..
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				DBHelper.close(connection, preparedStatement, resultSet);
			}
			e.printStackTrace();
		} finally {
			DBHelper.close(connection, preparedStatement, resultSet);
		}		
		return check;
	}

	public Member login(Member member) {
		System.out.println("login 메서드... MemberService.java");
		memberDao = new MemberDao();
		Member loginmember = new Member();	
		try {
			connection = DBHelper.getConnection();
			loginmember = memberDao.login(connection, member);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(connection, preparedStatement, resultSet);
		}
		return loginmember;
	}

	public void addMember(Member member) {
		System.out.println("addMember 메서드... MemberService.java");
		memberDao = new MemberDao();		
		try {
			connection = DBHelper.getConnection();
			memberDao.insertMember(connection, member);
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(connection, preparedStatement, resultSet);
		}	
	}
}
