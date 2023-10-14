package com.example.demo;

public class Member {
	
	private int  memberId;
	private String memberName;
	private String address;
	private String phoneNumber;
	private String email;
	private String birthday;
	private String passward;
	private String signupDate;
	private String studentId;
	
	public Member() {
		
	}
	
	public Member(
			int memberId,	
			String memberName,
			String address,
			String phoneNumber,
			String email,
			String birthday,
			String passward,
			String signupDate,
			String studentId
			) {
		
		this.memberId = memberId;
		this.memberName = memberName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.birthday = birthday;
		this.passward = passward;
		this.signupDate = signupDate;
		this.studentId = studentId;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public String getMemberName () {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getPassward() {
		return passward;
	}
	
	public void setPassward(String passward) {
		this.passward = passward;
	}
	
	public String getSignupDate() {
		return signupDate;
	}
	
	
	public void setSignupDate(String signupDate) {
		this.signupDate = signupDate;
	}
	
	public String getStudentId() {
		return studentId;
	}
	
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
