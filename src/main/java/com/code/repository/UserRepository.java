package com.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.model.Book;
import com.code.model.Magazine;
import com.code.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	 
}
