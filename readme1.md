# 비디오 파일관리 기능

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
- 회원탈퇴
- 회원정보수정
- 로그인
- 로그아웃
- 비디오업로드
- 비디오조회
- 비디오재생

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

## 4. 기능
 - 로그인 : /page/login-view
 - 로그아웃 : /data/logout
 - 회원가입 : /page/join-view
 - 회원정보변경 : /page/edit-user-view
 - 비디오업로드 : /page/video-upload-view
 - 비디오조회 : /page/video-search-view
 - 플레이어 : /page/video-player-view

## 5. 페이지접근 권한
### 5.1. admin
 - 비디오조회 : /page/video-search-view
 - 회원정보변경 : /page/edit-user-view
### 5.2. member
 - 비디오조회 : /page/video-search-view
 - 비디오업로드 : /page/video-upload-view
 - 회원정보변경 : /page/edit-user-view