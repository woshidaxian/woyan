package com.example.woyan.videoCourse.service.impl;

import com.example.woyan.returnmsg.*;
import com.example.woyan.school.dao.CollageRepo;
import com.example.woyan.school.service.SchoolService;
import com.example.woyan.user.dao.UserRepo;
import com.example.woyan.user.dto.User;
import com.example.woyan.user.service.UserService;
import com.example.woyan.videoCourse.dao.*;
import com.example.woyan.videoCourse.dto.*;
import com.example.woyan.videoCourse.service.VideoCourseService;
import com.example.woyan.videoCourse.vo.ReturnOneVCourse;
import com.example.woyan.videoCourse.vo.ReturnVCourse;
import com.example.woyan.videoCourse.vo.ReturnVCourseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VideoCourseServiceImpl implements VideoCourseService {

    @Autowired
    private VCourseRepo vCourseRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VCourseCollectRepo vCourseCollectRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private VCourseCommentRepo vCourseCommentRepo;
    @Autowired
    private MyVCourseRepo myVCourseRepo;
    @Autowired
    private VideoCommentRepo videoCommentRepo;
    @Autowired
    private VCourseRecommendRepo vCourseRecommendRepo;
    @Autowired
    private CollageRepo collageRepo;
    
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;

    @Override
    public ReturnCode getVCourseList(int pageNum, long schoolCode, long collageId, long proCourseId, String order) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果学校代码为0，默认为用户的报考学校
        if (schoolCode == 0){
            schoolCode = user.getSchoolCode();
        }

        if (collageId == 0){
            collageId = collageRepo.findFirstBySchoolCode(schoolCode).getCollageId();
        }
        // 默认按照最新排序，如果设置排序方式为最热，则按照最热排序
        String property = "vcourseId";
        Sort.Direction direction = Sort.Direction.ASC;
        if(order.equals("hot")){
            property = "seeNum";
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageNum,12,Sort.by(direction,property));
        Page<VCourse> courses = new PageImpl<>(new ArrayList<>());
        if (proCourseId == 0){ // 如果没有选择专业id，返回当前学校 第一个学院全部专业的前30条视频
            courses = vCourseRepo.findAllByCollageId(collageId,pageable);
        }else { // 如果选择了，按照学校和专业返回结果
            courses = vCourseRepo.findAllByCollageIdAndProCourseId(collageId,proCourseId,pageable);
        }
        // 如果token中的用户存在，查询所有课程的收藏情况,并返回
        List<ReturnVCourse> returnVCourses = new ArrayList<>();
        for (VCourse course : courses){
            VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(),course.getVcourseId());
            returnVCourses.add(new ReturnVCourse(userRepo.findByUserId(course.getUserId()).getAccount(),course,
                    vCourseCollect!=null && vCourseCollect.isCollect()));
        }
        return new ReturnCode<>("200",new ReturnVCourseList(courses.getTotalElements(),courses.getTotalPages(),
                returnVCourses));
    }

    @Override
    public ReturnCode getVCourseById(long id) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        VCourse vCourse = vCourseRepo.findByVcourseId(id);
        // 如果课程不存在，返回未知错误
        if (vCourse == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        List<Video> videos = videoRepo.findAllByVcourseId(id);
        for (Video video: videos){
            video.setVideoSrc("");
        }
        VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(),id);
        MyVCourse myVCourse = myVCourseRepo.findByUserIdAndVcourseId(user.getUserId(), id);
        // 是否已经收藏了该视频课程
        boolean isCollect =  vCourseCollect!= null && vCourseCollect.isCollect();
        // 是否已经购买
        boolean isBought = myVCourse != null && myVCourse.getCanSee() > 0;
        // 查看数 +1
        vCourse.setSeeNum(vCourse.getSeeNum()+1);
        vCourseRepo.save(vCourse);
        vCourseRepo.flush();
        return new ReturnCode<>("200", new ReturnOneVCourse(new ReturnVCourse(userRepo.findByUserId(vCourse.getUserId()).getAccount(),vCourse,isCollect),isBought,videos));
    }

    @Override
    public ReturnCode addVCourseCollect(long id) {
        User user = null;
        VCourse vCourse = vCourseRepo.findByVcourseId(id);
        // 如果token存在，要收藏的用户存在，并且用户存在，则更改收藏情况
        if (vCourse != null &&
                (user = userService.getUserByToken()) != null){
            VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(),id);
            // 如果为空，表示从未点过该视频的收藏，数据库存储新对象
            if (vCourseCollect == null){
                vCourseCollect = new VCourseCollect(user.getUserId(),id);
            }else { // 如果不为空，更改收藏情况
                vCourseCollect.setCollect(!vCourseCollect.isCollect());
            }
            // 保存并返回结果
            vCourseCollectRepo.save(vCourseCollect);
            vCourseCollectRepo.flush();
            return new ReturnCode<>("200",vCourseCollect.isCollect());
        }
        return new ReturnCode<>("200",false);
    }

    @Override
    public ReturnCode addVCourseComment(long id, long replyId, long recommentId, String content) {
        User user = null;
        VCourse vCourse = vCourseRepo.findByVcourseId(id);
        // 如果用户和课程 都存在，添加评论
        if (vCourse != null &&
                (user = userService.getUserByToken()) != null){
            VCourseComment vCourseComment = new VCourseComment(user.getUserId(), id, replyId, recommentId, content);
            vCourseCommentRepo.save(vCourseComment);
            // 课程的评论数 +1
            vCourse.setCommentNum(vCourse.getCommentNum()+1);
            vCourseRepo.save(vCourse);
            vCourseRepo.flush();
            return new ReturnCode<>("200",new ReturnComment(user.getAccount(),
                    userRepo.findByUserId(replyId).getAccount(), vCourseComment, null));
        }
        return ReturnCode.LOGIN_NO_USER;
    }

    @Override
    public ReturnCode delVCourseComment(long id) {
        User user = null;
        VCourseComment vCourseComment = vCourseCommentRepo.findByCommentId(id);
        // 如果要删除的评论存在，用户存在，并且要删除的评论是当前登录用户的（之后可以添加 有****权限的也可以删除）
        if (vCourseComment != null &&
                (user = userService.getUserByToken()) != null &&
                        user.getUserId() == vCourseComment.getUserId()){
            // 删除当前评论
            vCourseComment.setDelete(true);
            vCourseCommentRepo.save(vCourseComment);
            return new ReturnCode<>("200",true);
        }
        return ReturnCode.LOGIN_NO_USER;
    }

    // 该方法配置了只有拥有“W0YAN_ADMIN”权限的用户才可以访问。因此直接设置、修改
    @Override
    public ReturnCode changeVCourse(long id, int freeNum, int price) {
        VCourse vCourse = vCourseRepo.findByVcourseId(id);
        vCourse.setFreeNum(freeNum);
        vCourse.setPrice(price);
        vCourseRepo.save(vCourse);
        vCourseRepo.flush();
        return new ReturnCode<>("200",vCourse);
    }

    // 默认返回前20条父评论 和 其3条子评论
    @Override
    public ReturnCode getVcourseComment(long id, int pageNum) {
        // 先判断要返回评论的课程是否存在
        VCourse vCourse = vCourseRepo.findByVcourseId(id);
        if (vCourse == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 定义要返回的数组
        List<ReturnComment> returnComments = new ArrayList<>();
        // 先找到前30条
        Pageable pageable = PageRequest.of(pageNum,30,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<VCourseComment> vCourseComments = vCourseCommentRepo.findAllByVcourseIdAndReCommentId(id,0, pageable);
        // 找子评论的pageable
        Pageable sonPageable = PageRequest.of(0,3,Sort.by(Sort.Direction.ASC,"commentId"));
        // 遍历搜索子评论，同时修改父评论信息
        for (VCourseComment courseComment : vCourseComments){
            User user = userRepo.findByUserId(courseComment.getUserId());
            // 找到前三条子评论
            Page<VCourseComment> replies = vCourseCommentRepo.findAllByVcourseIdAndReCommentId(id,courseComment.getCommentId(),sonPageable);
            List<ReturnReply> returnReplies = new ArrayList<>();
            // 转换子评论的格式
            for (VCourseComment reply: replies){
                // 如果当前回复已经删除，则修改内容为""
                if (reply.isDelete()){
                    reply.setContent("");
                }
                returnReplies.add(new ReturnReply(userRepo.findByUserId(reply.getUserId()).getAccount(),user.getAccount(),reply));
            }
            // 如果父评论已经删除，修改内容为""
            if (courseComment.isDelete()){
                courseComment.setContent("");
            }
            //将父评论添加至数组
            returnComments.add(new ReturnComment(user.getAccount(),"",courseComment,new ReturnReplyList(replies.getTotalElements(),replies.getTotalPages(),returnReplies)));
        }
        return new ReturnCode<>("200",new ReturnCommentList(vCourse.getCommentNum(),vCourseComments.getTotalPages(), returnComments));
    }

    // 分页返回某一条评论的子评论
    @Override
    public ReturnCode getVCourseReply(long id, int pageNum) {
        VCourseComment courseComment = vCourseCommentRepo.findByCommentId(id);
        // 如果父评论不存在，返回错误信息
        if (courseComment == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 每页五个，按id降序排序
        Pageable pageable = PageRequest.of(pageNum,5,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<VCourseComment> vCourseComments = vCourseCommentRepo.findAllByReCommentId(id,pageable);
        List<ReturnReply> replies = new ArrayList<>();
        User user = userRepo.findByUserId(courseComment.getUserId());
        // 将要返回的信息格式化
        for (VCourseComment comment: vCourseComments){
            replies.add(new ReturnReply(userRepo.findByUserId(comment.getUserId()).getAccount(),
                    comment.getReplyId() == user.getUserId()?user.getAccount():userRepo.findByUserId(comment.getReplyId()).getAccount(),
                    comment));
        }
        return new ReturnCode<>("200", new ReturnReplyList(vCourseComments.getTotalElements(), vCourseComments.getTotalPages(), replies));
    }

    @Override
    public ReturnCode searchVCourse(String key, String order, int pageNum) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 默认按照最新排序，如果设置排序方式为最热，则按照最热排序
        String property = "vcourseId";
        Sort.Direction direction = Sort.Direction.ASC;
        if(order.equals("hot")){
            property = "seeNum";
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(direction, property));
        // 找到所有标题或者简介包含key的内容
        key = "%" + key + "%";
        Page<VCourse> courses = vCourseRepo.findAllByTitleLikeOrIntroLike(key, key, pageable);
        // 如果token中的用户存在，查询所有课程的收藏情况,并返回
        List<ReturnVCourse> returnVCourses = new ArrayList<>();
        for (VCourse course : courses){
            VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(),course.getVcourseId());
            returnVCourses.add(new ReturnVCourse(userRepo.findByUserId(course.getUserId()).getAccount(),course,
                    vCourseCollect!=null && vCourseCollect.isCollect()));
        }
        return new ReturnCode<>("200",returnVCourses);
    }

    // 判断是否已经购买
    @Override
    public ReturnCode getVideoById(long id) {
        Video video = videoRepo.findByVideoId(id);
        if (video == null){
            return ReturnCode.NO_BOUGHT;
        }
        VCourse vCourse = vCourseRepo.findByVcourseId(video.getVcourseId());
        // 如果不是试看 或者 付费视频
        if (vCourse.getFreeNum() < video.getOrderNum() || vCourse.getPrice() > 0){
            // 如果token为空，返回课程尚未购买
            User user = userService.getUserByToken();
            // 如果用户或者视频为空，返回当前视频尚未购买
            if (user == null){
                return ReturnCode.NO_BOUGHT;
            }
            MyVCourse myVCourse = myVCourseRepo.findByUserIdAndVcourseId(user.getUserId(), video.getVcourseId());
            if (myVCourse != null){
                if (myVCourse.getCanSee() <= 0){
                    return ReturnCode.NO_SEE_NUM;
                }
            }else {
                return ReturnCode.NO_BOUGHT;
            }
        }
        // 如果是 试看 或者 免费视频 或者用户已经购买并且还有观看次数
        return new ReturnCode<>("200", video);
    }

    // 显示用户尚未登录？
    @Override
    public ReturnCode addVideoComment(long id, long replyId, long recommentId, String content) {
        User user = userService.getUserByToken();
        Video video = videoRepo.findByVideoId(id);
        if(user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        if (video == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        VideoComment comment = new VideoComment(id, user.getUserId(), replyId, recommentId, content);
        videoCommentRepo.save(comment);
        videoCommentRepo.flush();
        // 视频评论数 +1
        video.setCommentNum(video.getCommentNum()+1);
        videoRepo.save(video);
        return new ReturnCode<>("200", new ReturnComment(user.getAccount(),
                replyId == 0?null:userRepo.findByUserId(replyId).getAccount(),
                comment,
                null));
    }

    @Override
    public ReturnCode delVideoComment(long id) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        VideoComment videoComment = videoCommentRepo.findByCommentId(id);
        if (videoComment == null || user.getUserId() != videoComment.getUserId()){
            return ReturnCode.LOGIN_NO_USER;
        }
        videoComment.setDelete(true);
        videoCommentRepo.save(videoComment);
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode getVideoComment(long id, int pageNum) {
        Video video = videoRepo.findByVideoId(id);
        if (video == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 如果是 试看 或者 免费视频 或者用户已经购买并且还有观看次数
        Pageable fPage = PageRequest.of(pageNum, 30,Sort.by(Sort.Direction.ASC,"commentId"));
        Pageable sPage = PageRequest.of(0,3,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<VideoComment> videoComments = videoCommentRepo.findAllByVideoIdAndReCommentId(id, 0, fPage);
        List<ReturnComment> returnCommentList = new ArrayList<>();
        for (VideoComment comment: videoComments){
            // 找到当前评论所有者的id
            User commentUser = userRepo.findByUserId(comment.getUserId());
            // 找到当前评论下前三条子评论
            Page<VideoComment> replies = videoCommentRepo.findAllByReCommentId(comment.getCommentId(), sPage);
            List<ReturnReply> returnReplies = new ArrayList<>();
            // 格式化子评论内容
            for (VideoComment reply : replies){
                returnReplies.add(new ReturnReply(userRepo.findByUserId(reply.getUserId()).getAccount(),
                        commentUser.getAccount(), reply));
            }
            returnCommentList.add(new ReturnComment(userRepo.findByUserId(comment.getUserId()).getAccount(),
                    "", comment,
                    new ReturnReplyList(replies.getTotalElements(), replies.getTotalPages(), returnReplies)));
        }
        return new ReturnCode<>("200", new ReturnCommentList(video.getCommentNum(),
                videoComments.getTotalPages(),returnCommentList));
    }

    @Override
    public ReturnCode getVideoReply(long id, int pageNum) {
        VideoComment videoComment = videoCommentRepo.findByCommentId(id);
        if (videoComment == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        User user = userRepo.findByUserId(videoComment.getUserId());
        Pageable pageable = PageRequest.of(pageNum,5,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<VideoComment> videoComments = videoCommentRepo.findAllByReCommentId(id,  pageable);
        List<ReturnReply> returnReplies = new ArrayList<>();
        // 格式化回复数据
        for (VideoComment comment : videoComments){
            returnReplies.add(new ReturnReply(userRepo.findByUserId(comment.getUserId()).getAccount(), user.getAccount(),
                    comment));
        }
        return new ReturnCode<>("200", new ReturnReplyList(videoComments.getTotalElements(),
                videoComments.getTotalPages(),returnReplies));
    }

    @Override
    public ReturnCode getMyVcourses(int pageNum) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        //分页 查找我的资料
        Pageable pageable = PageRequest.of(pageNum,30,Sort.by(Sort.Direction.DESC,"vcourseId"));
        Page<MyVCourse> myVCourses = myVCourseRepo.findAllByUserId(user.getUserId(), pageable);
        List<ReturnVCourse> returnVCourses = new ArrayList<>();
        for (MyVCourse myVCourse : myVCourses){
            VCourse vCourse = vCourseRepo.findByVcourseId(myVCourse.getVcourseId());
            VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(), vCourse.getVcourseId());
            returnVCourses.add(new ReturnVCourse(userRepo.findByUserId(vCourse.getUserId()).getAccount(), vCourse, vCourseCollect != null && vCourseCollect.isCollect()));
        }
        return new ReturnCode<>("200", returnVCourses);
    }

    @Override
    public ReturnCode getVCourseRecommend(long schoolCode) {
        User user = userService.getUserByToken();
        if (schoolCode == 0 || schoolService.getSchoolByCode(schoolCode) == null){
            if (user == null){
                return ReturnCode.LOGIN_NO_USER;
            }
            schoolCode = user.getSchoolCode();
        }
        Pageable pageable = PageRequest.of(0,3,Sort.by(Sort.Direction.DESC,"vcourseId"));
        Page<VCourseRecommend> vCourseCommends = vCourseRecommendRepo.findAllBySchoolCode(schoolCode, pageable);
        List<ReturnVCourse> vCourseList = new ArrayList<>();
        for (VCourseRecommend recommend : vCourseCommends){
            VCourseCollect vCourseCollect = vCourseCollectRepo.findFirstByUserIdAndVcourseId(user.getUserId(), recommend.getVcourseId());
            VCourse vCourse = vCourseRepo.findByVcourseId(recommend.getVcourseId());
            vCourseList.add(new ReturnVCourse(userRepo.findByUserId(vCourse.getUserId()).getAccount(), vCourse, vCourseCollect != null && vCourseCollect.isCollect()));
        }
        return new ReturnCode<>("200", vCourseList);
    }
}
