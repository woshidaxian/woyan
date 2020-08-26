package com.example.woyan.info.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface InfoService {
    /**
     * 分页获取所有的资讯
     * @param pageNum 第几页
     * @param themeId 主题id
     * @return 资讯Page
     */
    ReturnCode getInfoList(int pageNum, long schoolCode, long themeId);

    /**
     * 根据id获取资讯详细信息
     * @param id 资讯id
     * @return 一篇资讯的详细内容
     */
    ReturnCode getInfoById(long id);
}
