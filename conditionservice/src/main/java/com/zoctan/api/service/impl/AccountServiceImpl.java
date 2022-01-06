package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Account;
import com.zoctan.api.mapper.AccountMapper;
import com.zoctan.api.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {
  @Resource private AccountMapper accountMapper;

  @Override
  public List<Account> findWithUsername(String UserNmae) {
    return accountMapper.findWithUsername(UserNmae);
  }
}

