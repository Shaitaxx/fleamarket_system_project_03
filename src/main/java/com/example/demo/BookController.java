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
public class BookController {

	@Autowired
	HttpSession session;

	@Autowired
	BookDao bookDao;
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	BbsDao bbsDao;

	//教科書検索画面表示
	@RequestMapping("/searchTop")
	public ModelAndView searchTop(ModelAndView mv) {
		List<Book> bookList = bookDao.findAll();
		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList = memberDao.findByEmail(mail);
		mv.addObject("memberList", memberList);
		mv.addObject("bookList", bookList);
		mv.setViewName("book_search");
		return mv;
	}
		
	//教科書検索機能
	@RequestMapping("/search")
	public ModelAndView findByBooks(
			@RequestParam("title_find") String title_find,
			@RequestParam("author_find") String author_find,
			@RequestParam("category_find") String category_find,
			ModelAndView mv) {
		
		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}
		
		List<Book> bookList;
		bookList = bookDao.findByBooks(title_find, author_find, category_find);
		
		mv.addObject("bookList", bookList);
		mv.setViewName("book_search");
		return mv;
	}
	
	//教科書出品画面表示
	@RequestMapping(value = "/insertTop",method = RequestMethod.POST)
	public String insertTop() {
	    return "listing";
	}
	
	@RequestMapping("/insertConfirm")
	public ModelAndView confirm(
			@RequestParam(name = "bookcode") String bookcode,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "author") String author,
			@RequestParam(name = "category") String category,
			@RequestParam(name = "looklike") String looklike,
			@RequestParam(name = "sellingprice") String sellingprice,
			@RequestParam(name = "comment") String comment,
			ModelAndView mv) {
		
//		エラーメッセージ
		int insertErrorMessage = 0;
		
//		タイトルの未入力チェック
		if(title==null || title.length()==0) { 
			mv.addObject("titlemessage", "タイトルを入力してください");
			insertErrorMessage =+ 1;
		}
		
//		カテゴリの未入力チェック
		if(category==null || category.length()==0 || category.equals("0")) { 
			mv.addObject("categorymessage", "カテゴリを入力してください");
			insertErrorMessage =+ 1;
		}
		
//		販売価格の未入力チェック
		if(sellingprice==null || sellingprice.length()==0) { 
			mv.addObject("sellingpricemessage", "販売価格を入力してください");
			insertErrorMessage =+ 1;
		}
		
//		著者の未入力チェック
		if(author==null || author.length()==0) { 
			mv.addObject("authormessage", "著者を入力してください");
			insertErrorMessage =+ 1;
		}
		
//		状態の未入力チェック
		if(looklike==null || looklike.equals("0")) { 
			mv.addObject("looklikemessage", "状態を選択してください");
			insertErrorMessage =+ 1;
		}
		
//		ISBN番号の未入力チェック
		if(bookcode==null || bookcode.length()==0) { 
			mv.addObject("bookcodemessage", "ISBN番号を入力してください");
			insertErrorMessage =+ 1;
		}
		
//		コメントの未入力チェック
		if(comment==null || comment.length()==0) { 
			mv.addObject("commentmessage", "コメントを入力してください");
			insertErrorMessage =+ 1;
		}
		
//		エラーメッセージ・入力していたデータを表示
		if(insertErrorMessage != 0) {
			mv.addObject("title",title);
			mv.addObject("category",category);
			mv.addObject("sellingprice",sellingprice);
			mv.addObject("author",author);
			mv.addObject("looklike",looklike);
			mv.addObject("bookcode",bookcode);
			mv.addObject("comment",comment);
			
			mv.setViewName("listing");
			return mv;
		}
		
		mv.addObject("bookcode", bookcode);
		mv.addObject("title", title);
		mv.addObject("author", author);
		mv.addObject("category", category);
		mv.addObject("looklike", looklike);
		mv.addObject("sellingprice", sellingprice);
		mv.addObject("comment", comment);
		String mail = (String) session.getAttribute("memberEmail");
		List<Member> memberList = memberDao.findByEmail(mail);
		mv.addObject("memberList", memberList);
		mv.setViewName("listing_confirmation");

		return mv;
	}
	
	//教科書出品機能　
	@RequestMapping("/insert")
	  public ModelAndView insert(
	      @RequestParam("bookcode") String bookcode,
	      @RequestParam("title") String title,
	      @RequestParam("author") String author,
	      @RequestParam("category") String category,
	      @RequestParam("looklike") String looklike,
	      @RequestParam("sellingprice") String sellingprice,
	      @RequestParam("comment") String comment,
	      @RequestParam("sellerid") String sellerid,
	      ModelAndView mv) {
	    bookDao.insert(bookcode, title, author, category, looklike, sellingprice, comment, sellerid);
	    mv.setViewName("listing_completion");
	    return mv;
	  }
	
	//検索画面→教科書詳細画面
	@RequestMapping("/detail")
	public ModelAndView detail(
			@RequestParam("stockid") int stockid,
			ModelAndView mv) {
		

		//ログインしていない場合画面推移
		String loginmail = (String) session.getAttribute("memberEmail");
		if(loginmail == null) {
			mv.setViewName("login");
			return mv;
		}

		List<Book> bookList = bookDao.findByStockId(stockid);
		List<Bbs>bbsList = bbsDao.findByStockId(Integer.toString(stockid));
		mv.addObject("bbsList",bbsList);
		mv.addObject("bookList",bookList);
		mv.setViewName("book_detail");
		return mv;
	}

	
}