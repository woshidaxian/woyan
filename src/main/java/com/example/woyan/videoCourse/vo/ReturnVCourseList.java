package com.example.woyan.videoCourse.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnVCourseList {
    private long vCourseNum;
    private long vCoursePage;
    private List<ReturnVCourse> vCourses;
}
