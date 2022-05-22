package com.ncl.team3.mappers;

import com.ncl.team3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User,String> {

}
