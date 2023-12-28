[POST MAN Document](https://documenter.getpostman.com/view/21641910/2s9YXfahxL)

## CORS 에러 해결 과정

```text
1. Webconfig 파일을 추가해서 전역 설정으로 해결함
2. Nginx를 사용하고 있는데 전역 설정을 하는게 마음에 안 들어서, Webconfig 제거
3. nginx.conf 에서 tabihoudai-api proxy 설정을 해줌
4. tenki-api도 돌아가야 하기 때문에 conf.d 폴더를 만들어서 설정을 추가해줌
5. proxy 설정만 해줬을때는 Get 요청은 잘 처리함
6. post man으로 테스트 할때는 post 요청을 바로 처리했는데, 브라우저에서 요청할때는 OPTIONS로 먼저 요청해서 임시로 테스트 해본뒤 실제 요청을 하는것을 알게됨
7. 그래서 location / { } 부분을 아래처럼 수정함
8. OPTIONS 요청도 허용해줘서 CORS 에러를 해결함
```

```shell
location / {
 45             if ($request_method = 'OPTIONS') {
 46             add_header 'Access-Control-Allow-Origin' '*';
 47             add_header 'Access-Control-Allow-Methods' 'GET, POST, DELETE, PATCH, OPTIONS';
 48             add_header 'Access-Control-Allow-Headers' 'Content-Type, Authorization';
 49             add_header 'Access-Control-Max-Age' 86400;
 50             return 204;
 51             }
 52             root   html;
 53             index  index.html index.htm;
 54             add_header 'Access-Control-Allow-Origin' '*';
 55             add_header 'Access-Control-Allow-Methods' 'GET, POST, DELETE';
 56             add_header 'Access-Control-Allow-Headers' 'DNT,User-Agent,X-Requested-With,If-Modifie    d-Since,Cache-Control,Content-Type,Range,Authorization';
 57             add_header 'Access-Control-Allow-Credentials' 'true';
 58             add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
 59             proxy_pass http://localhost:2094;
 60             proxy_set_header X-Real-IP $remote_addr;
 61             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 62             proxy_set_header Host $http_host;
 63         }
```

# tabihoudai_API
おすすめの観光地を紹介し、旅行の計画を作成し、コミュニティで意見を口合えるサービスのAPI

## Wiki 탭 참고

* 프로젝트 세팅 및 기타 사항들 기재

## Issues 등록

* 해결못하는 문제는 issue 열어서 등록할것

## commit 규칙

* 코드 정렬 (intelliJ 기본 정렬), 주석, 변수명, 함수명 (카멜케이스) 규칙 꼭 지킬것
* 기능 단위를 먼저 나누고 그 기능을 나눠서 커밋, 푸시 할것
* 메세지에 "단위: 작업내용"으로 작성할것
* 푸시 할때는 기능이 만들어지면 (여러개의 커밋 단위가 쌓이면) 푸시할것

> EX) BOARD: Entity, DTO 작성

## 폴더 규칙

* controller, dto, repository 등의 폴더 안에 board / admin / attraction / plan / users로 나눠서 작업할것

```
tabihoudai api
    > controller
        > admin
        > attraction
        > plan
        > users
        > board
```