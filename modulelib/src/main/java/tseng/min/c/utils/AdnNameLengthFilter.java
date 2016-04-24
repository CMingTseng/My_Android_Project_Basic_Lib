package tseng.min.c.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * http://www.lxway.net/51600182.html
 * Created by Neo on 2016/3/15.
 */
public class AdnNameLengthFilter implements InputFilter {
	static final String TAG = AdnNameLengthFilter.class.getSimpleName();
	private int nMax = 10;

	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		Log.w(TAG, "source(" + start + "," + end + ")=" + source + ",dest(" + dstart + "," + dend + ")=" + dest);
		int source_count = countChinese(source.toString());
		int dest_count = countChinese(dest.toString());
		int keep = nMax - dest_count - (dest.length() - (dend - dstart));
		if (keep <= 0) {
			return "";
		} else if (keep - source_count >= end - start) {
			return null;
		} else {
			char[] ch = source.toString().toCharArray();
			int k = keep;
			keep = 0;
			for (int i = 0; i < ch.length; i++) {
				if (isChinese(ch[i])) {
					k = k - 2;
				} else {
					k--;
				}
				keep++;
				if (k <= 0) {
					break;
				}
			}
			return source.subSequence(start, start + keep);
		}
	}

	private boolean isChinese(char c) {
		final Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	private int countChinese(String strName) {
		int count = 0;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				count++;
			}
		}
		return count;
	}
}
