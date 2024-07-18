<!--    jquery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.8.0.min.js"></script>

async function logOut() {
    Swal.fire({
        title: '로그아웃 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'logOut',
        cancelButtonText: '취소',
        icon:'warning'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: '로그아웃 성공'
            }).then((result) => {
                location.href='/users/logOut'
            })
        }
    });
}