package com.chou.securityDemo.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
		long start = System.currentTimeMillis();
		long size = file.getSize();
		log.info("file size >>>>>>>>>>>>>>>>>> {}", size);
		InputStream inputStream = null;
        try {
			inputStream = file.getInputStream();
			EasyExcel.read(inputStream, new ReadListener() {
                @Override
                public void invoke(Object o, AnalysisContext analysisContext) {

                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }

				@Override
				public void invokeHead(Map headMap, AnalysisContext context) {
					log.info("invokeHead headMapInfo >>>>>>>>> {}",JSONUtil.toJsonStr(headMap));
				}

			}).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
			IoUtil.close(inputStream);
		}
		log.info("parse excel size = {}mb,cost {} s",size/1014/1024,(System.currentTimeMillis()-start)/1000);
	}
}
