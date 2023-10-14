package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	HttpSession session;
	
	@Autowired
	  AdminDao adminDao;
	
	@Autowired
	  BookDao bookDao;
	
	@Autowired
	  MemberDao memberDao;
	
//	ログイン画面表示
	@RequestMapping("/adminLoginTop")
	public String adminLoginTop() {
		session.invalidate();
	    return "admin_login";
	  }
	
//	ログイン機能
	@RequestMapping("loginAdmin")
	public ModelAndView loginAdmin(
			@RequestParam("email") String email,
			@RequestParam("passward") String passward,
			ModelAndView mv
		){
		
		int errorMessage = 0;
//		emailの未入力チェック
		if(email==null || email.length()==0) { 
			mv.addObject("emailmessage", "メールアドレスを入力してください");
			errorMessage =+ 1;
		}
//		passwardの未入力チェック
		if(passward==null || passward.length()==0) { 
			mv.addObject("passwardmessage", "パスワードを入力してください");
			errorMessage =+ 1;
		}
		
//		emailとpasswardの一致チェック
		if(errorMessage == 0) {
			List<Admin> adminList;
			adminList = adminDao.findByLogin(email, passward);
			if(adminList==null) {
				mv.addObject("message", "メールアドレスとパスワードが一致していません");
				errorMessage =+ 1;
			}
		}
		
//		エラーメッセージ・入力していたデータを表示
		if(errorMessage != 0) {
			mv.addObject("email",email);
			mv.addObject("passward",passward);
			
			mv.setViewName("admin_login");
			return mv;
		}
		
//		ログイン情報をセッションスコープに格納
		session.setAttribute("email", email);
		session.setAttribute("passward", passward);
		
		mv.addObject("email", email);
		mv.addObject("passward", passward);
		
		mv.setViewName("admin_top");
		return mv;
	}
	
//	新規登録画面表示
	@RequestMapping("addAdmin")
	public String AddAdmin() {
	    return "admin_signup";
	}
	
//	新規登録機能
	@RequestMapping("insertAdmin")
	public ModelAndView insertAdmin(
	    @RequestParam(name = "adminName") String adminName,
	    @RequestParam(name = "passward") String passward,
	    @RequestParam(name = "email") String email,
	    ModelAndView mv) {
		
		//エラーメッセージ
		int errorMessage = 0;
		
		//名前の未入力チェック
		if(adminName==null || adminName.length()==0) { 
			mv.addObject("adminNameMessage", "名前を入力してください");
			errorMessage =+ 1;
		}
		
		//パスワードの未入力チェック
		if(passward==null || passward.length()==0) { 
			mv.addObject("passwardMessage", "パスワードを入力してください");
			errorMessage =+ 1;
		}
		
		//メールアドレスの未入力チェック
		if(email==null || email.length()==0) { 
			mv.addObject("emailMessage", "メールアドレスを入力してください");
			errorMessage =+ 1;
		}
		
		//メールアドレスの一致チェック
		if(email!=null && email.length()!=0) {
			List<Admin> adminList;
			adminList = adminDao.findByEmail(email);
			if(adminList != null) {
				mv.addObject("emailMessage", "このメールアドレスは既に登録されています");
				errorMessage =+ 1;
			}
		}
		
		//エラーメッセージ・入力していたデータを表示
		if(errorMessage != 0) {
			mv.addObject("adminName",adminName);
			mv.addObject("passward",passward);
			mv.addObject("email",email);
			
			mv.setViewName("admin_signup");
			return mv;
		}
				
	  adminDao.insertAdmin(adminName, passward, email);
	  
	  session.setAttribute("email", email);
	  session.setAttribute("passward", passward);
		
	  mv.addObject("adminList", adminDao.findAllBookAdmin());
	  
      mv.setViewName("admin_top");
      	return mv;
	}
	
//	教科書検索画面表示
	@RequestMapping("/searchTopAdmin")
	public ModelAndView searchTopAdmin(ModelAndView mv) {
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
					
		List<Book> bookList = bookDao.findAll();
		mv.addObject("bookList", bookList);
		
		mv.setViewName("admin_book_search");
		return mv;
	}
	
//	教科書検索機能
//	※検索できない（複数パラメーターを一度に保存できない）：検索処理でエラーが出ないのでパラメーターの受け取りか出力の段階で失敗している可能性あり
	@RequestMapping("searchAdmin")
	public ModelAndView findAllBookAdmin(
			@RequestParam("title_find") String title_find,
			@RequestParam("author_find") String author_find,
			@RequestParam("category_find") String category_find,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
				
		List<Book> bookList;
		bookList = bookDao.findByBooks(title_find, author_find, category_find);
		
		mv.addObject("bookList", bookList);
		mv.setViewName("admin_book_search");
		return mv;
	}
	
//	教科書詳細画面表示
	@RequestMapping(value = "/detailAdmin")
	public ModelAndView detailAdmin(
			@RequestParam(name = "bookcode") String bookcode,
			@RequestParam(name = "stockid") int stockid,
			@RequestParam(name = "sellerid") String sellerid,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "category") String category,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "price") String price,
			@RequestParam(name = "looklike") String looklike,
			
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		mv.addObject("bookcode", bookcode);
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("category", category);
		mv.addObject("author", author);
		mv.addObject("price", price);
		mv.addObject("sellerid", sellerid);
		mv.addObject("looklike", looklike);

		mv.setViewName("admin_book_detail");
		return mv;
	}
	
//	教科書編集画面表示
	@RequestMapping(value = "/showEditAdmin")
	public ModelAndView showeditAdmin(
			@RequestParam(name = "stockid") int stockid,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "looklike") String looklike,
			@RequestParam(name = "price") String price,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("author", author);
		mv.addObject("price", price);
		mv.setViewName("admin_book_info_edit");
		return mv;
	}
	
//	教科書編集の確認
	@RequestMapping("/adminlistingEditConfirm")
	public ModelAndView adminlistingEditConfirm(
			@RequestParam(name = "stockid") int stockid,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "looklike") String looklike,
			@RequestParam(name = "price") String price,
			ModelAndView mv
			) {
		
		int ErrorMessage = 0;
		
		if(title == null || title.length() == 0
			||author == null || author.length() == 0
			||looklike == null || looklike.length() == 0
			||price == null || price.length() == 0) {
			mv.addObject("nullMsg","未入力の項目があります。変更内容を入力してください。");
			ErrorMessage =+ 1;
		}
		
		if(ErrorMessage != 0) {
			mv.addObject("stockid", stockid);
			mv.addObject("title", title);
			mv.addObject("author", author);
			mv.addObject("looklike", looklike);
			mv.addObject("price", price);
			mv.setViewName("admin_book_info_edit");
			return mv;
		}
		
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("author", author);
		mv.addObject("looklike", looklike);
		mv.addObject("price", price);
		
		mv.setViewName("admin_listing_info_edit_confirmation");
		
		return mv;
	}
	
//	教科書編集機能
	@RequestMapping(value = "/updateAdmin")
	public ModelAndView updateAdmin(
			@RequestParam(name = "stockid") int stockid,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "looklike") String looklike,
			@RequestParam(name = "price") String price,
		    @RequestParam(name = "tradingdate") String tradingdate,
		    @RequestParam(name = "purchaserid") String purchaserid,
		    @RequestParam(name = "sellerid") String sellerid,
			@RequestParam(name = "comment") String comment,
			ModelAndView mv) {
		bookDao.update(stockid,title,author,looklike,price,tradingdate,purchaserid,sellerid,comment);
		mv.addObject("bookList", bookDao.findAll());
		//※↓変更完了画面を設定する必要あり
		mv.setViewName("admin_listing_info_edit_completion");
		return mv;
	}
	
//	教科書削除画面表示
	@RequestMapping(value = "/bookdeleteconfirmationAdmin")
	public ModelAndView adminbookdeleteconfirmation(
			@RequestParam(name = "bookcode") String bookcode,
			@RequestParam(name = "stockid") int stockid,
			@RequestParam(name = "sellerid") String sellerid,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "category") String category,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "price") String price,
			@RequestParam(name = "looklike") String looklike,
			
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		mv.addObject("bookcode", bookcode);
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("category", category);
		mv.addObject("author", author);
		mv.addObject("price", price);
		mv.addObject("sellerid", sellerid);
		mv.addObject("looklike", looklike);

		mv.setViewName("admin_book_info_delete_confirmation");
		return mv;
	}
	
//	教科書削除機能
	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.GET)
	public ModelAndView deleteAdmin(
			@RequestParam(name = "stockid") int stockid,
			ModelAndView mv) {
		bookDao.delete(stockid);
		mv.addObject("bookList", bookDao.findAll());
		//※↓削除完了画面を設定する必要あり
		mv.setViewName("admin_book_info_delete_completion");
		return mv;
	}
	
//	会員検索画面表示
	@RequestMapping("/memberSearchTopAdmin")
	public ModelAndView memberSearchTopAdmin(ModelAndView mv) {
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
				
		List<Member> memberList = memberDao.findAll();
		mv.addObject("memberList", memberList);
		mv.setViewName("admin_member_search");
		return mv;
	}
	
//	会員検索機能
	@RequestMapping("memberSearchAdmin")
	public ModelAndView memberSearchAdmin(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "studentID") String studentID,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		List<Member> memberList;
		memberList = memberDao.findByMember(name, studentID);
		
		mv.addObject("memberList", memberList);
		mv.setViewName("admin_member_search");
		return mv;
	}
	
//	会員詳細画面表示
	@RequestMapping(value = "/memberdetailAdmin")
	public ModelAndView detailAdmin(
			@RequestParam(name = "id") int memberId,
			@RequestParam(name = "name") String memberName,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "phone") String phoneNumber,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "birthday") String birthday,
			@RequestParam(name = "passward") String passward,
			@RequestParam(name = "signup") String signupDate,
			@RequestParam(name = "studentId") String studentId,
			
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		mv.addObject("memberId", memberId);
		mv.addObject("memberName", memberName);
		mv.addObject("address", address);
		mv.addObject("phoneNumber", phoneNumber);
		mv.addObject("email", email);
		mv.addObject("birthday", birthday);
		mv.addObject("passward", passward);
		mv.addObject("signupDate", signupDate);
		mv.addObject("studentId", studentId);

		mv.setViewName("admin_member_detail");
		return mv;
	}
	
//	会員情報変更画面表示
	@RequestMapping(value = "/showMemberupdateAdmin",method = RequestMethod.POST)
	public ModelAndView showEditAdmin(
			@RequestParam(name = "id") int memberId,
			@RequestParam(name = "name") String memberName,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "phone") String phoneNumber,
			@RequestParam(name = "email") String email,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
		
		mv.addObject("memberId", memberId);
		mv.addObject("memberName", memberName);
		mv.addObject("address", address);
		mv.addObject("phoneNumber", phoneNumber);
		mv.addObject("email", email);
		mv.setViewName("admin_member_info_edit");
		return mv;
	}
	
//	会員情報の変更確認
	@RequestMapping("/memberupdateconfirmationAdmin")
	public ModelAndView listingEditConfirm(
			@RequestParam(name = "memberId") int memberId,
			@RequestParam(name = "memberName") String memberName,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "phoneNumber") String phoneNumber,
			@RequestParam(name = "email") String email,
			ModelAndView mv
			) {
		
		int ErrorMessage = 0;
		
		if(memberName == null || memberName.length() == 0
			||address == null || address.length() == 0
			||phoneNumber == null || phoneNumber.length() == 0
			||email == null || email.length() == 0){
			mv.addObject("nullMsg","未入力の項目があります。変更内容を入力してください。");
			ErrorMessage =+ 1;
		}
		
		if(ErrorMessage != 0) {
			mv.addObject("memberName", memberName);
			mv.addObject("address", address);
			mv.addObject("phoneNumber", phoneNumber);
			mv.addObject("email", email);
			
			mv.setViewName("admin_member_info_edit");
			return mv;
		}
		
		mv.addObject("memberId", memberId);
		mv.addObject("memberName", memberName);
		mv.addObject("address", address);
		mv.addObject("phoneNumber", phoneNumber);
		mv.addObject("email", email);
		
		mv.setViewName("admin_member_info_edit_confirmation");
		
		return mv;
	}
	
//	会員情報変更機能
	@RequestMapping(value = "/memberupdateAdmin",method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam(name = "memberId") int memberId,
			@RequestParam(name = "memberName") String memberName,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "phoneNumber") String phoneNumber,
			@RequestParam(name = "email") String email,
			ModelAndView mv) {
		
		memberDao.update(memberId,memberName,address,phoneNumber,email); 
		mv.addObject("memberList",memberDao.findAll());
		mv.setViewName("admin_member_info_edit_completion");
		return mv;
	}
	
//	アカウント削除確認画面
	@RequestMapping(value = "/admindeleteConfirmation",method = RequestMethod.POST)
	public ModelAndView deleteConfirm(
			@RequestParam("id")int memberId,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("email");
		if(loginmail == null) {
			mv.setViewName("admin_login");
			return mv;
		}
				
		List<Member> memberList = memberDao.findById(memberId);
		mv.addObject("memberList", memberList);
		mv.setViewName("admin_delete_confirmation");
		return mv;
		
	}
	
//	会員情報削除（DBから削除するだけでセッションには残っている）
	@RequestMapping(value = "/memberdeleteAdmin", method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam("memberId")int memberId,
			ModelAndView mv){
		memberDao.delete(memberId);
		mv.addObject("memberList",memberDao.findAll());
		mv.setViewName("admin_member_delete");
		return mv;
	}
	
	
//	新規登録画面表示
	@RequestMapping("/topAdmin")
	public String TopAdmin() {
	    return "admin_top";
	}
	
}
