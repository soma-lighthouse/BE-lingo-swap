package com.lighthouse.lingoswap.member.dto;


import com.lighthouse.lingoswap.common.dto.BaseResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReadDetailsBaseResponse extends BaseResponse<String> {

    @NotNull
    @Email
    private final String email;

    public ReadDetailsBaseResponse(final String code, final String message, final @NotNull String email) {
        super(code, message, email);
        this.email = email;
    }
}
