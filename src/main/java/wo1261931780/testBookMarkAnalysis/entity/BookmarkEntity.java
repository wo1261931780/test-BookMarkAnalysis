package wo1261931780.testBookMarkAnalysis.entity;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.Date;

/**
 * Created by Intellij IDEA.
 * Project:analysis_booklist
 * Package:test
 *
 * @author liujiajun_junw
 * @Date 2023-11-10-27  星期二
 * @Description
 */
@Data
public class BookmarkEntity {
	private long id;
	private String href;
	private long addDate;
	// private longtext title;
	private String type;
	private long lastModified;
}
