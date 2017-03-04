package com.eddy.myai;

import com.eddy.interfaces.IAIProcess;
import com.eddy.myai.interfaces.IIntentProcess;
import com.eddy.myai.modules.JianShu;
import com.eddy.turing.Turing;
import com.eddy.utils.LogUtil;

public class EddyAI implements IAIProcess {
	private IntentJudger mIntentionJudger = new IntentJudger();
	private IIntentProcess mIntentProcess = null;

	@Override
	public String process(String content) {
		return eddyAIProcess(content);
	}

	private String eddyAIProcess(String content) {
		switch (mIntentionJudger.intentAnalyze(content)) {
		case CommonParameter.INTENT_JIANSHU:
			mIntentProcess = new JianShu();

		default:
			break;
		}

		if (mIntentProcess != null) {
			return mIntentProcess.intentProcess(content);
		}

		return null;
	}
}
