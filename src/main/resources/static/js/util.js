const util = {
    postajax: function (_url, _data, _fns, _fne) {
        $.ajax({
            url: _url,
            type: 'POST',
            data: JSON.stringify(_data),
            success: _fns,
            beforeSend: function (xhr) {xhr.setRequestHeader("Content-type","application/json")},
            error: async function (jqXHR, textStatus, errorThrown) {
                _fne(jqXHR, textStatus, errorThrown);
            }
        });
    },
    getajax: function (_url, _data, _fns, _fne) {
        $.ajax({
            url: _url,
            type: 'GET',
            data: _data,
            success: _fns,
            error: async function (jqXHR, textStatus, errorThrown) {
                _fne(jqXHR, textStatus, errorThrown);
            }
        });
    }
}