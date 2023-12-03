package wo1261931780.testBookMarkAnalysis.controller;

import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarks2Mapper;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks2;
/**
*Created by Intellij IDEA.
*Project:test-BookMarkAnalysis
*Package:wo1261931780.testBookMarkAnalysis.controller
*@author liujiajun_junw
*@Date 2023-12-17-47  星期日
*@Description 
*/

@Service
public class BookMarks2Service extends ServiceImpl<BookMarks2Mapper, BookMarks2> {

    
    public int updateBatch(List<BookMarks2> list) {
        return baseMapper.updateBatch(list);
    }
    
    public int updateBatchSelective(List<BookMarks2> list) {
        return baseMapper.updateBatchSelective(list);
    }
    
    public int batchInsert(List<BookMarks2> list) {
        return baseMapper.batchInsert(list);
    }
    
    public int insertOrUpdate(BookMarks2 record) {
        return baseMapper.insertOrUpdate(record);
    }
    
    public int insertOrUpdateSelective(BookMarks2 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
