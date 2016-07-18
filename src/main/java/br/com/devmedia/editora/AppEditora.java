package br.com.devmedia.editora;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {AppEditora.class})
public class AppEditora implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AppEditora.class);
    }

    @Override
    public void run(String... arg) throws Exception {
        System.out.println("Hello World!");
    }
}