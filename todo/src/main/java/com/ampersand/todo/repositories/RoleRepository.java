package com.ampersand.todo.repositories;


import com.ampersand.todo.models.Role;
import com.ampersand.todo.view.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long>
{   //just returns data
    @Query(value = "SELECT COUNT(*) as count FROM userroles WHERE userid = :userid AND roleid = :roleid",
           nativeQuery = true) //better to not have this, have hibernate but if it doesn't work, do this
    JustTheCount checkUserRolesCombo(
            long userid,
            long roleid);//results of a query return inside interface = column w/ method for each, Titlecase

    @Transactional //changing so make trans
    @Modifying //modifies
    @Query(value = "DELETE FROM UserRoles WHERE userid = :userid AND roleid = :roleid") //custom sql, better to use built in jpa
    void deleteUserRoles(
            long userid,
            long roleid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UserRoles(userid, roleid) VALUES (:userid, :roleid)",
           nativeQuery = true)
    void insertUserRoles(
            long userid,
            long roleid);

    Role findByNameIgnoreCase(String name);

    @Transactional
    @Modifying
    // user Role instead of roles in order to use Hibernate SQL
    @Query(value = "UPDATE Role SET NAME = :name WHERE roleid = :roleid")
    void updateRoleName(
            long roleid,
            String name);
}
