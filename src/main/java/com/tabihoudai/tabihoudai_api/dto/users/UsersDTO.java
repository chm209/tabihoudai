package com.tabihoudai.tabihoudai_api.dto.users;

import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Setter
@Builder
@ToString
public class UsersDTO {

    private String pw;

    private String email;

    private java.sql.Date birthday;

    private String profile;

}
