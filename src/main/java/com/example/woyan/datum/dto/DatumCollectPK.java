package com.example.woyan.datum.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DatumCollectPK implements Serializable {
    // 资料id
    private Long datumId;
    // 用户id
    private Long userId;
}
