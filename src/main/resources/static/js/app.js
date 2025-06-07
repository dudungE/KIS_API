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

// 거개량 랭킹
function showRanking() {
    document.getElementById('rankingSection').style.display = 'block';
    document.getElementById('chartSection').style.display = 'none';
    document.querySelectorAll('.btn-toggle')[0].classList.add('active');
    document.querySelectorAll('.btn-toggle')[1].classList.remove('active');

    // Ajax로 테이블 불러오기
    // 거래량
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

// 급상승 종목 보기 (버튼 클릭 시 호출)
function priceChangeRanking() {
    // 섹션 가시성 설정
    document.getElementById('priceChangeRanking').style.display = 'block';
    document.getElementById('chartSection').style.display = 'none';

    // 버튼 활성화 상태 변경
    document.querySelectorAll('.btn-toggle')[0].classList.remove('active');
    document.querySelectorAll('.btn-toggle')[1].classList.add('active');

    // Ajax로 급상승 테이블 불러오기
    fetch('/price-change-rank')
      .then(response => response.text())
      .then(html => {
          const tempDiv = document.createElement('div');
          tempDiv.innerHTML = html;
          const table = tempDiv.querySelector('table');
          const title = tempDiv.querySelector('h2');
          document.getElementById('priceChangeRanking').innerHTML = title.outerHTML + table.outerHTML;
      });
}





// 차트 보기
function showChart() {
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
