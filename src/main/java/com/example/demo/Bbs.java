package com.example.demo;

public class Bbs {
	
	private String message;
	private String name;
	private String memberId;
	private int bbsId;
	private String postDate;
	private String stockId;

	public Bbs() {
		
	}
	
	public Bbs(String message,String name,String memberId,int bbsId,String postDate,String stockId) {
		this.message = message;
		this.name = name;
		this.memberId = memberId;
		this.bbsId = bbsId;
		this.postDate = postDate;
		this.stockId = stockId;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public int getBbsId() {
		return bbsId;
	}

	public void setBbsId(int bbsId) {
		this.bbsId = bbsId;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
}
