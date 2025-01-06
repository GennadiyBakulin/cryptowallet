package com.javaacademy.cryptowallet.storage.user;

import com.javaacademy.cryptowallet.entity.User;
import java.util.Map;

public interface UserStorage {

  Map<String, User> getUserBd();
}
