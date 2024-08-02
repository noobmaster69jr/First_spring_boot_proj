package com.example.restspring.first;

import org.springframework.stereotype.Service;

@Service("fakeTodoService")
public class FakeTodoService implements TodoService {


    // in spring boot logging order is unpredictable 
    // even if we attach multiple annotation , they are not called multiple times 
    // they are nested in spring boot
    // TimeMonitor(TimeMonitor(doSomething))
    @TimeMonitor
    public String doSomething(){
         return "something from fake todo service";
    }  // join point - methods where behaviour can be inserted 
    // point cut - is a expression that can select multiple join points
}
