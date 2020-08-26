package com.example.woyan.school.vo;

import com.example.woyan.school.dto.Collage;
import com.example.woyan.school.dto.ProCourse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnProCourses {
    private Collage collage;
    private List<ProCourse> proCourses;
}
