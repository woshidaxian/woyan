package com.example.woyan.datum.controller;

import com.example.woyan.datum.service.DatumService;
import com.example.woyan.returnmsg.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DatumController {
    @Autowired
    private DatumService datumService;

    /**
     * 分页返回资料列表
     * @param schoolCode 学校代码（不传默认为登录用户的报考学校）
     * @param proCourseId 专业课id（不传返回所有课程视频）
     * @param pageNum 第几页
     * @param order 排序方式（默认seeNum：热度 new：最新 price：价格）
     * @return 一页30条资料
     */
    @GetMapping("/getDatumList")
    public ReturnCode getDatumList(@RequestParam(required = false,defaultValue = "0") long schoolCode,
                                   @RequestParam(required = false,defaultValue = "0") long proCourseId,
                                   @RequestParam int pageNum,
                                   @RequestParam(required = false,defaultValue = "hot") String order){
        return datumService.getDatumList(schoolCode, proCourseId, pageNum, order);
    }

    /**
     * 根据id获取资料的详细信息
     * @param id 资料id
     * @return 资料的详细信息
     */
    @GetMapping("/getDatumById")
    public ReturnCode getDatumById(@RequestParam long id){
        return datumService.getDatumById(id);
    }

    /**
     * 增加资料收藏
     * @param id 资料id
     * @return true or false
     */
    @PutMapping("/addDatumCollect")
    public ReturnCode addDatumCollect(long id){
        return datumService.addDatumCollect(id);
    }

    /**
     * 分页搜索资料
     * @param key 关键字
     * @param order 排序方式（默认seeNum：热度 new：最新 price：价格）
     * @param pageNum 第几页
     * @return 一页30条搜索结果
     */
    @GetMapping("/searchDatum")
    public ReturnCode searchDatum(@RequestParam String key,
                                  @RequestParam String order,
                                  @RequestParam int pageNum,
                                  @RequestParam(required = false,defaultValue = "0") long schoolCode){
        return datumService.searchDatum(key, order, pageNum, schoolCode);
    }
}
