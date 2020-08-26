package com.example.woyan.datum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(DatumCollectPK.class)
public class DatumCollect {
    @Id
    private Long userId;
    @Id
    private Long datumId;
    private boolean isCollect;

    public DatumCollect(long userId,long datumId){
        this.userId = userId;
        this.datumId = datumId;
        this.isCollect = true;
    }
}
