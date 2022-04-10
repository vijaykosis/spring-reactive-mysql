package com.github.yshameer.reactive.mysql.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileResponse {

    List<Mdn> mdns;
}