const mealsAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealsAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealsAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealsAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealsAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: function () {
            $.get(mealsAjaxUrl, updateTableByData);
        }
    });
    jQuery.datetimepicker.setLocale('ru');
    $('#startDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#endDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#startTime').datetimepicker({
        format: 'H:i',
        datepicker: false
    });
    $('#endTime').datetimepicker({
        format: 'H:i',
        datepicker: false
    });
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});

function saveMeal() {
    let dt = replaceT(form.find("#dateTime").val(), " ", "T");
    form.find("#dateTime").val(dt);
    if(!save()) {
        let dt = replaceT(form.find("#dateTime").val(), "T", " ");
        form.find("#dateTime").val(dt);
    }
    setTimeout(updateFilteredTable, 100);
}

$.ajaxSetup({
    converters: {
        "text json": function (results) {
            let data = jQuery.parseJSON(results);
            if (Array.isArray(data)) {
                for (let i = 0; i < data.length; i++) {
                    data[i].dateTime = replaceT(data[i].dateTime, "T", " ");
                }
            } else if (typeof(data) === 'object') {
                data.dateTime = replaceT(data.dateTime, "T", " ");
            }
            return data;
        }
    }
});

function replaceT(val, from, to) {
    return val.replace(from, to).substring(0, 16);
}