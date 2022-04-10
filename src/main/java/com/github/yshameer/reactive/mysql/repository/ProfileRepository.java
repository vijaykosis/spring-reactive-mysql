package com.github.yshameer.reactive.mysql.repository;

import com.github.yshameer.reactive.mysql.entity.Mdn;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProfileRepository extends ReactiveCrudRepository<Mdn, Long> {


    @Query("select * from Mdn where MOBILE_NUMBER=:mobileNumber")
    Flux<Mdn> findByMobileNumber(String mobileNumber);

    @Query("select * from Mdn where MOBILE_NUMBER in (:mobileNumbers)")
    Flux<List<Mdn>> findByMobileNumbers(List<String> mobileNumbers);

}
