package wo1261931780.testBookMarkAnalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;

/**
*Created by Intellij IDEA.
*Project:test-BookMarkAnalysis
*Package:wo1261931780.testBookMarkAnalysis.mapper
*@author liujiajun_junw
*@Date 2023-11-15-18  星期二
*@Description 
*/

@Mapper
public interface BookMarksMapper extends BaseMapper<BookMarks> {
    int updateBatch(List<BookMarks> list);

    int updateBatchSelective(List<BookMarks> list);

    int batchInsert(@Param("list") List<BookMarks> list);
    int batchInsert2(@Param("list") List<BookMarks> list);

    int insertOrUpdate(BookMarks record);

    int insertOrUpdateSelective(BookMarks record);

    List<String> selectAll();
}
