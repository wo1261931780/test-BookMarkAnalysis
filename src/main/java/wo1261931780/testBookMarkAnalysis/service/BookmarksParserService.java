package wo1261931780.testBookMarkAnalysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:test-BookMarkAnalysis
 * Package:wo1261931780.testBookMarkAnalysis.service
 *
 * @author liujiajun_junw
 * @Date 2023-11-15-38  星期二
 * @Description
 */

public interface BookmarksParserService extends IService<BookMarks> {
	List<BookMarks> parseBookMarks();
}
