-- 카테고리 데이터 입력
INSERT INTO category (name) VALUES ('쇼핑');
INSERT INTO category (name) VALUES ('대학');
INSERT INTO category (name) VALUES ('연애');
INSERT INTO category (name) VALUES ('운동');
INSERT INTO category (name) VALUES ('음악');

-- todo 데이터 입력

-- 쇼핑 (category_id: 1)
INSERT INTO todo(title, status, category_id, deadline) VALUES ('무신사스탠다드 옷보기', '준비', 1, '2025-02-23');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('신발 세일 체크하기', '진행중', 1, '2025-02-20');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('주말 할인 이벤트 미루기', '중단됨', 1, '2025-02-22');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('쿠폰 사용하기', '완료', 1, '2025-02-21');

-- 대학 (category_id: 2)
INSERT INTO todo(title, status, category_id, deadline) VALUES ('공부하기', '준비', 2, '2025-03-03');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('강의 노트 정리', '진행중', 2, '2025-03-01');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('시험 준비 미루기', '중단됨', 2, '2025-03-04');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('과제 제출', '완료', 2, '2025-03-02');

-- 연애 (category_id: 3)
INSERT INTO todo(title, status, category_id, deadline) VALUES ('영화보기', '준비', 3, '2025-04-12');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('데이트 코스 찾기', '진행중', 3, '2025-04-10');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('연락 미루기', '중단됨', 3, '2025-04-13');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('꽃 선물 준비', '완료', 3, '2025-04-11');

-- 운동 (category_id: 4)
INSERT INTO todo(title, status, category_id, deadline) VALUES ('헬스장 등록', '준비', 4, '2025-05-07');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('조깅하기', '진행중', 4, '2025-05-05');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('운동 계획 중단', '중단됨', 4, '2025-05-08');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('스트레칭 하기', '완료', 4, '2025-05-06');

-- 음악 (category_id: 5)
INSERT INTO todo(title, status, category_id, deadline) VALUES ('콘서트 티켓 구매', '준비', 5, '2025-06-17');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('새 앨범 듣기', '진행중', 5, '2025-06-15');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('연습 중단하기', '중단됨', 5, '2025-06-18');
INSERT INTO todo(title, status, category_id, deadline) VALUES ('악기 연습', '완료', 5, '2025-06-16');
