package com.eadmarket.pangu.domain;

import com.eadmarket.pangu.common.IEnum;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 会员对象
 *
 * @author liuyongpo@gmail.com
 */
@Data
@ToString
public class UserDO {

  public static final int MAIN_SELLER_TYPE = 1;

  /**
   * 会员编号
   */
  private Long id;
  /**
   * 会员昵称
   */
  private String nick;
  /**
   * email
   */
  private String email;
  /**
   * 会员状态
   */
  private UserStatus status;
  /**
   * 会员密码
   */
  private String password;
  /**
   * 账户类型
   */
  private Integer type = MAIN_SELLER_TYPE;
  /**
   * 提现方式
   */
  private Integer payment;
  /**
   * 资金账户用于会员提现
   */
  private String account;
  /**
   * 账户余额，以分为单位
   */
  private Long balance;
  /**
   * 注册时间
   */
  private Date registerDate = new Date();
  /**
   * 头像图片链接
   */
  private String headerUrl = "default.jpg";
  /**
   * 会员评分
   */
  private Long score;

  public boolean isActive() {
    return UserStatus.ACTIVE == status;
  }

  /**
   * 是否注册之后并未激活
   */
  public boolean isUnactive() {
    return UserStatus.REGISTED_UNACTIVE == status;
  }

  public static enum UserStatus implements IEnum {
    REGISTED_UNACTIVE(0, "已注册未激活"),
    ACTIVE(1, "正常");

    @Getter
    private final int code;

    @Getter
    private final String desc;

    private UserStatus(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

  }
}
