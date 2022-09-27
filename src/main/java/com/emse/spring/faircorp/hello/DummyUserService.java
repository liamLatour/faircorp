package com.emse.spring.faircorp.hello;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DummyUserService implements UserService{

    private final GreetingService greetingService;

    public DummyUserService(GreetingService greetingService){
        this.greetingService = greetingService;
    }

    @Override
    public void greetAll() {
        List<String> people = new ArrayList<>();
        people.add("Elodie");
        people.add("Charles");

        for (String p:people) {
            greetingService.greet(p);
        }
    }
}
