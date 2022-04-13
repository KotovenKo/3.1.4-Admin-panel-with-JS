
$('document').ready(function()  {
    $('.updateBtn').click(function(event) {
         event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            method: "GET"
        }).then(function(data) {

            $('#editId').val(data.user.id);
            $('#editName').val(data.user.name);
            $('#editSecondName').val(data.user.secondName);
            $('#editAge').val(data.user.age);
            $('#editEmail').val(data.user.email);
           // $('#password').val(data.user.password);
           //  let container = $('#checkContainerId')
           //  container.empty();
           //  $.each(data.allRoles, function (index, value) {
           //      console.log(value)
           //      $('<input />', { type: 'checkbox', id: 'cb' + value.id, value: value.name, checked : value.active, class: 'form-control'})
           //          .appendTo(container);
           //      $('<label />', { 'for': 'cb' + value.id, text: value.name }).appendTo(container);
            $('#editrId1').val(data.allRoles[0].name);
            $('#editrV1').val(data.allRoles[0].id);
            $('#editrId1').prop('checked', data.allRoles[0].active);


            $('#editrId2').val(data.allRoles[1].name);
            $('#editrV2').val(data.allRoles[1].id);
            $('#editrId2').prop('checked', data.allRoles[1].active);

            // });



        });

        $('#editModal').modal('show');
    });
    $('.deleteBtn').click(function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            method: "GET"
        }).then(function(data) {

            $('#deleteId').val(data.user.id);
            $('#deleteName').val(data.user.name);
            $('#deleteSecondName').val(data.user.secondName);
            $('#deleteAge').val(data.user.age);
            $('#deleteEmail').val(data.user.email);

            $('#deleterId1').val(data.allRoles[0].name);
            $('#deleterV1').val(data.allRoles[0].id);
            $('#deleterId1').prop('checked', data.allRoles[0].active);

            $('#deleterId2').val(data.allRoles[1].name);
            $('#deleterV2').val(data.allRoles[1].id);
            $('#deleterId2').prop('checked', data.allRoles[1].active);
        });

        $('#deleteModal').modal('show');
    });


});
