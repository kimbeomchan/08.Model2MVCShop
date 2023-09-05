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
		return userService.getUser(user.getUserId());
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
		return userService.getUser(user.getUserId());
	}
	
}