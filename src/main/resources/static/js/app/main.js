const main = {
    init: function () {
        const _this = this;
        $('#button-login').on('click', () => {
            _this.login()
        })
        $('#button-cookie').on('click', () => {
            _this.checkCookie()
        })
        $('#button-delete').on('click', () => {
            _this.removeCookie()
        })
    },

    login: function () {
        const data = {
            accountId: $('#accountId').val(),
            password: $('#password').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            alert('로그인 성공 ' + response.accessToken);
            document.cookie = 'accessToken=' + response.accessToken + ';'
        }).fail(function (error) {
            const response = JSON.parse(error.responseText)
            alert(response.message);
        });
    },

    checkCookie: function () {
        alert(this.getCookie('accessToken'))
    },

    removeCookie: function () {
        document.cookie = 'accessToken=;'
    },

    getCookie: function getCookie(cname) {
        const name = cname + "=";
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    },
}

main.init()
