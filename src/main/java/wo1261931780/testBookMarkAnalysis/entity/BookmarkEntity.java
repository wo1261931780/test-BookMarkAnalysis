package wo1261931780.testBookMarkAnalysis.entity;

import jdk.jfr.DataAmount;
import lombok.Data;

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
	private String href;
	private String addDate;
	private String title;
	private String type;
	private String lastModified;
}
