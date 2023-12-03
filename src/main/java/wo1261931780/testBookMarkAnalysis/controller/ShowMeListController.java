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
import wo1261931780.testBookMarkAnalysis.entity.BookMarks2;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
import wo1261931780.testBookMarkAnalysis.service.BookMarksService;
import wo1261931780.testBookMarkAnalysis.service.BookmarksParserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
	private BookMarks2Service bookMarks2Service;

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
	 * 新增解析后的书签，包含文件夹和重复
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
	 * 书签去重后插入新表，不包含文件夹
	 *
	 * @return 插入结果
	 */
	@PostMapping("/insertNewOne")
	public ShowResult<Boolean> insertNewOne() {
		List<BookMarks> bookMarksList = new ArrayList<>();
		List<String> oneUrls = bookMarksMapper.selectAll();
		for (String oneUrl : oneUrls) {
			BookMarks bookMarks1 = new BookMarks();
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

	/**
	 * 文件夹插入新表
	 *
	 * @return 插入结果
	 */
	@PostMapping("/insertNewH3")
	public ShowResult<Boolean> insertNewH3() {
		List<BookMarks> oneUrls = bookMarksMapper.selectAllH3();
		log.info("当前文件夹数量：{}", oneUrls.size());
		int batchInsert = bookMarksService.batchInsert2(oneUrls);
		return ShowResult.sendSuccess(batchInsert > 0);
	}

	/**
	 * 将对象拼接属性到文件中
	 *
	 * @return 拼接结果
	 * @throws Exception 异常
	 */
	@PostMapping("/requestWriteHtml")
	public ShowResult<Boolean> requestWriteHtml() throws Exception {
		List<BookMarks2> bookMarksList = bookMarks2Service.list();
		bookMarksList.sort(Comparator.comparing(BookMarks2::getHref, Comparator.nullsLast(Comparator.reverseOrder())));
		// objects.sort(Comparator.comparing(obj -> obj.getHref(), Comparator.nullsLast(Comparator.reverseOrder())));
		File demoFile = new File("C:\\Users\\junw\\Documents\\GitHub\\test-BookMarkAnalysis\\src\\main\\java\\wo1261931780\\testBookMarkAnalysis\\bookmarks\\result.txt");
		log.info("创建文件：{}", demoFile.isFile());
		bookMarksList.forEach(s -> {
			log.info("书签：{}", s);
			if (ObjectUtil.isNotNull(s)) {
				FileWriter writer = new FileWriter(demoFile);
				switch (s.getType()) {
					case "a":
						writer.append("<DT><A HREF=\"" + s.getHref() + "\" ADD_DATE=\"" + s.getAddDate() + "\" ICON=\" \">" + s.getTitle() + "</A>\r\n");
						// <DT><A HREF="https://www.baidu.com/" ADD_DATE="1645517630" ICON="=">百度</A>
						break;
					case "h3":
						// writer.append("<DT><H3 ADD_DATE=\"" + s.getAddDate() + "\" LAST_MODIFIED=\"" + s.getLastModified() + "\" HREF=\" \">" + s.getTitle() + "</H3>\r\n");
						writer.append("<DT><H3 ADD_DATE=\"" + s.getAddDate() + "\" LAST_MODIFIED=\"" + s.getLastModified() + "\">" + s.getTitle() + "</H3>\r\n");
						break;
					default:
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
