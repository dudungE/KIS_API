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



//// 거개량 랭킹
//function showRanking() {
//    hideAllSections();
//    document.getElementById('rankingSection').style.display = 'block';
//    document.querySelectorAll('.btn-toggle')[0].classList.add('active');
//    document.querySelectorAll('.btn-toggle')[1].classList.remove('active');
//
//    // Ajax로 테이블 불러오기
//    // 거래량
//    fetch('/volume-rank-view')
//      .then(response => response.text())
//      .then(html => {
//          const tempDiv = document.createElement('div');
//          tempDiv.innerHTML = html;
//          const table = tempDiv.querySelector('table');
//          const title = tempDiv.querySelector('h2');
//          document.getElementById('rankingSection').innerHTML = title.outerHTML + table.outerHTML;
//      });
//}
//
//// 급상승 종목 보기 (버튼 클릭 시 호출)
//function priceUpRanking() {
//    hideAllSections();
//
//    // 섹션 가시성 설정
//    document.getElementById('priceUpRanking').style.display = 'block';
//
//    // 버튼 활성화 상태 변경
//    document.querySelectorAll('.btn-toggle')[0].classList.remove('active');
//    document.querySelectorAll('.btn-toggle')[1].classList.add('active');
//
//    // Ajax로 급상승 테이블 불러오기
//    fetch('/price-up-rank')
//      .then(response => response.text())
//      .then(html => {
//          const tempDiv = document.createElement('div');
//          tempDiv.innerHTML = html;
//          const table = tempDiv.querySelector('table');
//          const title = tempDiv.querySelector('h2');
//          document.getElementById('priceUpRanking').innerHTML = title.outerHTML + table.outerHTML;
//      });
//}
//
//// 급하락 종목 보기 (버튼 클릭 시 호출)
//function priceDownRanking() {
//    hideAllSections();
//
//    // 섹션 가시성 설정
//    document.getElementById('priceDownRanking').style.display = 'block';
//
//    // 버튼 활성화 상태 변경
//    document.querySelectorAll('.btn-toggle')[0].classList.remove('active');
//    document.querySelectorAll('.btn-toggle')[1].classList.add('active');
//
//    // Ajax로 급하락 테이블 불러오기
//    fetch('/price-down-rank')
//      .then(response => response.text())
//      .then(html => {
//          const tempDiv = document.createElement('div');
//          tempDiv.innerHTML = html;
//          const table = tempDiv.querySelector('table');
//          const title = tempDiv.querySelector('h2');
//          document.getElementById('priceDownRanking').innerHTML = title.outerHTML + table.outerHTML;
//      });
//}
//
//

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

// 페이지 로드 시 상태 복원
document.addEventListener('DOMContentLoaded', () => {
    const savedIdx = localStorage.getItem('activeBtnIdx');
    if (savedIdx !== null) {
        const idx = parseInt(savedIdx);
        const btns = document.querySelectorAll('.btn-toggle');
        if (btns[idx]) btns[idx].click(); // 저장된 버튼 클릭
    }
});

// 기존 함수 수정 (활성화 로직 제거)
function showRanking() {
    hideAllSections();
    document.getElementById('rankingSection').style.display = 'block';
    fetch('/volume-rank-view')
        .then(response => response.text())
        .then(html => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;
            const table = tempDiv.querySelector('table');
            const title = tempDiv.querySelector('h2');
            document.getElementById('rankingSection').innerHTML = title.outerHTML + table.outerHTML;
        });
}

function priceUpRanking() {
    hideAllSections();
    document.getElementById('priceUpRanking').style.display = 'block';
    fetch('/price-up-rank')
        .then(response => response.text())
        .then(html => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;
            const table = tempDiv.querySelector('table');
            const title = tempDiv.querySelector('h2');
            document.getElementById('priceUpRanking').innerHTML = title.outerHTML + table.outerHTML;
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
