package com.ampersand.todo.controllers;

import com.ampersand.todo.models.Todo;
import com.ampersand.todo.models.User;
import com.ampersand.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    //return the user and todo based off of the authenticated user. You can only look up your own.
    // It is okay if this also lists the users roles and authorities.
    //GET: http:localhost:2020/users/mine
    @GetMapping(value = "/mine",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    //adds a user. Can only be done by an admin.
    //POST:  http:localhost:2020/users/user
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    //adds a todo to the assigned user. Can be done by any user
    //POST: http:localhost:2020/users/todo/{userid}
    @PostMapping(value = "/todo/{userid}", consumes = {"application/json"})
    public ResponseEntity<?> addUserTodo(HttpServletRequest request,
                                         @Valid @RequestBody Todo ntodo,
                                         @PathVariable long userid)
        {
            userService.addTodo(ntodo, userid);

            return new ResponseEntity<>(HttpStatus.OK);
        }


    //Deletes a user by userid and deletes all associated todos. Can only be done by an admin.
    //DELETE: http:localhost:2020/users/userid/{userid}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/userid/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}