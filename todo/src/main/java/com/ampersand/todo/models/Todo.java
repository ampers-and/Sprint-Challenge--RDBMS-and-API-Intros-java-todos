package com.ampersand.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "todos")
public class Todo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date datestarted;

    private boolean complete = false;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties("todos")
    private User user;

    public Todo()
    {
    }

    public Todo(String description, Date datestarted, User user)
    {
        this.description = description;
        this.datestarted = datestarted;
        this.user = user;
    }

    public long getTodoid()
    {
        return todoid;
    }

    public void setTodoid(long todoid)
    {
        this.todoid = todoid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDatestarted()
    {
        return datestarted;
    }

    public void setDatestarted(Date datestarted)
    {
        this.datestarted = datestarted;
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
