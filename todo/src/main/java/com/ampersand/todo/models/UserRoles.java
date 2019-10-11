package com.ampersand.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userroles", //user + role together //unique
       uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "roleid"})})
public class UserRoles extends Auditable implements Serializable //json -> json, readable to json for composite
{
    @Id //not generating, primary composite key, bringing 2
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("userroles")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "roleid")
    @JsonIgnoreProperties("userroles")
    private Role role;

    public UserRoles()
    {
    }

    public UserRoles(User user,
                     Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    @Override //have to gen equals and hash for serializable
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserRoles))
        {
            return false;
        }
        UserRoles userRoles = (UserRoles) o;
        return getUser().equals(userRoles.getUser()) && getRole().equals(userRoles.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(),
                            getRole());
    }

    @Override
    public String toString()
    {
        return "UserRoles{" + "user=" + user.getUserid() + ", role=" + role.getRoleid() + '}';
    }
}
