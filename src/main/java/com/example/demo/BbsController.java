package com.example.demo;

import java.time.LocalDateTime;
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
public class BbsController {
	
	@Autowired
	HttpSession session;

	@Autowired
	BookDao bookDao;
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	BbsDao bbsDao;
	
	@RequestMapping(value = "/chat" ,method = RequestMethod.POST)
	public ModelAndView chat(
			@RequestParam("message")String message,
			@RequestParam("name")String name,
			@RequestParam("stockId")String stockId,
			@RequestParam("sellerId")String sellerId,
			ModelAndView mv
			) {
		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss");
		String post_date = d.format(dateTimeFormatter);
		
		String mail = (String) session.getAttribute("memberEmail");
		String contributorId = memberDao.findIdByEmail(mail);
		
		if(contributorId.equals(sellerId)) {
			name = "出品者";
		}
		
		bbsDao.bbsInsert(message, name,contributorId,post_date,stockId);
		
		List<Bbs>bbsList = bbsDao.findByStockId(stockId);
		List<Book> bookList = bookDao.findByStockId(Integer.parseInt(stockId));
		
		mv.addObject("bbsList",bbsList);
		mv.addObject("bookList",bookList);
		mv.setViewName("book_detail");

		return mv;
		
	}
	

}
