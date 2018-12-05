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
        let checkbox = $(this);
        $.ajax({
            type: "POST",
            url: ajaxUrl + userId,
            data: {enable: enableUser},
            success: function () {
                if (enableUser) {
                    successNoty("Enabled");
                }
                else {
                    successNoty("Disabled");
                }
                tableRow.attr("data-userEnabled", enableUser);
            },
            error: function () {
                checkbox.prop("checked", !enableUser);
            }
        });
    });
}