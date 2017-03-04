package com.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.demo.dao.ArticleCategoryDao;
import com.demo.entity.ArticleCategory;


/**
 * 文章分类数据接口实现
 * 2015-07-24
 * 
 */

@Repository("articleCategoryDao")
public class ArticleCategoryDaoImpl extends BaseDaoImpl<ArticleCategory, Long>implements ArticleCategoryDao
{

}
