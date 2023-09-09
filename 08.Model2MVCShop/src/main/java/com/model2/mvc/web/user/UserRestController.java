package com.model2.mvc.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping( value="json/addUser", method=RequestMethod.GET )
	public String addUser() throws Exception {

		System.out.println("/user/json/addUser : GET=======================================");
		
		return "redirect:/user/addUserView.jsp";
	}
	
	// addUser
	@RequestMapping( value="json/addUser", method=RequestMethod.POST )
	public User adduser( @RequestBody User user,
									HttpSession session ) throws Exception{
										
		System.out.println("/user/json/addUser : POST=======================================");
		
		userService.addUser(user);
		
		//Business Logic
		//return userService.getUser(user.getUserId());
		return user;
	}
	
	
	// getUser
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET=======================================");
		
		//Business Logic
		return userService.getUser(userId);
	}
	
	// login
	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST=======================================");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
	
	// updateUser
	@RequestMapping( value="json/updateUser", method=RequestMethod.POST )
	public User updateUser( @RequestBody User user , Model model , HttpSession session) throws Exception{

		System.out.println("/user/updateUser : POST=======================================");
		//Business Logic
		userService.updateUser(user);
		
//		String sessionId=((User)session.getAttribute("user")).getUserId();
//		if(sessionId.equals(user.getUserId())){
//			session.setAttribute("user", user);
//		}
		System.out.println("============"+userService.getUser(user.getUserId()));
		//return "redirect:/getUser.do?userId="+user.getUserId();
		return user;
	}
	
	// logout
	@RequestMapping( value="json/logout", method=RequestMethod.GET )
	public Object logoutGET( @RequestParam("userId") String userId, HttpSession session ) throws Exception{
		
		System.out.println("/user/json/logout : GET");
		
		//Object sessionAttribute = session.getAttribute("user");
		
//		User user = userService.getUser(userId);
//		
//		String sessionId = ((User) session.getAttribute("user")).getUserId();
//		
		
		session.invalidate();
		
//		if(sessionId.equals(user.getUserId())){
//			session.invalidate();
//			System.out.println("== 세션 삭제 ==if");
//		}else {
//			sessionId = "";
//		}
		
		return session;
	}
	
	//@RequestMapping("/logout.do")
	@RequestMapping( value="json/logout", method=RequestMethod.POST )
	public Object logoutPOST( @RequestBody User user, HttpSession session ) throws Exception{
			
		System.out.println("/user/json/logout : POST");
			
		Object sessionAttribute = session.getAttribute("user");
			
		if( sessionAttribute != null ) {
			session.removeAttribute("user");
		} else {
			sessionAttribute = null;
		}
			
		return sessionAttribute;
	}
	
	@RequestMapping( value="json/checkDuplication", method=RequestMethod.GET )
	public boolean checkDuplicationGET( @RequestParam(value="userId", required=false) String userId , Model model ) throws Exception{
		
		System.out.println("/user/json/checkDuplication : GET");
		System.out.println("json/checkDuplication :: " + userId);
		//Business Logic
		boolean result=userService.checkDuplication(userId);
		// Model 과 View 연결
//		model.addAttribute("result", new Boolean(result));
//		model.addAttribute("userId", userId);

		return result;
	}
	
	@RequestMapping( value="json/checkDuplication", method=RequestMethod.POST )
	public boolean checkDuplicationPOST( @RequestBody User user, String userId , Model model ) throws Exception{
		
		System.out.println("/user/json/checkDuplication : POST");
		System.out.println("json/checkDuplication :: " + user.getUserId());
		//Business Logic
		boolean result=userService.checkDuplication(user.getUserId());
		// Model 과 View 연결
//		model.addAttribute("result", new Boolean(result));
//		model.addAttribute("userId", userId);

		return result;
	}
	
	
//	@RequestMapping( value="json/listUser",  method = {RequestMethod.GET,RequestMethod.POST} )
//	public Map<String , Object> listUserGET( @ModelAttribute("search") Search search ,
//											@RequestParam(value="pageUnit", required=false) Integer pageUnit ,
//											@RequestParam(value="pageSize", required=false) Integer pageSize ,
//											Model model , HttpServletRequest request) throws Exception{
//		
//		System.out.println("/user/listUser : GET / POST");
//		
////		if(search.getCurrentPage() ==0 ){
////			search.setCurrentPage(1);
////		}
//		search.getPageSize();
//		search.getPageUnit();
////		search.setPageSize(pageSize);
//		
//		// Business logic 수행
//		Map<String , Object> map = userService.getUserList(search);
//		
//		System.out.println("GET map :: " + map + "\n\n");
//		
//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		System.out.println(resultPage);
//		
//		// Model 과 View 연결
////		model.addAttribute("list", map.get("list"));
////		model.addAttribute("resultPage", resultPage);
////		model.addAttribute("search", search);
//		
//		return map;
//	}
	
	// listUser
	@RequestMapping( value="json/listUser",  method = RequestMethod.GET )
	public Map<String , Object> listUserGET( @ModelAttribute("search") Search search ,
											@RequestParam(value="pageUnit", required=false) Integer pageUnit ,
											@RequestParam(value="pageSize", required=false) Integer pageSize ,
											Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");
		
//		if(search.getCurrentPage() ==0 ){
//			search.setCurrentPage(1);
//		}
		System.out.println("어딜까1" + pageUnit + pageSize);
		search.setPageSize(pageSize);
		System.out.println("어딜까2" + search);
		
		// Business logic 수행
		Map<String , Object> map = userService.getUserList(search);
		
		System.out.println("GET map :: " + map + "\n\n");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
//		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
//		model.addAttribute("search", search);
		
		return map;
	}
	
	@RequestMapping( value="json/listUser",  method = RequestMethod.POST )
	public Map<String , Object> listUsePOST( @RequestBody Search search ,
											Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
	
		// Business logic 수행
		Map<String , Object> map = userService.getUserList(search);
		
		System.out.println("POST map :: " + map + "\n\n");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
//		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
//		model.addAttribute("search", search);
		
		return map;
	}
	
	
}