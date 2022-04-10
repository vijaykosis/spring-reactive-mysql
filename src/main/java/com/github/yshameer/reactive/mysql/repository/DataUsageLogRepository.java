package com.github.yshameer.reactive.mysql.repository;

import com.github.yshameer.reactive.mysql.entity.DatausageLogs;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DataUsageLogRepository extends ReactiveCrudRepository<DatausageLogs, Long> {


}
