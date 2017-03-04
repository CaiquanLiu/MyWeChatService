package com.eddy.myai;

import java.util.ArrayList;
import java.util.List;

import com.eddy.utils.LogUtil;
import com.eddy.utils.RegularExpress;

public class IntentJudger {
	private RegularExpress mRegularExpress = new RegularExpress();
	// 简书
	private final String REG_EX_JIANSHU = "简书|jianshu";

	public IntentJudger() {
	}

	public int intentAnalyze(String content) {
		// 简书
		if (mRegularExpress.isContainKeyWord(content, REG_EX_JIANSHU)) {
			return CommonParameter.INTENT_JIANSHU;
		}

		return CommonParameter.INTENT_UNKOWN;
	}
}
