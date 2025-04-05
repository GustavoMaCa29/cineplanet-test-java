package com.cineplanet.demo.service.interfaces;

import com.cineplanet.demo.entity.User;

public interface AuthService {
    String login(User user);
    String registerGuest(User user);
}
