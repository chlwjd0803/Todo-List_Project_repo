# 투두 리스트 토이 프로젝트

> **투두 리스트 토이 프로젝트**는 간단한 할 일 관리 애플리케이션으로, Spring Boot 3(Java, Mustache)를 기반으로 개발되었습니다.

---

## 목차
- [사용 기술 스택](#사용-기술-스택)
- [업데이트 태그 기준](#업데이트-태그-기준)
- [테스트 방법](#테스트-방법)
- [업데이트 내용](#업데이트-내용)
  - [2025/02/14](#20250214)
  - [2025/02/13](#20250213)
  - [2025/02/12](#20250212)
  - [2025/02/11](#20250211)
  - [2025/02/10](#20250210)
  - [2025/02/07](#20250207)
  - [2025/02/06](#20250206)
  - [2025/02/05](#20250205)
  - [2025/02/04](#20250204)
- [개발 예정 기능](#개발-예정-기능)
- [향후 업데이트 계획](#향후-업데이트-계획)

---

## 사용 기술 스택 
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
  
  ![Java](https://img.shields.io/badge/Java-A0522D?style=for-the-badge&logo=java&logoColor=white)
   
  ![Mustache](https://img.shields.io/badge/Mustache-800000?style=for-the-badge&logo=https%3A%2F%2Fimg.icons8.com%2Fios-filled%2F50%2F000000%2Fmoustache.png&logoWidth=15&logoColor=white)

---

## 업데이트 태그 기준
- **Major:** 새로운 틀의 생성이나 주요 기능 추가
- **Minor:** 세부 기능 추가
- **Patch:** 버그 수정, 주석, README 등 문서 수정

---

## 테스트 방법
- /todos/start : 시작 페이지
- /todos/index : 투두리스트 본 페이지
- /todos/calendar : 달력 페이지
- 네비게이션 바를 통해 이동가능

---

## 업데이트 내용

### 20250214
- 카테고리 추가기능 추가
- mustache파일 반복되는 부분 최적화(StatusGroup DTO 추가)

### 20250213
- 카테고리 수정기능 추가
- 카테고리 전체를 선택하였을 경우 수정버튼 비활성화(카테고리를 모두 지웠을때의 버그 발견)
- 카테고리 삭제기능 추가
- 카테고리 삭제 시 하위 작업들도 삭제 기능 추가
- 카테고리가 없을 때 전체 선택버튼에서 수정버튼이 활성화 되어있는 버그 수정
- 작업 전체 삭제기능 추가(카테고리 전체버튼 누른 후 삭제 클릭)

### 20250212
- **카테고리 상위 엔티티화**
- 카테고리 버튼이 계속 이동되는 버그 수정
- 작업추가, 작업수정 로직 리펙터링
- 작업추가 컨테이너 하단고정, 호버기능 추가
- 작업목록 스크롤바 추가
- data.sql 초기 데이터 추가(테스트용)

### 20250211
- 카테고리 필터링과 상태숨김이 새로고침 시 풀리는 버그 수정
- 카테고리 상위 엔티티화 작업 착수(backend-test1 브랜치에서 작업)


### 20250210
- **카테고리 필터링 기능 구현**
- 홈화면 뷰페이지 구현(개발 중)
- 네비게이션 바 UI 개선
- 달력페이지 기초 구현(개발 중)


### 20250207
- ID 번호 항목표시 삭제 -> 관리버튼으로 대체
- 카테고리 필터링(체크박스) -> 카테고리 선택버튼(라디오 혹은 일반버튼)으로 계획 변경
- 뷰 페이지 UI 개선
- CSS파일 분리

### 20250206
- **작업상태 기준 목록 정렬로 뷰페이지 변화**
- JavaScript 파일 분리
- 일반 컨트롤러 → 서비스 위임 구축 *(미완성)*
- 카테고리 필드를 백엔드 코드에 추가
- 작업추가에 카테고리 지정 기능 추가
- 작업수정에 카테고리 수정 기능 추가
- 카테고리 필터링 기능 버튼 구현 *(미완성)*

### 20250205
- 라디오버튼 표시 기능 폐기 → 일반 버튼 채택
- 작업 상태 버튼 표시 기능 *(완성)*
- 목록 순서 섞임 버그 수정
- 패치노트 및 개발자 정보 링크 연결 추가
- 수정 기능 추가
- 삭제 기능 추가

### 20250204
- **기본 틀 완성**
  - 엔티티, 리포지토리, DTO, 컨트롤러 기본 코드 작성
  - 헤더, 푸터 기본 레이아웃 작성
- 작업 상태 ~~라디오 버튼~~ 추가
- 작업 만들기 기능 추가 *(초기)*
- API 컨트롤러 기본 구현
- 서비스 분리 기본 구현
- ~~작업 상태 표시 변경 기능 *(미완성)*~~

---

## 개발 예정 기능
- UI 개선(지속)
- 회원가입 / 로그인 기능
- 작업 우선순위 지정
- 일부 사용자화 가능한 정렬 방식
- ~~통계 페이지 구현~~
- 홈화면 뷰페이지 개선
- 작업 마감날짜 추가
- 작업 검색기능 추가

---

## 향후 업데이트 계획
- 삭제하기 전 되묻기 기능 추가
- 다크모드 지원

---

> **참고:** 프로젝트의 진행 상황과 업데이트 계획은 수시로 변경될 수 있으니 최신 정보를 확인해 주세요.
