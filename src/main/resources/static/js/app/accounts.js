const accounts = {
    init: function () {
        this.loadAccounts()
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
            alert(response.message);
            window.location.href = "/"
        });
    },
}

accounts.init()
