package com.example.project.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class FindCourseQuery {
    private String id;

    public FindCourseQuery(String id) {
        this.id = id;
    }
}