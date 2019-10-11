package com.ampersand.todo.repositories;

import com.ampersand.todo.models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long>
{
   Todo findByTodoid(long id);

    List<Todo> findAllByUser_Userid(long id);
}
