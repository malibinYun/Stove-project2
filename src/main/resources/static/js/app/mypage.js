const myPage = {
    init: function () {
        this.loadAccount()
        $('#button-logout').on('click', () => {
            this.logout()
        })
    },

    loadAccount: function loadAccount() {
        $.ajax({
            type: 'GET',
            url: 'api/account',
            dataType: 'json',
            headers: {
                'Authorization': getCookie('accessToken')
            },
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            $('#accountId').html(response.accountId)
            $('#nickName').html(response.nickName)
            $('#isAdmin').html(response.isAdmin.toString())
            $('#joinDate').html(response.joinDate)

        }).fail(function (error) {
            const response = JSON.parse(error.responseText)
            alert(response.message);
            window.location.href = "/"
        });
    },

    logout: function logout() {
        alert("로그아웃 되었습니다")
        deleteCookie()
        window.location.href = "/"
    }
}

myPage.init()
