const ajaxUrl = "ajax/admin/users/";
let datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
    setActive();
});

function setActive() {
    $(":checkbox").change(function () {
        let tableRow = $(this).closest("tr");
        let userId = $(this).attr("id");
        let enableUser = this.checked;
        $.ajax({
            type: "POST",
            url: ajaxUrl + userId,
            data: {enable: enableUser},
            success: function(data){
                console.log(data);
                if (enableUser && data) {
                    tableRow.css("opacity", "1");
                    successNoty("Enabled");
                }
                else {
                    tableRow.css("opacity", "0.3");
                    successNoty("Disabled");
                }
            }
        });
    });
}