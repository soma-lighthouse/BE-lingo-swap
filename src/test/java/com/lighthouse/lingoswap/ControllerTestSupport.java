package com.lighthouse.lingoswap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lighthouse.lingoswap.auth.application.AuthManager;
import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.country.application.CountryManager;
import com.lighthouse.lingoswap.image.application.ImageManager;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.interests.application.InterestsManager;
import com.lighthouse.lingoswap.language.application.LanguageManager;
import com.lighthouse.lingoswap.likemember.application.LikeMemberManager;
import com.lighthouse.lingoswap.match.application.MatchManager;
import com.lighthouse.lingoswap.member.application.MemberManager;
import com.lighthouse.lingoswap.question.application.QuestionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected SendbirdService sendbirdService;

    @MockBean
    protected S3Service s3Service;

    @MockBean
    protected MemberManager memberManager;

    @MockBean
    protected QuestionManager questionManager;

    @MockBean
    protected LikeMemberManager likeMemberManager;

    @MockBean
    protected CountryManager countryManager;

    @MockBean
    protected InterestsManager interestsManager;

    @MockBean
    protected LanguageManager languageManager;

    @MockBean
    protected MatchManager matchManager;

    @MockBean
    protected ImageManager imageManager;

    @MockBean
    protected AuthManager authManager;

}
