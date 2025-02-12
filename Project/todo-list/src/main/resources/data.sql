-- 카테고리 데이터 입력
INSERT INTO category (name) VALUES ('쇼핑');
INSERT INTO category (name) VALUES ('대학');
INSERT INTO category (name) VALUES ('연애');
INSERT INTO category (name) VALUES ('운동');
INSERT INTO category (name) VALUES ('음악');
INSERT INTO category (name) VALUES ('독서');
INSERT INTO category (name) VALUES ('여행');
INSERT INTO category (name) VALUES ('요리');
INSERT INTO category (name) VALUES ('자기계발');
INSERT INTO category (name) VALUES ('취미');

-- todo 데이터 입력

-- 쇼핑 (category_id: 1)
INSERT INTO todo(title, status, category_id) VALUES ('무신사스탠다드 옷보기', '준비', 1);
INSERT INTO todo(title, status, category_id) VALUES ('신발 세일 체크하기', '진행중', 1);
INSERT INTO todo(title, status, category_id) VALUES ('주말 할인 이벤트 미루기', '중단됨', 1);
INSERT INTO todo(title, status, category_id) VALUES ('쿠폰 사용하기', '완료', 1);

-- 대학 (category_id: 2)
INSERT INTO todo(title, status, category_id) VALUES ('공부하기', '준비', 2);
INSERT INTO todo(title, status, category_id) VALUES ('강의 노트 정리', '진행중', 2);
INSERT INTO todo(title, status, category_id) VALUES ('시험 준비 미루기', '중단됨', 2);
INSERT INTO todo(title, status, category_id) VALUES ('과제 제출', '완료', 2);

-- 연애 (category_id: 3)
INSERT INTO todo(title, status, category_id) VALUES ('영화보기', '준비', 3);
INSERT INTO todo(title, status, category_id) VALUES ('데이트 코스 찾기', '진행중', 3);
INSERT INTO todo(title, status, category_id) VALUES ('연락 미루기', '중단됨', 3);
INSERT INTO todo(title, status, category_id) VALUES ('꽃 선물 준비', '완료', 3);

-- 운동 (category_id: 4)
INSERT INTO todo(title, status, category_id) VALUES ('헬스장 등록', '준비', 4);
INSERT INTO todo(title, status, category_id) VALUES ('조깅하기', '진행중', 4);
INSERT INTO todo(title, status, category_id) VALUES ('운동 계획 중단', '중단됨', 4);
INSERT INTO todo(title, status, category_id) VALUES ('스트레칭 하기', '완료', 4);

-- 음악 (category_id: 5)
INSERT INTO todo(title, status, category_id) VALUES ('콘서트 티켓 구매', '준비', 5);
INSERT INTO todo(title, status, category_id) VALUES ('새 앨범 듣기', '진행중', 5);
INSERT INTO todo(title, status, category_id) VALUES ('연습 중단하기', '중단됨', 5);
INSERT INTO todo(title, status, category_id) VALUES ('악기 연습', '완료', 5);

-- 독서 (category_id: 6)
INSERT INTO todo(title, status, category_id) VALUES ('책 한 권 읽기', '준비', 6);
INSERT INTO todo(title, status, category_id) VALUES ('도서관 방문', '진행중', 6);
INSERT INTO todo(title, status, category_id) VALUES ('읽기 중단하기', '중단됨', 6);
INSERT INTO todo(title, status, category_id) VALUES ('독후감 작성', '완료', 6);

-- 여행 (category_id: 7)
INSERT INTO todo(title, status, category_id) VALUES ('여행지 조사하기', '준비', 7);
INSERT INTO todo(title, status, category_id) VALUES ('항공권 예약', '진행중', 7);
INSERT INTO todo(title, status, category_id) VALUES ('여행 계획 중단', '중단됨', 7);
INSERT INTO todo(title, status, category_id) VALUES ('호텔 예약', '완료', 7);

-- 요리 (category_id: 8)
INSERT INTO todo(title, status, category_id) VALUES ('요리책 구매', '준비', 8);
INSERT INTO todo(title, status, category_id) VALUES ('새 레시피 시도', '진행중', 8);
INSERT INTO todo(title, status, category_id) VALUES ('요리 중단하기', '중단됨', 8);
INSERT INTO todo(title, status, category_id) VALUES ('친구 초대 요리하기', '완료', 8);

-- 자기계발 (category_id: 9)
INSERT INTO todo(title, status, category_id) VALUES ('온라인 강의 듣기', '준비', 9);
INSERT INTO todo(title, status, category_id) VALUES ('새 기술 배우기', '진행중', 9);
INSERT INTO todo(title, status, category_id) VALUES ('학습 중단하기', '중단됨', 9);
INSERT INTO todo(title, status, category_id) VALUES ('자기소개서 업데이트', '완료', 9);

-- 취미 (category_id: 10)
INSERT INTO todo(title, status, category_id) VALUES ('사진 찍기', '준비', 10);
INSERT INTO todo(title, status, category_id) VALUES ('수공예 작품 만들기', '진행중', 10);
INSERT INTO todo(title, status, category_id) VALUES ('취미 중단하기', '중단됨', 10);
INSERT INTO todo(title, status, category_id) VALUES ('영화 감상', '완료', 10);
