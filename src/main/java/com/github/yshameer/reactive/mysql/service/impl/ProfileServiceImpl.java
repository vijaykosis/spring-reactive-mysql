package com.github.yshameer.reactive.mysql.service.impl;

import com.github.yshameer.reactive.mysql.entity.Mdn;
import com.github.yshameer.reactive.mysql.entity.ProfileResponse;
import com.github.yshameer.reactive.mysql.entity.TokeDTO;
import com.github.yshameer.reactive.mysql.repository.ProfileRepository;
import com.github.yshameer.reactive.mysql.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Mono<List<Mdn>> getAllProfiles() {
        System.out.println("getAllProfiles");
        Mono<List<Mdn>> listMono = profileRepository.findAll().collectList();
        System.out.println(listMono.log());
        return listMono;
    }

    @Override
    public Mono<ProfileResponse> getAllProfilesType() {
        Flux<Mdn> mdnFlux = profileRepository.findAll();
        Mono<List<Mdn>> listMono = mdnFlux.collectList();

        return listMono.flatMap(mdns -> {
            final ProfileResponse studentDto = new ProfileResponse();
            studentDto.setMdns(new ArrayList<>(mdns));
            return Mono.just(studentDto);
        });
    }

    @Override
    public Mono<List<Mdn>> getMdnDetails(TokeDTO tokeDTO) {

        return profileRepository.findByMobileNumber(tokeDTO.getMdn()).collectList();
    }

    @Override
    public Mono<List<Mdn>> getMdnDetailByMobileNumbers(String mobileNumbers) {
        return profileRepository.findByMobileNumber(mobileNumbers).collectList();
    }

    @Override
    public Flux<List<Mdn>> getAllProfilesByMdns(List<String> mobileNumbers) {
        return profileRepository.findByMobileNumbers(mobileNumbers);
    }
}
