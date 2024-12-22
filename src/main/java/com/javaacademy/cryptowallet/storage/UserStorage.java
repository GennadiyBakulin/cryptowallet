package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.User;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserStorage {

  private final Map<String, User> userBd = new HashMap<>();
}
