package com.model2.mvc.service.user;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


public interface UserDao {
	
	// INSERT
	public void insertUser(User user) throws Exception;

	// SELECT ONE
	public User findUser(String userId) throws Exception;

	// SELECT LIST
	public List<User> getUserList(Search search) throws Exception;

	// UPDATE
	public void updateUser(User vo) throws Exception;
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;
	
}