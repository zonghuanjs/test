package com.demo.test;


import java.util.Date;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.ArticleCategory;
import com.demo.service.ArticleCategoryService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@Transactional
public class BeanViewTest
{

	@Resource
	private ArticleCategoryService service;
	@Test  
    public void testGet(){  
        
		ArticleCategory model = new  ArticleCategory();
		model.setCreateDate(new Date());
		model.setModifyDate(new Date());
		model.setName("哈哈");
		boolean b=this.service.add(model);
		System.out.println(b);
		
         
    }  
}
