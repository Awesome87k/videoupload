window.addEventListener('load', () => {
    view.init();
});

const view = {
    init:  function () {
        document.querySelector('#edit-btn').addEventListener('click', e => {
            e.preventDefault();
            view.doEdit();
        });
    }
    , doEdit: function() {
        let params = {};
        params.name = document.querySelector('#name').value;
        params.phonenum = document.querySelector('#phonenum').value;
        params.level = document.querySelector('#level').value;

        if(!params.name.trim() || !params.phonenum.trim() || !params.level.trim()) {
            alert("빈값을 입력할 수 없습니다.");
            return;
        }

        const fns =  ({result, message, content}) => {
            if (result) {
                alert("회원정보 수정에 성공했습니다.");
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

        util.putajax("/data/user", params, fns, fne);
    }
}