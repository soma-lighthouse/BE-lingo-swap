<p align="middle">
  <img width="200px;" alt="Logo" src="https://github.com/soma-lighthouse/BE-lingo-talk/assets/71017759/9c50ffb7-1bdd-4a21-9042-7d2cba7bb6cc"/>
</p>

<h1 align="middle">LingoTalk</h1>
<p align="middle">글로벌 언어교환 안드로이드 애플리케이션</p>
<p align="middle">
  <a href='https://play.google.com/store/apps/details?id=com.lighthouse.lingo_talk&pli=1&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
    <img width="200px;"; alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/>
  </a>
</p>

## ⚙️ Environment
- Java 17
- SpringBoot 3.0.9
- MySQL 8.1.0

## ✏️ Architecture
<img width="909" alt="image" src="https://github.com/soma-lighthouse/BE-lingo-talk/assets/71017759/dfee304e-d7cf-41d1-8a35-fe670c980980">

- Auth  
  - Spring Security config
  - Verify JWT, Google Id token
  - Generate JWT
- Chat
  - Create user or chatroom on Sendbird using WebClient
- Image
  - Generate s3 pre-signed url
- Common
  - ControllerAdvice
  - Common response dto
  - i18n service using LocaleContext
- Infra
  - AWS SDK config

## 🔀 Infra
<img width="708" alt="Architecture" src="https://github.com/soma-lighthouse/BE-lingo-talk/assets/71017759/570ee417-1214-4f2a-b404-22a775903ae1">
