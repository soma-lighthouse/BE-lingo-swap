package com.lighthouse.lingoswap.common.fixture;

import com.lighthouse.lingoswap.member.domain.model.Gender;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.model.Role;

import java.time.LocalDate;

public class MemberFixture {

    public static final LocalDate USER_BIRTHDAY = LocalDate.of(1997, 3, 28);
    public static final String USER_NAME = "Lee";
    public static final String USER_DESCRIPTION = "Hello";
    public static final String USER_PROFILE_IMAGE_URL = "/a142abb1-050a-4178-9564-9984109eded1/test.webp";
    public static final Gender USER_GENDER = Gender.MALE;
    public static final String USER_USERNAME = "abc123@naver.com";
    public static final String USER_UUID = "a142abb1-050a-4178-9564-9984109eded1";
    public static final Role USER_ROLE = Role.USER;
    public static final String USER_REGION = "kr";

    public static Member user() {
        return Member.builder()
                .birthday(USER_BIRTHDAY)
                .name(USER_NAME)
                .description(USER_DESCRIPTION)
                .profileImageUrl(USER_PROFILE_IMAGE_URL)
                .gender(USER_GENDER)
                .username(USER_USERNAME)
                .uuid(USER_UUID)
                .role(USER_ROLE)
                .region(USER_REGION)
                .build();
    }

}
