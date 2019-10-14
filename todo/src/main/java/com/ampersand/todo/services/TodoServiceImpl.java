package com.ampersand.todo.services;

import com.ampersand.todo.models.*;
import com.ampersand.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service(value = "todoService")
public class TodoServiceImpl implements TodoService
{

    @Autowired
    private TodoRepository todorepos;

    @Override
    public List<Todo> findAll()
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findTodoById(long id)
    {
        return todorepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " Not Found!"));
    }

    @Override
    public List<Todo> findByUserid(long id)
    {
        return todorepos.findAllByUser_Userid(id);
    }

    @Override
    public Todo update(Todo todo, long todoid,
                            boolean isUser)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (todorepos.findById(todoid)
                .isPresent())
        {
            if (todorepos.findById(todoid)
                    .get()
                    .getUser()
                    .getUsername()
                    .equalsIgnoreCase(authentication.getName()) || isUser)
            {
                Todo todo1 = todorepos.findById(todoid).get();

                if (todo.getDatestarted() != null)
                {
                    todo1.setDatestarted(todo.getDatestarted());
                }

                if(todo.getDescription() != null)
                {
                    todo1.setDescription(todo.getDescription());
                }

                if(todo.isCompleted() != todo1.isCompleted())
                {
                    todo1.setCompleted(todo.isCompleted());
                }

                return todorepos.save(todo1);
            } else
            {
                throw new EntityNotFoundException(authentication.getName() + " not authorized to make change");
            }
        } else
        {
            throw new EntityNotFoundException("Todo with id " + todoid + " Not Found!");
        }
    }

    @Override
    public Todo save(Todo todo)
    {
        return todorepos.save(todo);
    }
}
