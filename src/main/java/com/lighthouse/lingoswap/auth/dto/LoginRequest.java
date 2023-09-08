package com.lighthouse.lingoswap.auth.dto;

import com.lighthouse.lingoswap.auth.entity.OAuthProvider;
import jakarta.validation.constraints.Email;

public record LoginRequest(OAuthProvider provider, @Email String email) {

}
