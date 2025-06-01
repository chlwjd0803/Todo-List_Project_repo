//처음 로딩시에 작업
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

        // 2. 카테고리 필터링 복원 및 이벤트 등록
        // 필터링 함수: 선택된 카테고리와 각 행의 두 번째 열(카테고리)을 비교하여 display 설정
        function filterRowsByCategory(selectedCategory) {
            const taskItems = document.querySelectorAll(".task-item");
            taskItems.forEach(item => {
                // .task-category의 텍스트는 "카테고리: {이름}" 형태일 수 있으므로 분리합니다.
                const catText = item.querySelector('.task-category').textContent.replace("카테고리:", "").trim();
                if (selectedCategory === "전체" || catText === selectedCategory) {
                    item.style.display = "";
                } else {
                    item.style.display = "none";
                }
            });
        }


        // 라디오 버튼 변경 이벤트 등록
        const radioButtons = document.querySelectorAll('input[name="categoryradio"]');
        // 수정 버튼 활성화 및 비활성화
        const cateEditBtn = document.querySelector(`.category-edit-btn`);

        // 전체버튼 하나만 남았을때 비활성화
        if(radioButtons.length === 1) cateEditBtn.setAttribute(`disabled`, true);

        radioButtons.forEach(radio => {
            radio.addEventListener("change", function() {
                let selectedCategory;
                if (this.id === "categoryradio-all") {
                    selectedCategory = "전체";
                    cateEditBtn.setAttribute(`disabled`, true);
                } else {
                    const label = document.querySelector(`label[for="${this.id}"]`);
                    selectedCategory = label ? label.textContent.trim() : "";
                    cateEditBtn.removeAttribute(`disabled`);
                }
                localStorage.setItem("selectedCategory", selectedCategory);
                filterRowsByCategory(selectedCategory);
            });
        });

        // 페이지 로드 시 저장된 카테고리 필터 복원
        const savedCategory = localStorage.getItem("selectedCategory") || "전체";
        let selectRadio;

        if (savedCategory === "전체") {
            selectRadio = document.getElementById("categoryradio-all");
            selectRadio.checked = true;
        } else {
            selectRadio = document.querySelector(`input[name="categoryradio"][id="categoryradio-${savedCategory}"]`);
            if (selectRadio) {
                selectRadio.checked = true;
            }
        }
        // 이벤트를 강제로 발생시켜 라디오 버튼에 따라 효과가 바로 나타나게함 (전체버튼에 대한 버그 수정)
        if(selectRadio) selectRadio.dispatchEvent(new Event("change"));


        // 초기 필터링 적용
        filterRowsByCategory(savedCategory);

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


// 카테고리 추가
{
    const cateAddBtn = document.querySelector("#category-add-btn");
    cateAddBtn.addEventListener("click", function() {

        const check = document.querySelector("#add-category-name").value.trim();

        if (!check || check.length === 0){
            alert("카테고리 이름을 빈 문자로 지정할 수 없습니다.");
            return;
        }
        if (check.length > 13){
            alert("카테고리 이름은 12자를 초과할 수 없습니다.");
            return;
        }

        const category = {
            name : document.querySelector("#add-category-name").value
        }
        const url = "/api/todos/index/category"

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(category)
        }).then(response => {
            const msg = (response.ok) ? "카테고리가 추가되었습니다." : "추가 오류";
            alert(msg);
            window.location.reload();
        })
    });
}

// 카테고리 수정 모달 띄우기
{
    const cateEditModal = document.querySelector("#category-edit-modal");
    cateEditModal.addEventListener("show.bs.modal", function(event){
        const selectedRadio = document.querySelector('input[name="categoryradio"]:checked');
        document.querySelector("#edit-category-name").value = selectedRadio.getAttribute("data-name");
    });
}


// 카테고리 수정 반영
{
    document.getElementById('category-edit-btn').addEventListener('click', function() {
        // 메인 페이지에서 선택된 카테고리 라디오 버튼 찾기
        const selectedRadio = document.querySelector('input[name="categoryradio"]:checked');
        const allRadio = document.querySelectorAll('input[name="categoryradio"]');

        // 모달의 입력 필드에서 새 이름 가져오기
        const newName = document.getElementById('edit-category-name').value.trim();
        if (!newName || newName === '') {
            alert('변경할 이름을 입력하세요.');
            return;
        }
        if(newName === `전체`){
            alert(`"전체"라는 이름으로 카테고리를 설정할 수 없습니다.`);
            return;
        }
        if(newName.length > 13){
            alert("카테고리 이름은 12자를 초과할 수 없습니다.");
            return;
        }
        for(let i=0; i < allRadio.length; i++) if(allRadio[i].id === `categoryradio-${newName}`){
                alert(`${newName} (이)라는 카테고리는 이미 존재합니다.`);
                return;
        }

        const category = {
            id : selectedRadio.getAttribute(`data-id`),
            name : newName
        }

        const url = "/api/todos/index/category/" + category.id;

        fetch(url, {
            method: "PATCH",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(category)
        }).then(response => {
            const msg = (response.ok) ? "수정이 반영되었습니다." : "수정 오류";
            alert(msg);
            window.location.reload();
        })
    });
}
// 카테고리 삭제
{
    const cateDltBtn = document.querySelector(`.category-delete-btn`);

    cateDltBtn.addEventListener("click", function(){
        // 현재 체크되어있는 라디오를 가져오는 것, 이벤트 메소드 안에 넣어야함
        if(!confirm("선택한 카테고리를 삭제하겠습니까?")) return;

        const selectedRadio = document.querySelector('input[name="categoryradio"]:checked');
        if (!selectedRadio) {
            alert("삭제할 카테고리를 선택해주세요.");
            return;
        }
        //문자열에서 정수형으로 바꿔주어야한다.
        const cateId = parseInt(selectedRadio.getAttribute("data-id"));
        console.log(cateId);

        // 전체 라디오버튼이 눌린 상태에서는 모두 삭제
        if(cateId === 0){
            const url = `/api/todos/index/category`

            fetch(url, {
                method: "DELETE"
            }).then(response => {
                const msg = (response.ok) ? "모두 삭제되었습니다" : "삭제 오류";
                alert(msg);
                localStorage.setItem("selectedCategory", "전체"); // 전체로 라디오버튼이 이동하게 강제지정
                window.location.reload();
            })
        }
        // 아니면 해당 카테고리만 삭제
        else{
            const url = `/api/todos/index/category/${cateId}`;

            fetch(url, {
                method: "DELETE"
            }).then(response => {
                const msg = (response.ok) ? "삭제되었습니다." : "삭제 오류";
                alert(msg);
                localStorage.setItem("selectedCategory", "전체"); // 전체로 라디오버튼이 이동하게 강제지정
                window.location.reload();
            })
        }

    });
}

// 작업 추가
{
    const taskAddBtn = document.querySelector("#task-add-btn");
    taskAddBtn.addEventListener("click", function(){
       const selectedCategory = document.querySelector(`input[name="categoryradio"]:checked`);
       if(!selectedCategory){
           alert("카테고리를 선택하여 주세요");
           return;
       }

       const check = document.querySelector("#new-task").value.trim();
       if (!check || check.length === 0){
           alert("작업 이름을 비울 수 없습니다.");
           return;
       }
       if (check.length > 13){
           alert("작업 이름은 12글자를 초과할 수 없습니다");
           return;
       }

       const deadlineInput = document.querySelector("#deadline-date");

       if(!deadlineInput.value || deadlineInput.value === '') deadlineInput.value = null;

       const task = {
           category_name : selectedCategory.getAttribute("data-name"),
           title : document.querySelector("#new-task").value,
           status : "준비",
           deadline_str : deadlineInput.value
       }
       console.log(task);

       const url = `/api/todos/index/task`;

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
        const checkTask = document.querySelector("#edit-task-title").value.trim();

        if(!checkTask || checkTask.length === 0){
            alert("작업 이름을 비울 수 없습니다.");
            return;
        }
        if(checkTask.length > 13){
            alert("작업 이름은 13글자를 초과할 수 없습니다.");
            return;
        }

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
