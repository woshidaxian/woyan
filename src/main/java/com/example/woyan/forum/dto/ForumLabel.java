package com.example.woyan.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ForumLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flabelId;
    private String name;
}
