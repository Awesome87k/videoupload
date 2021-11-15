#비디오 파일관리 기능
## 머릿말
 안녕하세요 금번 입사 지원한 채승주 입니다.
 현재직중인 회사의 프로젝트이 딜레이됨에 따라 야근 및 주말 출근으로 테스트 프로젝트 구현 일정이 겹치게되어
 회원수정기능 미개발, 각 화면UI 미구현, 테스트코드 미구현 등 급박하게 구현되어 과제의 전체적인 완성도가 떨어지는점 양해부탁드립니다. 
 감사합니다.
##1.프로젝트 구성
 - Spring Boot 
 - Spring Security
 - JPA
 - QueryDSL
 - redis ( 실 서비스에서 토큰을 관리할 수 있도록 세팅만구성 )
 - JTWToken
 - Mysql
##2.기능
- 회원가입
- 로그인
- 로그아웃
- 회원탈퇴 (api만 구성되어있습니다.)
- 비디오업로드
- 비디오조회
##3. 설정
### 3.1. DB
> ERD
 - src/main/resources/erd/video_uploaDB.mwb
### 3.2.application-local.yml
> application-local.yml 
 - servlet.multipart.location : ${projectPath}/**static**/${uploadPath}/
 - spring.datasource.jdbcUrl 설정필요
 - spring.datasource.username 설정필요
 - spring.datasource.password 설정필요