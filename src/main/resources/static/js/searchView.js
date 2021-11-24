window.addEventListener('load', () => {
    view.init();
});

const view = {
    init:  function () {
        view.doSearch();

        $(document).on("click", "#play-video", function(){
            view.playVideoPop(this.getAttribute("value"));
        });
        $(document).on("click", "#upload-btn", function(){
            view.uploadPop(this.getAttribute("value"));
        });
        $(document).on("click", "#edit-user-btn", function(){
            view.editUserPop(this.getAttribute("value"));
        });
        $(document).on("click", "#del-user-btn", function(){
            view.doDelUser();
        });
        $(document).on("click", "#logout-btn", function(){
            location.href="/data/logout";
        });
    }
    , uploadPop: function() {
        window.open("/page/video-upload-view", "_blank");
    }
    , playVideoPop: function(_vf_idx) {
        window.open("/page/video-player-view/"+_vf_idx, "_blank");
    }
    , editUserPop: function() {
        window.open("/page/edit-user-view", "_blank");
    }
    , doSearch: function() {
        let params = {};
        const fns =  ({result, message, content}) => {
            if (result) {
                let table = $("#dataTable > tbody");
                let html = "";
                for(const i of content) {
                    html += "<tr>";
                    html += "<td>"+i.vf_filename+"</td>"
                            +"<td><button id='play-video' value='"+i.vf_idx+"'>영상보기</button></td>";
                    html += "</tr>";
                }
                table.empty();
                table.append(html);
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

        util.getajax("/data/video", params, fns, fne);
    }

    , doDelUser: function() {
        let params = {};
        const fns =  ({result, message, content}) => {
            if (result) {
                alert("회원탈퇴에 성공했습니다.");
                location.href="/data/logout";
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

        util.delajax("/data/user", params, fns, fne);
    }
}