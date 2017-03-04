package com.eddy;

import com.eddy.interfaces.IAIProcess;
import com.eddy.myai.EddyAI;
import com.eddy.turing.Turing;

public class GeneralAI implements IAIProcess {
	private IAIProcess mEddyAI = new EddyAI();
	private IAIProcess mTuring = new Turing(false);

	@Override
	public String process(String content) {
		if (content == null) {
			return null;
		}

		String rst = mEddyAI.process(content);
		if (rst == null) {
			rst = mTuring.process(content);
		}

		return rst;
	}
}
