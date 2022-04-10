package com.github.yshameer.reactive.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@ToString
@Table("userLogResponse")
public class UserLogResponse {

    @Id
    private Long id;

    String cposition;
    String maxrows;
    String previousposition;
}