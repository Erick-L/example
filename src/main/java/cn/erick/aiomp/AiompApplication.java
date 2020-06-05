package cn.erick.aiomp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.erick.aiomp.mapper")
public class AiompApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiompApplication.class, args);
    }

}
