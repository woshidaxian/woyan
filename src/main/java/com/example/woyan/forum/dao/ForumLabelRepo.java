package com.example.woyan.forum.dao;

import com.example.woyan.forum.dto.ForumLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumLabelRepo extends JpaRepository<ForumLabel,Long> {
}
