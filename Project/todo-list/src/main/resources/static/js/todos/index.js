//처음 로딩시에 작업
{
    document.addEventListener("DOMContentLoaded", function() {
        // 모든 테이블 행 선택
        const rows = document.querySelectorAll('tbody tr');

        // 각 행마다 버튼 스타일을 설정합니다.
        rows.forEach(row => {
            // 실제 데이터베이스에 있는 작업의 상태
            const dbStatus = row.querySelector('.status-text').getAttribute('data-status');
            // 버튼의 작업 상태를 불러옴
            const buttons = row.querySelectorAll(".status-btn");

            // 버튼은 4개 이므로 각각을 모두 비교할 수 있어야함
            buttons.forEach(button => {
                const btnStatus = button.getAttribute("data-status");

                if (btnStatus === dbStatus) {
                    // 데이터베이스에 저장된 상태와 일치하는 버튼의 디자인 변경
                    button.style.backgroundColor = "#6c757d";
                    button.style.color = "#fff";
                    button.disabled = true;
                } else {
                    // 나머지 버튼은 기본 스타일로 초기화
                    button.style.backgroundColor = "";
                    button.style.color = "";
                    button.disabled = false;
                }
            });
        });

        // 모든 상태 버튼에 클릭 이벤트 리스너를 등록합니다.
        const statusButtons = document.querySelectorAll(".status-btn");

        statusButtons.forEach(button => {
            button.addEventListener("click", function() {
                const taskId = this.getAttribute("data-id");
                const newStatus = this.getAttribute("data-status");

                //console.log(`ID ${taskId}의 작업 상태가 ${newStatus}로 변경됩니다.`);

                const task = {
                    id: taskId,
                    status: newStatus
                };

                const url = `/api/todos/updateStatus/${taskId}`;
                fetch(url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(task)
                }).then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        alert("상태 변경 실패!");
                    }
                });
            });
        });
    });
}
// 모달 이벤트 관련
// 수정 모달 띄우기
{
    const taskEditModal = document.querySelector("#task-edit-modal");
    taskEditModal.addEventListener("show.bs.modal", function(event){
        const triggerBtn = event.relatedTarget;

        const id = triggerBtn.getAttribute("data-bs-id"); // 속성 위에가서 추가해주기
        const title = triggerBtn.getAttribute("data-bs-title");
        const status = triggerBtn.getAttribute("data-bs-status");
        const category = triggerBtn.getAttribute("data-bs-category");

        document.querySelector("#edit-task-id").value = id;
        document.querySelector("#edit-task-title").value = title;
        document.querySelector("#edit-task-status").value = status;
        document.querySelector("#edit-task-category").value = category;

    });
}
// 수정 반영
{
    const taskEditBtn = document.querySelector("#task-edit-btn");
    taskEditBtn.addEventListener("click", function(){
        const task = {
            id : document.querySelector("#edit-task-id").value,
            title : document.querySelector("#edit-task-title").value,
            status : document.querySelector("#edit-task-status").value,
            category : document.querySelector("#edit-task-category").value
        }
        console.log(task);

        const url = "/api/todos/editTask/" + task.id;

        fetch(url, {
            method: "PATCH",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(task)
        }).then(response => {
            const msg = (response.ok) ? "수정이 반영되었습니다." : "수정 오류";
            alert(msg);
            window.location.reload();
        })
    });
}
// 작업 삭제
{
    const taskDltBtn = document.querySelectorAll(".task-delete-btn");
    // 삭제 버튼은 작업마다 존재하므로
    taskDltBtn.forEach(btn => {
        btn.addEventListener("click", (event) => {
            const taskDeleteBtn = event.target;
            const taskId = taskDeleteBtn.getAttribute("data-task-id");
            console.log(`${taskId}번 작업 삭제버튼 클릭하였음.`);

            const url = `/api/todos/deleteTask/${taskId}`;
            fetch(url, {
                method: "DELETE"
            }).then(response => {
                if(!response.ok){
                    alert("작업 삭제 실패");
                    return;
                }
                alert("해당 작업을 삭제하였습니다.");
                window.location.reload();
            });
        });
    });
}