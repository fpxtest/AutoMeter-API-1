package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
public interface AccountMapper extends MyMapper<Account> {
  /**
   * 获取所有用户以及对应角色
   *
   * @return 用户列表
   */
  List<Account> findWithUsername(@Param("name") String name);

}
