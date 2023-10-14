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
public class BookDao {
	String url = "jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC";
	String user = "root";
	String pass = "Shaitaxx1225!";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public List<Book> findAll() {
		List<Book> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM stockList");

			while (rs.next()) {
				int stockid = rs.getInt("stock_id");
				String bookcode = rs.getString("bookcode");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				String looklike = rs.getString("looklike");
				String sellingprice = rs.getString("selling_price");
				String tradingdate = rs.getString("trading_date");
				String purchaserid = rs.getString("purchaser_id");
				String sellerid = rs.getString("seller_id");
				String comment = rs.getString("comment");
				Book book = new Book(stockid, bookcode, title, author, category, looklike, sellingprice,
						tradingdate, purchaserid, sellerid, comment);
				bookList.add(book);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;
	}
		
	public List<Book> findByBooks(String inputtitle,String inputauthor,String inputcategory) {
		List<Book> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM stockList WHERE title LIKE ? AND author LIKE ? AND category LIKE ? ");
			ps.setString(1, "%" + inputtitle + "%");
			ps.setString(2, "%" + inputauthor + "%");
			ps.setString(3, "%" + inputcategory + "%");

			
			rs = ps.executeQuery();

			while (rs.next()) {
				int stockid = rs.getInt("stock_id");
				String bookcode = rs.getString("bookcode");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				String looklike = rs.getString("looklike");
				String sellingprice = rs.getString("selling_price");
				String tradingdate = rs.getString("trading_date");
				String purchaserid = rs.getString("purchaser_id");
				String sellerid = rs.getString("seller_id");
				String comment = rs.getString("comment");
				Book book = new Book(stockid, bookcode, title, author, category, looklike, sellingprice,
						tradingdate, purchaserid, sellerid, comment);
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;
	}
	
	public List<Book> findByStockId(int stock_id) {
		List<Book> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM stockList WHERE stock_id = ?");
			ps.setInt(1, stock_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				int stockid = rs.getInt("stock_id");
				String bookcode = rs.getString("bookcode");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				String looklike = rs.getString("looklike");
				String sellingprice = rs.getString("selling_price");
				String tradingdate = rs.getString("trading_date");
				String purchaserid = rs.getString("purchaser_id");
				String sellerid = rs.getString("seller_id");
				String comment = rs.getString("comment");
				Book book = new Book(stockid, bookcode, title, author, category, looklike, sellingprice,
						tradingdate, purchaserid, sellerid, comment);
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;
	}
	
	
	public List<Book> findByPurchaserId(int purchaser_id) {
		List<Book> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM stockList WHERE purchaser_id = ?");
			ps.setInt(1, purchaser_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				int stockid = rs.getInt("stock_id");
				String bookcode = rs.getString("bookcode");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				String looklike = rs.getString("looklike");
				String sellingprice = rs.getString("selling_price");
				String tradingdate = rs.getString("trading_date");
				String purchaserid = rs.getString("purchaser_id");
				String sellerid = rs.getString("seller_id");
				String comment = rs.getString("comment");
				Book book = new Book(stockid, bookcode, title, author, category, looklike, sellingprice,
						tradingdate, purchaserid, sellerid, comment);
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;
	}
	
	
	public List<Book> findBySellerId(int seller_id) {
		List<Book> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM stockList WHERE seller_id = ?");
			ps.setInt(1, seller_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				int stockid = rs.getInt("stock_id");
				String bookcode = rs.getString("bookcode");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				String looklike = rs.getString("looklike");
				String sellingprice = rs.getString("selling_price");
				String tradingdate = rs.getString("trading_date");
				String purchaserid = rs.getString("purchaser_id");
				String sellerid = rs.getString("seller_id");
				String comment = rs.getString("comment");
				Book book = new Book(stockid, bookcode, title, author, category, looklike, sellingprice,
						tradingdate, purchaserid, sellerid, comment);
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;
	}
	
	public void delete(int stockid) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM stockList WHERE stock_id = ?");
			ps.setInt(1, stockid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(int stockid, String title,String author,String looklike,String price,String tradingdate,String purchaserid,String sellerid,String comment) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE stockList SET title=?,author = ?,looklike=?,selling_price = ?,trading_date = ?,purchaser_id = ?,seller_id = ?,comment = ?  WHERE stock_id = ?");
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, looklike);
			ps.setString(4, price);
			ps.setString(5, tradingdate);
			ps.setString(6, purchaserid);
			ps.setString(7, sellerid);
			ps.setString(8, comment);
			ps.setInt(9, stockid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	//教科書出品処理
		public void insert(String bookcode, String title, String author, String category, String looklike, String sellingprice, String comment, String sellerid) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, pass);
				ps = con.prepareStatement("INSERT INTO stockList (bookcode, title, author, category, looklike, selling_price, comment, seller_id) values (?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, bookcode);
				ps.setString(2, title);
				ps.setString(3, author);
				ps.setString(4, category);
				ps.setString(5, looklike);
				ps.setString(6, sellingprice);
				ps.setString(7, comment);
				ps.setString(8, sellerid);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
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