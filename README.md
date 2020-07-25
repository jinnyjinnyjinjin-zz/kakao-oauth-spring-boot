# kakao-oauth

## Reference
[카카오 로그인](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)

## 프로젝트 설정
`application.properties` 설정

```$shell
kakao.oauth.login.redirectUrl=YOUR_REDIRECT_URI  # 로그인 버튼 누르고 redirect 되는 uri
kakao.oauth.appKey=YOUR_APP_KEY
```
## 프로젝트 실행
#### 1. `http://localhost:8080` 접속
<img src="./images/login.png" width="50%">
   
#### 2. 로그인 버튼을 클릭하면 로그인 화면으로 이동
<img src="./images/login-page.png" width="50%">

#### 3. 로그인 후 인증 코드를 받음
<img src="./images/code.png" width="50%">

#### 4. `http://localhost:8080/swagger-ui.html` 접속
<img src="./images/swagger.png" width="50%">

#### 5. `사용자 토큰 요청` 부터 시도. 필수 파라미터를 채워 넣고 실행.   
#### 6. 실행 후 받아오는 `access_token` 을 가지고 `access token 정보 요청` 실행.  
#### 7. 사용자 정보 response