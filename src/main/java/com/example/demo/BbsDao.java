package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class BbsDao {
		String url = "jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC";
		String user = "root";
		String pass = "Shaitaxx1225!";
		Connection con;
		Statement st;
		PreparedStatement ps;
		ResultSet rs;
		
	public List<Bbs> findAll(){
		List<Bbs> bbsList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM bbslist");

			while (rs.next()) {
				String message = rs.getString("message");
				String name = rs.getString("name");
				String memberId = rs.getString("member_id");
				String postDate = rs.getString("post_date");
				String stockId = rs.getString("stock_id");
				int bbsId = rs.getInt("bbs_id");
				Bbs bbs = new Bbs(message, name, memberId,bbsId,postDate,stockId);
				bbsList.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bbsList;
	}
	
	public List<Bbs> findByStockId(String stock_id) {
		List<Bbs> bbsList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM bbslist WHERE stock_id = ?");
			ps.setString(1, stock_id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String message = rs.getString("message");
				String name = rs.getString("name");
				String memberId = rs.getString("member_id");
				String postDate = rs.getString("post_date");
				String stockId = rs.getString("stock_id");
				int bbsId = rs.getInt("bbs_id");
				Bbs bbs = new Bbs(message, name, memberId, bbsId, postDate, stockId);
				bbsList.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return bbsList;
		
	}
	
	public void bbsInsert(String message,String name,String memberId,String postDate,String stockId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO bbslist (message,name,member_id,post_date,stock_id) values (?,?,?,?,?)");
			ps.setString(1, message);
			ps.setString(2, name);
			ps.setString(3, memberId);
			ps.setString(4, postDate);
			ps.setString(5, stockId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void delete(int memberId) {
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM bbslist WHERE member_id = ?");
			ps.setInt(1,memberId);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
		
	private void close() {
		try {
			if (Objects.nonNull(con)) {
				con.close();
			}
			if (Objects.nonNull(st)) {
				st.close();
			}
			if (Objects.nonNull(ps)) {
				ps.close();
			}
			if (Objects.nonNull(rs)) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
