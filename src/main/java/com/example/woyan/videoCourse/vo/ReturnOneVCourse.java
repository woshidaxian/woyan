package com.example.woyan.videoCourse.vo;

import com.example.woyan.videoCourse.dto.VCourse;
import com.example.woyan.videoCourse.dto.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOneVCourse {
    private ReturnVCourse vCourse;
    // 是否购买
    private boolean isBought;
    private List<Video> videos;
}
