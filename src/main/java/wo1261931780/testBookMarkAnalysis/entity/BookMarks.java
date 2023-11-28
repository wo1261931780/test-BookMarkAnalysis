package wo1261931780.testBookMarkAnalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*Created by Intellij IDEA.
*Project:test-BookMarkAnalysis
*Package:wo1261931780.testBookMarkAnalysis.entity
*@author liujiajun_junw
*@Date 2023-11-15-18  星期二
*@Description 
*/

@ApiModel(description="test_technical.book_marks")
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "test_technical.book_marks")
public class BookMarks implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    @Schema(description="主键")
    private Long id;

    /**
     * 链接
     */
    @TableField(value = "href")
    @ApiModelProperty(value="链接")
    @Schema(description="链接")
    private String href;

    /**
     * 新增时间
     */
    @TableField(value = "add_date")
    @ApiModelProperty(value="新增时间")
    @Schema(description="新增时间")
    private Long addDate;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="标题")
    @Schema(description="标题")
    private String title;

    /**
     * 类型
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="类型")
    @Schema(description="类型")
    private String type;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_modified")
    @ApiModelProperty(value="最后修改时间")
    @Schema(description="最后修改时间")
    private Long lastModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_HREF = "href";

    public static final String COL_ADD_DATE = "add_date";

    public static final String COL_TITLE = "title";

    public static final String COL_TYPE = "type";

    public static final String COL_LAST_MODIFIED = "last_modified";
}
