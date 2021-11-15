window.addEventListener('load', () => {
    view.init();
});

const view = {
    init:  function () {
        $(document).on("click", "#upload-btn", function(){
            view.upload();
        });
        $(document).on("click", "#logout-btn button", function(){
            location.href="/data/logout";
        });
    }
    , upload: function() {
        const form = $("#form")[0];
        const formData = new FormData(form);

        const fns =  ({result, message, content}) => {
            if (result) {
                alert("업로드에 성공했습니다.");
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

        util.uploadajax("/data/video/upload-file", formData, fns, fne);
    }
}