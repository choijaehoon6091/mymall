package com.test.mymall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.mymall.vo.Member;

public class MemberDao {
	// 디비 연결..
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        String jdbcDriver = "jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=euckr";
        String dbID = "root";
        String dbPW = "java0000";
        connection = DriverManager.getConnection(jdbcDriver, dbID, dbPW);
        return connection;
    }
    // 공통 사용 코드 메서드화
    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if(resultSet != null) {
            try {resultSet.close();} catch(Exception exception){exception.printStackTrace();}
        }
        if(preparedStatement != null) {
            try {preparedStatement.close();} catch(Exception exception){exception.printStackTrace();}
        }
        if(connection != null) {
            try {connection.close();} catch(Exception exception){exception.printStackTrace();}
        }
    }

	//멤버 입력 메서드
	public int insertMember(Member member) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = this.getConnection();
            	//no 컬럼은 자동증가
            preparedStatement = connection.prepareStatement("insert into member (id, pw, level) value (?, ?, ?)");
            preparedStatement.setString(1, member.getId());
            preparedStatement.setString(2, member.getPw());
            preparedStatement.setInt(3, member.getLevel());
	  	    
            preparedStatement.executeUpdate();
	  	    
            preparedStatement.close();
            connection.close();
	  	    
		} catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            this.close(connection, preparedStatement, resultSet);
        }
        
		return 0;
	}	
	
	//멤버로그인 처리
	public boolean loginMember(Member member) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean loginResult = false; // 리턴을 위한 변수 선언. 기본값 false.. false 반환시 회원정보없음->실패
        
        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement("select id, level from member where id=? and pw=?");
            preparedStatement.setString(1, member.getId());
            preparedStatement.setString(2, member.getPw());
            
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
            	//select 값이 있을경우 로그인성공,, 리턴값 true
            	//서블릿에서 true값일시 세션에 로그인정보를 저장
            	loginResult = true;
            }
            
		} catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            this.close(connection, preparedStatement, resultSet);
        }
        return loginResult;
	}
	
	//한명의 회원정보
	public Member selectMember(String id) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Member member = new Member();
        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement("select * from member where id=?");
            preparedStatement.setString(1, member.getId());
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
            	member = new Member();
            	member.setId(resultSet.getString("id"));
            	member.setPw(resultSet.getString("pw"));
            	member.setLevel(resultSet.getInt("level"));	
            }
            
		} catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            this.close(connection, preparedStatement, resultSet);
        }      
        return member;
	}
}
