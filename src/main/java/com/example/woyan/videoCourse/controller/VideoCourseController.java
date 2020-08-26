package com.example.woyan.videoCourse.controller;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.videoCourse.service.VideoCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class VideoCourseController {
    @Autowired
    private VideoCourseService videoCourseService;

    /**
     * 获取视频课程列表 （已测试：2019年11月20日 21:03:12）
     * @param pageNum 第几页
     * @param schoolCode 学校代码
     * @param proCourseId 专业课id
     * @param order 排序方式：new 最新，hot 最热
     * @return 状态码 + 数组
     */
    @GetMapping("/getVCourseList")
    public ReturnCode getVCourseList(@RequestParam int pageNum,
                                     @RequestParam(required = false,defaultValue = "0") long schoolCode,
                                     @RequestParam(required = false,defaultValue = "0") long collageId,
                                     @RequestParam(required = false,defaultValue = "0") long proCourseId,
                                     @RequestParam(required = false,defaultValue = "new") String order){
        return videoCourseService.getVCourseList(pageNum, schoolCode, collageId, proCourseId, order);
    }

    /**
     * 根据id获取单个视频课程 （已测试）
     * @param id 课程id
     * @return 状态码 + 视频信息 + 是否收藏 + 是否购买
     */
    @GetMapping("/getVCourseById")
    public ReturnCode getVCourseById(@RequestParam long id){
        return videoCourseService.getVCourseById(id);
    }

    /**
     * 改变视频课程收藏信息(已测试：2019年11月20日 21:00:47)
     * @param id 课程id
     * @return 状态码 + true or false
     */
    @PutMapping("/addVCourseCollect")
    public ReturnCode addVCourseCollect(@RequestParam long id){
        return videoCourseService.addVCourseCollect(id);
    }

    /**
     * 增加视频课程评论
     * @param id 课程id
     * @param replyId 被评论的用户id
     * @param reCommentId 被评论的评论id，如果是0，则是评论给课程的
     * @param content 评论的内容
     * @return 状态码 + 评论
     */
//    @PutMapping("/addVCourseComment")
//    public ReturnCode addVCourseComment(@RequestParam long id,
//                                        @RequestParam(required = false,defaultValue = "0") long replyId,
//                                        @RequestParam(required = false,defaultValue = "0") long  reCommentId,
//                                        @RequestParam String content){
//        return videoCourseService.addVCourseComment(id, replyId, reCommentId, content);
//    }

    /**
     * 删除视频课程评论
     * @param id 评论id
     * @return 如果评论是自己发布的，则可以删除，删除成功返回true 否则返回false
     */
//    @DeleteMapping("/delVCourseComment")
//    public ReturnCode delVCourseComment(@RequestParam long id){
//        return videoCourseService.delVCourseComment(id);
//    }

    /**
     * 更改课程信息（只有校区管理员才可以修改）
     * @param id 课程id
     * @param freeNum 免费的课时数
     * @param price 价格
     * @return 修改后的课程信息
     */
    @PutMapping("/changeVCourse")
    public ReturnCode changeVCourse(@RequestParam long id,
                                    @RequestParam(required = false,defaultValue = "1") int freeNum,
                                    @RequestParam(required = false,defaultValue = "0") int price){
        return videoCourseService.changeVCourse(id, freeNum, price);
    }

    /**
     * 分页获得当前视频课程的评论
     * @param id 视频课程id
     * @param pageNum 获得第几页的评论
     * @return 取得的所有评论
     */
//    @GetMapping("/getVCourseComment")
//    public ReturnCode getVCourseComment(@RequestParam long id,
//                                        @RequestParam int pageNum){
//        return videoCourseService.getVcourseComment(id, pageNum);
//    }

    /**
     * 分页获取视频课程评论的回复
     * @param id 评论的id
     * @param pageNum 第几页
     * @return ReturnCode
     */
//    @GetMapping("/getVCourseReply")
//    public ReturnCode getVCourseReply(@RequestParam long id,
//                                      @RequestParam int pageNum){
//        return videoCourseService.getVCourseReply(id, pageNum);
//    }

    /**
     * 根据标题和简介分页搜索视频
     * @param key 要搜索的关键字
     * @param order 排序方式（new or hot）最新或者最热
     * @param pageNum 第几页
     * @return  ReturnCode
     */
//    @GetMapping("/searchVCourse")
//    public ReturnCode searchVCourse(@RequestParam String key,
//                                    @RequestParam(required = false,defaultValue = "new") String order,
//                                    @RequestParam int pageNum){
//        return videoCourseService.searchVCourse(key, order, pageNum);
//    }

    /**
     * 获取单个视频（只有用户购买了才会返回对应的Src）（已测试：2019年11月21日 12:54:27）
     * @param id 视频id
     * @return 完整的视频信息
     */
    @GetMapping("/getVideoById")
    public ReturnCode getVideoById(@RequestParam long id){
        return videoCourseService.getVideoById(id);
    }

    /**
     * 增加视频评论（已测试：2019年11月21日 16:59:41）
     * @param id 视频id
     * @param replyId 被评论的用户id
     * @param reCommentId 被回复的评论id
     * @param content 评论内容
     * @return 评论
     */
    @PutMapping("/addVideoComment")
    public ReturnCode addVideoComment(@RequestParam long id,
                                      @RequestParam(required = false,defaultValue = "0") long replyId,
                                      @RequestParam(required = false,defaultValue = "0") long reCommentId,
                                      @RequestParam String content){
        return videoCourseService.addVideoComment(id, replyId, reCommentId, content);
    }

    /**
     * 删除视频评论
     * @param id 评论id
     * @return true or false
     */
    @DeleteMapping("/delVideoComment")
    public ReturnCode delVideoComment(@RequestParam long id){
        return videoCourseService.delVideoComment(id);
    }

    /**
     * 分页获得视频评论（已测试：2019年11月21日 17:00:41）
     * @param id 视频id
     * @param pageNum 第几页
     * @return 30条评论 + 总评论数
     */
    @GetMapping("/getVideoComment")
    public ReturnCode getVideoComment(@RequestParam long id,
                                      @RequestParam int pageNum){
        return videoCourseService.getVideoComment(id, pageNum);
    }

    /**
     * 分页获取视频回复
     * @param id 评论id
     * @param pageNum 分页
     * @return 一页五条回复
     */
    @GetMapping("/getVideoReply")
    public ReturnCode getVideoReply(@RequestParam long id,
                                    @RequestParam int pageNum){
        return videoCourseService.getVideoReply(id, pageNum);
    }

    @GetMapping("/getMyVcourses")
    public ReturnCode getMyVcourses(@RequestParam int pageNum){
        return videoCourseService.getMyVcourses(pageNum);
    }

    @GetMapping("/getVCourseRecommend")
    public ReturnCode getVCourseRecommend(@RequestParam(required = false,defaultValue = "0") long schoolCode){
        return videoCourseService.getVCourseRecommend(schoolCode);
    }
}
