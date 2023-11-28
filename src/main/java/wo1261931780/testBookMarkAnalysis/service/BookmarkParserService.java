package wo1261931780.testBookMarkAnalysis.service;

import lombok.extern.slf4j.Slf4j;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author junw
 */
@Slf4j
public class BookmarkParserService {
	public static void main(String[] args) {

		List<BookMarks> bookmarkList = new ArrayList<>();

		try {
			File file = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\entity\\bookmark.html");
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				log.info("line:{}", line);
				if (Pattern.compile("<H3([^>]*)>").matcher(line).find()) {
					// 处理 <H3> 逻辑
					parseH3(line, bookmarkList);
				} else if (Pattern.compile("<A([^>]*)>").matcher(line).find()) {
					parseA(line, bookmarkList);
				}
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理解析结果
		log.info("bookmarkList:{}", bookmarkList.size());
		log.info("bookmarkList:{}", bookmarkList);
	}

	private static void parseH3(String text, List<BookMarks> list) {
		BookMarks entity = new BookMarks();
		entity.setType("h3");

		Pattern pattern = Pattern.compile("HREF=\"([^\"]+)\"");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setHref(matcher.group(1));
		}

		pattern = Pattern.compile("ADD_DATE=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setAddDate(Long.valueOf(matcher.group(1)));
		}

		pattern = Pattern.compile("LAST_MODIFIED=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setLastModified(Long.valueOf(matcher.group(1)));
		}
		pattern = Pattern.compile("\">([^<]+)</H3>");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setTitle(matcher.group(1));
		}
		log.info("entity:{}", entity);

		list.add(entity);
	}


	private static void parseA(String text, List<BookMarks> list) {
		BookMarks entity = new BookMarks();
		entity.setType("a");

		Pattern pattern = Pattern.compile("HREF=\"([^\"]+)\"");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setHref(matcher.group(1));
		}

		pattern = Pattern.compile("ADD_DATE=\"([^\"]+)\"");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			entity.setAddDate(Long.valueOf(matcher.group(1)));
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
