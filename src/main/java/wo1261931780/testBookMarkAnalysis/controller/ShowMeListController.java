package wo1261931780.testBookMarkAnalysis.controller;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wo1261931780.testBookMarkAnalysis.config.ShowResult;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
import wo1261931780.testBookMarkAnalysis.service.BookMarksService;
import wo1261931780.testBookMarkAnalysis.service.BookmarksParserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.constant.ConstantDescs.NULL;

/**
 * Created by Intellij IDEA.
 * Project:test-BookMarkAnalysis
 * Package:wo1261931780.testBookMarkAnalysis.controller
 *
 * @author liujiajun_junw
 * @Date 2023-11-15-29  星期二
 * @Description
 */
@RestController
@RequestMapping("/BookMarks")
@Slf4j
public class ShowMeListController {

	@Autowired
	private BookMarksService bookMarksService;
	@Autowired
	private BookMarksMapper bookMarksMapper;
	@Autowired
	private BookmarksParserService bookmarksParserService;

	/**
	 * 查询所有书签
	 *
	 * @param page  页码
	 * @param limit 每页条数
	 * @param sort  排序
	 * @param type  类型
	 * @return 书签分页
	 */
	@GetMapping("/list")
	public ShowResult<Page<BookMarks>> showMeList(@RequestParam Integer page
			, @RequestParam Integer limit
			, String sort
			, String type) {
		Page<BookMarks> pageInfo = new Page<>();// 页码，每页条数
		pageInfo.setCurrent(page);// 当前页
		pageInfo.setSize(limit);// 每页条数
		Page<BookMarks> testPage = bookmarksParserService.page(pageInfo);
		return ShowResult.sendSuccess(testPage);
	}

	/**
	 * 新增或者更新一条书签
	 *
	 * @param bookMarks 书签
	 * @return 新增或者更新结果
	 */
	@PostMapping("/insertOrUpdateOne")
	public ShowResult<Boolean> insertOrUpdateOne(BookMarks bookMarks) {
		boolean oneResult = bookmarksParserService.saveOrUpdate(bookMarks);
		return ShowResult.sendSuccess(oneResult);
	}

	/**
	 * 新增解析后的书签
	 *
	 * @return 插入数量
	 */
	@PostMapping("/insertOrUpdateBatch")
	public ShowResult<Integer> insertBatchBookMarks() {
		List<BookMarks> bookMarks = bookmarksParserService.parseBookMarks();
		if (bookMarks.isEmpty()) {
			return ShowResult.sendSuccess(0);
		}
		int batchInsert = bookMarksService.batchInsert(bookMarks);
		log.info("解析得到的书签数量：{}", bookMarks.size());
		log.info("批量插入的数量：{}", batchInsert);
		return ShowResult.sendSuccess(batchInsert);
	}

	/**
	 * 书签去重后插入新表
	 *
	 * @return 插入结果
	 */
	@PostMapping("/insertNewOne")
	public ShowResult<Boolean> insertNewOne() {
		List<BookMarks> bookMarksList = new ArrayList<>();
		List<String> oneUrls = bookMarksMapper.selectAll();
		for (String oneUrl : oneUrls) {
			BookMarks bookMarks1 = new BookMarks();
			// LambdaQueryWrapper<BookMarks> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			// lambdaQueryWrapper.eq(BookMarks::getHref, oneUrl);
			// BookMarks one = bookMarksService.selectByUrl(lambdaQueryWrapper);
			BookMarks selectedByUrl = bookmarksParserService.selectByUrl(oneUrl);
			if (ObjectUtil.isNull(selectedByUrl)) {// 为空则跳过
				continue;
			}
			BeanUtils.copyProperties(selectedByUrl, bookMarks1);
			bookMarksList.add(bookMarks1);
		}
		log.info("去重后的书签数量：{}", bookMarksList.size());
		int batchInsert = bookMarksService.batchInsert2(bookMarksList);
		return ShowResult.sendSuccess(batchInsert > 0);
	}

	@PostMapping("/requestWriteHtml")
	public ShowResult<Boolean> requestWriteHtml() throws Exception {
		List<BookMarks> bookMarksList = bookMarksService.list();
		bookMarksList.sort((b1, b2) -> b1.getHref().compareToIgnoreCase(b2.getHref()));
		File demoFile = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\bookmarks\\result.txt");
		// boolean newFile = file.createNewFile();
		log.info("创建文件：{}", demoFile.isFile());
		// BufferedWriter x2 = new BufferedWriter(new FileWriter(demoFile.getName()));
		// char[] x3 = new char[1024];
		bookMarksList.forEach(s -> {
			log.info("书签：{}", s);
			if (ObjectUtil.isNotNull(s)) {
				FileWriter writer = new FileWriter(demoFile);
				switch (s.getType()) {
					case "a":
						writer.append("<DT><A HREF=\"" + s.getHref() + "\" ADD_DATE=\"" + s.getAddDate() + "ICON=\" \">" + s.getTitle() + "</A>\r\n");
						// x2.write("<DT><A HREF=\"" + s.getHref() + "\" ADD_DATE=\"" + s.getAddDate() + "ICON=\" \">" + s.getTitle() + "</A>");
						// <DT><A HREF="https://www.baidu.com/" ADD_DATE="1645517630" ICON="=">百度</A>
						break;
					case "H3":
						writer.append("<DT><H3 HREF=\" \" ADD_DATE=\" " + s.getAddDate() + " LAST_MODIFIED=\"" + s.getLastModified() + "\">" + s.getTitle() + "</H3>\r\n");
						// log.info("书签：{}", s);
						break;
					default:
						// s.setLastModified(0);
						break;
				}
			}
		});
		log.info("测试：" + demoFile);
		// 将列表组装，输出到txt文件中，手动新增到一个html文件
		// 文件夹的形式同理
		return ShowResult.sendSuccess(Boolean.TRUE);
	}

}
