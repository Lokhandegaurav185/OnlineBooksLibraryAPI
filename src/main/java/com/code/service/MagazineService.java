package com.code.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.model.Magazine;
import com.code.repository.MagazineRepository;
@Service
public class MagazineService {
	@Autowired
    private MagazineRepository magazineRepository;

    public Optional<Magazine> getMagazineByTitle(String title) {
        return magazineRepository.findByTitle(title);
    }

    public Magazine saveMagazine(Magazine magazine) {
       return magazineRepository.save(magazine);
    }
}
