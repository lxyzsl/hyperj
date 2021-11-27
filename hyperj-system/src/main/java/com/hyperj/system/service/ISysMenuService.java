package com.hyperj.system.service;

import java.util.List;
import java.util.Set;

public interface ISysMenuService {

    Set<String> selectPermsByUserId(Long userId);
}
