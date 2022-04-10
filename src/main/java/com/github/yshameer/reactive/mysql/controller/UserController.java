package com.github.yshameer.reactive.mysql.controller;

import com.github.yshameer.reactive.mysql.entity.*;
import com.github.yshameer.reactive.mysql.repository.UserLogRepository;
import com.github.yshameer.reactive.mysql.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    public static List<UserLogResponse> userLogResponseLis = new ArrayList();
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserLogRepository userLogRepository;

    @GetMapping("/getAllProfiles")
    public Mono<List<Mdn>> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/getAllProfiles1")
    public Mono<ProfileResponse> getAllProfilesType() {
        return profileService.getAllProfilesType();
    }

    @PostMapping("/getAllByMdns")
    public Mono<Flux<List<Mdn>>> getParents(@RequestBody List<Mdn> mdns) {
        return Flux.fromIterable(mdns)
                .filter(mdn -> mdn.getMobileNumber() != null)
                .map(Mdn::getMobileNumber).collectList()
                .map(parentIds -> profileService.getAllProfilesByMdns(parentIds));

    }


    @PostMapping("/getMdnDetailById1")
    public Mono<List<Mdn>> getMdnDetails1(@RequestBody TokeDTO tokeDTO) {
        return profileService.getMdnDetails(tokeDTO);
    }

    @PostMapping("/getMdnDetailByMdn")
    public Mono<List<Mdn>> getMdnDetails(@RequestBody TokeDTO tokeDTO) {
        List<Mdn> mdnList = new ArrayList<>();
        Mono<ProfileResponse> responseMono = profileService.getAllProfilesType();
        Mono<List<Mdn>> profileServiceMdnDetails = profileService.getMdnDetails(tokeDTO);
        return Mono.zip(responseMono, profileServiceMdnDetails)
                .flatMap(a -> {
                    final ProfileResponse t1 = a.getT1();
                    final List<Mdn> t2 = a.getT2();
                    mdnList.addAll(t1.getMdns());
                    mdnList.addAll(t2);
                    return Mono.just(mdnList);
                });
    }

    @PostMapping("/getLogs")
    public Mono<List<Mdn>> getLogs(@RequestBody TokeDTO tokeDTO) {
       /* List<Mono<UserRequest>> userRequest = prepareUserRequest(profileService.getAllProfiles());
        Mono<List<UserLogResponse>> listMono = Flux.fromIterable(userRequest)
                .flatMap(r -> {
                    return userLogRepository.findAll();
                }).flatMap(userLogResponse -> {
                    if (Integer.parseInt(userLogResponse.getCposition()) > Integer.parseInt(userLogResponse.getMaxrows())) {
                        Mono<UserRequest> userRequestMono = prepareUserRequest(userLogResponse);
                        return userLogRepository.findAll();
                    }
                    return null;
                }).collectList();*/


        // List<Mono<UserRequest>> userRequest = prepareUserRequest(profileService.getAllProfiles().collectList());
/*
        return Flux.concat(userRequest).collectList().map(responseList -> {
            responseList.forEach(response -> {
                profileService.getAllProfiles();
            });
//Return Mono<OrderCombinedResponse> object
            return combinedResponse;
        });*/

/*        @Data
        public class UserRequest {

            private String mobileNumber;
            private String billDate;
            private String effectiveDate;
            private String billCode;
            String cposition;
            String maxrows;
        }*/
        //external(Mono<UserRequest>,tokeDTO)
        Mono<ProfileResponse> responseMono = profileService.getAllProfilesType();
/*
        Flux.fromIterable(userRequest.getMdns())
                .parallel(4)
                .runOn(Schedulers.parallel())
                .map(mdn -> fetchMdnDetails(mdn))
                .sequential()
                .elapsed()
                .subscribe(i -> System.out.printf(" %s ", i));

        return null;*/
        return null;
    }

    private Mono<UserRequest> prepareUserRequest(UserLogResponse userLogResponse) {
        UserRequest userRequest = new UserRequest();
        userRequest.setMaxrows(userLogResponse.getMaxrows());
        userRequest.setCposition(userLogResponse.getCposition());
        return Mono.just(userRequest);
    }


    private Flux<List<Mono<UserRequest>>> prepareUserRequest(Mono<List<Mdn>> allProfiles) {

        return allProfiles.map(x -> x.stream().map(mdn -> {
            UserRequest userRequest = new UserRequest();
            userRequest.setBillCode(mdn.getBillCode());
            userRequest.setMobileNumber(mdn.getMobileNumber());
            userRequest.setEffectiveDate(mdn.getEffectiveDate());
            userRequest.setBillDate(mdn.getBillDate());
            System.out.println(userRequest.toString());
            return Mono.just(userRequest);
        }).collect(Collectors.toList())).flatMapMany(Flux::just);

    }

    @PostMapping("/logs")
    public List<UserLogResponse> logs(@RequestBody TokeDTO tokeDTO) {
        Flux<List<Mono<UserRequest>>> userRequest = prepareUserRequest(profileService.getAllProfiles());

      /*  userRequest.subscribe(u -> {
            System.out.println("Finished processing" + u.size());
        }, error -> {
            System.err.println("The following error happened on processFoo method!");
        });*/

        List<UserLogResponse> userLogResponse = new ArrayList<>();
        //Mono<Respons> 5 req / 5 response then combin
        Flux<List<UserLogResponse>> listFlux = Flux.from(userRequest)
                .flatMap(r -> {
                    System.out.println("fromIterable....");
                    return userLogRepository.findAll();
                }).flatMap(response -> {
                    System.out.println("fromIterable response....");
                    System.out.println("current>>" + response.getCposition() + "Max>>" + response.getMaxrows());
                    if (Integer.parseInt(response.getCposition()) > Integer.parseInt(response.getMaxrows())) {
                        Mono<UserLogResponse> byId = userLogRepository.findById(11L);
                        System.out.println(response.toString());
                        byId.map(userLogResponse::add);
                     //Map(Iin)

                    } else {
                        System.out.println(response.toString());
                        userLogResponse.add(response);
                    }
                    System.out.println(userLogResponse.size());
                    return Mono.empty();
                });

        listFlux.subscribe(u -> {
            //  System.out.println("Finished processing" + u.size());
            System.out.println(listFlux.log());
            // u.stream().forEach(System.out::println);
        }, error -> {
            error.printStackTrace();

        });
        return userLogResponse;

/*
        userLogResponseFlux.subscribe(userLogResponse -> {
            System.out.println("Finished processing" + userLogResponse.getId());
        }, error -> {
            System.err.println("The following error happened on processFoo method!" + error.getMessage());
        });*/

        /*
           call 1
               call conditon
               addAll object

            call 2
               addAll object
             call 3
                  addAll object

         */
/*        List<UserLogResponse> userLogResponse = new ArrayList<>();
        Flux<Object> listMono = userLogResponseFlux.map(response -> {
            if (Integer.parseInt(response.getCposition()) > Integer.parseInt(response.getMaxrows())) {
                Flux<UserLogResponse> responseFlux = userLogRepository.findAll();
                System.out.println(response.toString());
                responseFlux.map(userLogResponse::add);
            } else {
                System.out.println(response.toString());
                userLogResponse.add(response);
            }
            System.out.println(userLogResponse.size());
            return userLogResponse;
        });
        return userLogResponse;*/
    }


    @GetMapping("/getAllUserLogs")
    public List<UserLogResponse> getAllUserLogs() {
        Flux<UserLogResponse> userLogResponseFlux = userLogRepository.findAll();
        int current = 0;
        int max = 0;
        processRecursiveRecords(userLogResponseFlux);
        return userLogResponseLis;
    }

    private void processRecursiveRecords(Flux<UserLogResponse> userLogResponseFlux) {

        Flux.from(userLogResponseFlux)
                .flatMap(r -> {
                    AtomicInteger current = new AtomicInteger(Integer.parseInt(r.getCposition()));
                    AtomicInteger Max = new AtomicInteger(Integer.parseInt(r.getMaxrows()));
                    while (current.get() <= Max.get()) {
                        System.out.println("current>>" + current + "Max>>" + Max);
                        Mono<UserLogResponse> byId = userLogRepository.findById(11L).subscribeOn(Schedulers.single())
                                .doOnNext(
                                        userLogResponse ->
                                        {
                                            System.out.println("Finished processing" + userLogResponse);
                                            current.compareAndSet(Integer.parseInt(r.getCposition()), Integer.parseInt(userLogResponse.getCposition()));
                                            current.compareAndSet(Integer.parseInt(r.getMaxrows()), Integer.parseInt(userLogResponse.getMaxrows()));
                                            System.out.println(
                                                    "Received: " + userLogResponse.getId() + " on thread: " + Thread.currentThread().getName());
                                            userLogResponseLis.add(userLogResponse);
                                        });
                        current.incrementAndGet();
                        //processRecursiveRecords(byId.flux());
                        byId.subscribe(userLogResponse -> {
                            System.out.println("Finished processing" + userLogResponse);
                        }, error -> System.err.println("The following error happened on processFoo method!" + error.getMessage()));
                    }
                    System.out.println("current>>" + r.getCposition() + "Max>>" + r.getMaxrows());
                    userLogResponseLis.add(r);
                    System.out.println("size>>" + userLogResponseLis.size());
                    return Mono.empty();
                }).subscribe(userLogResponse -> {
            System.out.println("Finished processing" + userLogResponse);
        }, error -> System.err.println("The following error happened on processFoo method!" + error.getMessage()));
    }

}

