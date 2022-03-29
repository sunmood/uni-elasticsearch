package com.uni.unielasticsearch.controller;

import com.uni.unielasticsearch.domain.Sku;
import com.uni.unielasticsearch.service.SkuService;
import com.uni.unielasticsearch.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Slf4j
@Api(tags={"元器件搜索引擎"})
@Controller
@RequestMapping("/")
public class SearchController {

    @Autowired
    private SkuService skuService;

    @GetMapping("index")
    @ApiOperation(value = "首页")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping("search/{keywords}/{pageNo}/{pageSize}")
    @ApiOperation(value = "查询")
    public Result<List<Map<String, Object>>> search(@PathVariable("keywords") String keywords,
                                                    @PathVariable("pageNo") int pageNo,
                                                    @PathVariable("pageSize") int pageSize) {
        try {
            List<Map<String, Object>> skuList = skuService.search(keywords, pageNo, pageSize);
            return Result.ok(skuList);
        } catch (Exception e) {
            log.error("查询失败");
            return Result.error("查询失败！");
        }
    }
}
