//처음 로딩시에 작업
{
    document.addEventListener("DOMContentLoaded", function() {
        // Collapse 상태 복원 및 이벤트 등록 (먼저 실행)
        const collapseIds = ["collapse-준비", "collapse-진행중", "collapse-중단됨", "collapse-완료"];
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
            const rows = document.querySelectorAll("tbody tr");
            rows.forEach(row => {
                // 카테고리는 1번째 열에 있으므로
                const rowCategory = row.children[1].textContent.trim();
                if (selectedCategory === "전체" || rowCategory === selectedCategory) {
                    row.style.display = "";  // 기본값
                } else {
                    row.style.display = "none";  // 숨김 처리
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
            const selectRadio = document.querySelector(`input[name="categoryradio"][id="categoryradio-${savedCategory}"]`);
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
        const rows = document.querySelectorAll('tbody tr');
        rows.forEach(row => {
            // 기존에는 .status-text 요소에서 데이터를 가져왔지만,
            // 만약 현재 구조가 그대로라면 그대로 사용
            const dbStatus = row.querySelector('.status-text').getAttribute('data-status');
            const buttons = row.querySelectorAll(".status-btn");

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

// 카테고리 추가
{
    const cateAddBtn = document.querySelector("#category-add-btn");
    cateAddBtn.addEventListener("click", function() {
        console.log("눌리고있나");
        const category = {
            name : document.querySelector("#add-category-name").value
        }
        const url = "/api/todos/index/categoryAdd"

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
        for(let i=0; i < allRadio.length; i++) if(allRadio[i].id === `categoryradio-${newName}`){
                alert(`${newName} (이)라는 카테고리는 이미 존재합니다.`);
                return;
        }

        const category = {
            id : selectedRadio.getAttribute(`data-id`),
            name : newName
        }

        const url = "/api/todos/index/categoryEdit/" + category.id;

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
            const url = `/api/todos/index/categoryDelete/all`

            fetch(url, {
                method: "DELETE"
            }).then(response => {
                const msg = (response.ok) ? "모두 삭제되었습니다" : "삭제 오류";
                alert(msg);
                window.location.reload();
            })
        }
        // 아니면 해당 카테고리만 삭제
        else{
            const url = `/api/todos/index/categoryDelete/${cateId}`;

            fetch(url, {
                method: "DELETE"
            }).then(response => {
                const msg = (response.ok) ? "삭제되었습니다." : "삭제 오류";
                alert(msg);
                window.location.reload();
            })
        }

    });
}



// 단일 작업 수정 모달 띄우기
{
    const taskEditModal = document.querySelector("#task-edit-modal");
    taskEditModal.addEventListener("show.bs.modal", function(event){
        const triggerBtn = event.relatedTarget;

        const id = triggerBtn.getAttribute("data-bs-id"); // 속성 위에가서 추가해주기
        const title = triggerBtn.getAttribute("data-bs-title");
        const status = triggerBtn.getAttribute("data-bs-status");
        const category_name = triggerBtn.getAttribute("data-bs-category-name");

        document.querySelector("#edit-task-id").value = id;
        document.querySelector("#edit-task-title").value = title;
        document.querySelector("#edit-task-status").value = status;
        document.querySelector("#edit-task-category-name").value = category_name;

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
            category_name : document.querySelector("#edit-task-category-name").value
        }
        console.log(task);

        const url = "/api/todos/index/editTask/" + task.id;

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

            const url = `/api/todos/index/deleteTask/${taskId}`;
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