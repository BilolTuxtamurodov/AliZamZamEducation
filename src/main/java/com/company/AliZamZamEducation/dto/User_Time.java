package com.company.AliZamZamEducation.dto;

import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;

public class User_Time {
    private User user;
    private LocalDateTime created_time;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
    }
}
