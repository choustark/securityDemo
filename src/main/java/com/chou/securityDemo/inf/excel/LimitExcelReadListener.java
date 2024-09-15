package com.chou.securityDemo.inf.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Slf4j
@Data
public class LimitExcelReadListener extends AnalysisEventListener<Map<Integer, String>> {


    private Integer rowNum;

    private String chatName = "";

    private  boolean isSameChatName = true;

    /**
     * 表头数据
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();
    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    public LimitExcelReadListener(Integer rowNum){
        this.rowNum = rowNum;
    }

    public LimitExcelReadListener(){}

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        Map<Integer, String> newMap = this.getLimitColMap(integerStringMap, integerStringMap.size());
        String value = "聊天人Id";
        Optional<Integer> optional = getHeadList().stream().flatMap(map -> map.entrySet().stream())
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst();

        String cellContent = integerStringMap.get(optional.get());
        log.info(">>>>>LimitExcelReadListener#invoke value >>>>>>{}",cellContent);
        //log.info(">>>>>>> 当前行{}的数据，第{}列内容是{}",rowNum,integer,stringValue);
        if (StringUtils.isNotBlank(chatName) && !cellContent.equals(chatName)) {
            isSameChatName = false;
        } else {
            chatName = cellContent;
            this.rowNum++;
        }
        dataList.add(newMap);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, String> newMap = this.getLimitColMap(headMap,  headMap.size());
        log.info(">>>>>LimitExcelReadListener#invokeHeadMap {}>>>>>>",JSON.toJSONString(newMap));
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
        if (Objects.nonNull(rowIndex) && rowIndex == 0){
            return true;
        }
        // 发现指定列与当前列的内容不一致则并且达到了最大的行读取限制则不继续往下读excel
        if (Objects.nonNull(rowIndex) && rowIndex >= rowNum && !isSameChatName) {
            log.info(">>>>>>>>hasNext.rowIndex >>>>>> {} >>>>>>>>> {}",rowIndex,JSONUtil.toJsonStr(readRowHolder.getCellMap()));
            return false;
        }
        return super.hasNext(context);
    }
}
