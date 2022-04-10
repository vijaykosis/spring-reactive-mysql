package com.github.yshameer.reactive.mysql.controller;

import com.github.yshameer.reactive.mysql.entity.DatausageLogs;
import com.github.yshameer.reactive.mysql.entity.UserLogResponse;
import com.github.yshameer.reactive.mysql.repository.DataUsageLogRepository;
import com.github.yshameer.reactive.mysql.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserLogsController {
    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private DataUsageLogRepository dataUsageLogRepository;

    @PostMapping("/createLogs")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserLogResponse> createLogs(@RequestBody UserLogResponse userLogResponse) {
        return userLogRepository.save(userLogResponse);
    }

    @PostMapping("/createDataUsageLogs")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DatausageLogs> createDataUsageLogs(@RequestBody DatausageLogs datausageLogs) {
        return dataUsageLogRepository.save(datausageLogs);
    }

    @GetMapping("/getAllUserLogResponse")
    public Mono<List<UserLogResponse>> getAllUserLogResponse() {
        return userLogRepository.findAll().collectList();
    }

    @GetMapping("/getAllDataUsageLogs")
    public Flux<DatausageLogs> getAllDataUsageLogs() {
        return dataUsageLogRepository.findAll();
    }

    @GetMapping("/getbyId/{id}")
    public Mono<UserLogResponse> getMdnDetails1(@PathVariable Long id) {
        return userLogRepository.findById(id);
    }
}
