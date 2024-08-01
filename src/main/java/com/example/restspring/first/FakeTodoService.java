package com.example.restspring.first;

import org.springframework.stereotype.Service;

@Service("fakeTodoService")
public class FakeTodoService implements TodoService {
    public String doSomething(){
         return "something from fake todo service";
    }
}
