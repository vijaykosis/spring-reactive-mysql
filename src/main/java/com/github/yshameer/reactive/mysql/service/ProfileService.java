package com.github.yshameer.reactive.mysql.service;

import com.github.yshameer.reactive.mysql.entity.Mdn;
import com.github.yshameer.reactive.mysql.entity.ProfileResponse;
import com.github.yshameer.reactive.mysql.entity.TokeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProfileService {

    Mono<List<Mdn>> getAllProfiles();

    Mono<ProfileResponse> getAllProfilesType();

    Mono<List<Mdn>> getMdnDetails(TokeDTO tokeDTO);


    Mono<List<Mdn>> getMdnDetailByMobileNumbers(String mobileNumbers);

    Flux<List<Mdn>> getAllProfilesByMdns(List<String> mobileNumbers);
}
