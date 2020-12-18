const login = {
    init: function () {
        const _this = this;
        $('#button-login').on('click', () => {
            _this.login()
        })
        $('#button-delete').on('click', () => {
            removeCookie()
        })
        $('#button-cookie').on('click', () => {
            checkCookie()
        })
    },

    login: function () {
        const data = {
            accountId: $('#accountId').val(),
            password: $('#password').val(),
        };

        $.ajax({
            type: 'POST',
            url: 'api/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            alert('로그인 성공');
            document.cookie = 'accessToken=' + response.accessToken + ';'
            document.cookie = 'refreshToken=' + response.refreshToken + ';'
            window.location.href = "/accounts"
        }).fail(function (error) {
            const response = JSON.parse(error.responseText)
            alert(response.message);
        });
    },
}

login.init()
