package wo1261931780.testBookMarkAnalysis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wo1261931780.testBookMarkAnalysis.config.ShowResult;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
import wo1261931780.testBookMarkAnalysis.service.BookMarksService;

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
public class ShowMeListController {

	@Autowired
	private BookMarksService bookMarksService;
	@Autowired
	private BookMarksMapper bookMarksMapper;

	@GetMapping("/list")
	public ShowResult<Page<BookMarks>> showMeList(@RequestParam Integer page
			, @RequestParam Integer limit
			, String sort
			, String type) {
		Page<BookMarks> pageInfo = new Page<>();// 页码，每页条数
		pageInfo.setCurrent(page);// 当前页
		pageInfo.setSize(limit);// 每页条数
		Page<BookMarks> testPage = bookMarksService.page(pageInfo);
		return ShowResult.sendSuccess(testPage);
	}


	@PostMapping("/insertOrUpdate")
	public ShowResult<Boolean> insertOrUpdateAcademyGenerateScore() {

		// 直接执行Service中的代码
		// 然后返回更新的结果
		// 采用批量更新的方式插入MySQL
		return ShowResult.sendSuccess(Boolean.TRUE);
	}


}
