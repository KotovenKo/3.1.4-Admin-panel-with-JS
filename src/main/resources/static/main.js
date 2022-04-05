
// $('document').ready(function () {
//     $('.table .btn').on('click', function (event){
//         event.preventDefault();
//         $('#editmodal').modal();
//     });
// });



//Method 1 working
// $('document').ready(function()  {

    // при клике на #btn
//     $('.updateBtn').click(function(event) {
//           event.preventDefault();
//         var href = $(this).attr('href');
//         $.get(href, function (user, status, json){
//             $('#id').val(user.id);
//             $('#name').val(user.name);
//             $('#secondName').val(user.secondName);
//             $('#age').val(user.age);
//             $('#email').val(user.email);
//             // $('#password').val(user.getPassword());
//             $('#roles').text(user.roles[0].name);
//
//         });
//         $('#editmodal').modal('show');
//     });
// });


//method 3 working

$('document').ready(function()  {

    // при клике на #btn
    $('.updateBtn').click(function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            method: "GET"
        }).then(function(user) {
        // var json = {"id":4,
        //     "name":"Sylvester",
        //     "secondName":"Stallone",
        //     "password":"$2a$10$Cpie4raSn/Voh6LoJy5f1.Ixybf352yfBg5wwfgaF5/9v4m203pHC",
        //     "age":55,"email":"stallone@gmail.com",
        //     "roles":[{"id":2,"name":"ROLE_USER"}]};

            $('#id').val(user.id);
            $('#name').val(user.name);
            $('#secondName').val(user.secondName);
            $('#age').val(user.age);
            $('#email').val(user.email);
         //   $('#password').val(user.password);

            $.each(user.roles, function (index, item) {
                $('#dropDownList').val(
                    $('<option></option>').append(item.id).html('name')
                );
            });
        });

        $('#editmodal').modal('show');
    });
});
