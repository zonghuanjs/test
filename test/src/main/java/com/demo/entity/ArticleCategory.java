package com.demo.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author huan.zong
 *
 */

@Entity
@Table(name="tb_article_category")
public class ArticleCategory
{

	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;//文章分类编号
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;//创建时间
	
	@Column(name="modify_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;//修改时间
	
	@Column(name="orders")
	private int orders;// 显示顺序
	
	@Column(name="name")
	private String name;//分类名称
	
	@Column(name="seo_description")
	private String seoDescription;//SEO描述
	
	@Column(name="seo_keywords")
	private String seoKeywords;//SEO关键字
	
	@Column(name="seo_title")
	private String seoTitle;//SEO标题
	
	@Column(name="tree_path")
	private String treePath;//SEO标题
	
	@Column(name="parent")
	private Long parent;//上级分类
	
	@OneToMany(targetEntity=Article.class, mappedBy="category", fetch=FetchType.LAZY)
	@OrderBy("isTop desc")
	private List<Article> articles;//文章列表

	public ArticleCategory()
	{
		this.articles = new LinkedList<>();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getModifyDate()
	{
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	public int getOrders()
	{
		return orders;
	}

	public void setOrders(int orders)
	{
		this.orders = orders;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSeoDescription()
	{
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription)
	{
		this.seoDescription = seoDescription;
	}

	public String getSeoKeywords()
	{
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords)
	{
		this.seoKeywords = seoKeywords;
	}

	public String getSeoTitle()
	{
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle)
	{
		this.seoTitle = seoTitle;
	}

	public String getTreePath()
	{
		return treePath;
	}

	public void setTreePath(String treePath)
	{
		this.treePath = treePath;
	}

	public Long getParent()
	{
		return parent;
	}

	public void setParent(Long parent)
	{
		this.parent = parent;
	}

	public List<Article> getArticles()
	{
		return articles;
	}

	public void setArticles(List<Article> articles)
	{
		this.articles = articles;
	}

	
	
	
	
}
