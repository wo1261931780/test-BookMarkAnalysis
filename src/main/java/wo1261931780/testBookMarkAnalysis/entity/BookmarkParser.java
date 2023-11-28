package wo1261931780.testBookMarkAnalysis.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author junw
 */
@Slf4j
public class BookmarkParser {
	public static void main(String[] args) {

		List<BookmarkEntity> bookmarkList = new ArrayList<>();

		try {
			File file = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\entity\\bookmark2.html");
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				log.info("line:{}", line);
				if (Pattern.compile("<H3([^>]*)>").matcher(line).find()) {
					// 处理 <H3> 逻辑
					parseH3(line, bookmarkList);
				} else if (Pattern.compile("<A([^>]*)>").matcher(line).find()) {
					parseA(line, bookmarkList);

					// 独立匹配单条<A>标签
					// BookmarkEntity entity = parseAEntity(line);

					// if (entity != null) {
					// 	bookmarkList.add(entity);
					// }
				}
				// h3标签正则
				// Pattern h3Pattern = Pattern.compile("<H3([^>]*)>");
				// Matcher h3Matcher = h3Pattern.matcher(line);
				// while (h3Matcher.find()) {
				// 	parseH3(h3Matcher.group(1), bookmarkList);
				// }

				// a标签正则
				// Pattern aPattern = Pattern.compile("<A([^>]*)>");
				// Matcher aMatcher = aPattern.matcher(line);
				// while (aMatcher.find()) {
				// 	parseA(aMatcher.group(1), bookmarkList);
				// }
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理解析结果
		log.info("bookmarkList:{}", bookmarkList.size());
		log.info("bookmarkList:{}", bookmarkList);
	}

	private static void parseH3(String text, List<BookmarkEntity> list) {
		BookmarkEntity entity = new BookmarkEntity();
		entity.setType("h3");

		Pattern pattern = Pattern.compile("HREF=\"([^\"]+)\"");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setHref(matcher.group(1));
		}

		pattern = Pattern.compile("ADD_DATE=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setAddDate(matcher.group(1));
		}

		pattern = Pattern.compile("LAST_MODIFIED=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setLastModified(matcher.group(1));
		}
		pattern = Pattern.compile("\">([^<]+)</H3>");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setTitle(matcher.group(1));
		}
		log.info("entity:{}", entity);

		list.add(entity);
	}


	private static void parseA(String text, List<BookmarkEntity> list) {
		BookmarkEntity entity = new BookmarkEntity();
		entity.setType("a");

		Pattern pattern = Pattern.compile("HREF=\"([^\"]+)\"");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setHref(matcher.group(1));
		}

		pattern = Pattern.compile("ADD_DATE=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setAddDate(matcher.group(1));
		}
		pattern = Pattern.compile("\">([^<]+)</A>");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setTitle(matcher.group(1));
		}

		log.info("entity:{}", entity);

		list.add(entity);
	}
}
