package com.demo.dao;

import com.demo.entity.Article;

/**
 * 文章数据访问接口
 * 
 */
public interface ArticleDao
{

	Article getObject(Long id);
}
