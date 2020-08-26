package com.example.woyan.datum.vo;

import com.example.woyan.datum.dto.Datum;
import lombok.Data;

@Data
public class ReturnDatum {
    private Datum datum;
    private boolean isCollect;

    public ReturnDatum(Datum datum, boolean isCollect){
        this.setDatum(datum);
        this.isCollect = isCollect;
    }
}
