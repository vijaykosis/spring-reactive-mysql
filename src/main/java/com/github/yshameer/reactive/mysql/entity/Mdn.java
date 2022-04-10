package com.github.yshameer.reactive.mysql.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@ToString
@Table("Mdn")
@EqualsAndHashCode
public class Mdn {

    @Id
    private Long id;
    private String mobileNumber;
    private String billDate;
    private String effectiveDate;
    private String billCode;

}