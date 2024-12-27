package com.code.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.model.PlanLimits;
import com.code.model.User;
import com.code.repository.UserRepository;
@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;

    private static final Map<String, PlanLimits> planLimits = Map.of(
            "Silver", new PlanLimits(2,0),  //2,0
            "Gold", new PlanLimits(3,1),    //3,1
            "Platinum", new PlanLimits(4,2) //4,2
    );

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public PlanLimits getPlanLimits(String plan) {
        return planLimits.getOrDefault(plan, new PlanLimits(0,0)); //0 0
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
