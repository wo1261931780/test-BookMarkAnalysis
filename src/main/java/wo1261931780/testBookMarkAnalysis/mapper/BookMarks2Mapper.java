package wo1261931780.testBookMarkAnalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks2;

/**
*Created by Intellij IDEA.
*Project:test-BookMarkAnalysis
*Package:wo1261931780.testBookMarkAnalysis.mapper
*@author liujiajun_junw
*@Date 2023-12-17-47  星期日
*@Description 
*/

@Mapper
public interface BookMarks2Mapper extends BaseMapper<BookMarks2> {
    int updateBatch(List<BookMarks2> list);

    int updateBatchSelective(List<BookMarks2> list);

    int batchInsert(@Param("list") List<BookMarks2> list);

    int insertOrUpdate(BookMarks2 record);

    int insertOrUpdateSelective(BookMarks2 record);
}