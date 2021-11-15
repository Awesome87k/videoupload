window.addEventListener('load', () => {
    view.init();
});

const view = {
    init:  function () {
        view.doSearch();

        $(document).on("click", "#play-video", function(){
            view.playVideo(this.getAttribute("value"));
        });
        $(document).on("click", "#logout-btn button", function(){
            location.href="/data/logout";
        });
    }
    , doSearch: function() {
        let params = {};
        const fns =  ({result, message, content}) => {
            if (result) {
                let table = $("#dataTable");
                let html = "";
                for(const i of content) {
                    html += "<tr>";
                    html += "<td id='play-video' >"+i.vf_filename+"</td>"
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

        util.getajax("/data/video/file-list", params, fns, fne);
    }
    , playVideo: function(_vf_idx) {
        window.open("/page/video-player-view/"+_vf_idx, "_blank");
    }
}