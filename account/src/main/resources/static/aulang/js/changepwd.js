$(function () {
    var password = $('#password');
    var confirmed = $('#confirmedPassword');
    var policyPatternRegex = new RegExp(policyPattern);

    $('#changepwdForm').submit(function () {
        if (!policyPatternRegex.test(password.val())) {
            $('#errorMsg').html('密码格式：6-20位，不能为纯字母或数字！');
            return false;
        }

        if (password.val() != confirmed.val()) {
            $('#errorMsg').html('两次密码不一致！');
            return false;
        }
        return true;
    });
});