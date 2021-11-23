window.addEventListener('load', () => {
    view.init();
});

const view = {
    init:  function () {
        document.querySelector('#join-btn').addEventListener('click', e => {
            e.preventDefault();
            view.doJoin();
        });
    }
    , doJoin: function() {
        let params = {};
        params.email = document.querySelector('#email').value;
        params.pw = document.querySelector('#pw').value;
        params.name = document.querySelector('#name').value;
        params.phonenum = document.querySelector('#phonenum').value;
        params.level = document.querySelector('#level').value;

        if(!params.email.trim() || !params.pw.trim() || !params.name.trim() || !params.phonenum.trim() || !params.level.trim()) {
            alert("빈값을 입력할 수 없습니다.");
            return;
        }

        const fns =  ({result, message, content}) => {
            if (result) {
                alert("회원가입에 성공했습니다.");
                location.href="/page/login-view";
            } else {
                alert(message);
            }
        };

        const fne =  (jqXHR, textStatus, errorThrown) => {
            let resJson = jqXHR.responseJSON;
            if(resJson)
                alert(resJson.message);
            else
                alert(errorThrown);
        };

        util.postajax("/data/user", params, fns, fne);
    }
}