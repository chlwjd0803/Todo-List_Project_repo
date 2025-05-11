// calendar.js
document.addEventListener("DOMContentLoaded", function() {
    // ① 달력을 그릴 DOM 선택
    const calendarEl = document.getElementById("calendar");

    // ② FullCalendar 인스턴스 생성
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',    // 월간 보기
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek'
        },
        // ③ 이벤트 데이터 로드 방식
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('/api/todos/calendar/events')  // 서버에서 JSON 받아오기
                .then(res => res.json())
                .then(data => successCallback(data))
                .catch(err => failureCallback(err));
        },
        // ④ 이벤트 클릭 시 동작 예시
        eventClick: function(info) {
            alert(
                '할 일: ' + info.event.title + '\n' +
                '날짜: ' + info.event.startStr
            );
        }


    });

    // ⑤ 달력 화면에 렌더링
    calendar.render();
});
