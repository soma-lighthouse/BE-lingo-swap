package com.lighthouse.lingoswap.auth.application;

import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.common.util.UuidHolder;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.member.domain.model.AuthDetails;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.model.Role;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthManager {

    private final AuthService authService;
    private final TokenPairService tokenPairService;
    private final IdTokenService idTokenService;
    private final MemberRepository memberRepository;
    private final InterestsRepository interestsRepository;
    private final CountryRepository countryRepository;
    private final PreferredCountryRepository preferredCountryRepository;
    private final PreferredInterestsRepository preferredInterestsRepository;
    private final SendbirdService sendbirdService;
    private final UuidHolder uuidHolder;

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
        String uuid = uuidHolder.randomUuid();

        Member member = new Member(
                memberCreateRequest.birthday(),
                memberCreateRequest.name(),
                memberCreateRequest.description().trim(),
                memberCreateRequest.profileImageUri(),
                memberCreateRequest.gender(),
                email,
                uuid,
                Role.USER,
                memberCreateRequest.region()
        );
        memberRepository.save(member);
        savePreferredCountries(member, memberCreateRequest.preferredCountries());
        savePreferredInterests(member, memberCreateRequest.preferredInterests());

        sendbirdService.createUser(member.getUuid(), member.getName(), member.getProfileImageUri());

        TokenPairInfoResponse tokenPairInfoResponse = tokenPairService.generateTokenPairDetailsByUsername(email);
        log.info("SignUp User: {}", member);
        return LoginResponse.of(uuid, member.getUsername(), tokenPairInfoResponse);
    }

    private void savePreferredCountries(final Member member, final List<String> codes) {
        codes.stream()
                .map(countryRepository::getByCode)
                .map(c -> new PreferredCountry(member, c))
                .forEach(preferredCountryRepository::save);
    }

    private void savePreferredInterests(final Member member, final List<String> names) {
        List<Interests> interests = interestsRepository.findAllByNameIn(names);
        List<PreferredInterests> preferredInterests = interests.stream().map(i -> new PreferredInterests(member, i)).toList();
        preferredInterestsRepository.saveAll(preferredInterests);
    }

    @Transactional
    public TokenPairInfoResponse reissue(final ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        return tokenPairService.reissue(refreshToken);
    }

}
