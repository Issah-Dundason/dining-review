package com.example.diningreview.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterestRepository extends CrudRepository<Interest, InterestId> {
    List<Interest> findByUser(User id);
    void deleteAllByUser(User user);
}
