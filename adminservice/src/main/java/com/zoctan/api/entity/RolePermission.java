package com.zoctan.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Data
@Table(name = "role_permission")
public class RolePermission {
  /** 角色Id */
  @Id
  @Column(name = "role_id")
  private Long roleId;


  /**
   * 获取字典项值
   *
   * @return roleId - 字典项值
   */
  public Long getroleId() {
    return roleId;
  }

  /**
   * 设置字典项值
   *
   * @param roleId 字典项值
   */
  public void setroleId(Long roleId) {
    this.roleId = roleId;
  }

  /** 权限Id */
  @Column(name = "permission_id")
  private Long permissionId;
}
