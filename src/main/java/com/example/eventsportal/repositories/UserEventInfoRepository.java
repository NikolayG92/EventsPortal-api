package com.example.eventsportal.repositories;

import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.entities.UserEventInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserEventInfoRepository extends JpaRepository<UserEventInfo, String> {
    UserEventInfo findByUser(User user);

    List<UserEventInfo> findAllByUser(User user);
}
