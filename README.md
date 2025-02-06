# 투두 리스트 토이 프로젝트

> **투두 리스트 토이 프로젝트**는 간단한 할 일 관리 애플리케이션으로, Spring Boot 3(Java, Mustache)를 기반으로 개발되었습니다.

---

## 목차
- [사용 기술 스택](#사용-기술-스택)
- [업데이트 태그 기준](#업데이트-태그-기준)
- [업데이트 내용](#업데이트-내용)
  - [2025/02/06](#20250206)
  - [2025/02/05](#20250205)
  - [2025/02/04](#20250204)
- [개발 예정 기능](#개발-예정-기능)
- [향후 업데이트 계획](#향후-업데이트-계획)

---

## 사용 기술 스택
- **Spring Boot 3**  
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
  
- **Java**  
  ![Java](https://img.shields.io/badge/Java-A0522D?style=for-the-badge&logo=java&logoColor=white)
  
- **Mustache 템플릿 엔진**  
  ![Mustache](https://img.shields.io/badge/Mustache-800000?style=for-the-badge&logo=https%3A%2F%2Fimg.icons8.com%2Fios-filled%2F50%2F000000%2Fmoustache.png&logoWidth=15&logoColor=white)

---

## 업데이트 태그 기준
- **Major:** 새로운 틀의 생성이나 주요 기능 추가
- **Minor:** 세부 기능 추가
- **Patch:** 버그 수정, 주석, README 등 문서 수정

---

## 업데이트 내용

### 2025/02/06 {#20250206}
- **작업상태 기준 목록 정렬로 뷰페이지 변화**
- JavaScript 파일 분리
- 일반 컨트롤러 → 서비스 위임 구축 *(미완성)*

### 2025/02/05 {#20250205}
- 라디오버튼 표시 기능 폐기 → 일반 버튼 채택
- 작업 상태 버튼 표시 기능 *(완성)*
- 목록 순서 섞임 버그 수정
- 패치노트 및 개발자 정보 링크 연결 추가
- 수정 기능 추가
- 삭제 기능 추가

### 2025/02/04 {#20250204}
- **기본 틀 완성**
  - 엔티티, 리포지토리, DTO, 컨트롤러 기본 코드 작성
  - 헤더, 푸터 기본 레이아웃 작성
- 작업 상태 라디오 버튼 추가
- 작업 만들기 기능 추가 *(초기)*
- API 컨트롤러 기본 구현
- 서비스 분리 기본 구현
- ~~작업 상태 표시 변경 기능 *(미완성)*~~

---

## 개발 예정 기능
- 작업 마감 날짜 표시
- UI 개선
- 사용자 인증 기능
- 작업 우선순위 지정
- 일부 사용자화 가능한 정렬 방식
- 작업 카테고리화

---

## 향후 업데이트 계획
- 작업 상태별 개수 분류

---

> **참고:** 프로젝트의 진행 상황과 업데이트 계획은 수시로 변경될 수 있으니 최신 정보를 확인해 주세요.
