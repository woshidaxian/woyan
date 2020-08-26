package com.example.woyan.datum.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnDatumList {
    private long datumNum;
    private int datumPage;
    private List<ReturnDatum> datums;
}
