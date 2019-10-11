package com.ampersand.todo.controllers;

import com.ampersand.todo.models.Todo;
import com.ampersand.todo.services.TodoService;
import com.ampersand.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/todos")
public class TodoController
{
    @Autowired
    private TodoService todoService;

    //updates a todo based on todoid. Can be done by any user.
    // Note: set completed to whatever comes across in the RequestBody.
    //PUT: http:localhost:2020/todos/todoid/{id}
    @PutMapping(value = "/todoid/{id}")
    public ResponseEntity<?> updateTodo(
            HttpServletRequest request,
            @RequestBody
                    Todo updateTodo,
            @PathVariable
                    long id)
    {
        todoService.update(updateTodo,
                id,
                request.isUserInRole("USER"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}