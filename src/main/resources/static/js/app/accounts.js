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
            const data = {accounts: response}
            const template = getAccountsTemplate()
            const output = Mustache.render(template, data)
            $('#tbody').html(output)
            initPermissionButtons()

        }).fail(function (error) {
            const response = JSON.parse(error.responseText)
            if (response.message === '권한이 없습니다.') {
                window.location.href = "/mypage"
            } else {
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

function getAccountsTemplate() {
    return '{{#accounts}}\n' +
        '    <tr>\n' +
        '        <td>{{id}}</td>\n' +
        '        <td>{{accountId}}</td>\n' +
        '        <td>{{nickName}}</td>\n' +
        '        <td>{{isAdmin}}</td>\n' +
        '        <td>{{joinDate}}</td>\n' +
        '        <td>\n' +
        '            <button type="button" class="btn btn-primary" id="button-admin" data-id="{{id}}"\n' +
        '                    data-isadmin="{{isAdmin}}">{{#isAdmin}}권한 삭제{{/isAdmin}}{{^isAdmin}}권한 부여{{/isAdmin}}</button>\n' +
        '        </td>\n' +
        '    </tr>\n' +
        '{{/accounts}}'
}

function initPermissionButtons() {
    $(document).ready(() => {
        $('button[id=button-admin]').on('click', (event) => {
            const id = $(event.currentTarget).attr('data-id')
            const isAdmin = $(event.currentTarget).attr('data-isadmin')
            changePermission(id, isAdmin)
        })
    })
}

function changePermission(id, isAdmin) {
    const changedPermission = !(isAdmin === 'true')
    $.ajax({
        type: 'PATCH',
        url: `api/account/permission/${id}?isAdmin=${changedPermission}`,
        contentType: 'application/json; charset=utf-8',
        headers: {
            'Authorization': getCookie('accessToken')
        },
    }).done(function () {
        alert('권한 변경을 완료했습니다!')
        location.reload();
    }).fail(function (error) {
        const response = JSON.parse(error.responseText)
        alert(response.message);
        window.location.href = "/"
    });
}

accounts.init()
