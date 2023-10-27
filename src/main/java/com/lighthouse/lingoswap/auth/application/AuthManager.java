package com.lighthouse.lingoswap.auth.application;

import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.language.domain.repository.LanguageRepository;
import com.lighthouse.lingoswap.member.domain.model.AuthDetails;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.model.Role;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfoDto;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfoDto;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import com.lighthouse.lingoswap.usedlanguage.domain.repository.UsedLanguageRepository;
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
    private final LanguageRepository languageRepository;
    private final UsedLanguageRepository usedLanguageRepository;
    private final InterestsRepository interestsRepository;
    private final CountryRepository countryRepository;
    private final PreferredCountryRepository preferredCountryRepository;
    private final PreferredInterestsRepository preferredInterestsRepository;
    private final SendbirdService sendbirdService;

    @Transactional
    public LoginResponse login(final String idToken) {
        String email = idTokenService.parseIdToken(idToken);
        AuthDetails authDetails = authService.loadUserByUsername(email);
        TokenPairInfoResponse tokenPairInfoResponse = tokenPairService.generateTokenPairDetailsByUsername(email);
        return LoginResponse.of(authDetails.getUuid(), authDetails.getUsername(), tokenPairInfoResponse);
    }

    @Transactional
    public LoginResponse signup(final String idToken, final MemberCreateRequest memberCreateRequest) {
        String email = idTokenService.parseIdToken(idToken);
        String uuid = memberCreateRequest.uuid();

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

        sendbirdService.createUser(member.getUuid(), member.getName(), member.getProfileImageUrl());

        TokenPairInfoResponse tokenPairInfoResponse = tokenPairService.generateTokenPairDetailsByUsername(email);
        return LoginResponse.of(uuid, member.getUsername(), tokenPairInfoResponse);
    }

    private void savePreferredCountries(Member member, List<String> codes) {
        codes.stream()
                .map(countryRepository::getByCode)
                .map(c -> new PreferredCountry(member, c))
                .forEach(preferredCountryRepository::save);
    }

    private void saveUsedLanguages(Member member, List<UsedLanguageInfoDto> usedLanguageInfoDtos) {
        usedLanguageInfoDtos.stream()
                .map(dto -> {
                    Language language = languageRepository.getByCode(dto.code());
                    return new UsedLanguage(member, language, dto.level());
                })
                .forEach(usedLanguageRepository::save);
    }

    private void savePreferredInterests(Member member, List<PreferredInterestsInfoDto> preferredInterestsInfoDtos) {
        preferredInterestsInfoDtos.stream()
                .flatMap(userInterestsByDto -> userInterestsByDto.interests().stream())
                .map(interestsRepository::getByName)
                .map(interest -> new PreferredInterests(member, interest))
                .forEach(preferredInterestsRepository::save);
    }

    @Transactional
    public TokenPairInfoResponse reissue(final ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        return tokenPairService.reissue(refreshToken);
    }

}
