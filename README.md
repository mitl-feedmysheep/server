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
3. 초기 Data seeding
    - 기본 데이터가 셋팅 초기작업을 위해서 API call로 간단하게 구현했습니다.
    - /data-seeding **"한번"** 만 호출하시면 됩니다. (자세한 내용은 swagger에 나와있습니다.)

---

## 협업툴

1. SwaggerUI
    - Frontend <-> Backend 소통을 위해서 만들었습니다.
    - https://github.com/mitl-feedmysheep/api-spec
    - 해당 프로젝트의 README를 읽어보시면 사용방법을 알 수 있습니다.
2. Postman
    - 함께 사용할 수 있는 Workspace가 있습니다.
    - 초기작업 / 앱 / 어드민 각 폴더가 분리되어 있으며 도메인별로 하위 폴더가 존재합니다.
3. ERD
    - https://www.erdcloud.com/d/7PhCjKPXwjPcS5uiP
4. 지라
    - 태스크를 관리해요.
5. 노션
    - 각종 문서작업을 해요.
6. 슬랙
    - 소통할 때 사용해요.
7. 구글밋 or 줌
    - 일주일에 한번 회의할 때 사용해요.
      **따로 요청하시면 됩니다!**

---

## 테스트 케이스

---
리포지토리 테스트케이스 작성 완료 (2023.11.26)

## 기타 로직

1. 토큰 로직
    - 로그인 시, "refreshToken"과 "accessToken"을 함께 발행해준다.
    - accessToken 완료될 시
        - invalidToken이라는 status와 토큰이 완료되었다라는 메시지를 함께 보낸다.
        - /app/token 으로 refresh 토큰을 보내면, 새로운 refreshToken과 accessToken을 발급해준다.
        - 다시 요청한다.
    - refreshToken 완료될 시
        - 로그인을 다시해야한다.