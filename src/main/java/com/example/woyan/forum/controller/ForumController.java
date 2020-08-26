package com.example.woyan.forum.controller;

import com.example.woyan.forum.service.ForumService;
import com.example.woyan.returnmsg.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ForumController {
    @Autowired
    private ForumService forumService;

    /**
     * 增加论坛文章
     * @param title 论坛文章标题
     * @param content 论坛文章内容（富文本）
     * @param labels 论坛文章标签
     * @param schoolCode 学校代码（不传默认是已选学校）
     * @return 文章
     */
    @PostMapping("/addForum")
    public ReturnCode addForum(@RequestParam String title,
                               @RequestParam String content,
                               @RequestParam int[] labels,
                               @RequestParam(required = false, defaultValue = "0") long schoolCode){
        return forumService.addForum(title, content, labels, schoolCode);
    }

    /**
     * 分页获取论坛文章
     * @param schoolCode 学校带啊
     * @param pageNum 第几页
     * @param order 排序方式（默认new： 最新，hot 查看人数最多）
     * @return 一页返回30篇文章
     */
    @GetMapping("/getForumList")
    public ReturnCode getForumList(@RequestParam(required = false,defaultValue = "0") long schoolCode,
                                   @RequestParam int pageNum,
                                   @RequestParam(required = false,defaultValue = "new") String order){
        return forumService.getForumList(schoolCode, pageNum, order);
    }

    /**
     * 根据id获取论坛文章
     * @param forumId 文章id
     * @return ReturnCode
     */
    @GetMapping("/getForumById")
    public ReturnCode getForumById(@RequestParam long forumId){
        return forumService.getForumById(forumId);
    }

    /**
     * 删除论坛文章
     * @param id 论坛文章id
     * @return true or false
     */
    @DeleteMapping("/delForum")
    public ReturnCode delForum(@RequestParam int id){
        return forumService.delForum(id);
    }

    /**
     * 增加论坛文章评论
     * @param id 文章id
     * @param replyId 被回复的用户id
     * @param reCommentId 被回复的评论id
     * @param content 回复内容
     * @return 添加后的评论
     */
    @PutMapping("/addForumComment")
    public ReturnCode addForumComment(@RequestParam long id,
                                      @RequestParam(required = false,defaultValue = "0") long replyId,
                                      @RequestParam(required = false,defaultValue = "0") long reCommentId,
                                      @RequestParam String content){
        return forumService.addForumComment(id, replyId, reCommentId, content);
    }

    /**
     * 删除论坛评论
     * @param id 论坛评论id
     * @return true or false
     */
    @DeleteMapping("/delForumComment")
    public ReturnCode delForumComment(@RequestParam long id){
        return forumService.delForumComment(id);
    }

    /**
     * 分页获取论坛评论
     * @param id 文章id
     * @param pageNum 第几页
     * @return 一页返回30条评论 + 每条评论3条回复
     */
    @GetMapping("/getForumComment")
    public ReturnCode getForumComment(@RequestParam long id,
                                      @RequestParam int pageNum){
        return forumService.getForumComment(id, pageNum);
    }

    /**
     * 分页获取论坛回复
     * @param id 评论id
     * @param pageNum 第几页
     * @return 一页返回5条回复
     */
    @GetMapping("/getForumReply")
    public ReturnCode getForumReply(@RequestParam long id,
                                    @RequestParam int pageNum){
        return forumService.getForumReply(id, pageNum);
    }

    /**
     * 增加论坛文章收藏
     * @param id 文章id
     * @return true or false
     */
    @PutMapping("/addForumCollect")
    public ReturnCode addForumCollect(@RequestParam long id){
        return forumService.addForumCollect(id);
    }

    /**
     * 添加论坛文章点赞
     * @param id 文章id
     * @return true or false
     */
    @PutMapping("/addForumGood")
    public ReturnCode addForumGood(@RequestParam long id){
        return forumService.addForumGood(id);
    }

    /**
     * 搜索论坛文章
     * @param key 关键字
     * @param schoolCode 学校代码
     * @param pageNum 第几页
     * @param order 排序方式（和返回文章的排序方式一样）
     * @return 一页返回30条
     */
    @GetMapping("/searchForum")
    public ReturnCode searchForum(@RequestParam String key,
                                  @RequestParam(required = false,defaultValue = "0") long schoolCode,
                                  @RequestParam int pageNum,
                                  @RequestParam String order){
        return forumService.searchForum(key, schoolCode, pageNum, order);
    }

    /**
     * 获取所有的论坛文章标签
     * @return 标签的数组
     */
    @GetMapping("/getForumLabels")
    public ReturnCode getForumLabels(){
        return forumService.getForumLabels();
    }
}
