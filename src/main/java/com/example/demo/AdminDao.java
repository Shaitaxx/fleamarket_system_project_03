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
public class AdminDao {
	String url = "jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC";
	String user = "root";
	String pass = "Shaitaxx1225!";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	
		//管理者全件検索処理
		public List<Admin> findAllBookAdmin() {
			List<Admin> adminList = new ArrayList<>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, pass);
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM adminList");

				while (rs.next()) {
					int adminId = rs.getInt("admin_id");
					String adminName = rs.getString("admin_name");
					String passward = rs.getString("passward");
					String email = rs.getString("email");
					Admin admin = new Admin(adminId, adminName, passward, email);
					adminList.add(admin);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return adminList;
		}
		
		//管理者新規登録処理
		public void insertAdmin(String adminName, String passward, String email) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, pass);
				ps = con.prepareStatement("INSERT INTO adminList (admin_name, passward, email) values (?, ?, ?)");
				ps.setString(1, adminName);
				ps.setString(2, passward);
				ps.setString(3, email);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		
//		ログイン処理（Emailチェック）
		public List<Admin> findByEmail(String mail){
			List<Admin> adminList = new ArrayList<>();
			try {
				Connection con = DriverManager.getConnection(url, user, pass);
				ps = con.prepareStatement("SELECT * FROM adminList WHERE email = ?");
				ps.setString(1,mail);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int adminId = rs.getInt("admin_id");
					String adminName = rs.getString("admin_name");
					String passward = rs.getString("passward");
					String email = rs.getString("email");
					Admin admin = new Admin(adminId, adminName, passward, email);
					adminList.add(admin);
				}
				//リストが空の時nullを返す
				if(adminList.isEmpty()){
				    return null;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			return adminList;
		}
		
		
//		ログイン処理(emailとpasswardの一致チェック)
		public List<Admin> findByLogin(String mail,String password){
			List<Admin> adminList = new ArrayList<>();
			try {
				Connection con = DriverManager.getConnection(url, user, pass);
				ps = con.prepareStatement("SELECT *  FROM adminList WHERE email = ? AND passward = ?");
				ps.setString(1,mail);
				ps.setString(2,password);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int adminId = rs.getInt("admin_id");
					String adminName = rs.getString("admin_name");
					String passward = rs.getString("passward");
					String email = rs.getString("email");
					Admin admin = new Admin(adminId, adminName, passward, email);
					adminList.add(admin);
				}
				
				//リストが空の時nullを返す
				if(adminList.isEmpty()){
				    return null;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			return adminList;
		}
		
		//close処理
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
