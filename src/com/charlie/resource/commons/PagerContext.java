package com.charlie.resource.commons;

/**
 * 用來傳遞列表對象的ThreadLocal數據
 * 
 * @author tommy
 * 
 */
public class PagerContext {
	/**
	 * 分頁大小
	 */
	private static ThreadLocal<Integer> size = new ThreadLocal<Integer>();
	/**
	 * 分頁的起始頁
	 */
	private static ThreadLocal<Integer> page = new ThreadLocal<Integer>();
	/**
	 * 列表的排序字段(id,name,.....)
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	/**
	 * 列表的排序方式(asc,desc)
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();

	public static Integer getSize() {
		return size.get();
	}

	public static void setSize(Integer _Size) {
		PagerContext.size.set(_Size);
	}

	public static Integer getPage() {
		return page.get();
	}

	public static void setPage(Integer _page) {
		PagerContext.page.set(_page);
	}

	public static String getSort() {
		return sort.get();
	}

	public static void setSort(String _sort) {
		PagerContext.sort.set(_sort);
	}

	public static String getOrder() {
		return order.get();
	}

	public static void setOrder(String _order) {
		PagerContext.order.set(_order);
	}

	public static void removeSize() {
		size.remove();
	}

	public static void removePage() {
		page.remove();
	}

	public static void removeSort() {
		sort.remove();
	}

	public static void removeOrder() {
		order.remove();
	}

}
