package etf.bg.diplomski.user;


import etf.bg.diplomski.common.UserStatus;

public record AppUserDTO(
    Long id,
    String first_name,
    String last_name,
    String address,
    String mail,
    String phone,
    String imgUrl,
    UserStatus status) {}
