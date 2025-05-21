{
    document.addEventListener("DOMContentLoaded", function() {
        // Collapse 상태 복원 및 이벤트 등록 (먼저 실행)
        const collapseIds = ["collapse-준비", "collapse-완료"];
        collapseIds.forEach(id => {
            // 해당 상태(id)에 따라 요소를 가져오는것 -> 저 id속성이 붙어있는 태그를 참조함
            const collapseEl = document.getElementById(id);
            // 저장되어있던 상태를 가져옴
            const savedState = localStorage.getItem(id);
            // 클래스 추가와 제거 과정
            if (savedState === "hidden") {
                collapseEl.classList.remove("show");
            } else {
                collapseEl.classList.add("show");
            }
        });

        // Collapse 이벤트 리스너 등록: 상태 변화 시 localStorage에 저장
        const collapses = document.querySelectorAll('.collapse');
        collapses.forEach(collapse => {
            collapse.addEventListener("hide.bs.collapse", function() {
                localStorage.setItem(collapse.id, "hidden");
            });
            collapse.addEventListener("show.bs.collapse", function() {
                localStorage.setItem(collapse.id, "shown");
            });
        });


        // 3. 상태 버튼 스타일 초기화 및 클릭 이벤트 등록
        // (이 코드는 필터링 이후에 실행하여 필터링 display 상태를 덮어쓰지 않도록 합니다.)

        const taskCards = document.querySelectorAll(".task-item");
        taskCards.forEach(card => {
            const dbStatus = card.querySelector('.status-text').getAttribute('data-status');
            const buttons = card.querySelectorAll(".status-btn");
            buttons.forEach(button => {
                const btnStatus = button.getAttribute("data-status");
                if (btnStatus === dbStatus) {
                    button.style.backgroundColor = "#6c757d";
                    button.style.color = "#fff";
                    button.disabled = true;
                } else {
                    button.style.backgroundColor = "";
                    button.style.color = "";
                    button.disabled = false;
                }
            });
        });


        // 상태 버튼 클릭 이벤트 (업데이트 API 호출)
        const statusButtons = document.querySelectorAll(".status-btn");
        statusButtons.forEach(button => {
            button.addEventListener("click", function() {
                const taskId = this.getAttribute("data-id");
                const newStatus = this.getAttribute("data-status");
                const task = { id: taskId, status: newStatus };
                const url = `/api/todos/index/updateStatus/${taskId}`;
                fetch(url, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(task)
                }).then(response => {
                    if (response.ok) {
                        // 기존 row 대신 .task-item 사용
                        const taskCard = this.closest('.task-item');
                        // 업데이트: 작업 제목과 상태 정보는 내부에 별도 저장 (status-text은 여전히 유지)
                        const statusTextEl = taskCard.querySelector('.status-text');
                        statusTextEl.setAttribute("data-status", newStatus);
                        statusTextEl.textContent = newStatus;

                        // 상태 버튼들의 스타일 업데이트 (필요 시 업데이트)
                        const buttons = taskCard.querySelectorAll(".status-btn");
                        buttons.forEach(btn => {
                            const btnStatus = btn.getAttribute("data-status");
                            if (btnStatus === newStatus) {
                                btn.style.backgroundColor = "#6c757d";
                                btn.style.color = "#fff";
                                btn.disabled = true;
                            } else {
                                btn.style.backgroundColor = "";
                                btn.style.color = "";
                                btn.disabled = false;
                            }
                        });

                        // 새 상태 섹션으로 애니메이션 효과와 함께 이동 (섹션 id는 "collapse-" + newStatus)
                        const targetCollapse = document.getElementById("collapse-" + newStatus);
                        if (targetCollapse) {
                            const targetTaskList = targetCollapse.querySelector(".task-list");
                            if (targetTaskList) {
                                taskCard.classList.add("fly-in");
                                targetTaskList.appendChild(taskCard);
                                setTimeout(() => {
                                    taskCard.classList.remove("fly-in");
                                }, 500);
                            }
                        }
                    } else {
                        alert("상태 변경 실패!");
                    }
                });
            });
        });

        // 즐겨찾기 버튼 클릭 이벤트 (별 토글)
        const favButtons = document.querySelectorAll(".btn-favorite");
        favButtons.forEach(btn => {
            btn.addEventListener("click", function(e) {
                // 카드 전체 클릭 이벤트와 겹치지 않도록 전파 차단
                e.stopPropagation();

                const taskId = this.getAttribute("data-id");
                // API 엔드포인트 (PATCH로 favorite 상태 업데이트)
                const url = `/api/todos/updateFavorite/${taskId}`;

                // 현재 아이콘 상태 검사
                // const starEl = this.querySelector(".star-icon");
                // const willFavorite = starEl.textContent === "☆";

                const icon = this.querySelector("i.fa-star");
                const willFavorite = icon.classList.contains("fa-regular");

                fetch(url, {
                    method: "PATCH",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ favorite: willFavorite })
                }).then(response => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    // 성공 시 UI 업데이트
                    // starEl.textContent = willFavorite ? "★" : "☆";

                    // 성공 시 클래스만 토글 → 크기·디자인 유지
                    icon.classList.toggle("fa-regular",  !willFavorite);
                    icon.classList.toggle("fa-solid",    willFavorite);
                }).catch(error => {
                    console.error("즐겨찾기 변경 실패:", error);
                    alert("즐겨찾기 상태 변경에 실패했습니다.");
                });
            });
        });

    });
}

// 작업 추가
{
    const taskAddBtn = document.querySelector("#task-add-btn");
    taskAddBtn.addEventListener("click", function(){
        const selectedCategory = document.querySelector(`select[id=select-task-category]`);
        if(!selectedCategory){
            alert("카테고리를 선택하여 주세요");
            return;
        }

        const task = {
            category_name : selectedCategory.value,
            title : document.querySelector("#new-task").value,
            status : "준비",
            deadline_str : null
        }
        console.log(task);

        const url = `/api/todos/today/task`;

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(task)
        }).then(response => {
            const msg = (response.ok) ? "작업이 추가되었습니다." : "추가 실패";
            alert(msg);
            window.location.reload();
        });

    });
}

// 개별 작업 카드 클릭 시 관리 모달 띄우기
const taskItems = document.querySelectorAll(".task-item");
taskItems.forEach(item => {
    item.addEventListener("click", function(e) {
        // 만약 클릭한 요소가 이미 버튼이나 내부 컨트롤이라면 모달을 띄우지 않음
        if (e.target.closest(".task-actions") || e.target.closest(".status-btn-group")) {
            return;
        }

        // 작업 카드에서 필요한 데이터 추출
        const taskId = this.getAttribute("data-id");
        const title = this.querySelector(".task-title").textContent;
        // .task-category 텍스트는 "카테고리: {이름}" 형태, 뒤쪽 부분만 추출
        const categoryText = this.querySelector(".task-category").textContent.replace("카테고리:", "").trim();
        // .task-deadline 텍스트는 "마감: {날짜}" 형태, 마찬가지
        const deadlineText = this.querySelector(".task-deadline").textContent.replace("마감:", "").trim();
        const status = this.querySelector(".status-text").getAttribute("data-status");

        // 모달 내의 입력 필드에 데이터를 채워넣습니다.
        document.querySelector("#edit-task-id").value = taskId;
        document.querySelector("#edit-task-title").value = title;
        document.querySelector("#edit-task-status").value = status;
        document.querySelector("#edit-task-category-name").value = categoryText;
        document.querySelector("#edit-deadline").value = deadlineText;

        // Bootstrap Modal을 이용해 관리 모달을 띄웁니다.
        const taskEditModal = document.getElementById("task-edit-modal");
        const modal = new bootstrap.Modal(taskEditModal);
        modal.show();
    });
});


// 작업 수정
{
    const taskEditBtn = document.querySelector("#task-edit-btn");
    taskEditBtn.addEventListener("click", function(){
        const task = {
            id : document.querySelector("#edit-task-id").value,
            title : document.querySelector("#edit-task-title").value,
            status : document.querySelector("#edit-task-status").value,
            category_name : document.querySelector("#edit-task-category-name").value,
            deadline_str : document.querySelector("#edit-deadline").value
        }
        console.log(task);

        const url = "/api/todos/index/task/" + task.id;

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
    const deleteBtn = document.querySelector("#task-delete-btn");
    deleteBtn.addEventListener("click", (event) => {
        if (!confirm("선택한 작업을 삭제하겠습니까?")) return;

        // 모달 내의 숨겨진 input에서 작업 id 가져오기
        const taskId = document.querySelector("#edit-task-id").value;
        if (!taskId) {
            alert("삭제할 작업이 선택되지 않았습니다.");
            return;
        }

        const url = `/api/todos/index/task/${taskId}`;
        fetch(url, {
            method: "DELETE"
        }).then(response => {
            if (!response.ok) {
                alert("작업 삭제 실패");
                return;
            }
            alert("해당 작업을 삭제하였습니다.");
            window.location.reload();
        });
    });
}