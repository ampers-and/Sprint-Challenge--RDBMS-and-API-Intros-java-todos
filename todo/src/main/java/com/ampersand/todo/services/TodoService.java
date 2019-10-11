package com.ampersand.todo.services;


import com.ampersand.todo.models.Todo;

import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findTodoById(long id);

    List<Todo> findByUserid(long id);

    Todo update(Todo todo, long id );//boolean isUser);

    Todo save(Todo todo);
}