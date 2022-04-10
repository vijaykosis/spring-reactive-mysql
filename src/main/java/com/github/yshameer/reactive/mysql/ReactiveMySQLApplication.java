package com.github.yshameer.reactive.mysql;

import com.github.yshameer.reactive.mysql.entity.DatausageLogs;
import com.github.yshameer.reactive.mysql.entity.Mdn;
import com.github.yshameer.reactive.mysql.entity.UserLogResponse;
import com.github.yshameer.reactive.mysql.repository.DataUsageLogRepository;
import com.github.yshameer.reactive.mysql.repository.ProfileRepository;
import com.github.yshameer.reactive.mysql.repository.UserLogRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMySQLApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMySQLApplication.class, args);
    }

    @Bean
    ApplicationRunner init(ProfileRepository repository, UserLogRepository userLogRepository, DataUsageLogRepository dataUsageLogRepository, DatabaseClient client) {
        return args -> {
            client.execute("create table IF NOT EXISTS Mdn" +
                    "(id SERIAL PRIMARY KEY, MOBILE_NUMBER varchar (255) not null, bill_date varchar (255) not null,effective_date varchar (255) not null,bill_code varchar (255) not null);").fetch().first().subscribe();
            client.execute("DELETE FROM Mdn;").fetch().first().subscribe();


            client.execute("create table IF NOT EXISTS userLogResponse" +
                    "(id SERIAL PRIMARY KEY, cposition varchar (255) not null, maxrows varchar (255) not null,previousposition varchar (255) not null);").fetch().first().subscribe();
            client.execute("DELETE FROM userLogResponse;").fetch().first().subscribe();


            client.execute("create table IF NOT EXISTS datausageLogs" +
                    "(id SERIAL PRIMARY KEY, date varchar (255) not null,time varchar (255) not null);").fetch().first().subscribe();
            client.execute("DELETE FROM datausageLogs;").fetch().first().subscribe();


            Stream<Mdn> stream = Stream.of(new Mdn(null, "9855850950", "17/05/1991", "17/05/1991", "119"),
                    new Mdn(null, "9855850950", "17/05/1991", "17/05/1991", "120"),
                    new Mdn(null, "9855850950", "17/05/1991", "17/05/1991", "121"));


            ArrayList<UserLogResponse> userLogResponses = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                UserLogResponse userLogResponse = new UserLogResponse(null, "99" + i, "995", "0");
                userLogResponses.add(userLogResponse);
            }

            Stream<UserLogResponse> stream2 = userLogResponses.stream();

            ArrayList<DatausageLogs> arrayList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                DatausageLogs datausageLogs = new DatausageLogs(null, "17/05/1991", "2021-03-09 22:19:52");
                arrayList.add(datausageLogs);
            }
            Stream<DatausageLogs> stream3 = arrayList.stream();

            repository.saveAll(Flux.fromStream(stream))
                    .then()
                    .subscribe(); // execute
            userLogRepository.saveAll(Flux.fromStream(stream2))
                    .then()
                    .subscribe(); // execute
            dataUsageLogRepository.saveAll(Flux.fromStream(stream3))
                    .then()
                    .subscribe(); // execute

        };
    }

}
