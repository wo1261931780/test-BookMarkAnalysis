package wo1261931780.testBookMarkAnalysis.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import wo1261931780.testBookMarkAnalysis.entity.BookMarks;
import wo1261931780.testBookMarkAnalysis.mapper.BookMarksMapper;
/**
*Created by Intellij IDEA.
*Project:test-BookMarkAnalysis
*Package:wo1261931780.testBookMarkAnalysis.controller
*@author liujiajun_junw
*@Date 2023-11-15-18  星期二
*@Description 
*/

@Service
public class BookMarksService extends ServiceImpl<BookMarksMapper, BookMarks> {

    
    public int updateBatch(List<BookMarks> list) {
        return baseMapper.updateBatch(list);
    }
    
    public int updateBatchSelective(List<BookMarks> list) {
        return baseMapper.updateBatchSelective(list);
    }
    
    public int batchInsert(List<BookMarks> list) {
        return baseMapper.batchInsert(list);
    }
    public int batchInsert2(List<BookMarks> list) {
        return baseMapper.batchInsert2(list);
    }
    
    public int insertOrUpdate(BookMarks record) {
        return baseMapper.insertOrUpdate(record);
    }
    
    public int insertOrUpdateSelective(BookMarks record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
