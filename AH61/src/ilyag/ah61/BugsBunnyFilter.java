package ilyag.ah61;

import android.text.InputFilter;
import android.text.Spanned;

public class BugsBunnyFilter implements InputFilter {
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		String addedText = source.toString().substring(dstart, end);
		boolean append = false;
		StringBuilder textToBe = new StringBuilder(dest.toString());
		if (dstart == dest.length()) {
			append = true;
			textToBe.append(addedText);
		} else {
			textToBe.replace(dstart, dend, addedText);
		}
		
		if (textToBe.toString().toLowerCase().contains("bugs bunny")) {
			if (append) {
				return "";
			} else {
				return dest.subSequence(dstart, dend);
			}
		}
		
		return null;
	}
	
}
