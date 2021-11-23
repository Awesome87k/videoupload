# 비디오 파일관리 기능

## 머릿말

## 1.프로젝트 구성
 - Spring Boot 
 - Spring Security
 - JPA
 - QueryDSL
 - redis ( 실 서비스에서 토큰을 관리할 수 있도록 세팅만구성 )
 - JTWToken
 - Mysql

## 2.기능
- 회원가입
- 로그인
- 로그아웃
- 회원탈퇴 (api만 구성되어있습니다.)
- 비디오업로드
- 비디오조회

## 3. 설정
### 3.1. DB
> ERD
 - src/main/resources/erd/video_uploaDB.mwb
### 3.2.application-local.yml
> application-local.yml
 - spring.datasource.jdbcUrl 설정필요
 - spring.datasource.username 설정필요
 - spring.datasource.password 설정필요
 - servlet.multipart.location: 설정필요 (현재설정 - D:\test\video\)

## 4. 접근페이지
 - 로그인 : /page/login-view
 - 회원가입 : /page/join-view
 - 업로드 : /page/video-upload-view
 - 파일조회 : /page/video-search-view
 - 플레이어 : /page/video-player-view

## 5. 사용자 권한
 - admin(A) : !@#!@@!#메뉴나열
 - member(M) : !@#!@#!@#메뉴나열
