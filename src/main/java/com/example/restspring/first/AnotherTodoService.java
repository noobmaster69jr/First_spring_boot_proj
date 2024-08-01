package com.example.restspring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


// @Primary 
@Service("anotherTodoService")
public class AnotherTodoService implements TodoService{
    @Override
    public String doSomething() {
        return "Something from another todo service";
    }
}
