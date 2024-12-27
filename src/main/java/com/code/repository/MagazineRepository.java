package com.code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.model.*;
@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {
    Optional<Magazine> findByTitle(String title);
}
