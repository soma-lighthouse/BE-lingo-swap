package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairDetails;
import com.lighthouse.lingoswap.auth.entity.AuthDetails;
import com.lighthouse.lingoswap.auth.entity.Role;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.service.SendbirdService;
import com.lighthouse.lingoswap.member.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfo;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfo;
import com.lighthouse.lingoswap.member.entity.*;
import com.lighthouse.lingoswap.member.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthManager {

    private final AuthService authService;
    private final TokenPairService tokenPairService;
    private final GoogleIdTokenService idTokenService;
    private final MemberService memberService;
    private final LanguageService languageService;
    private final UsedLanguageService usedLanguageService;
    private final InterestsService interestsService;
    private final CountryService countryService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsService preferredInterestsService;
    private final SendbirdService sendbirdService;

    @Transactional
    public ResponseDto<LoginResponse> login(final String idToken) {
        String email = idTokenService.parseIdToken(idToken);
        AuthDetails authDetails = authService.loadUserByUsername(email);
        TokenPairDetails tokenPairDetails = tokenPairService.generateTokenPairDetailsByUsername(email);
        return ResponseDto.success(LoginResponse.of(authDetails.getUuid(), tokenPairDetails));
    }

    @Transactional
    public ResponseDto<LoginResponse> signup(final String idToken, final MemberCreateRequest memberCreateRequest) {
        String email = idTokenService.parseIdToken(idToken);
        String uuid = memberCreateRequest.uuid();

        AuthDetails authDetails = new AuthDetails(email, uuid, Role.USER);

        Country country = countryService.findCountryByCode(memberCreateRequest.region());
        Member member = new Member(
                memberCreateRequest.birthday(),
                memberCreateRequest.name(),
                memberCreateRequest.description(),
                memberCreateRequest.profileImageUri(),
                memberCreateRequest.gender(),
                authDetails,
                country
        );
        memberService.save(member);

        savePreferredCountries(member, memberCreateRequest.preferredCountries());
        saveUsedLanguages(member, memberCreateRequest.usedLanguages());
        savePreferredInterests(member, memberCreateRequest.preferredInterests());

        SendbirdCreateUserRequest sendbirdCreateUserRequest = new SendbirdCreateUserRequest(authDetails.getUuid(), member.getName(), member.getProfileImageUri());
        sendbirdService.createUser(sendbirdCreateUserRequest);

        TokenPairDetails tokenPairDetails = tokenPairService.generateTokenPairDetailsByUsername(email);
        return ResponseDto.success(LoginResponse.of(uuid, tokenPairDetails));
    }

    private void savePreferredCountries(Member member, List<String> preferredCountries) {
        preferredCountries.stream()
                .map(countryService::findCountryByCode)
                .map(preferredCountry -> new PreferredCountry(member, preferredCountry))
                .forEach(preferredCountryService::save);
    }

    private void saveUsedLanguages(Member member, List<UsedLanguageInfo> usedLanguageInfos) {
        usedLanguageInfos.stream()
                .map(lang -> {
                    Language language = languageService.findLanguageByCode(lang.code());
                    return new UsedLanguage(member, language, lang.level());
                })
                .forEach(usedLanguageService::save);
    }

    private void savePreferredInterests(Member member, List<PreferredInterestsInfo> preferredInterestsInfos) {
        preferredInterestsInfos.stream()
                .flatMap(userInterestsByDto -> userInterestsByDto.interests().stream())
                .map(interestsService::findByName)
                .map(interest -> new PreferredInterests(member, interest))
                .forEach(preferredInterestsService::save);
    }

    @Transactional
    public ResponseDto<TokenPairDetails> reissue(final ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        return ResponseDto.success(tokenPairService.reissue(refreshToken));
    }
}
