package com.exam.demo.crud.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseModel {
    private Long code;
    private String description;
    private DataModel data;
}
