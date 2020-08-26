package com.example.woyan.datum.dao;

import com.example.woyan.datum.dto.DatumCollect;
import com.example.woyan.datum.dto.DatumCollectPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatumCollectRepo extends JpaRepository<DatumCollect, DatumCollectPK> {
    DatumCollect findByUserIdAndDatumId(long userId, long datumId);
}
