# FeedMySheep

---

## 로컬 셋팅

1. 로컬 테스트 DB 셋팅
    1. docker 설치
    2. mysql 8.0 image 설치
       ```
       docker pull mysql:8.0
       ```
    3. docker mysql container 백그라운드 실행
       ```
       docker run --name feed_my_sheep -e MYSQL_ROOT_PASSWORD=feedmysheep -e MYSQL_DATABASE=feed_my_sheep -d -p 3306:3306 mysql:8.0
       ```

2. 서버 구동
    1. profile 분리하여 실행 (intelliJ)
       ```
       // application-local.yml 파일을 읽게 설정합니다.   
       active profiles >> local
       ```

---

## 기타

1. 토큰 로직
    - 로그인 시, "refreshToken"과 "accessToken"을 함께 발행해준다.
    - accessToken 완료될 시
        - invalidToken이라는 status와 토큰이 완료되었다라는 메시지를 함께 보낸다.
        - /app/token 으로 refresh 토큰을 보내면, 새로운 refreshToken과 accessToken을 발급해준다.
        - 다시 요청한다.
    - refreshToken 완료될 시
        - 로그인을 다시해야한다.