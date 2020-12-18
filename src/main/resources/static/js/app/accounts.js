const accounts = {
    init: function () {
        this.loadAccounts()
        $('#button-logout').on('click', () => {
            this.logout()
        })
    },

    loadAccounts: function loadAccounts() {
        $.ajax({
            type: 'GET',
            url: 'api/accounts',
            dataType: 'json',
            headers: {
                'Authorization': getCookie('accessToken')
            },
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {

            const data = {
                accounts: response
            }
            const template =
                '{{#accounts}}\n' +
                '    <tr>\n' +
                '        <td>{{id}}</td>\n' +
                '        <td>{{accountId}}</td>\n' +
                '        <td>{{nickName}}</td>\n' +
                '        <td>{{isAdmin}}</td>\n' +
                '        <td>{{joinDate}}</td>\n' +
                '    </tr>\n' +
                '{{/accounts}}'

            const output = Mustache.render(template, data)
            $('#tbody').html(output)

        }).fail(function (error) {
            const response = JSON.parse(error.responseText)
            if(response.message === '권한이 없습니다.'){
                console.log("??")
                window.location.href = "/mypage"
            }else{
                alert(response.message);
                window.location.href = "/"
            }
        });
    },

    logout: function logout() {
        alert("로그아웃 되었습니다")
        deleteCookie()
        window.location.href = "/"
    }
}

accounts.init()
