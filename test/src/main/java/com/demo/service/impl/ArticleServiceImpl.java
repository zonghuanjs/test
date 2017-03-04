package com.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.demo.dao.ArticleDao;
import com.demo.entity.Article;
import com.demo.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Resource
	private ArticleDao dao;
	
	@Override
	public Article getObject(Long id) {
		// TODO Auto-generated method stub
		return dao.getObject(id);
	}

}
