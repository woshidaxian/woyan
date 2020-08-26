package com.example.woyan.forum.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface ForumService {
    /**
     * 增加论坛文章
     * @param title 论坛文章标题
     * @param content 论坛文章内容（富文本）
     * @param labels 论坛文章标签
     * @param schoolCode 学校代码（不传默认是已选学校）
     * @return 文章
     */
    public ReturnCode addForum(String title, String content, int[] labels,long schoolCode);

    /**
     * 分页获取论坛文章
     * @param schoolCode 学校带啊
     * @param pageNum 第几页
     * @param order 排序方式（默认new： 最新，hot 查看人数最多）
     * @return 一页返回30篇文章
     */
    public ReturnCode getForumList(long schoolCode, int pageNum, String order);

    /**
     * 根据id获取论坛文章
     * @param forumId 文章id
     * @return ReturnCode
     */
    public ReturnCode getForumById(long forumId);

    /**
     * 删除论坛文章
     * @param forumId 论坛文章id
     * @return true or false
     */
    public ReturnCode delForum(int forumId);

    /**
     * 增加论坛文章评论
     * @param id 文章id
     * @param replyId 被回复的用户id
     * @param reCommentId 被回复的评论id
     * @param content 回复内容
     * @return 添加后的评论
     */
    public ReturnCode addForumComment(long id, long replyId, long reCommentId, String content);

    /**
     * 删除论坛评论
     * @param id 论坛评论id
     * @return true or false
     */
    public ReturnCode delForumComment(long id);

    /**
     * 分页获取论坛评论
     * @param id 文章id
     * @param pageNum 第几页
     * @return 一页返回30条评论 + 每条评论3条回复
     */
    public ReturnCode getForumComment(long id, int pageNum);

    /**
     * 分页获取论坛回复
     * @param id 评论id
     * @param pageNum 第几页
     * @return 一页返回5条回复
     */
    public ReturnCode getForumReply(long id, int pageNum);

    /**
     * 添加论坛文章点赞
     * @param id 文章id
     * @return true or false
     */
    public ReturnCode addForumGood(long id);

    /**
     * 增加论坛文章收藏
     * @param id 文章id
     * @return true or false
     */
    public ReturnCode addForumCollect(long id);

    /**
     * 搜索论坛文章
     * @param key 关键字
     * @param pageNum 第几页
     * @param order 排序方式（和返回文章的排序方式一样）
     * @return 一页返回30条
     */
    public ReturnCode searchForum(String key, long schoolCode, int pageNum, String order);

    /**
     * 获取所有的论坛文章标签
     * @return 标签的数组
     */
    public ReturnCode getForumLabels();
}
