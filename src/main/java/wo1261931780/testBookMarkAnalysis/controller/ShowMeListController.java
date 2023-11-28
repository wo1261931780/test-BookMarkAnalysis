package wo1261931780.testBookMarkAnalysis.controller;

import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wo1261931780.testBookMarkAnalysis.config.ShowResult;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
import wo1261931780.testBookMarkAnalysis.service.BookMarksService;
import wo1261931780.testBookMarkAnalysis.service.BookmarksParserService;

import java.util.List;

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

}
