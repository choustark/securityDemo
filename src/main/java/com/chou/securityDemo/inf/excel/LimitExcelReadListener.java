package com.chou.securityDemo.inf.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 控制读取前几行，读取完之后抛出异常，读取时捕获此异常，捕获到异常时代表读取完毕
        if (dataList.size() == 1) {
            throw new ExcelAnalysisException("1行20列读取完成");
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, String> newMap = this.getLimitColMap(headMap,getColumNum());
        System.out.println(JSON.toJSONString(newMap));
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
}
