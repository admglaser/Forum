package hu.bme.aut.onlab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationUtils {

	private static final String[] MENTION_REGEXP_EXPRESSIONS = {
			"(?<=@)[a-zA-Z0-9]+",
			"(?<=@\")[a-zA-Z0-9\\s]+(?=\")"
	};
	
	public static String getMentionedName(String postText) {
		
		for (String regexp : MENTION_REGEXP_EXPRESSIONS) {
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(postText);
			if (matcher.find()) {
				return matcher.group();
			}
		}
		
		return null;
	}
	
}
