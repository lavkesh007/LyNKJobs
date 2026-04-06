package com.Spring.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.TotalUser;

public interface TotalUserRepo extends JpaRepository<TotalUser,Integer>{

}
