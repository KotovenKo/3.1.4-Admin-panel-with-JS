
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
    $('.updateBtn').click(function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            method: "GET"
        }).then(function(user) {

            $('#id').val(user.id);
            $('#name').val(user.name);
            $('#secondName').val(user.secondName);
            $('#age').val(user.age);
            $('#email').val(user.email);
         //   $('#password').val(user.password);
            $.each(user.roles, function (index,value) {
                console.log('Индекс' + index + '; Значение' + value.name); //Выод в консоль работает
                // Как добавить роли в checkboxes модальном окне?
            });
        });

        $('#editmodal').modal('show');
    });
});
