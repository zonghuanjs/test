package com.demo.pager;

import java.util.Map;
import java.util.Set;

/**
 * 分页器
 * @author ChenMingcai
 * 2014-09-29
 *
 */

public class Pager<T>
{
	/**
	 * 分页大小：默认为20
	 */
	private int pageSize = 20;
	
	/**
	 * 当前页：默认为第一页
	 */
	private int currentIdx = 1;
	
	/**
	 * 总记录数
	 */
	private long totalCount;
	
	/**
	 * 总页数
	 */
	private int pageCount = 1;
	
	/**
	 * 等值条件表：key=object and ...
	 */
	private Map<String, Object> filter;
	
	/**
	 * 集合条件表：key in (object, ...) and ...
	 */
	private Map<String, Set<Object> > inset;
	
	/**
	 * 模糊匹配条件表: key like Object and ...
	 */
	private Map<String, Object> likes;//模糊条件
	
	/**
	 * 排序方式：属性 desc|asc, ...
	 */
	private String orderby;
	
	public int getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
		if(totalCount > 0)
		{
			this.pageCount = (int)(totalCount / pageSize);
			if(totalCount % pageSize != 0)
				this.pageCount++;
		};
	}
	public int getCurrentIdx()
	{
		return currentIdx;
	}
	public void setCurrentIdx(int currentIdx)
	{
		this.currentIdx = currentIdx;
	}
	public long getTotalCount()
	{
		return totalCount;
	}
	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
		if(totalCount > 0)
		{
			this.pageCount = (int)(totalCount / pageSize);
			if(totalCount % pageSize != 0)
				this.pageCount++;
		};
	}
	public Map<String, Object> getFilter()
	{
		return filter;
	}
	public void setFilter(Map<String, Object> filter)
	{
		this.filter = filter;
	}
	public String getOrderby()
	{
		return orderby;
	}
	public void setOrderby(String orderby)
	{
		this.orderby = orderby;
	}
	
	/**
	 * 获取第1条记录号
	 * @return
	 */
	public int getFirst()
	{
		if(this.currentIdx <= 0)
			return 0;
		return pageSize * (currentIdx - 1);
	}
	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageCount()
	{
		return this.pageCount;
	}
	public Map<String, Set<Object> > getInset()
	{
		return inset;
	}
	public void setInset(Map<String, Set<Object> > inset)
	{
		this.inset = inset;
	}
	public Map<String, Object> getLikes()
	{
		return likes;
	}
	public void setLikes(Map<String, Object> likes)
	{
		this.likes = likes;
	}
	
	/**
	 * 获取起始页
	 * @return
	 */
	public int getStart()
	{
		int start = 1;
		if(currentIdx - 3 > 0)
			start = currentIdx -3;
		return start;
	}
	
	/**
	 * 获取结束页
	 * @return
	 */
	public int getEnd()
	{
		int end = currentIdx + 3;
		if(end > this.getPageCount())
			end = this.getPageCount();
		return end;
	}
	
	/**
	 * 是否显示前置省略号
	 * @return
	 */
	public boolean getPrePageBreak()
	{
		return this.getStart() > 1;
	}
	
	/**
	 * 是否显示后置省略号
	 * @return
	 */
	public boolean getNextPageBreak()
	{
		return this.getEnd() < this.getPageCount();
	}
}
