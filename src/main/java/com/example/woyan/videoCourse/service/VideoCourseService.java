package com.example.woyan.videoCourse.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface VideoCourseService {
    /**
     * 分页获得视频课程列表
     * @param pageNum 当前页数
     * @param schoolCode 学校代码
     * @param collageId 学院id
     * @param proCourseId 专业课id
     * @param order 排行方式
     */
    public ReturnCode getVCourseList(int pageNum, long schoolCode, long collageId, long proCourseId, String order);

    /**
     * 根据id获取单个视频课程
     * @param id 课程id
     * @return 状态码 + 视频信息
     */
    public ReturnCode getVCourseById(long id);

    /**
     * 改变视频课程收藏信息(已测试)
     * @param id 课程id
     * @return 状态码 + true or false
     */
    public ReturnCode addVCourseCollect(long id);

    /**
     * 增加视频课程评论
     * @param id 课程id
     * @param replyId 被评论的用户id
     * @param reCommentId 被评论的评论id，如果是0，则是评论给课程的
     * @param content 评论的内容
     * @return 状态码 + 评论
     */
    public ReturnCode addVCourseComment(long id,long replyId,long reCommentId,String content);

    /**
     * 删除视频课程评论
     * @param id 评论id
     * @return 如果评论是自己发布的，则可以删除，删除成功返回true 否则返回false
     */
    public ReturnCode delVCourseComment(long id);

    /**
     * 更改课程信息（只有校区管理员才可以修改）
     * @param id 课程id
     * @param freeNum 免费的课时数
     * @param price 价格
     * @return 修改后的课程信息
     */
    public ReturnCode changeVCourse(long id,int freeNum,int price);

    /**
     * 分页获得当前视频课程的评论
     * @param id 视频课程id
     * @param pageNum 获得第几页的评论
     * @return 取得的所有评论
     */
    public ReturnCode getVcourseComment(long id,int pageNum);

    /**
     * 分页获取视频课程评论的回复
     * @param id 评论的id
     * @param pageNum 第几页
     * @return ReturnCode
     */
    public ReturnCode getVCourseReply(long id,int pageNum);

    /**
     * 分页搜索视频
     * @param key 要搜索的关键字
     * @param order 排序方式（new or hot）最新或者最热
     * @param pageNum 第几页
     * @return  ReturnCode
     */
    public ReturnCode searchVCourse(String key,String order,int pageNum);

    /**
     * 获取单个视频（只有用户购买了才会返回对应的Src）
     * @param id 视频id
     * @return 完整的视频信息
     */
    public ReturnCode getVideoById(long id);

    /**
     * 增加视频评论
     * @param id 视频id
     * @param replyId 被评论的用户id
     * @param reCommentId 被回复的评论id
     * @param content 评论内容
     * @return 评论
     */
    public ReturnCode addVideoComment(long id,long replyId,long reCommentId,String content);

    /**
     * 删除视频评论
     * @param id 评论id
     * @return true or false
     */
    public ReturnCode delVideoComment(long id);

    /**
     * 分页获得视频评论
     * @param id 视频id
     * @param pageNum 第几页
     * @return 30条评论 + 总评论数
     */
    public ReturnCode getVideoComment(long id,int pageNum);

    /**
     * 分页获取视频回复
     * @param id 评论id
     * @param pageNum 分页
     * @return 一页五条回复
     */
    public ReturnCode getVideoReply(long id,int pageNum);

    ReturnCode getMyVcourses(int pageNum);

    /**
     * 获取课程推荐（根据学校）
     * @return 3*VCourse
     */
    ReturnCode getVCourseRecommend(long schoolCode);
}
