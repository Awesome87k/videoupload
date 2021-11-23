const util = {
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
    },
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
    putajax: function (_url, _data, _fns, _fne) {
        $.ajax({
            url: _url,
            type: 'PUT',
            data: JSON.stringify(_data),
            success: _fns,
            beforeSend: function (xhr) {xhr.setRequestHeader("Content-type","application/json")},
            error: async function (jqXHR, textStatus, errorThrown) {
                _fne(jqXHR, textStatus, errorThrown);
            }
        });
    },
    delajax: function (_url, _data, _fns, _fne) {
        $.ajax({
            url: _url,
            type: 'DELETE',
            data: _data,
            success: _fns,
            beforeSend: function (xhr) {xhr.setRequestHeader("Content-type","application/json")},
            error: async function (jqXHR, textStatus, errorThrown) {
                _fne(jqXHR, textStatus, errorThrown);
            }
        });
    },
    uploadajax: function (_url, _data, _fns, _fne) {
        $.ajax({
            url: _url,
            type: 'POST',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            data: _data,
            success: _fns,
            error: async function (jqXHR, textStatus, errorThrown) {
                _fne(jqXHR, textStatus, errorThrown);
            }
        });
    }
}