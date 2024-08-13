package com.example.leave_manager;

import org.springframework.boot.SpringApplication;

public class TestLeaveManagerApplication {

    public static void main(String[] args) {
        SpringApplication.from(LeaveManagerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
