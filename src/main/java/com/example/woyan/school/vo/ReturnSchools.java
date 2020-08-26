package com.example.woyan.school.vo;

import com.example.woyan.school.dto.Province;
import com.example.woyan.school.dto.School;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnSchools {
    private Province province;
    private List<School> schools;
}
