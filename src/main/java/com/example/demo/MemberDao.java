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
public class MemberDao {
	
	String url = "jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC";
	String user = "root";
	String pass = "Shaitaxx1225!";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	
	public List<Member> findAll(){
		List<Member>memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM membersList");
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId
						);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
	

	public List<Member> findById(int id){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE member_id = ?");
			ps.setInt(1,id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
	public List<Member> findByName(String name){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE member_name = ?");
			ps.setString(1,name);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}

	
	public List<Member> findByPhone(String phone){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE phone_number = ?");
			ps.setString(1,phone);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
	public List<Member> findByEmail(String mail){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE email = ?");
			ps.setString(1,mail);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
			//リストが空の時nullを返す
			if(memberList.isEmpty()){
			    return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
	public List<Member> findByStudentId(String studentID){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE student_id = ?");
			ps.setString(1,studentID);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
        public List<Member> findBySignupDate(String signUp){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM membersList WHERE signup_date = ?");
			ps.setString(1,signUp);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId
						);
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
        
      //会員情報検索処理
    	public List<Member> findByMember(String inputname, String inputstudentID){
    		List<Member> memberList = new ArrayList<>();
    		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con = DriverManager.getConnection(url, user, pass);
    			ps = con.prepareStatement("SELECT * FROM membersList WHERE member_name LIKE ? AND student_id LIKE ?");
    			ps.setString(1,"%" + inputname + "%");
    			ps.setString(2,"%" + inputstudentID + "%");
    			rs = ps.executeQuery();
    			
    			while(rs.next()) {
    				int memberId = rs.getInt("member_id");
    				String memberName = rs.getString("member_name");
    				String address = rs.getString("address");
    				String phoneNumber = rs.getString("phone_number");
    				String email = rs.getString("email");
    				String birthday = rs.getString("birthday");
    				String passward = rs.getString("passward");
    				String signupDate = rs.getString("signup_date");
    				String studentId = rs.getString("student_id");
    				Member member = new Member(
    						memberId,
    						memberName,
    						address,
    						phoneNumber,
    						email,
    						birthday,
    						passward,
    						signupDate,
    						studentId
    						);
    				memberList.add(member);
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}finally {
    			close();
    		}
    		return memberList;
    	}
        
    	public String findIdByEmail(String mail) {
    		String memberId = null;
    		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con = DriverManager.getConnection(url, user, pass);
    			ps = con.prepareStatement("SELECT member_id FROM membersList WHERE email = ?");
    			ps.setString(1, mail);
    			rs = ps.executeQuery();
    			while(rs.next()) {
    			memberId = rs.getString("member_id");
    			}
    			
    		}catch(Exception e) {
    			e.printStackTrace();
    		}finally {
    			close();
    		}
    				
    		return memberId;
    	}   

	public void update(int id,String name,String address,String phone,String mail) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE membersList SET member_name = ?,address = ?,phone_number = ?,email = ? WHERE member_id = ?");
			ps.setString(1,name);
			ps.setString(2,address);
			ps.setString(3,phone);
			ps.setString(4,mail);
			ps.setInt(5,id);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void delete(int id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM membersList WHERE member_id = ?");
			ps.setInt(1,id);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void insert(String name,String address,String phone,String mail,String bDay,String passward,String signup,String studentId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO membersList(member_name,address,phone_number,email,birthday,passward,signup_date,student_id)values(?,?,?,?,?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2, address);
			ps.setString(3, phone);
			ps.setString(4, mail);
			ps.setString(5, bDay);
			ps.setString(6, passward);
			ps.setString(7, signup);
			ps.setString(8, studentId);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void cardDataInsert(String card_number,String expiration_date,String card_holder,String security_code,int member_id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO stockList(purchaser_id,trading_date)values(?,?)");
			ps.setInt(1,member_id);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void purchaseDataInsert(String purchaser_id, String trading_date,int stock_id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE stockList SET purchaser_id = ?,trading_date = ? WHERE stock_id = ?");
			ps.setString(1,purchaser_id);
			ps.setString(2,trading_date);
			ps.setInt(3,stock_id);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void cardDataInsert(int seller_id,String card_number,String expiration_date,String card_holder,String security_code) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO cardList(card_number,expiration_date,security_code,card_holder,member_id)values(?,?,?,?,?)");
			ps.setString(1,card_number);
			ps.setString(2,expiration_date);
			ps.setString(3,security_code);
			ps.setString(4,card_holder);
			ps.setInt(5,seller_id);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
//	ログイン処理(emailとpasswardの一致チェック)
	public List<Member> findByLogin(String mail,String password){
		List<Member> memberList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT *  FROM membersList WHERE email = ? AND passward = ?");
			ps.setString(1,mail);
			ps.setString(2,password);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int memberId = rs.getInt("member_id");
				String memberName = rs.getString("member_name");
				String address = rs.getString("address");
				String phoneNumber = rs.getString("phone_number");
				String email = rs.getString("email");
				String birthday = rs.getString("birthday");
				String passward = rs.getString("passward");
				String signupDate = rs.getString("signup_date");
				String studentId = rs.getString("student_id");
				Member member = new Member(
						memberId,
						memberName,
						address,
						phoneNumber,
						email,
						birthday,
						passward,
						signupDate,
						studentId);
				memberList.add(member);
			}
			
			//リストが空の時nullを返す
			if(memberList.isEmpty()){
			    return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return memberList;
	}
	
	public void close() {
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
