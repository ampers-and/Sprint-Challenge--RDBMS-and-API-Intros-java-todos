package com.ampersand.todo.repositories;

import com.ampersand.todo.models.Todo;
import com.ampersand.todo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username); //build own jpa queries
    //expose, specific points we want

    List<User> findByUsernameContainingIgnoreCase(String name);
}
