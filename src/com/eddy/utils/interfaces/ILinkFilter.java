package com.eddy.utils.interfaces;

/**
 * 本接口所定义的过滤器，用于判断url是否属于本次搜索范围
 * 
 * @author liucaiquan
 *
 */
public interface ILinkFilter {
	public boolean accept(String url);
}
