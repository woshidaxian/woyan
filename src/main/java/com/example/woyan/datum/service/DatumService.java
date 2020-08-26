package com.example.woyan.datum.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface DatumService {
    /**
     * 分页返回资料列表
     * @param schoolCode 学校代码（不传默认为登录用户的报考学校）
     * @param proCourseId 专业课id（不传返回所有课程视频）
     * @param pageNum 第几页
     * @param order 排序方式（默认seeNum：热度 new：最新 price：价格）
     * @return 一页30条资料
     */
    ReturnCode getDatumList(long schoolCode, long proCourseId, int pageNum, String order);

    /**
     * 根据id获取资料的详细信息
     * @param id 资料id
     * @return 资料的详细信息
     */
    ReturnCode getDatumById(long id);

    /**
     * 增加资料收藏
     * @param id 资料id
     * @return true or false
     */
    ReturnCode addDatumCollect(long id);

    /**
     * 分页搜索资料
     * @param key 关键字
     * @param order 排序方式（默认seeNum：热度 new：最新 price：价格）
     * @param pageNum 第几页
     * @param schoolCode 学校代码
     * @return 一页30条搜索结果
     */
    ReturnCode searchDatum(String key, String order, int pageNum, long schoolCode);
}
