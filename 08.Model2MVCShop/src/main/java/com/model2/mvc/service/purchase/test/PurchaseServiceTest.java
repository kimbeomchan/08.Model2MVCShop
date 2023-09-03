package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//@Test
	public void testAddPurchase() throws Exception {
		Purchase purchase = new Purchase();
		
		Product product = productService.getProduct(10085);
		System.out.println("TEST :: AddPurchase -> product :: " + product);
		
		User user = userService.getUser("user04");
		System.out.println("TEST :: AddPurchase -> user :: " + user);

		purchase.setPurchaseProd(product);
		purchase.getPurchaseProd().setProdNo(10085);
		
		
		purchase.setBuyer(user);
		//purchase.getBuyer().set
		
		purchase.setPaymentOption("0");
		purchase.setReceiverName("AddTestName");
		purchase.setReceiverPhone("010-1234-5678");
		purchase.setDlvyAddr("AddAddrTest");
		purchase.setDlvyRequest("AddRequestTest");
		purchase.setTranCode("1");
		purchase.setDlvyDate("20230829");
		
		purchaseService.insertPurchase(purchase);
		

		//==> console 확인
		System.out.println("TEST :: AddPurchase :: " + purchase);
		
		//==> API 확인
		Assert.assertEquals(10085, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user04", purchase.getBuyer().getUserId());
		Assert.assertEquals("0", purchase.getPaymentOption());
		Assert.assertEquals("AddTestName", purchase.getReceiverName());
		Assert.assertEquals("010-1234-5678", purchase.getReceiverPhone());
		Assert.assertEquals("AddAddrTest", purchase.getDlvyAddr());
		Assert.assertEquals("AddRequestTest", purchase.getDlvyRequest());
		Assert.assertEquals("1", purchase.getTranCode());
		Assert.assertEquals("20230829", purchase.getDlvyDate());
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		Product product = productService.getProduct(10085);
		User user = userService.getUser("user04");
		Purchase purchase = purchaseService.findPurchase(10093);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		//==> console 확인
		System.out.println("TEST :: GetPurchase :: " + purchase);
		
		//==> API 확인
//		Assert.assertEquals("testUserId", user.getUserId());
//		Assert.assertEquals("testUserName", user.getUserName());
//		Assert.assertEquals("testPasswd", user.getPassword());
//		Assert.assertEquals("111-2222-3333", user.getPhone());
//		Assert.assertEquals("경기도", user.getAddr());
//		Assert.assertEquals("test@test.com", user.getEmail());
		//Assert.assertEquals(10088, product.getProdNo());
		Assert.assertEquals(10093, purchase.getTranNo());
		Assert.assertEquals(10085, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user04", purchase.getBuyer().getUserId());
		Assert.assertEquals("0", purchase.getPaymentOption().trim());
		Assert.assertEquals("AddTestName", purchase.getReceiverName());
		Assert.assertEquals("010-1234-5678", purchase.getReceiverPhone());
		Assert.assertEquals("AddAddrTest", purchase.getDlvyAddr());
		Assert.assertEquals("AddRequestTest", purchase.getDlvyRequest());
		Assert.assertEquals("1", purchase.getTranCode().trim());
		Assert.assertEquals("2023-08-28", purchase.getOrderDate().toString());
		Assert.assertEquals("2023-08-29", purchase.getDlvyDate());
		
	}
	
	//@Test
	public void testUpdateTranCode() throws Exception{
		Purchase purchase = purchaseService.findPurchase(10093);
		Product product = productService.getProduct(10085);
		
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals("1", purchase.getTranCode().trim());
		
		purchase.setPurchaseProd(product);
		purchase.getPurchaseProd().setProdNo(10085);
		purchase.setTranCode("2");
		
		purchaseService.updateTranCode(purchase);
		
		Assert.assertEquals("2", purchase.getTranCode().trim());
		System.out.println(purchase.getTranCode());
		
		
		purchase.setTranCode("1");
		
		purchaseService.updateTranCode(purchase);
		Assert.assertEquals("1", purchase.getTranCode().trim());
		System.out.println(purchase.getTranCode());
		
		Assert.assertNotNull(purchase);
		
	 }
	
	
	//@Test
		public void testUpdateProduct() throws Exception{
			
			Purchase purchase = purchaseService.findPurchase(10093);
			Assert.assertNotNull(purchase);
			
			Assert.assertEquals("0", purchase.getPaymentOption().trim());
			Assert.assertEquals("AddTestName", purchase.getReceiverName());
			Assert.assertEquals("010-1234-5678", purchase.getReceiverPhone());
			Assert.assertEquals("AddAddrTest", purchase.getDlvyAddr());
			Assert.assertEquals("AddRequestTest", purchase.getDlvyRequest());
			Assert.assertEquals("2023-08-29", purchase.getDlvyDate());
			
			purchase.setPaymentOption("1");
			purchase.setReceiverName("UpdateTestName");
			purchase.setReceiverPhone("010-8765-4321");
			purchase.setDlvyAddr("UpdateAddrTest");
			purchase.setDlvyRequest("UpdateRequestTest");
			purchase.setDlvyDate("2023-09-09");
			
			purchaseService.updatePurchase(purchase);
			
			purchase = purchaseService.findPurchase(10093);
			
			Assert.assertNotNull(purchase);
			

			purchase.setPaymentOption("0");
			purchase.setReceiverName("AddTestName");
			purchase.setReceiverPhone("010-1234-5678");
			purchase.setDlvyAddr("AddAddrTest");
			purchase.setDlvyRequest("AddRequestTest");
			purchase.setDlvyDate("20230829");
			
			Assert.assertEquals("0", purchase.getPaymentOption());
			Assert.assertEquals("AddTestName", purchase.getReceiverName());
			Assert.assertEquals("010-1234-5678", purchase.getReceiverPhone());
			Assert.assertEquals("AddAddrTest", purchase.getDlvyAddr());
			Assert.assertEquals("AddRequestTest", purchase.getDlvyRequest());
			Assert.assertEquals("20230829", purchase.getDlvyDate());
			
			
			//==> console 확인
			//System.out.println(user);
				
			//==> API 확인
//			Assert.assertEquals("1", purchase.getPaymentOption().trim());
//			Assert.assertEquals("UpdateTestName", purchase.getReceiverName());
//			Assert.assertEquals("010-8765-4321", purchase.getReceiverPhone());
//			Assert.assertEquals("UpdateAddrTest", purchase.getDlvyAddr());
//			Assert.assertEquals("UpdateRequestTest", purchase.getDlvyRequest());
//			Assert.assertEquals("2023-09-09", purchase.getDlvyDate());
		 }
		
		
		 @Test
		 public void testGetPurchaseListByPurchaseName() throws Exception{
			User user = userService.getUser("user04");
			Purchase purchase = purchaseService.findPurchase(10093);
			purchase.setBuyer(user);
			
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("user04");
		 	Map<String,Object> map = purchaseService.getPurchaseList(search, purchase.getBuyer().getUserId());
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
		 
		
		
//	 
//	@Test
//	public void testCheckDuplication() throws Exception{
//
//		//==> 필요하다면...
////		User user = new User();
////		user.setUserId("testUserId");
////		user.setUserName("testUserName");
////		user.setPassword("testPasswd");
////		user.setSsn("1111112222222");
////		user.setPhone("111-2222-3333");
////		user.setAddr("경기도");
////		user.setEmail("test@test.com");
////		
////		userService.addUser(user);
//		
//		//==> console 확인
//		System.out.println(userService.checkDuplication("testUserId"));
//		System.out.println(userService.checkDuplication("testUserId"+System.currentTimeMillis()) );
//	 	
//		//==> API 확인
//		Assert.assertFalse( userService.checkDuplication("testUserId") );
//	 	Assert.assertTrue( userService.checkDuplication("testUserId"+System.currentTimeMillis()) );
//		 	
//	}
	
	 //==>  주석을 풀고 실행하면....
//	 @Test
//	 public void testGetPurchaseListAll() throws Exception{
//		 
//	 	Search search = new Search();
//	 	User user = userService.getUser("user04");
//	 	
//	 	userId
//	 	
//	 	System.out.println(user);
//	 	
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	
//	 	
//	 	Map<String,Object> map = purchaseService.getPurchaseList(search, userId);
//	 	
//	 	List<Object> list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(3, list.size());
//	 	
//		//==> console 확인
//	 	//System.out.println(list);
//	 	
//	 	Integer totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 	
//	 	System.out.println("=======================================");
//	 	
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	search.setSearchCondition("0");
//	 	search.setSearchKeyword("user04");
//	 	map = purchaseService.getPurchaseList(search, "user04");
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(3, list.size());
//	 	
//	 	//==> console 확인
//	 	//System.out.println(list);
//	 	
//	 	totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 }
	 
//	 
//	 //@Test
//	 public void testGetPurchaseListByPurchaseNo() throws Exception{
//		 
//	 	Search search = new Search();
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	search.setSearchCondition("0");
//	 	search.setSearchKeyword("10088");
//	 	Map<String,Object> map = productService.getProductList(search);
//	 	
//	 	List<Object> list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(1, list.size());
//	 	
//		//==> console 확인
//	 	//System.out.println(list);
//	 	
//	 	Integer totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 	
//	 	System.out.println("=======================================");
//	 	
//	 	search.setSearchCondition("0");
//	 	search.setSearchKeyword(""+System.currentTimeMillis());
//	 	map = productService.getProductList(search);
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(0, list.size());
//	 	
//		//==> console 확인
//	 	//System.out.println(list);
//	 	
//	 	totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 }
	 
	 
//	 //@Test
//	 public void testGetProductListByProductPrice() throws Exception{
//		 
//	 	Search search = new Search();
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(3);
//	 	search.setSearchCondition("2");
//	 	search.setSearchKeyword("4001");
//	 	Map<String,Object> map = productService.getProductList(search);
//	 	
//	 	List<Object> list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(3, list.size());
//	 	
//		//==> console 확인
//	 	System.out.println(list);
//	 	
//	 	Integer totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 	
//	 	System.out.println("=======================================");
//	 	
//	 	search.setSearchCondition("2");
//	 	search.setSearchKeyword(""+System.currentTimeMillis());
//	 	map = productService.getProductList(search);
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(0, list.size());
//	 	
//		//==> console 확인
//	 	System.out.println(list);
//	 	
//	 	totalCount = (Integer)map.get("totalCount");
//	 	System.out.println(totalCount);
//	 }	 
}