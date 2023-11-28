package test;

import jdk.jfr.DataAmount;

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
	// <A HREF="https://www.cn.emb-japan.go.jp/itpr_zh/consular.html" ADD_DATE="1683800060">赴日签证（签证咨询） | 日本国驻华大使馆</A>

	private String href;
	private String addDate;
	private String title;
	private String type;
	private String lastModified;
}
