package com.example.demo;

public class Admin {
  private int adminId;
  private String adminName;
  private String passward;
  private String email;

  public Admin(int adminId, String adminName, String passward, String email) {
    this.adminName = adminName;
    this.passward = passward;
    this.email = email;
  }
  
  public int getAdmin_id() {
    return adminId;
  }

  public String getAdmin_name() {
    return adminName;
  }

  public String getPasswardl() {
	    return passward;
	  }

  public String getEmail() {
    return email;
  }
}