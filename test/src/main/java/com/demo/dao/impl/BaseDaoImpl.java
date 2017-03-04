package com.demo.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.demo.dao.BaseDao;
import com.demo.pager.Pager;


@Repository
@Transactional
public abstract class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK>
{
	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/**
	 * 实体类类型
	 */
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl()
	{
		this.entityClass = null;
		Class<?> cs = this.getClass();
		Type type = cs.getGenericSuperclass();
		if(type instanceof ParameterizedType)
		{
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
		}
	}
	
	@Autowired
	public void setHibernateSessionFactory(SessionFactory factory)
	{
		super.setSessionFactory(factory);
	}
	
	
	/**
	 * 获取实体类
	 * @return
	 */
	protected Class<T> entityClass()
	{
		return this.entityClass;
	}
	
	@Override
	public boolean add(T model)
	{
		try
		{
			if(model != null)
			{
				super.getHibernateTemplate().save(model);
			}
			else
			{
				logger.error("实体对象为空, 放弃保存");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(PK id)
	{
		try
		{
			T model = (T)get(id);
			if(model != null)
			{
				super.getHibernateTemplate().delete(model);
			}
			else
			{
				logger.error("无法删除不存在的实体");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(PK[] ids)
	{
		try
		{
			for(PK id : ids){
				delete(id);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public T get(PK id)
	{
		T model = null;
		try
		{			
			if(id != null)
			{
				model = super.getHibernateTemplate().get(entityClass, id);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return model;
	}
	
	@Override
	public List<T> get(PK[] ids)
	{
		List<T> list = new LinkedList<T>();
		try
		{
			for(PK id : ids){
				T model = get(id);
				if(model != null){
					list.add(model);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<T> getAll()
	{
		return this.getList(null,null);
	}
	
	@Override
	public List<T> getAll(String orderby)
	{
		return this.getList(null, orderby);
	}
	
	@Override
	public List<T> getListFromProperty(String propertyName, Object value)
	{
		Map<String, Object> filter = new HashMap<>();
		filter.put(propertyName, value);
		return this.getList(filter, null);
	}
	
	@Override
	public List<T> getList(String propertyName, Object value, String orderby)
	{
		Map<String, Object> filter = new HashMap<>();
		filter.put(propertyName, value);		
		return this.getList(filter, orderby);
	}

	@Override
	public boolean update(T model)
	{
		try
		{
			if(model != null)
			{
				super.getHibernateTemplate().merge(model);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void refreshObject(T model)
	{
		try
		{
			if(model != null)
			{
				super.getHibernateTemplate().refresh(model);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public long getCount()
	{		
		return this.getCount(null);
	}

	@Override
	public long getCount(String propertyName, Object value)
	{
		Map<String, Object> filter = new HashMap<>();
		filter.put(propertyName, value);
		return this.getCount(filter);
	}
	
	@Override
	public long getCount(Map<String, Object> filter)
	{
		Session session = null;
		long count = 0;
		try
		{
			session = this.currentSession();
			Criteria query = session.createCriteria(this.entityClass);
			query.setProjection(Projections.rowCount());
			if(filter != null)
			{
				this.setEQCondition(query, filter);
			}
			count = ((Number)query.uniqueResult()).longValue();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<T> getList(Map<String, Object> filter)
	{
		return this.getList(filter, null);
	}
	
	@Override
	public List<T> getList(Map<String, Object> filter, String orderby)
	{
		return this.getList(filter, orderby, 0, 0);
	}
	
	/**
	 * 检索数据
	 * @param filter 等值过滤条件
	 * @param orderby 排序条件
	 * @param first 第一条数据
	 * @param count 数量
	 * @return
	 */
	protected List<T> getList(Map<String, Object> filter, String orderby, int first, int count)
	{
		return this.getList(filter, null, orderby, first, count);
	}
	
	/**
	 * 检索数据
	 * @param filter 等值过滤条件
	 * @param orderby 排序条件
	 * @param propertyName 
	 * @param lo 
	 * @param hi 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getList(Map<String, Object> filter, String orderby ,String  propertyName , Object lo , Object hi)
	{
		Session session = null;
		List<T> list = new LinkedList<>();
		try
		{
			session = this.currentSession();
			Criteria query = session.createCriteria(this.entityClass);
			//等值条件
			if(filter != null)
			{
				this.setEQCondition(query, filter);
			}
			//排序条件
			if(orderby != null && orderby.length() > 0)
			{
				this.setOrderCondition(query, orderby);
			}
			
			if(lo != null && hi != null && propertyName != null)
			{
				this.setBetweenCondition(query, propertyName, lo, hi);
			}
			list.addAll(query.list());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 检索数据
	 * @param filter 等值过滤条件
	 * @param orderby 排序条件
	 * @param first 第一条数据
	 * @param count 数量
	 * @param inSet 过滤集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getList(Map<String, Object> filter, Map<String, Set<Object> > inSet, String orderby, int first, int count)
	{
		Session session = null;
		List<T> list = new LinkedList<>();
		try
		{
			session = this.currentSession();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);			
			Criteria query = detachedCriteria.getExecutableCriteria(session);
			//等值条件
			if(filter != null)
			{
				this.setEQCondition(query, filter);
			}
			//排序条件
			if(orderby != null && orderby.length() > 0)
			{
				this.setOrderCondition(query, orderby);
			}
			//集合筛选
			if(inSet != null)
			{
				this.setInCondition(query, inSet);
			}
			if(first > 0)
				query.setFirstResult(first);
			if(count > 0)
				query.setMaxResults(count);
			list.addAll(query.list());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询类
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPager(Pager<T> pager)
	{
		Session session = null;
		List<T> list = new LinkedList<>();
		try
		{
			session = this.currentSession();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
			Criteria query = detachedCriteria.getExecutableCriteria(session);
			
			//等值条件
			if(pager.getFilter() != null && !pager.getFilter().isEmpty())
			{
				this.setEQCondition(query, pager.getFilter());
			}
			//模糊查询
			if(pager.getLikes() != null && !pager.getLikes().isEmpty())
			{
				this.setLikeCondition(query, pager.getLikes());
			}
			//排序条件
			if(pager.getOrderby() != null && pager.getOrderby().length() > 0)
			{
				this.setOrderCondition(query, pager.getOrderby());
			}
			//集合筛选
			if(pager.getInset() != null && !pager.getInset().isEmpty())
			{
				this.setInCondition(query, pager.getInset());
			}
			
			//查询数量
			query.setProjection(Projections.rowCount());
			pager.setTotalCount(((Number)query.uniqueResult()).longValue());
			
			query.setProjection(null);
			query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			
			if(pager.getFirst() > 0)
				query.setFirstResult(pager.getFirst());
			if(pager.getPageSize() > 0)
				query.setMaxResults(pager.getPageSize());
			list.addAll(query.list());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 以下是工具方法
	 */
	
	/**
	 * 设置等值条件
	 * @param query    查询
	 * @param filter   等值条件
	 */
	protected void setEQCondition(Criteria query, Map<String, Object> filter)
	{
		if(query == null || filter == null)
			return;
		
		if(filter.isEmpty())
			return;
		
		//设置条件
		Set<String> keys = filter.keySet();
		for(String key : keys)
		{
			if(filter.get(key) != null)
			{
				query.add(Restrictions.eq(key, filter.get(key)));
			}
			else
			{
				query.add(Restrictions.isNull(key));
			}
		}
	}
	
	/**
	 * 设置模糊查询条件
	 * @param query		查询
	 * @param filter	模糊条件
	 */
	protected void setLikeCondition(Criteria query, Map<String, Object> filter)
	{
		if(query == null || filter == null)
			return;
		
		if(filter.isEmpty())
			return;
		
		//设置模糊条件
		Set<String> keys = filter.keySet();
		for(String key : keys)
		{
			if(filter.get(key) != null)
				query.add(Restrictions.like(key, filter.get(key) + "", MatchMode.ANYWHERE));
		}
	}

	/**
	 * 设置查询排序字段
	 * @param query		查询
	 * @param orderBy	排序条件
	 */
	protected void setOrderCondition(Criteria query, String orderBy)
	{
		if(query == null || StringUtils.isEmpty(orderBy))
			return;
		
		//设置排序字段
		String[] orders = orderBy.split(",");
		for(String order : orders)
		{
			Order propertyOrder = null;
			String orderStr = order.trim();
			int idx = orderStr.indexOf(' ');
			if(idx > 0)
			{
				String property = orderStr.substring(0, idx);
				String by = orderStr.substring(idx + 1);
				if(by.equals("asc"))
					propertyOrder = Order.asc(property);
				else if(by.equals("desc"))
					propertyOrder = Order.desc(property);
			}
			if(propertyOrder != null)
			{
				query.addOrder(propertyOrder);
			}
		}
	}

	/**
	 * 设置查询In条件集合
	 * @param query		查询
	 * @param inset		IN条件
	 */
	protected void setInCondition(Criteria query, Map<String, Set<Object> > inset)
	{
		if(query == null || inset == null)
			return;
		
		if(inset.isEmpty())
			return;
		
		//设置集合条件
		Set<String> keys = inset.keySet();
		for(String key : keys)
		{
			Set<Object> sets = inset.get(key);
			if(sets != null && !sets.isEmpty())
			{
				query.add(Restrictions.in(key, sets));
			}
		}
	}
	
	protected void setBetweenCondition(Criteria query,String  propertyName , Object lo , Object hi)
	{
		if(query == null || lo == null || hi == null || propertyName == null)
			return;
		if(lo != null && hi != null && propertyName != null)
		{
			query.add(Restrictions.between(propertyName, lo, hi));
		}
		
	}
}
