package com.example.woyan.forum.service.impl;

import com.example.woyan.forum.service.ForumService;
import com.example.woyan.forum.vo.ReturnForum;
import com.example.woyan.forum.vo.ReturnForumList;
import com.example.woyan.user.dto.User;
import com.example.woyan.forum.dto.Forum;
import com.example.woyan.forum.dto.ForumCollect;
import com.example.woyan.forum.dto.ForumComment;
import com.example.woyan.forum.dto.ForumGood;
import com.example.woyan.forum.dao.*;
import com.example.woyan.school.dao.SchoolRepo;
import com.example.woyan.returnmsg.*;
import com.example.woyan.user.service.UserService;
import com.example.woyan.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ForumServiceImpl implements ForumService {
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolRepo schoolRepo;
    @Autowired
    private ForumRepo forumRepo;
    @Autowired
    private ForumGoodRepo forumGoodRepo;
    @Autowired
    private ForumCollectRepo forumCollectRepo;
    @Autowired
    private ForumCommentRepo forumCommentRepo;
    @Autowired
    private ForumLabelRepo forumLabelRepo;

    private TextUtil textUtil = new TextUtil();

    @Override
    public ReturnCode addForum(String title, String content, int[] labels, long schoolCode) {
        // 如果没登录，返回用户尚未登录
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 通过数组生成labels的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < labels.length; ++i){
            if (i == 0){
                stringBuilder.append(labels[i]);
            }else {
                stringBuilder.append(",").append(labels[i]);
            }
        }
        // 如果学校代码为0，设置为用户的报考学校
        if (schoolCode == 0 || schoolRepo.findBySchoolCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 创建实例
        Forum forum = new Forum(user.getUserId(),
                title, textUtil.DeleteHTML(content),
                stringBuilder.toString(), schoolCode);
        forumRepo.save(forum);
        forumRepo.flush();
        return new ReturnCode<>("200", new ReturnForum(user.getAccount(), forum, false));
    }

    @Override
    public ReturnCode getForumList(long schoolCode, int pageNum, String order) {
        // 如果用户不存在，返回用户尚未登录
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果没有穿学校代码或者学校不存在，设置为用户报考的学校
        if (schoolCode == 0 || schoolRepo.findBySchoolCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 分页
        // 默认按照最新排序，如果设置排序方式为最热，则按照最热排序
        String property = "forumId";
        Sort.Direction direction = Sort.Direction.DESC;
        if(order.equals("hot")){
            property = "seeNum";
            direction = Sort.Direction.DESC;
        }else if (order.equals("newr")){
            property = "replyTime";
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(direction,property));
        Page<Forum> forums = forumRepo.findAllBySchoolCodeAndDelete(schoolCode, false, pageable);
        List<ReturnForum> returnForums = new ArrayList<>();
        // 处理数据
        for (Forum forum : forums){
            // 获取列表的时候不需要文章内容
            forum.setContent("");
            ForumCollect forumCollect = forumCollectRepo.findByUserIdAndForumId(user.getUserId(), forum.getForumId());
            returnForums.add(new ReturnForum(userService.getUserById(forum.getUserId()).getAccount(),
                    forum, forumCollect!=null && forumCollect.isCollect()));
        }
        return new ReturnCode<>("200", new ReturnForumList(forums.getTotalElements(),
                forums.getTotalPages(), returnForums));
    }

    @Override
    public ReturnCode getForumById(long forumId) {
        // 如果用户不存在，返回用户尚未登录
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在 或者 论坛文章不是当前用户的，返回未知错误
        Forum forum = forumRepo.findByForumId(forumId);
        if (forum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 文章增加浏览数
        forum.setSeeNum(forum.getSeeNum()+1);
        forumRepo.save(forum);
        forumRepo.flush();
        ForumCollect forumCollect = forumCollectRepo.findByUserIdAndForumId(user.getUserId(), forumId);
        return new ReturnCode<>("200", new ReturnForum(userService.getUserById(forum.getUserId()).getAccount(),
                forum, forumCollect!=null && forumCollect.isCollect()));
    }

    @Override
    public ReturnCode delForum(int forumId) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在 或者 论坛文章不是当前用户的，返回未知错误
        Forum forum = forumRepo.findByForumId(forumId);
        if (forum == null || forum.getUserId() != user.getUserId()){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 删除当前论坛文章
        forum.setDelete(true);
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode addForumComment(long id, long replyId, long reCommentId, String content) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在，返回未知错误
        Forum forum = forumRepo.findByForumId(id);
        if (forum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 回复数+1
        forum.setReplyNum(forum.getReplyNum()+1);
        forum.setReplyTime(new Date());
        forumRepo.save(forum);
        forumRepo.flush();
        // 生成论坛文章实例
        ForumComment forumComment = new ForumComment(user.getUserId(), id, replyId, reCommentId, content);
        // 保存实例
        forumCommentRepo.save(forumComment);
        forumCommentRepo.flush();
        return new ReturnCode<>("200", new ReturnComment(user.getAccount(),
                userService.getUserById(forum.getUserId()).getAccount(),
                forumComment, null));
    }

    @Override
    public ReturnCode delForumComment(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛评论不存在 或者 当前评论不是当前登录用户的，返回未知错误
        ForumComment forumComment = forumCommentRepo.findByCommentId(id);
        if (forumComment == null || forumComment.getUserId() != user.getUserId()){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 删除评论
        forumComment.setDelete(true);
        forumCommentRepo.save(forumComment);
        forumCommentRepo.flush();
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode getForumComment(long id, int pageNum) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在，返回未知错误
        Forum forum = forumRepo.findByForumId(id);
        if (forum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        User forumUser = userService.getUserById(forum.getUserId());
        // 分页获取30条评论 + 每条评论3条回复
        Pageable fPage = PageRequest.of(pageNum, 30,Sort.by(Sort.Direction.ASC,"commentId"));
        Pageable sPage = PageRequest.of(0,3,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<ForumComment> comments = forumCommentRepo.findAllByForumIdAndReCommentId(id, 0, fPage);
        // 格式化评论数据 and 获取回复
        List<ReturnComment> returnComments = new ArrayList<>();
        for (ForumComment comment : comments){
            User commentUser = userService.getUserById(comment.getUserId());
            // 获取回复，格式化数据 存入数组
            Page<ForumComment> replies = forumCommentRepo.findAllByReCommentId(comment.getCommentId(), sPage);
            List<ReturnReply> returnReplies = new ArrayList<>();
            for (ForumComment reply : replies){
                returnReplies.add(new ReturnReply(userService.getUserById(reply.getUserId()).getAccount(),
                        commentUser.getAccount(),
                        reply));
            }
            // 把评论添加 到数组
            returnComments.add(new ReturnComment(commentUser.getAccount(),
                    forumUser.getAccount(),
                    comment,
                    new ReturnReplyList(replies.getTotalElements(), replies.getTotalPages(), returnReplies)));
        }
        return new ReturnCode<>("200", new ReturnCommentList(forum.getReplyNum(),
                comments.getTotalPages(),
                returnComments));
    }

    @Override
    public ReturnCode getForumReply(long id, int pageNum) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果评论不存在，返回未知错误
        ForumComment forumComment = forumCommentRepo.findByCommentId(id);
        if (forumComment == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        User commentUser = userService.getUserById(forumComment.getUserId());
        // Page 分页获取回复
        Pageable pageable = PageRequest.of(pageNum,5,Sort.by(Sort.Direction.ASC,"commentId"));
        Page<ForumComment> replies = forumCommentRepo.findAllByReCommentId(forumComment.getCommentId(), pageable);
        // 格式化数据，存入数组
        List<ReturnReply> returnReplies = new ArrayList<>();
        for (ForumComment reply: replies){
            returnReplies.add(new ReturnReply(userService.getUserById(reply.getUserId()).getAccount(),
                    reply.getReCommentId() == 0?commentUser.getAccount():userService.getUserById(reply.getReCommentId()).getAccount(),
                    reply));
        }
        return new ReturnCode<>("200", new ReturnReplyList(replies.getTotalElements(),
                replies.getTotalPages(),
                returnReplies));
    }

    @Override
    public ReturnCode addForumGood(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在，返回未知错误
        Forum forum = forumRepo.findByForumId(id);
        if (forum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ForumGood forumGood = forumGoodRepo.findByUserIdAndForumId(user.getUserId(), id);
        if (forumGood == null){
            forumGood = new ForumGood(id, user.getUserId());
        }else {
            forumGood.setGood(!forumGood.isGood());
        }
        forumGoodRepo.save(forumGood);
        forumGoodRepo.flush();
        return new ReturnCode<>("200", forumGood.isGood());
    }

    @Override
    public ReturnCode addForumCollect(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果论坛文章不存在，返回未知错误
        Forum forum = forumRepo.findByForumId(id);
        if (forum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ForumCollect forumCollect = forumCollectRepo.findByUserIdAndForumId(user.getUserId(), id);
        if (forumCollect == null){
            forumCollect = new ForumCollect(user.getUserId(), id);
        }else {
            forumCollect.setCollect(!forumCollect.isCollect());
        }
        forumCollectRepo.save(forumCollect);
        return new ReturnCode<>("200", forumCollect.isCollect());
    }

    @Override
    public ReturnCode searchForum(String key, long schoolCode, int pageNum, String order) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回用户尚未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        if (schoolCode == 0 || schoolRepo.findBySchoolCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 分页
        // 默认按照最新排序，如果设置排序方式为最热，则按照最热排序
        String property = "forumId";
        Sort.Direction direction = Sort.Direction.DESC;
        if(order.equals("hot")){
            property = "seeNum";
            direction = Sort.Direction.DESC;
        }else if (order.equals("newr")){
            property = "replyTime";
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(direction,property));
        Page<Forum> forums = forumRepo.findAllBySchoolCodeAndDeleteAndTitleLike(schoolCode,false,"%"+key+"%", pageable);
        List<ReturnForum> returnForums = new ArrayList<>();
        for(Forum forum : forums){
            ForumCollect forumCollect = forumCollectRepo.findByUserIdAndForumId(user.getUserId(), forum.getForumId());
            returnForums.add(new ReturnForum(userService.getUserById(forum.getUserId()).getAccount(),
                    forum,
                    forumCollect != null && forumCollect.isCollect()));
        }
        return new ReturnCode<>("200", new ReturnForumList(forums.getTotalElements(),
                forums.getTotalPages(),
                returnForums));
    }

    @Override
    public ReturnCode getForumLabels() {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        return new ReturnCode<>("200", forumLabelRepo.findAll());
    }
}
