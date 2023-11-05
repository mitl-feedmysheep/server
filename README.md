# FeedMySheep

### 토큰 로직 (임시)
- 로그인 시, "리프레시토큰"과 "엑세스토큰"을 함께 발행해준다.
- 엑세스 토큰이 완료될 시
  - invalidToken이라는 status와 토큰이 완료되었다라는 메시지를 함께 보낸다.
  - /app/token endpoint로 refresh 토큰을 보내면, 새로운 refreshToken과 accessToken을 발급해준다.
  - 다시 요청한다.
- 리프레시 토큰이 완료될 시
  - 로그인을 다시해야한다.