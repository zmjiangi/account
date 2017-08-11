$(function () {
    if (areCookiesEnabled()) {
        var username = $('#username');
        var password = $('#password');
        var captcha = $('#captcha');

        var userName = $.cookie('userName');
        if (userName) {
            username.val(userName);
        }

        $('#captchaImg').click(function () {
            this.src = this.src.split('?')[0] + '?rnd=' + Math.random();
        });

        $('#loginSubmit').click(function () {
            if (!$.trim(username.val())) {
                $('#errorMsg').html('请输入账号！');
                return false;
            }
            if (!$.trim(password.val())) {
                $('#errorMsg').html('请输入密码。');
                return false;
            }

            if (captcha.length > 0 && !$.trim(captcha.val())) {
                $('#errorMsg').html('请输入验证码。');
            }

            saveUserInfo();
            $('#loginForm').submit();
        });

        $('#loginForm input').keypress(function (e) {
            if (e.keyCode === 13) {
                $('#loginSubmit').trigger('click');
            }
        });
    } else {
        $('#errorMsg').html('您的浏览器禁用了cookies，您无法登录！');
    }
});


/**
 * 保存用户信息
 */
function saveUserInfo() {
    if ($('#rememberMe').prop('checked')) {
        var userName = $('#username').val();
        $.cookie('userName', userName, {
            expires: 7
        });
    } else {
        $.cookie('userName', null);
    }
}

function areCookiesEnabled() {
    if ($.cookie == undefined) {
        console.log("JQuery Cookie library is not defined!");
        return false;
    }

    $.cookie('cookiesEnabled', 'true');
    var value = $.cookie('cookiesEnabled');
    $.removeCookie('cookiesEnabled');
    if (value != undefined) {
        return true;
    }
    return false;
}