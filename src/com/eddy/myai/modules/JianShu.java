package com.eddy.myai.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;

import com.eddy.myai.interfaces.IIntentProcess;
import com.eddy.utils.HtmlParserTool;
import com.eddy.utils.LogUtil;
import com.eddy.utils.RegularExpress;
import com.eddy.utils.interfaces.ILinkFilterWithAttriClass;

public class JianShu implements IIntentProcess {
	private final String URL_HOME_PAGE = "http://www.jianshu.com/u/bf03aa158e75";
	private final String KEY_WORD_CLASS_TITLE = "title";

	private RegularExpress mRegularExpress = new RegularExpress();

	private final String TAG = JianShu.class.getSimpleName();
	// 不确定的目标
	private final int TARGET_UNKOWN = 0x00000000;
	// 文章
	private final int TARGET_ARTICLE = 0x00000001;
	private final String REG_EX_ARTICLE = "文章|article";

	private HtmlParserTool mHtmlParserTool = new HtmlParserTool();

	public JianShu() {
	}

	@Override
	public String intentProcess(String content) {
		String rst = null;

		LogUtil.d(TAG, "intent: JianShu");
		LogUtil.d(TAG, "request:" + content);

		switch (getTarget(content)) {
		case TARGET_ARTICLE:
			LogUtil.d(TAG, "target: article");
			ILinkFilterWithAttriClass filter = new ILinkFilterWithAttriClass() {

				@Override
				public boolean accept(String cls) {
					if (cls == null) {
						return false;
					}

					if (cls.equals(KEY_WORD_CLASS_TITLE)) {
						return true;
					}

					return false;
				}
			};

			Set<String> articleSet = mHtmlParserTool.extractLinksWithAtrriClass(URL_HOME_PAGE, filter);

			rst = "";
			for (String article : articleSet) {
				rst += "《" + article + "》" + " ";
			}
			break;

		default:
			break;
		}

		return rst;
	}

	private int getTarget(String content) {
		// 文章
		if (mRegularExpress.isContainKeyWord(content, REG_EX_ARTICLE)) {
			return TARGET_ARTICLE;
		}

		return TARGET_UNKOWN;
	}
}
