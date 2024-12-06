package net.rahuls.hitpixel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class HitpixelAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitpixelAssignmentApplication.class, args);
    }

}
