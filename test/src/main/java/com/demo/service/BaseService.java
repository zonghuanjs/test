package com.demo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.demo.pager.Pager;

public interface BaseService<T, PK extends Serializable> {

	/**
	 * 添加模型
	 * @param model
	 * @return
	 */
	public boolean add(T model);
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	public boolean delete(PK id);
	public boolean delete(PK[] ids);
	/**
	 * 通过ID获取模型
	 * @param id
	 * @return
	 */
	public T get(PK id);
	/**
	 * 获取模型列表
	 * @param ids
	 * @return
	 */
	public List<T> get(PK[] ids);
	public List<T> getAll();
	public List<T> getAll(String orderby);
	/**
	 * 通过属性值获取模型	
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> getListFromProperty(String propertyName, Object value);
	public List<T> getList(String propertyName, Object value, String orderby);
	public List<T> getList(Map<String, Object> filter);
	public List<T> getList(Map<String, Object> filter, String orderby);
	
	/**
	 * 更新模型
	 * @param model
	 * @return
	 */
	public boolean update(T model);
	
	/**
	 * 刷新实体
	 * @param model
	 */
	public void refreshObject(T model);
	
	/**
	 * 获取记录数
	 * @return
	 */
	public long getCount();
	public long getCount(String propertyName, Object value);
	public long getCount(Map<String, Object> filter);
	
	/**
	 * 通过Pager查找数据
	 * @param pager
	 * @return
	 */
	public List<T> findByPager(Pager<T> pager);
}
