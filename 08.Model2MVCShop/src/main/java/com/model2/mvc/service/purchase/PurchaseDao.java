package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseDao {

	// SELECT
	public Purchase findPurchase(int tranNo) throws Exception;
		

	public List<Purchase> getPurchaseList(Search search) throws Exception;


	//public HashMap<String, Object> getSaleList(Search search) throws Exception;


	public void insertPurchase(Purchase purchase) throws Exception;

	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	
	public void updateTranCode(Purchase purchase) throws Exception;
	

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;

}
