package com.chou.securityDemo.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.chou.securityDemo.inf.excel.LimitExcelReadListener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName IndexController
 * @Date 2023/11/18 21:57
 * @Version 1.0
 **/
@Controller
@Slf4j
@RequestMapping("index")
@Tag(name = "首页信息",description = "首页信息")
public class IndexController {

	@GetMapping("/authenticationinfo")
	@ResponseBody
	public String index(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return JSON.toJSONString(authentication);
	}

	@GetMapping("/indexHtml")
	@ResponseBody
	public String index1(){
		return "index.html";
	}

	@PostMapping("uploadfile")
	@ResponseBody
	@Operation(summary = "文件上传",description = "文件上传")
	public void batchUpload(@Valid @NotNull(message = "上传文件不能为空") @RequestBody MultipartFile file) {
		InputStream inputStream = null;
		ExcelReader excelReader = null;
		try {
			long start = System.currentTimeMillis();
			inputStream = file.getInputStream();
			excelReader = EasyExcel.read(inputStream).build();
			for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()) {
				LimitExcelReadListener readListener = new LimitExcelReadListener(10);
				String sheetName = readSheet.getSheetName();
				ArrayList<ReadListener<?>> readListeners = new ArrayList<>();
				readListeners.add(readListener);
				readSheet.setCustomReadListenerList(readListeners);
				excelReader.read(readSheet);
				log.info("sheetName:{} header list {}", sheetName, readListener.getHeadList());
				log.info("sheetName:{} contentOne list {}", sheetName, readListener.getDataList());
			}
			log.info("parse excel size = {}mb,cost {} ms", file.getSize() / 1014 / 1024, (System.currentTimeMillis() - start));
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
		if (Objects.nonNull(excelReader)){
			excelReader.finish();
		}
		IoUtil.close(inputStream);
	}
}
