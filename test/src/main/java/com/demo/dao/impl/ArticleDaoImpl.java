package com.demo.dao.impl;

import javax.annotation.Resource;

import org.hibernate.ConnectionReleaseMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.demo.dao.ArticleDao;
import com.demo.entity.Article;


/**
 * 文章数据接口实现
 * @author CaoDepeng
 * 2015-07-24
 * 
 */

@Repository
public class ArticleDaoImpl  implements ArticleDao
{

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public Article getObject(Long id)
	{
		// TODO Auto-generated method stub
		Session session = null;
		Article model = null;
		try
		{
			session = this.getSession();
			System.out.println(session);
			//model = (Article) session.get(new Article(), id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return model;
	}
	
	protected Session getSession()
	{
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
		}
		catch(HibernateException ex)
		{
			session = sessionFactory.withOptions().connectionReleaseMode(ConnectionReleaseMode.AFTER_TRANSACTION)
					.flushBeforeCompletion(true).openSession();
		}
		return session;
	}

}
