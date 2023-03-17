package com.testtask.noteservice;

import com.testtask.noteservice.config.DataInitializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Log4j2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NoteServiceApplication {
    private static DataInitializer dataInitializer;

    @Autowired
    public NoteServiceApplication(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);
        log.info("Start application at: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        dataInitializer.createAdmin();
    }

}
