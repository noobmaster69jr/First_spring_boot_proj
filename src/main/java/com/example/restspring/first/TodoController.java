package com.example.restspring.first;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1")
public class TodoController {
  private static List<Todo> todoList;
  // Error message when the todo is not found
    private static final String TODO_NOT_FOUND = "Todo not found";
     
    //@Autowired   - not recommended
    // @Qualifier("fakeTodoService")
    private TodoService todoService;

    private TodoService todoService2;

  public TodoController(@Qualifier("anotherTodoService") TodoService todoService,
                    @Qualifier("fakeTodoService") TodoService todoService2){ 
                        this.todoService = todoService;
                        this.todoService2 = todoService2;
    todoList = new ArrayList<>();
     todoList.add(new Todo(1, false, "Todo 1", 1));
    todoList.add(new Todo(2, true, "Todo 2", 2));
  }


   @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok(todoList);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {

        /**
         * we can use this annotation to set the status code @ResponseStatus(HttpStatus.CREATED)
         *
         */
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }
  
     @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                return ResponseEntity.ok(todo);
            }
        }
        // HW: Along with 404 status code, try to send a json {message: Todo not found}
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }

      /**
     * API to delete a Todo
     * We can delete a particular todo using its ID as it is unique for every todo.
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long todoId) {
        Todo todoToRemove = null;
        for(Todo todo : todoList) {
            if(todo.getId() == todoId) {
                todoToRemove = todo;
                break;
            }
        }

        if(todoToRemove != null) {
            todoList.remove(todoToRemove);
            String deleteSuccessMessage = "Todo deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(deleteSuccessMessage);
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
        }
    }

    /**
     * API to update an existing Todo using its ID
     * @param todoId
     * @param title
     * @param isCompleted
     * @param userId
     * @return
     */
    @PatchMapping("/{todoId}")
    ResponseEntity<?> updateTodoById(@PathVariable Long todoId, @RequestParam(required = false) String title, @RequestParam(required = false) Boolean isCompleted, Integer userId) {
        for(Todo todo : todoList) {
            if(todo.getId() == todoId) {
                if(title != null) {
                    todo.setTitle(title);
                }
                if(isCompleted != null) {
                    todo.setCompleted(isCompleted);
                }
                if(userId != null) {
                    todo.setUserId(userId);
                }

                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }
}
