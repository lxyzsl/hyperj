package com.hyperj.system.service;

import java.util.Set;

public interface ISysRoleService {

    Set<String> selectRoleKeys(Long userId);
}
