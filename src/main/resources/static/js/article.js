
//  // 시세 화면 전환
//  function loadPrice(iscd) {
//    hideAllSections()
//    fetch('/stock/' + iscd + '/price', { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
//      .then(response => response.text())
//      .then(html => {
//        document.getElementById('sise-area').innerHTML = html;
//      });
//  }
//
//
//// 커뮤니티 화면 전환
//function loadCommunity(button) {
//    hideAllSections()
//    const iscd = button.dataset.iscd;
//    // Ajax요청
//    fetch('/stock/' + iscd + '/community', { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
//      .then(response => response.text())
//      .then(html => {
//        document.getElementById('community-area').innerHTML = html;
//      });
//}

function loadPrice(button) {
    hideAllSections();
    const iscd = button.dataset.iscd;
    fetch('/stock/' + iscd + '/price', { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
      .then(response => response.text())
      .then(html => {
        const siseArea = document.getElementById('sise-area');
        siseArea.innerHTML = html;
        siseArea.style.display = 'block'; // 반드시 표시!
      });
}

function loadCommunity(button) {
    hideAllSections();
    const iscd = button.dataset.iscd;
    fetch('/articles/' + iscd + '/community', { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
      .then(response => response.text())
      .then(html => {
        const communityArea = document.getElementById('community-area');
        communityArea.innerHTML = html;
        communityArea.style.display = 'block'; // 반드시 표시!
      });
}



// 숨기는 함수 추가
function hideAllSections() {
    document.getElementById('sise-area').style.display = 'none'; // 시세 영역 숨김 추가
    document.getElementById('community-area').style.display = 'none'; // 커뮤니티도 초기화
}







// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/articles');
            });
    });
}





