package com.eddy.utils;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.eddy.utils.interfaces.ILinkFilter;
import com.eddy.utils.interfaces.ILinkFilterWithAttriClass;

/**
 * 代码参考：http://blog.csdn.net/jediael_lu/article/details/26572175
 * 
 * @author liucaiquan
 *
 */
public class HtmlParserTool {
	// 本方法用于提取某个html文档中内嵌的链接
	public Set<String> extractLinks(String url, ILinkFilter filter) {
		Set<String> links = new HashSet<String>();
		try {
			// 1、构造一个Parser，并设置相关的属性
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");

			// 2.1、自定义一个Filter，用于过滤<Frame >标签，然后取得标签中的src属性值
			NodeFilter frameNodeFilter = new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};

			// 2.2、创建第二个Filter，过滤<a>标签
			NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);

			// 2.3、净土上述2个Filter形成一个组合逻辑Filter。
			OrFilter linkFilter = new OrFilter(frameNodeFilter, aNodeFilter);

			// 3、使用parser根据filter来取得所有符合条件的节点
			NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);

			// 4、对取得的Node进行处理
			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				String linkURL = "";
				// 如果链接类型为<a />
				if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					linkURL = link.getLink();
				} else {
					// 如果类型为<frame />
					String nodeText = node.getText();
					int beginPosition = nodeText.indexOf("src=");
					nodeText = nodeText.substring(beginPosition);
					int endPosition = nodeText.indexOf(" ");
					if (endPosition == -1) {
						endPosition = nodeText.indexOf(">");
					}
					linkURL = nodeText.substring(5, endPosition - 1);
				}
				// 判断是否属于本次搜索范围的url
				if (filter.accept(linkURL)) {
					links.add(linkURL);
				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}

	// 本方法用于提取某个html文档中<a>标签中的内容
	public Set<String> extractLinksWithAtrriClass(String url, ILinkFilterWithAttriClass filter) {
		Set<String> links = new HashSet<String>();
		try {
			// 1、构造一个Parser，并设置相关的属性
			Parser parser = new Parser(url);
			parser.setEncoding("utf-8");

			// 2.2、创建第二个Filter，过滤<a>标签
			NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);

			// 3、使用parser根据filter来取得所有符合条件的节点
			NodeList nodeList = parser.extractAllNodesThatMatch(aNodeFilter);

			// 4、对取得的Node进行处理
			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				String linkURL = "";
				// 如果链接类型为<a />
				if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					linkURL = link.getLink();
				}
				// 判断是否属于本次搜索范围的url
				LinkTag tag = (LinkTag) node;
				if (filter.accept(tag.getAttribute("class"))) {
					links.add(tag.getStringText());
				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
}
