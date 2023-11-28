package wo1261931780.testBookMarkAnalysis.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Intellij IDEA.
 * Project:test-BookMarkAnalysis
 * Package:wo1261931780.testBookMarkAnalysis.entity
 *
 * @author liujiajun_junw
 * @Date 2023-11-12-25  星期二
 * @Description
 */
public class test {
	public static void main(String[] args) {
		String text = "<DT><A HREF=\"https://www.baidu.com/?dsp=ipad\" ADD_DATE=\"1525773079\" ICON=\"d\">百度一下,你就知道</A>";

		Pattern pattern = Pattern.compile("<DT><A HREF=\"https?://www\\.baidu\\.com/\\?dsp=ipad\" ADD_DATE=\"[\\d:]+\" ICON=\"d\">(百度一下,你就知道)</A>");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
