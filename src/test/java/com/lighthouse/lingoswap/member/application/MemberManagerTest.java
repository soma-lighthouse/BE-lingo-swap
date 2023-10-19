package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.common.support.IntegrationTestSupport;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class MemberManagerTest extends IntegrationTestSupport {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void getPreference() {
    }

    @Test
    void createPreSignedUrl() {
    }

    @Test
    void updatePreference() {
    }

    @Test
    void updatePreferredCountries() {
    }

    @Test
    void updateUsedLanguages() {
    }

    @Test
    void updatePreferredInterests() {
    }

    @Test
    void readCountryForm() {
    }

    @Test
    void readLanguageForm() {
    }

    @Test
    void updateProfile() {
    }

}
