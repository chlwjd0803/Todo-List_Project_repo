document.addEventListener("DOMContentLoaded", function() {
    // 달력을 생성하는 함수
    function generateCalendar(year, month) {
        const calendarBody = document.getElementById('calendarBody');
        calendarBody.innerHTML = "";  // 기존 내용 초기화
        const monthYear = document.getElementById('monthYear');

        // 현재 연도와 월에 따른 Date 객체 생성
        const date = new Date(year, month);
        monthYear.textContent = date.toLocaleDateString('ko-KR', { year: 'numeric', month: 'long' });

        // 이번 달의 첫째 날의 요일(0: 일요일, 1: 월요일, ...) 구하기
        const firstDay = new Date(year, month, 1).getDay();
        // 이번 달의 마지막 날짜 구하기
        const daysInMonth = new Date(year, month + 1, 0).getDate();

        let dateCount = 1;
        // 최대 6주(행)를 그리되, 달력이 끝나면 종료
        for (let i = 0; i < 6; i++) {
            let row = document.createElement("tr");
            for (let j = 0; j < 7; j++) {
                let cell = document.createElement("td");
                cell.classList.add("text-center");
                // 첫 행에서 첫 날 이전은 빈 칸 처리
                if (i === 0 && j < firstDay) {
                    cell.textContent = "";
                } else if (dateCount > daysInMonth) {
                    cell.textContent = "";
                } else {
                    cell.innerHTML = "<span>" + dateCount + "</span>";
                    dateCount++;
                }
                row.appendChild(cell);
            }
            calendarBody.appendChild(row);
            if (dateCount > daysInMonth) break;
        }
    }

    // 초기 날짜: 오늘 기준
    let today = new Date();
    let currentYear = today.getFullYear();
    let currentMonth = today.getMonth();

    // 처음 달력 생성
    generateCalendar(currentYear, currentMonth);

    // 이전 달 버튼 이벤트
    document.getElementById('prevMonth').addEventListener('click', function() {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        generateCalendar(currentYear, currentMonth);
    });

    // 다음 달 버튼 이벤트
    document.getElementById('nextMonth').addEventListener('click', function() {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        generateCalendar(currentYear, currentMonth);
    });

    // function generateCalendar(year, month) {
    //     const calendarBody = document.getElementById('calendarBody');
    //     calendarBody.innerHTML = "";  // 기존 내용 초기화
    //     const monthYear = document.getElementById('monthYear');
    //
    //     // 현재 연도와 월에 따른 Date 객체 생성
    //     const date = new Date(year, month);
    //     monthYear.textContent = date.toLocaleDateString('ko-KR', { year: 'numeric', month: 'long' });
    //
    //     // 이번 달의 첫째 날의 요일과 마지막 날짜 구하기
    //     const firstDay = new Date(year, month, 1).getDay();
    //     const daysInMonth = new Date(year, month + 1, 0).getDate();
    //
    //     let dateCount = 1;
    //     // 최대 6주(행)를 그리되, 달력이 끝나면 종료
    //     for (let i = 0; i < 6; i++) {
    //         let row = document.createElement("tr");
    //         for (let j = 0; j < 7; j++) {
    //             let cell = document.createElement("td");
    //             cell.classList.add("text-center");
    //             // 첫 행에서 첫 날 이전은 빈 칸 처리
    //             if (i === 0 && j < firstDay) {
    //                 cell.textContent = "";
    //             } else if (dateCount > daysInMonth) {
    //                 cell.textContent = "";
    //             } else {
    //                 // 날짜 표시
    //                 cell.innerHTML = "<span>" + dateCount + "</span>";
    //
    //                 // 현재 셀에 해당하는 날짜 객체 생성
    //                 const cellDate = new Date(year, month, dateCount);
    //
    //                 // tasks 배열에서 해당 날짜와 deadline이 일치하는 작업들을 필터링
    //                 const tasksForDate = tasks.filter(task => {
    //                     const taskDate = new Date(task.deadline);
    //                     return taskDate.getFullYear() === cellDate.getFullYear() &&
    //                         taskDate.getMonth() === cellDate.getMonth() &&
    //                         taskDate.getDate() === cellDate.getDate();
    //                 });
    //
    //                 // 만약 해당 날짜에 작업이 있다면 표시
    //                 if (tasksForDate.length > 0) {
    //                     let taskList = document.createElement("div");
    //                     taskList.classList.add("task-list");
    //                     tasksForDate.forEach(task => {
    //                         let taskItem = document.createElement("div");
    //                         taskItem.classList.add("task-item");
    //                         taskItem.textContent = task.title;
    //                         taskList.appendChild(taskItem);
    //                     });
    //                     // 날짜 숫자 아래쪽에 작업 리스트를 추가
    //                     cell.appendChild(taskList);
    //                 }
    //                 dateCount++;
    //             }
    //             row.appendChild(cell);
    //         }
    //         calendarBody.appendChild(row);
    //         if (dateCount > daysInMonth) break;
    //     }
    // }

});