const signup = {
    init: function () {
        $('#button-signup').on('click', () => {
            this.signup()
        })
    },

    signup: function signup() {
        const data = {
            accountId: $('#accountId').val(),
            nickName: $('#nickName').val(),
            password: $('#password').val(),
        }

        $.ajax({
            type: 'POST',
            url: 'api/signup',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('가입을 환영합니다~~ 로그인을 해주세요!');
            window.location.href = "/"
        }).fail(function (error) {
            console.log(error)
            const response = JSON.parse(error.responseText)
            alert(response.message);
        });
    }
}

signup.init()
