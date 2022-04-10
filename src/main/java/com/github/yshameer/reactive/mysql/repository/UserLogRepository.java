package com.github.yshameer.reactive.mysql.repository;

import com.github.yshameer.reactive.mysql.entity.Mdn;
import com.github.yshameer.reactive.mysql.entity.UserLogResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserLogRepository extends ReactiveCrudRepository<UserLogResponse, Long> {
}
