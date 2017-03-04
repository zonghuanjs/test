package com.demo.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.dao.BaseDao;
import com.demo.pager.Pager;
import com.demo.service.BaseService;

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK>{

	@Autowired
	private BaseDao<T, PK> dao;
	
	@Override
	public boolean add(T model) {
		// TODO Auto-generated method stub
		return dao.add(model);
	}

	@Override
	public boolean delete(PK id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(PK[] ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> get(PK[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll(String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getListFromProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(String propertyName, Object value, String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Map<String, Object> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList(Map<String, Object> filter, String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(T model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refreshObject(T model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCount(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCount(Map<String, Object> filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> findByPager(Pager<T> pager) {
		// TODO Auto-generated method stub
		return null;
	}

}
