const ajaxUrl = "ajax/profile/meals/";
let datatableApi;
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    if(checkEmpty()) return;
    let form = $("#filterForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: form.serialize()
    }).done(function (data) {
        datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function saveMeal() {
    let form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        if (!checkEmpty()) filter();
        else updateTable();
        successNoty("Saved");
    });

}

function checkEmpty() {
    let emptyFlag = true;
    if ($.trim($("#startDate").val()) !== '' ||
        $.trim($("#endDate").val()) !== '' ||
        $.trim($("#startTime").val()) !== '' ||
        $.trim($("#endTime").val()) !== '') emptyFlag = false;
    return emptyFlag;
}

function cancelFilter() {
    $('#filterForm')[0].reset();
    updateTable();
}