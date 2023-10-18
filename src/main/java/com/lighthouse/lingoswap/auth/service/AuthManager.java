package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairDetails;
import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.application.InterestsService;
import com.lighthouse.lingoswap.member.application.LanguageService;
import com.lighthouse.lingoswap.member.application.PreferredCountryService;
import com.lighthouse.lingoswap.member.application.UsedLanguageService;
import com.lighthouse.lingoswap.member.domain.model.AuthDetails;
import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.model.Role;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfo;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfo;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.preferredinterests.application.PreferredInterestsManager;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
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
    private final MemberRepository memberRepository;
    private final LanguageService languageService;
    private final UsedLanguageService usedLanguageService;
    private final InterestsService interestsService;
    private final CountryRepository countryRepository;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsManager preferredInterestsManager;
    private final SendbirdService sendbirdService;

    @Transactional
    public ResponseDto<LoginResponse> login(final String idToken) {
        String email = idTokenService.parseIdToken(idToken);
        AuthDetails authDetails = authService.loadUserByUsername(email);
        TokenPairDetails tokenPairDetails = tokenPairService.generateTokenPairDetailsByUsername(email);
        return ResponseDto.success(LoginResponse.of(authDetails.getUuid(), authDetails.getUsername(), tokenPairDetails));
    }

    @Transactional
    public ResponseDto<LoginResponse> signup(final String idToken, final MemberCreateRequest memberCreateRequest) {
        String email = idTokenService.parseIdToken(idToken);
        String uuid = memberCreateRequest.uuid();

        AuthDetails authDetails = new AuthDetails(email, uuid, Role.USER);

        countryRepository.validateExistsByCode(memberCreateRequest.region());

        Member member = new Member(
                memberCreateRequest.birthday(),
                memberCreateRequest.name(),
                memberCreateRequest.description(),
                memberCreateRequest.profileImageUrl(),
                memberCreateRequest.gender(),
                email,
                uuid,
                Role.USER,
                memberCreateRequest.region()
        );
        memberRepository.save(member);

        savePreferredCountries(member, memberCreateRequest.preferredCountries());
        saveUsedLanguages(member, memberCreateRequest.usedLanguages());
        savePreferredInterests(member, memberCreateRequest.preferredInterests());

        sendbirdService.createUser(authDetails.getUuid(), member.getName(), member.getProfileImageUrl());

        TokenPairDetails tokenPairDetails = tokenPairService.generateTokenPairDetailsByUsername(email);
        return ResponseDto.success(LoginResponse.of(uuid, authDetails.getUsername(), tokenPairDetails));
    }

    private void savePreferredCountries(Member member, List<String> codes) {
        codes.stream()
                .map(countryRepository::getByCode)
                .map(p -> new PreferredCountry(member, p))
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
                .forEach(preferredInterestsManager::save);
    }

    @Transactional
    public ResponseDto<TokenPairDetails> reissue(final ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        return ResponseDto.success(tokenPairService.reissue(refreshToken));
    }

}
