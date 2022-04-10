package com.github.yshameer.reactive.mysql.controller;

import com.github.yshameer.reactive.mysql.entity.Mdn;
import com.github.yshameer.reactive.mysql.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Mdn> createProfile(@RequestBody Mdn mdns) {
        return profileRepository.save(mdns);
    }

    @GetMapping("/getAll")
    public Flux<Mdn> getAllProfiles() {
        return profileRepository.findAll();
    }

    @GetMapping("/customer")
    public Mono<Mdn> getCustomer(Long id) {
        return profileRepository.findById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Mdn> updateCustomer(@RequestBody Mdn mdn) {
        return this.profileRepository
                .findById(mdn.getId())
                .map(c -> mdn)
                .flatMap(this.profileRepository::save);
    }

    @DeleteMapping("/delete")
    public Mono<Mdn> delete(Long id) {
        return this.profileRepository
                .findById(id)
                .flatMap(customer -> this.profileRepository.deleteById(customer.getId()).thenReturn(customer));
    }
}
