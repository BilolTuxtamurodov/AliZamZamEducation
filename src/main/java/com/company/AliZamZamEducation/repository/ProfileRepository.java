package com.company.AliZamZamEducation.repository;

import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.mapper.ProfileUserIdMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    @Query("select p.userId as user_id from ProfileEntity as p")
    List<ProfileUserIdMapper> getUserId();

    @Query("select count (p.userId) from ProfileEntity as p")
    Integer getUsersCount();
}