package com.example.woyan.info.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InfoTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeId;
    private String name;
}
