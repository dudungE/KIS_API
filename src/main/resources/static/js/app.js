// 종목 상세 조회
function goToStockDetail() {
    const ticker = document.getElementById("tickerInput").value.trim();
    if (ticker) {
        const url = `http://localhost:8090/stock/${ticker}`;
        window.location.href = url;
    } else {
        alert("종목 코드를 입력하세요.");
    }
}

// 숨기는 함수 추가
function hideAllSections() {
    document.getElementById('rankingSection').style.display = 'none';
    document.getElementById('priceUpRanking').style.display = 'none';
    document.getElementById('priceDownRanking').style.display = 'none';
    document.getElementById('chartSection').style.display = 'none';
}



// 차트 보기
function showChart() {
    hideAllSections();
    document.getElementById('rankingSection').style.display = 'none';
    document.getElementById('chartSection').style.display = 'block';
    document.querySelectorAll('.btn-toggle')[0].classList.remove('active');
    document.querySelectorAll('.btn-toggle')[1].classList.add('active');
    initChart();
}

// 차트 초기화
let chartInitialized = false;
function initChart() {
    if (chartInitialized) return;
    const ctx = document.getElementById('liveChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['09:00', '10:00', '11:00', '12:00'],
            datasets: [{
                label: '거래대금(백만)',
                data: [1200, 1500, 1400, 1700],
                borderColor: '#1a73e8',
                tension: 0.4
            }]
        }
    });
    chartInitialized = true;
}


// 버튼 클릭 이벤트 핸들러
document.querySelectorAll('.btn-toggle').forEach((btn, idx) => {
    btn.addEventListener('click', function() {
        // 1. 모든 버튼 비활성화
        document.querySelectorAll('.btn-toggle').forEach(b => b.classList.remove('active'));
        // 2. 현재 버튼 활성화
        this.classList.add('active');
        // 3. localStorage에 인덱스 저장
        localStorage.setItem('activeBtnIdx', idx.toString());

        // 4. 해당 섹션 표시
        switch(idx) {
            case 0: showRanking(); break;
            case 1: priceUpRanking(); break;
            case 2: priceDownRanking(); break;
            case 3: showChart(); break;
        }
    });
});



// 1. 재사용 함수 정의
// 페이지네이션 바에 이벤트 위임
function setupPagination(sectionId) {
    const section = document.getElementById(sectionId);
    if (!section) return;

    section.addEventListener('click', function(e) {
        if (e.target.matches('.pagination a')) {
            e.preventDefault();
            fetch(e.target.href)
                .then(response => response.text())
                .then(html => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    // 섹션 ID로 새로 받은 내용만 추출 (있으면 해당 부분만, 없으면 전체)
                    const newContent = tempDiv.querySelector('#' + sectionId);
                    section.innerHTML = newContent ? newContent.innerHTML : tempDiv.innerHTML;
                });
        }
    });
}

// 2. 여러 섹션에 적용 (예: 거래량 순위, 급상승, 급하락)
setupPagination('rankingSection');
setupPagination('priceUpRanking');


//// 페이지네이션 바에 이벤트 위임
//document.getElementById('rankingSection').addEventListener('click', function(e) {
//    // .pagination a 요소 클릭 시
//    if (e.target.matches('.pagination a')) {
//        e.preventDefault(); // 기본 링크 이동 막기
//        fetch(e.target.href)
//            .then(response => response.text())
//            .then(html => {
//                // 새로 받은 HTML에서 테이블/페이지네이션 부분만 추출
//                const tempDiv = document.createElement('div');
//                tempDiv.innerHTML = html;
//                // 예: 전체 조각 삽입
//                this.innerHTML = tempDiv.innerHTML;
//            });
//    }
//});




// 기존 함수 수정 (활성화 로직 제거)

//function showRanking() {
//    hideAllSections();
//    document.getElementById('rankingSection').style.display = 'block';
//    fetch('/volume-rank-view2')
//        .then(response => response.text())
//        .then(html => {
//            const tempDiv = document.createElement('div');
//            tempDiv.innerHTML = html;
//            // 전체 조각 선택 (예: div의 ID나 클래스 사용)
////            const table = tempDiv.querySelector('table');
////            const title = tempDiv.querySelector('h2');
//            document.getElementById('rankingSection').innerHTML = title.outerHTML + table.outerHTML;
//        });
//}

function showRanking() {
    hideAllSections();
    const rankingSection = document.getElementById('rankingSection');
    rankingSection.style.display = 'block';
    fetch('/volume-rank-view2')
        .then(response => response.text())
        .then(html => {
            rankingSection.innerHTML = html;
        });
}


function priceUpRanking() {
    hideAllSections();
    const rankingSection = document.getElementById('priceUpRanking');
    rankingSection.style.display = 'block';
    fetch('/price-up-rank')
        .then(response => response.text())
        .then(html => {
            rankingSection.innerHTML = html;
        });
}



function priceDownRanking() {
    hideAllSections();
    document.getElementById('priceDownRanking').style.display = 'block';
    fetch('/price-down-rank')
        .then(response => response.text())
        .then(html => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;
            const table = tempDiv.querySelector('table');
            const title = tempDiv.querySelector('h2');
            document.getElementById('priceDownRanking').innerHTML = title.outerHTML + table.outerHTML;
        });
}

