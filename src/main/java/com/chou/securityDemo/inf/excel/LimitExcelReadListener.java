package com.chou.securityDemo.inf.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Data
public class LimitExcelReadListener extends AnalysisEventListener<Map<Integer, String>> {

    private Integer columNum;

    /**
     * 表头数据
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();
    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    public LimitExcelReadListener(Integer columNum){
        this.columNum = columNum;
    }

    public LimitExcelReadListener(){}

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        Map<Integer, String> newMap = this.getLimitColMap(integerStringMap,getColumNum());
        log.info(JSON.toJSONString(newMap));
        dataList.add(newMap);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, String> newMap = this.getLimitColMap(headMap,getColumNum());
        log.info(JSON.toJSONString(newMap));
        headList.add(newMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
    // 因为我有读取前几列的需求，所以简单写了下取前几个列，不需要的只需要控制前几行即可
    private Map<Integer, String> getLimitColMap(Map<Integer, String> oldMap, Integer limit){
        Integer size = oldMap.keySet().size();
        Map<Integer, String> newMap = new HashMap<>();
        for (int i = 0; i < (size >= limit ? limit : size); i++) {
            newMap.put(i,oldMap.get(i));
        }
        return newMap;
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        if (Objects.nonNull(rowIndex) && rowIndex == 0 ){
            log.info(">>>>>>>>rowIndex >>>>>> {} >>>>>>>>> {}",rowIndex,JSONUtil.toJsonStr(readRowHolder.getCellMap()));
            return false;
        }else {
            log.info(">>>>>>>>>>> rowIndex {}",rowIndex);
            return true;
        }
    }
}
