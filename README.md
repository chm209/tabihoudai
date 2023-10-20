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