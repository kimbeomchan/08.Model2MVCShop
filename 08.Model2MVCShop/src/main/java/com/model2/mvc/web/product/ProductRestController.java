package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
			
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/addProduct", method=RequestMethod.GET )
	public Product addProductGET( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/product/json/addProduct : GET");
		
		productService.addProduct(product);
		
		return product;
	}
	
	@RequestMapping( value="json/addProduct", method=RequestMethod.POST )
	public Product addProductPOST( @RequestBody Product product ) throws Exception {

		System.out.println("/product/json/addProduct : POST");
		
		productService.addProduct(product);
		
		return product;
	}
	
	@RequestMapping( value="json/getProduct", method=RequestMethod.GET )
	public Product getProductGET( @RequestParam("prodNo") int prodNo , HttpSession session ) throws Exception {
		
		System.out.println("/product/json/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		return product;
	}
	
	@RequestMapping( value="json/getProduct", method=RequestMethod.POST )
	public Product getProductPOST( @RequestBody Product product , HttpSession session ) throws Exception {
		
		System.out.println("/product/json/getProduct : POST");
		//Business Logic
		product = productService.getProduct(product.getProdNo());
		
		return product;
	}
	
	@RequestMapping( value="json/listProduct", method=RequestMethod.GET )
	public Map<String, Object> listProductGET( @RequestParam("menu") String menu,
							    				@ModelAttribute("search") Search search, 
							    				HttpServletRequest request) throws Exception{
		
		System.out.println("/product/json/listProduct : GET");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		// Business logic 수행
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		return map;
	}
	
	
	@RequestMapping( value="json/listProduct", method=RequestMethod.POST )
	public Map<String , Object> listProductPOST( @RequestBody Search search, 
													HttpServletRequest request) throws Exception{
		
		System.out.println("/product/json/listProduct : POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		// Business logic 수행
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		return map;
	}
	
	@RequestMapping( value="json/updateProductView", method=RequestMethod.GET )
	public Product updateProductViewGET( @RequestParam("prodNo") int prodNo, 
									 @RequestParam(value="menu", required=false) String menu) throws Exception{

		System.out.println("/product/json/updateProductView : GET");
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		System.out.println("/product/json/updateProductView :: " + product);
		
		return product;
	}
	
	@RequestMapping( value="json/updateProductView", method=RequestMethod.POST )
	public Product updateProductViewPOST( @RequestBody Product product, 
									 		@RequestParam(value="menu", required=false) String menu) throws Exception{

		System.out.println("/product/json/updateProductView : POST");
		
		//Business Logic
		product = productService.getProduct(product.getProdNo());
		
		System.out.println("/product/json/updateProductView :: " + product);
		
		return product;
	}
	
	

	@RequestMapping( value="json/updateProduct", method=RequestMethod.GET )
	public Product updateProductGET( @ModelAttribute("product") Product product) throws Exception{

		System.out.println("/product/json/updateProduct : GET");
		
		//Business Logic
		productService.updateProduct(product);
		product = productService.getProduct(product.getProdNo());
		
		return product;
	}
	
	@RequestMapping( value="json/updateProduct", method=RequestMethod.POST )
	public Product updateProductPOST( @RequestBody Product product) throws Exception{

		System.out.println("/product/json//updateProduct : POST");
		
		//Business Logic
		productService.updateProduct(product);
		product = productService.getProduct(product.getProdNo());
		
		return product;
	}

}
