package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	HttpSession session;
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	BookDao bookDao;
	
//	マイページに登録情報表示
	@RequestMapping(value = "/mypage",method = RequestMethod.POST)
	public ModelAndView mypage(
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
				String loginmail = (String) session.getAttribute("memberEmail");
				if(loginmail == null) {
					mv.setViewName("login");
					return mv;
				}
		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList;
		memberList = memberDao.findByEmail(mail);
		mv.addObject("memberList", memberList);
		mv.setViewName("mypage");
		return mv;
		
	}
	
//	アカウント情報取得
	@RequestMapping("/mypageEdit")
	public ModelAndView edit(
			@RequestParam("memberId") int memberId,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberAddress") String memberAddress,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberBirthday") String memberBirthday,
			@RequestParam("memberPassward") String memberPassward,
			@RequestParam("memberSignupDate") String memberSignupDate,
			@RequestParam("memberStudentId") String memberStudentId,
			ModelAndView mv) {
		
		mv.addObject("memberId",memberId);
		mv.addObject("memberName",memberName);
		mv.addObject("memberAddress",memberAddress);
		mv.addObject("memberPhone",memberPhone);
		mv.addObject("memberEmail",memberEmail);
		mv.addObject("memberBirthday",memberBirthday);
		mv.addObject("memberPassward",memberPassward);
		mv.addObject("memberSignupDate",memberSignupDate);
		mv.addObject("memberStudentId",memberStudentId);
		
		mv.setViewName("member_info_edit");
		
		return mv;
		
	}
	
//	アカウント情報変更確認
	@RequestMapping("/mypageEditConfirmation")
	public ModelAndView editConfirmation(
			@RequestParam("memberId") int memberId,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberAddress") String memberAddress,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberBirthday") String memberBirthday,
			@RequestParam("memberPassward") String memberPassward,
			@RequestParam("memberSignupDate") String memberSignupDate,
			@RequestParam("memberStudentId") String memberStudentId,
			ModelAndView mv) {

		
		int ErrorMessage = 0;
		
		if(memberName == null || memberName.length() == 0
			||memberAddress == null || memberAddress.length() == 0
			||memberPhone == null || memberPhone.length() == 0
			||memberEmail == null || memberEmail.length() == 0) {
			mv.addObject("nullMsg","未入力の項目があります。変更内容を入力してください。");
			ErrorMessage =+ 1;
		}
		
		if(memberPhone.length() != 13) {
			mv.addObject("phoneLengthMsg","電話番号を正しくご入力ください（例「111-1111-1111」）");
			ErrorMessage =+ 1;
		}
		
		if(ErrorMessage != 0) {
		mv.addObject("memberId",memberId);
		mv.addObject("memberBirthday",memberBirthday);
		mv.addObject("memberPassward",memberPassward);
		mv.addObject("memberSignupDate",memberSignupDate);
		mv.addObject("memberStudentId",memberStudentId);
		mv.addObject("memberAddress",memberAddress);
		mv.addObject("memberPhone",memberPhone);
		mv.addObject("memberName",memberName);
		mv.addObject("memberEmail",memberEmail);
		mv.setViewName("member_info_edit");
		return mv;
		}
		
		mv.addObject("memberId",memberId);
		mv.addObject("memberBirthday",memberBirthday);
		mv.addObject("memberPassward",memberPassward);
		mv.addObject("memberSignupDate",memberSignupDate);
		mv.addObject("memberStudentId",memberStudentId);
		mv.addObject("memberAddress",memberAddress);
		mv.addObject("memberPhone",memberPhone);
		mv.addObject("memberName",memberName);
		mv.addObject("memberEmail",memberEmail);
		mv.setViewName("member_info_edit_confirmation");
		return mv;
		
	}
	
//	アカウント情報変更
	@RequestMapping("/mypageUpdate")
	public ModelAndView update(
			@RequestParam("memberId") int memberId,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberAddress") String memberAddress,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			ModelAndView mv) {
		
		memberDao.update(memberId,memberName,memberAddress,memberPhone,memberEmail); 
		mv.addObject("memberList",memberDao.findAll());
		mv.setViewName("member_info_edit_completion");
		return mv;
	}
	
//	アカウント削除確認画面
	@RequestMapping(value = "/deleteConfirmation",method = RequestMethod.POST)
	public ModelAndView deleteConfirm(
			@RequestParam("memberId")int memberId,
			ModelAndView mv) {
		List<Member> memberList = memberDao.findById(memberId);
		mv.addObject("memberList", memberList);
		mv.setViewName("delete_confirmation");
		return mv;
		
	}
	
//	アカウント削除
	@RequestMapping(value = "memberDelete", method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam("memberId")int memberId,
			ModelAndView mv){
		memberDao.delete(memberId);
		mv.setViewName("member_delete");
		return mv;
	}
	
	//ログイン画面表示
	@RequestMapping("/")
	public String login() {
		session.invalidate();
	    return "login";
	  }
	
	//ログイン機能
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberPassward") String memberPassward,
			ModelAndView mv
			){
		
		//エラーメッセージ
		int loginErrorMessage = 0;
		
		//emailとpasswardの一致チェック
		List<Member> memberList;
		memberList = memberDao.findByLogin(memberEmail, memberPassward);
		if (memberList == null) {
			mv.addObject("emailmessage", "メールアドレスとパスワードが一致していません");
			loginErrorMessage =+ 1;
		}
				
		//emailの未入力チェック
		if (memberEmail == null || memberEmail.length() == 0) {
			mv.addObject("emailmessage", "メールアドレスを入力してください");
			loginErrorMessage =+ 1;
		}
		//passwardの未入力チェック
		if (memberPassward == null || memberPassward.length() == 0) {
			mv.addObject("passwardmessage", "パスワードを入力してください");
			loginErrorMessage =+ 1;
		}
				
		//エラーメッセージ・入力していたデータを表示
		if(loginErrorMessage != 0) {
			mv.addObject("memberEmail",memberEmail);
			mv.addObject("memberPassward",memberPassward);
			
			mv.setViewName("login");
			return mv;
		}
		
		//ログイン情報をセッションスコープに格納
		session.setAttribute("memberEmail", memberEmail);
		session.setAttribute("memberPassward", memberPassward);
		
		mv.addObject("memberEmail", memberEmail);
		mv.addObject("memberPassward", memberPassward);
		
		//ユーザー名表示
		List<Member> memberList1 = memberDao.findByEmail(memberEmail);
		mv.addObject("memberList", memberList1);
		
		//一覧表示処理
		List<Book> bookList = bookDao.findAll();
		mv.addObject("bookList", bookList);
		
		mv.setViewName("book_search");
		return mv;
	}
	
	//新規登録画面表示
	@RequestMapping("/signupInput")
	  public ModelAndView addInfo(ModelAndView mv) {
		LocalDate d = LocalDate.now();
		DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String signup_date = d.format(dateTimeFormatter);
		mv.addObject("memberSignupDate", signup_date);
		mv.setViewName("signup");
	    return mv;
	}
	
	
//	新規アカウント登録情報保存
	@RequestMapping("/signup")
	  public ModelAndView insert(
		@RequestParam("memberName") String memberName,
		@RequestParam("memberAddress") String memberAddress,
		@RequestParam("memberPhone") String memberPhone,
		@RequestParam("memberEmail") String memberEmail,
		@RequestParam("memberBirthday") String memberBirthday,
		@RequestParam("memberPassward") String memberPassward,
		@RequestParam("memberSignupDate") String memberSignupDate,
		@RequestParam("memberStudentId") String memberStudentId,
	    ModelAndView mv) {
		
		//エラーメッセージ
		int signupErrorMessage = 0;
		
		//名前の未入力チェック
		if(memberName==null || memberName.length()==0) { 
			mv.addObject("nameMessage", "名前を入力してください");
			signupErrorMessage =+ 1;
		}
		
		//住所の未入力チェック
		if(memberAddress==null || memberAddress.length()==0) { 
			mv.addObject("addressMessage", "住所を入力してください");
			signupErrorMessage =+ 1;
		}
		
		//電話番号の未入力チェック
		if(memberPhone==null || memberPhone.length()==0) { 
			mv.addObject("phoneMessage", "電話番号を入力してください");
			signupErrorMessage =+ 1;
		}
		
		//メールアドレスの未入力チェック
		if(memberEmail == null || memberEmail.length() == 0) { 
			mv.addObject("emailMessage", "メールアドレスを入力してください");
			signupErrorMessage =+ 1;
		}
		
		//生年月日の未入力チェック
		if(memberBirthday==null || memberBirthday.length()==0) { 
			mv.addObject("birthdayMessage", "生年月日を入力してください");
			signupErrorMessage =+ 1;
		}
		
		//パスワードの未入力チェック
		if(memberPassward==null || memberPassward.length()==0) { 
			mv.addObject("passwardMessage", "パスワードを入力してください");
			signupErrorMessage =+ 1;
		}
		
		//学籍番号の未入力チェック
		if(memberStudentId==null || memberStudentId.length()==0) { 
			mv.addObject("studentidMessage", "学籍番号を入力してください");
			signupErrorMessage =+ 1;
		}
		
		//メールアドレスの一致チェック
		if(memberEmail!=null &&  memberEmail.length() != 0) {
			List<Member> memberList;
			memberList = memberDao.findByEmail(memberEmail);
			if(memberList != null) {
				mv.addObject("emailMessage", "このメールアドレスは既に登録されています");
				signupErrorMessage =+ 1;
			}
		}
		
		//エラーメッセージ・入力していたデータを表示
		if(signupErrorMessage != 0) {
			mv.addObject("memberName",memberName);
			mv.addObject("memberAddress",memberAddress);
			mv.addObject("memberPhone",memberPhone);
			mv.addObject("memberEmail",memberEmail);
			mv.addObject("memberBirthday",memberBirthday);
			mv.addObject("memberPassward",memberPassward);
			mv.addObject("memberStudentId",memberStudentId);
			
			mv.setViewName("signup");
			return mv;
		}
		
	    memberDao.insert(
	    		memberName,memberAddress,memberPhone,memberEmail,memberBirthday,
	    		memberPassward,memberSignupDate,memberStudentId
	    		);
	    
		session.setAttribute("memberEmail", memberEmail);
		session.setAttribute("memberPassward", memberPassward);
		
		mv.addObject("memberEmail", memberEmail);
		mv.addObject("memberPassward", memberPassward);
		
		//ユーザー名表示処理
		List<Member> memberList1;
		memberList1 = memberDao.findByEmail(memberEmail);
		mv.addObject("memberList", memberList1);
		
		//一覧表示処理
		List<Book> bookList = bookDao.findAll();
		mv.addObject("bookList", bookList);
		mv.setViewName("book_search");
		return mv;
	  }
	
//	購入ボタン→決済情報入力画面
	@RequestMapping("/purchase")
	public ModelAndView purchase(
			@RequestParam("stockid") int stockid,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}

		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList = memberDao.findByEmail(mail);
		List<Book>bookList = bookDao.findByStockId(stockid);
		
		mv.addObject("stockid", stockid);
		mv.addObject("memberList", memberList);
		mv.addObject("bookList", bookList);
		
		mv.setViewName("purchase");
		return mv;
	}
	
//	決済情報入力画面→決済確認画面
	@RequestMapping(value = "/purchaseConfirmation",method = RequestMethod.POST)
	public ModelAndView purchaseConfirmation(			
			@RequestParam("card_number") String card_number,
			@RequestParam("expiration_date") String expiration_date,
			@RequestParam("card_holder") String card_holder,
			@RequestParam("security_code") String security_code,
			@RequestParam("purchaserid") int purchaserid,
			@RequestParam("stockid") int stockid,
			ModelAndView mv) {
		
		
		int ErrorMessage = 0;
		
		if(card_number == null || card_number.length() == 0
			||expiration_date == null || expiration_date.length() == 0
			||card_holder == null || card_holder.length() == 0
			||security_code == null || security_code.length() == 0) {
			mv.addObject("nullMsg","未入力の項目があります。カード情報を入力してください。");
			ErrorMessage =+ 1;
		}else{
			if(card_number.length() != 16) {
			mv.addObject("cardNumMsg","カード番号を正しくご入力ください。");
			ErrorMessage =+ 1;
			}
			
			if(expiration_date.length() != 5) {
				mv.addObject("dateMsg","有効期限を正しくご入力ください。");
				ErrorMessage =+ 1;
			}
			
//			else if(expiration_date.substring(2,3) != "/") {
//				mv.addObject("codeFmtMsg","セキュリティコードを正しくご入力ください。（例「11/11」）");
//				ErrorMessage =+ 1;
//			}
			
			if(security_code.length() != 3) {
				mv.addObject("codeMsg","セキュリティコードを正しくご入力ください。");
				ErrorMessage =+ 1;
			}
		}
		
		if(ErrorMessage != 0) {
			String mail = (String) session.getAttribute("memberEmail");
			List<Member> memberList = memberDao.findByEmail(mail);
			List<Book>bookList = bookDao.findByStockId(stockid);
			mv.addObject("stockid", stockid);
			mv.addObject("memberList", memberList);
			mv.addObject("bookList", bookList);
			mv.addObject("card_number", card_number);
			mv.addObject("expiration_date", expiration_date);
			mv.addObject("card_holder", card_holder);
			mv.addObject("security_code", security_code);
			mv.setViewName("/purchase");
			return mv;
		}
		
		memberDao.cardDataInsert(purchaserid,card_number,expiration_date,card_holder,security_code);
		
		mv.addObject("card_number", card_number);
		mv.addObject("expiration_date", expiration_date);
		mv.addObject("card_holder", card_holder);
		mv.addObject("security_code", security_code);
		List<Book>bookList = bookDao.findByStockId(stockid);
		mv.addObject("bookList", bookList);
		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList = memberDao.findByEmail(mail);
		mv.addObject("memberList", memberList);
		mv.setViewName("purchase_confirmation");
		return mv;
		
	}
	
//	決済完了とともに時間と購入者を記録してstockListへ保存、完了画面へ
	@RequestMapping(value = "/purchaseCompletion",method = RequestMethod.POST)
	public ModelAndView purchaseCompletion(
			@RequestParam("purchaserid") String purchaserid,
			@RequestParam("stockid") int stockid,
			ModelAndView mv
			) {
		LocalDate d = LocalDate.now();
		DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String trading_date = d.format(dateTimeFormatter);
		memberDao.purchaseDataInsert(purchaserid, trading_date, stockid);
		List<Book>bookList = bookDao.findByStockId(stockid);
		mv.addObject("bookList", bookList);
		mv.setViewName("purchase_completion");
		return mv;
		
	}
	
//	購入履歴閲覧
	@RequestMapping("/purchaseLog")
	public ModelAndView showPurchaseLog(
			@RequestParam("memberId") int purchaserId,
			ModelAndView mv
			) {
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}
		List<Book> bookList;
		bookList = bookDao.findByPurchaserId(purchaserId);
		mv.addObject("bookList", bookList);
		mv.setViewName("purchase_log");
		return mv;
		
	}
	
//	出品履歴閲覧
	@RequestMapping("/listingLog")
	public ModelAndView showListingLog(
			@RequestParam("memberId") int sellerId,
			ModelAndView mv
			) {
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}
		List<Book> bookList;
		bookList = bookDao.findBySellerId(sellerId);
		mv.addObject("bookList", bookList);
		mv.setViewName("listing_log");
		return mv;
		
	}
	
//	出品履歴から書籍の情報を修正
	@RequestMapping("/listingEdit")
	public ModelAndView listingEdit(
		      @RequestParam("stockid") int stockid,
		      @RequestParam("title") String title,
		      @RequestParam("author") String author,
		      @RequestParam("looklike") String looklike,
		      @RequestParam("sellingprice") String sellingprice,
		      @RequestParam("tradingdate") String tradingdate,
		      @RequestParam("purchaserid") String purchaserid,
		      @RequestParam("sellerid") String sellerid,
		      @RequestParam("comment") String comment,
			ModelAndView mv) {
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("author", author);
		mv.addObject("sellingprice", sellingprice);
		mv.addObject("looklike", looklike);
		mv.addObject("tradingdate", tradingdate);
		mv.addObject("purchaserid", purchaserid);
		mv.addObject("sellerid", sellerid);
		mv.addObject("comment", comment);
		mv.setViewName("listing_info_edit"); 
		return mv;

	}
	
//	出品した書籍の変更確認
	@RequestMapping("/listingEditConfirm")
	public ModelAndView listingEditConfirm(
		      @RequestParam("stockid") int stockid,
		      @RequestParam("title") String title,
		      @RequestParam("author") String author,
		      @RequestParam("looklike") String looklike,
		      @RequestParam("sellingprice") String sellingprice,
		      @RequestParam("tradingdate") String tradingdate,
		      @RequestParam("purchaserid") String purchaserid,
		      @RequestParam("sellerid") String sellerid,
		      @RequestParam("comment") String comment,
			ModelAndView mv
			) {
		
		int ErrorMessage = 0;
		
		if(title == null || title.length() == 0
			||author == null || author.length() == 0
			||looklike == null || looklike.length() == 0
			||sellingprice == null || sellingprice.length() == 0
			||comment == null || comment.length() == 0
			||looklike.equals("0")) {
			mv.addObject("nullMsg","未入力の項目があります。変更内容を入力してください。");
			ErrorMessage =+ 1;
		}
		
		if(ErrorMessage != 0) {
			mv.addObject("stockid", stockid);
			mv.addObject("title", title);
			mv.addObject("author", author);
			mv.addObject("looklike", looklike);
			mv.addObject("sellingprice", sellingprice);
			mv.addObject("tradingdate", tradingdate);
			mv.addObject("purchaserid", purchaserid);
			mv.addObject("sellerid", sellerid);
			mv.addObject("comment", comment);
			mv.setViewName("listing_info_edit");
			return mv;
		}
		
		mv.addObject("stockid", stockid);
		mv.addObject("title", title);
		mv.addObject("author", author);
		mv.addObject("looklike", looklike);
		mv.addObject("sellingprice", sellingprice);
		mv.addObject("tradingdate", tradingdate);
		mv.addObject("purchaserid", purchaserid);
		mv.addObject("sellerid", sellerid);
		mv.addObject("comment", comment);
		
		mv.setViewName("listing_info_edit_confirmation");
		
		return mv;
	}
	
//	出品した書籍の変更完了
	@RequestMapping("/listingEditCompletion")
	public ModelAndView listingEditCompletion(
		      @RequestParam("stockid") int stockid,
		      @RequestParam("title") String title,
		      @RequestParam("author") String author,
		      @RequestParam("looklike") String looklike,
		      @RequestParam("sellingprice") String sellingprice,
		      @RequestParam("tradingdate") String tradingdate,
		      @RequestParam("purchaserid") String purchaserid,
		      @RequestParam("sellerid") String sellerid,
		      @RequestParam("comment") String comment,
			ModelAndView mv) {
		
		bookDao.update(stockid,title,author,looklike,sellingprice,tradingdate,purchaserid,sellerid,comment);
		mv.setViewName("listing_info_edit_completion");
		return mv;
	}
	
//	コンタクトへ
	@RequestMapping("/contact")
	public String contact() {
	    return "contact";
	  }
	
	//ホームページ戻る
	@RequestMapping("backtohomepage")
	public ModelAndView backtohomepage(ModelAndView mv) {
		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList1;
		memberList1 = memberDao.findByEmail(mail);
		mv.addObject("memberList", memberList1);
		List<Book> bookList = bookDao.findAll();

		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}
				
		mv.addObject("bookList", bookList);
		mv.setViewName("book_search");
		return mv;
	}
	
}
