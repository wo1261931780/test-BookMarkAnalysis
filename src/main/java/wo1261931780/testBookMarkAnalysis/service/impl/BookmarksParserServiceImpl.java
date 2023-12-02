package wo1261931780.testBookMarkAnalysis.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
import wo1261931780.testBookMarkAnalysis.service.BookMarksService;
import wo1261931780.testBookMarkAnalysis.service.BookmarksParserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Intellij IDEA.
 * Project:test-BookMarkAnalysis
 * Package:wo1261931780.testBookMarkAnalysis.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-11-15-38  星期二
 * @Description
 */
@Slf4j
@Service
public class BookmarksParserServiceImpl extends ServiceImpl<BookMarksMapper, BookMarks> implements BookmarksParserService {
	@Autowired
	private BookMarksService bookMarksService;

	@Override
	public List<BookMarks> parseBookMarks() {
		List<BookMarks> bookmarkList = new ArrayList<>();
		try {
			// File file = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\bookmarks\\bookmark2.html");
			File file = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\bookmarks\\bookmarks.html");
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
		return bookmarkList;
	}

	/**
	 * @param url
	 * @return
	 */
	@Override
	public BookMarks selectByUrl(String url) {
		if (StrUtil.isNotEmpty(url)) {
			LambdaQueryWrapper<BookMarks> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(BookMarks::getHref, url);
			List<BookMarks> bookMarks = bookMarksService.list(lambdaQueryWrapper);
			bookMarks.sort((o1, o2) -> {
				if (o1.getAddDate() > o2.getAddDate()) {
					return 1;
				} else if (o1.getAddDate() < o2.getAddDate()) {
					return -1;
				} else {
					return 0;
				}
			});
			if (!bookMarks.isEmpty()) {
				return bookMarks.get(0);
			}
		}
		log.info("url is null,something wrong!");
		return null;
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
		entity.setId(IdUtil.getSnowflakeNextId());
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
		// if (StrUtil.isEmpty(entity.getId().toString())) {
		entity.setId(IdUtil.getSnowflakeNextId());
		// }
		list.add(entity);
	}
}
