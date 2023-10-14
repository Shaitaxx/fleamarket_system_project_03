package com.example.demo;

public class Book {
	private int stockid;
	private String bookcode;
	private String title;
	private String author;
	private String category;
	private String looklike;
	private String sellingprice;
	private String tradingdate;
	private String purchaserid;
	private String sellerid;
	private String comment;
	private byte img;

	public Book(int stockid, String bookcode, String title, String author,
			String category, String looklike, String sellingprice, String tradingdate,
			String purchaserid, String sellerid, String comment) {
		this.stockid = stockid;
		this.bookcode = bookcode;
		this.title = title;
		this.author = author;
		this.category = category;
		this.looklike = looklike;
		this.sellingprice = sellingprice;
		this.tradingdate = tradingdate;
		this.purchaserid = purchaserid;
		this.sellerid = sellerid;
		this.comment = comment;
	}

	public int getStockid() {
		return stockid;
	}

	public String getBookcode() {
		return bookcode;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getCategory() {
		return category;
	}

	public String getLooklike() {
		return looklike;
	}

	public String getSellingprice() {
		return sellingprice;
	}

	public String getTradingdate() {
		return tradingdate;
	}

	public String getPurchaserid() {
		return purchaserid;
	}

	public String getSellerid() {
		return sellerid;
	}

	public String getComment() {
		return comment;
	}

	public byte getImg() {
		return img;
	}
}
