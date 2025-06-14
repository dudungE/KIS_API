//// 종목 상세 조회
//function goToStockDetail() {
//    const ticker = document.getElementById("tickerInput").value.trim();
//    if (ticker) {
//        const url = `http://localhost:8090/stock/${ticker}`;
//        window.location.href = url;
//    } else {
//        alert("종목 코드를 입력하세요.");
//    }
//}
//
//// 숨기는 함수 추가
//function hideAllSections() {
//    document.getElementById('rankingSection').style.display = 'none';
//    document.getElementById('priceUpRanking').style.display = 'none';
//    document.getElementById('priceDownRanking').style.display = 'none';
//    document.getElementById('chartSection').style.display = 'none';
//}
//// 버튼 클릭 이벤트 핸들러
//document.querySelectorAll('.btn-toggle').forEach((btn, idx) => {
//    btn.addEventListener('click', function() {
//        // 1. 모든 버튼 비활성화
//        document.querySelectorAll('.btn-toggle').forEach(b => b.classList.remove('active'));
//        // 2. 현재 버튼 활성화
//        this.classList.add('active');
//        // 3. localStorage에 인덱스 저장
//        localStorage.setItem('activeBtnIdx', idx.toString());
//
//        // 4. 해당 섹션 표시
//        switch(idx) {
//            case 0: showRanking(); break;
//            case 1: priceUpRanking(); break;
//            case 2: priceDownRanking(); break;
//            case 3: showChart(); break;
//        }
//    });
//});
//
//
//
//// 1. 재사용 함수 정의
//// 페이지네이션 바에 이벤트 위임
//function setupPagination(sectionId) {
//    const section = document.getElementById(sectionId);
//    if (!section) return;
//
//    section.addEventListener('click', function(e) {
//        if (e.target.matches('.pagination a')) {
//            e.preventDefault();
//            fetch(e.target.href)
//                .then(response => response.text())
//                .then(html => {
//                    const tempDiv = document.createElement('div');
//                    tempDiv.innerHTML = html;
//                    // 섹션 ID로 새로 받은 내용만 추출 (있으면 해당 부분만, 없으면 전체)
//                    const newContent = tempDiv.querySelector('#' + sectionId);
//                    section.innerHTML = newContent ? newContent.innerHTML : tempDiv.innerHTML;
//                });
//        }
//    });
//}
//
//// 2. 여러 섹션에 적용 (예: 거래량 순위, 급상승, 급하락)
//setupPagination('rankingSection');
//setupPagination('priceUpRanking');
//
//
//
//
//// 기존 함수 수정 (활성화 로직 제거)
//
//function showRanking() {
//    hideAllSections();
//    const rankingSection = document.getElementById('rankingSection');
//    rankingSection.style.display = 'block';
//    fetch('/volume-rank-view2')
//        .then(response => response.text())
//        .then(html => {
//            rankingSection.innerHTML = html;
//        });
//}
//
//
//function priceUpRanking() {
//    hideAllSections();
//    const rankingSection = document.getElementById('priceUpRanking');
//    rankingSection.style.display = 'block';
//    fetch('/price-up-rank')
//        .then(response => response.text())
//        .then(html => {
//            rankingSection.innerHTML = html;
//        });
//}
//
//
//
//function priceDownRanking() {
//    hideAllSections();
//    document.getElementById('priceDownRanking').style.display = 'block';
//    fetch('/price-down-rank')
//        .then(response => response.text())
//        .then(html => {
//            const tempDiv = document.createElement('div');
//            tempDiv.innerHTML = html;
//            const table = tempDiv.querySelector('table');
//            const title = tempDiv.querySelector('h2');
//            document.getElementById('priceDownRanking').innerHTML = title.outerHTML + table.outerHTML;
//        });
//}
//


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

// 숨기는 함수
function hideAllSections() {
    document.getElementById('rankingSection').style.display = 'none';
    document.getElementById('priceUpRanking').style.display = 'none';
    document.getElementById('priceDownRanking').style.display = 'none';
    document.getElementById('chartSection').style.display = 'none';
}

// section 표시 함수
function showSection(section) {
    hideAllSections();
    switch(section) {
        case 'volume':
            document.getElementById('rankingSection').style.display = 'block';
            fetch('/volume-rank-view2')
                .then(response => response.text())
                .then(html => {
                    document.getElementById('rankingSection').innerHTML = html;
                });
            break;
        case 'up':
            document.getElementById('priceUpRanking').style.display = 'block';
            fetch('/price-up-rank')
                .then(response => response.text())
                .then(html => {
                    document.getElementById('priceUpRanking').innerHTML = html;
                });
            break;
        case 'down':
            document.getElementById('priceDownRanking').style.display = 'block';
            fetch('/price-down-rank')
                .then(response => response.text())
                .then(html => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const table = tempDiv.querySelector('table');
                    const title = tempDiv.querySelector('h2');
                    document.getElementById('priceDownRanking').innerHTML = (title ? title.outerHTML : '') + (table ? table.outerHTML : '');
                });
            break;
        case 'chart':
            document.getElementById('chartSection').style.display = 'block';
            // 차트 데이터 fetch 등 필요시 추가
            break;
    }
}

// 버튼 클릭 이벤트 핸들러 (URL 변경)
document.querySelectorAll('.btn-toggle').forEach((btn, idx) => {
    btn.addEventListener('click', function() {
        // 모든 버튼 비활성화
        document.querySelectorAll('.btn-toggle').forEach(b => b.classList.remove('active'));
        // 현재 버튼 활성화
        this.classList.add('active');
        // 섹션 및 URL 정보
        const section = this.getAttribute('data-section');
        const url = this.getAttribute('data-url');
        // URL을 변경(pushState)
        history.pushState({section}, '', url);
        // 섹션 표시
        showSection(section);
    });
});

// 뒤로가기/앞으로가기(popstate) 대응
window.addEventListener('popstate', function(event) {
    let section = 'volume'; // 기본값
    if (event.state && event.state.section) {
        section = event.state.section;
    } else {
        // URL path로부터 section 추정
        const path = window.location.pathname;
        if (path.includes('price-up')) section = 'up';
        else if (path.includes('price-down')) section = 'down';
        else if (path.includes('chart')) section = 'chart';
        else section = 'volume';
    }
    // 버튼 active 처리
    document.querySelectorAll('.btn-toggle').forEach(b => {
        b.classList.toggle('active', b.getAttribute('data-section') === section);
    });
    showSection(section);
});

// 첫 로딩 시 URL에 따라 섹션 자동 표시
window.addEventListener('DOMContentLoaded', function() {
    let section = 'volume';
    const path = window.location.pathname;
    if (path.includes('price-up')) section = 'up';
    else if (path.includes('price-down')) section = 'down';
    else if (path.includes('chart')) section = 'chart';
    // 버튼 active 처리
    document.querySelectorAll('.btn-toggle').forEach(b => {
        b.classList.toggle('active', b.getAttribute('data-section') === section);
    });
    showSection(section);
});


function setupPagination(sectionId) {
    const section = document.getElementById(sectionId);
    if (!section) return;

    section.addEventListener('click', function(e) {
        // 페이지네이션 링크 클릭 시
        if (e.target.matches('.pagination a')) {
            e.preventDefault();
            const url = e.target.getAttribute('href');
            // AJAX로 데이터만 가져오기
            fetch(url)
                .then(response => response.text())
                .then(html => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    // 섹션 ID로 새로 받은 내용만 추출
                    const newContent = tempDiv.querySelector('#' + sectionId);
                    section.innerHTML = newContent ? newContent.innerHTML : tempDiv.innerHTML;
                    // URL도 변경
                    history.pushState({sectionId, url}, '', url);
                });
        }
    });
}

// popstate(뒤로/앞으로) 시에도 AJAX로 데이터만 변경
window.addEventListener('popstate', function(event) {
    if (event.state && event.state.url && event.state.sectionId) {
        fetch(event.state.url)
            .then(response => response.text())
            .then(html => {
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = html;
                const section = document.getElementById(event.state.sectionId);
                const newContent = tempDiv.querySelector('#' + event.state.sectionId);
                if (section) {
                    section.innerHTML = newContent ? newContent.innerHTML : tempDiv.innerHTML;
                }
            });
    }
});

// 예시: 거래량, 급상승, 급하락 섹션에 적용
setupPagination('rankingSection');
setupPagination('priceUpRanking');
setupPagination('priceDownRanking');

