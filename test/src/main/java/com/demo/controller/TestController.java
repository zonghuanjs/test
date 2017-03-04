package com.demo.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.entity.ArticleCategory;
import com.demo.service.ArticleCategoryService;


@Controller 
public class TestController
{

	@Resource
	private ArticleCategoryService articleCategoryService;
	
	@RequestMapping(value="index.do")  
    public ModelAndView index(){  
		ModelAndView mv = new ModelAndView("/index");
		ArticleCategory articleCategory = new ArticleCategory();
		//articleCategory = this.articleCategoryService.getObject(3L);
		System.out.println(articleCategory.getName());
		mv.addObject("name",articleCategory.getName());
		return mv;
    }  
}
