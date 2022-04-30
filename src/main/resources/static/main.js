
const url = 'http://localhost:8080/admin';
const urlReg = 'http://localhost:8080/admin/registered';
const tableOdUsers = document.querySelector('.tableOfUsers');
const tableBody = document.querySelector('.tableBody');
const editModal = document.getElementById('editModal');
const newModal = document.getElementById('newModal');
const deleteModal = document.getElementById('deleteModal')
const editButtoninModal = editModal.querySelector('#btnEdit');
const deleteButtoninModal = deleteModal.querySelector('#btnDelete');
const newButtoninModal = newModal.querySelector('#btnNew');
const newLink = document.querySelector('#newUserLink')
const registeredUser = document.querySelector('#registered');
const regUserEmail = document.querySelector('#regUserEmail');
const regUserRoles = document.querySelector('#regUserRoles');
const tableHeader = document.querySelector('#tableHeader');
const secondTableHeader = document.querySelector('#secondTableHeader');
const adminTabs = document.querySelector('#tabs');
const adminMenu = document.querySelector('#adminMenu');
const userMenu = document.querySelector('#userMenu');


newLink.addEventListener('click', buttonNewUser);
editButtoninModal.addEventListener('click', editInModal);
deleteButtoninModal.addEventListener('click', deleteInModal);
newButtoninModal.addEventListener('click', newInModal);
tableBody.addEventListener('click', buttonEditUser);
tableBody.addEventListener('click', buttonDeleteUser);
document.addEventListener('DOMContentLoaded', getRegisteredUser);
document.addEventListener('DOMContentLoaded', showScreen);


//Получение зарегестрированного пользователя
async function getRegisteredUser() {
    const response = await fetch(`${urlReg}`);
    return await response.json();
}

// Действия в зависимости от роли Пользователя
async function showScreen() {
    const data = await getRegisteredUser();

    regUserEmail.textContent = data.email;
    let regRoles = '';
    for (let i = 0; i < data.roles.length; i++) {
        regRoles += ' ' + data.roles[i].name
        if (regRoles.includes('ROLE_ADMIN')) {
            tableHeader.textContent = 'Admin panel';
            secondTableHeader.textContent = 'All users'
            getUsers();
            userMenu.style.display = 'none';
        } else {
            showTableRegUser(data);
            tableHeader.textContent = 'User information panel';
            secondTableHeader.textContent = 'About user';
            adminTabs.style.display = 'none';
            adminMenu.style.display = 'none';
        }
    }

    regUserRoles.textContent = regRoles;

}

// Запрос на обноление всех пользвателей
function getUsers() {
    fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            showTable(data);
        });
}

//Построение таблицы пользователей
function showTable(data) {
    let out = '';
    tableBody.innerHTML = out;
    for (let user of data) {
        let rolesNames = '';
        for (let i = 0; i < user.roles.length; i++) {
            rolesNames = rolesNames + ' ' + user.roles[i].name;
        }
        out += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.secondName}</td>
                <td>${user.email}</td>
                <td>${user.age}</td>
                <td>${rolesNames}</td>
                <td data-id=${user.id}>
                <a href='href'  class="btn btn-primary updateBtn" id="editUser">Edit</a>
                </td>
                <td data-id=${user.id}>
                <a href='href'  class="btn btn-danger deleteBtn" id="deleteUser"> Delete</a>
                </td>
            </tr>`;
    }

    tableBody.innerHTML = out;

}

//Построение таблицы зарегистрированного пользователя
function showTableRegUser(user) {
    let out = '';
    tableBody.innerHTML = out;
    let rolesNames = '';
    for (let i = 0; i < user.roles.length; i++) {
        rolesNames = rolesNames + ' ' + user.roles[i].name;
    }
    out = `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.secondName}</td>
                <td>${user.email}</td>
                <td>${user.age}</td>
                <td>${rolesNames}</td>
                <td data-id=${user.id}>
                <a href='href'  class="btn btn-primary updateBtn" id="editUser">Edit</a>
                </td>
                <td data-id=${user.id}>
                <a href='href'  class="btn btn-danger deleteBtn" id="deleteUser"> Delete</a>
                </td>
            </tr>`;
    tableBody.innerHTML = out;
}

//Модальное окно редактирования пользователя
function buttonEditUser(e) {
    e.preventDefault();
    let editButtonPressed = e.target.id == 'editUser';
    let id = e.target.parentElement.dataset.id;
    if (editButtonPressed) {
        fetch(`${url}/${id}`)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                $('#editId').val(data.id);
                $('#editName').val(data.name);
                $('#editSecondName').val(data.secondName);
                $('#editAge').val(data.age);
                $('#editEmail').val(data.email);
                $.each(data.roles, function (index, value) {
                    if (value.name == 'ROLE_ADMIN') {
                        $('#editrId1').prop('checked', true);
                    }
                    if (value.name == 'ROLE_USER') {
                        $('#editrId2').prop('checked', true);
                    }
                });

                $('#editModal').modal('show');
            });
    }
}

//Действия при нажатии на кнопку EDIT в модальном окне
function editInModal(e) {
    e.preventDefault();
    let modalButtonEditPressed = e.target.id == 'btnEdit';
    let id = document.getElementById('editId').value;

    if (modalButtonEditPressed) {
        let roles = [];

        if (editModal.querySelector('#editrId1').checked) {
            roleAdmin = {
                id: 1,
                name: 'ROLE_ADMIN',
                active: false,
            };
            roles.push(roleAdmin);
        }

        if (editModal.querySelector('#editrId2').checked) {
            roleUser = {
                id: 2,
                name: 'ROLE_USER',
                active: false,
            };
            roles.push(roleUser);
        }

        const formData = {
            id: editModal.querySelector('#editId').value,
            name: editModal.querySelector('#editName').value,
            secondName: editModal.querySelector('#editSecondName').value,
            email: editModal.querySelector('#editEmail').value,
            password: editModal.querySelector('#editPassword').value,
            age: editModal.querySelector('#editAge').value,
            roles: roles,
        };

        //Запрос на обновление пользователя
        fetch(`${url}/${id}`, {
            method: 'PUT',
            body: JSON.stringify(formData),
            headers: {'Content-Type': 'application/json'},
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                getUsers();
                $('#editModal').modal('toggle');
            });
    }
}

//Модальное окно удаления пользователя
function buttonDeleteUser(e) {
    e.preventDefault();

    let deleteButtonPressed = e.target.id == 'deleteUser';
    let id = e.target.parentElement.dataset.id;
    if (deleteButtonPressed) {
        fetch(`${url}/${id}`)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                $('#deleteId').val(data.id);
                $('#deleteName').val(data.name);
                $('#deleteSecondName').val(data.secondName);
                $('#deleteAge').val(data.age);
                $('#deleteEmail').val(data.email);
                $.each(data.roles, function (index, value) {
                    if (value.name == 'ROLE_ADMIN') {
                        $('#deleterId1').prop('checked', true);
                    }
                    if (value.name == 'ROLE_USER') {
                        $('#deleterId2').prop('checked', true);
                    }
                });

                $('#deleteModal').modal('show');
            });
    }
}

//Действия при нажатии на кнопку DELETE в модальном окне
async function deleteInModal(e) {
    e.preventDefault();
    //let modalButtonDeletePressed = e.target.id == 'btnDelete'; // Альтернативный способ обращения к кнопке
    let id = document.getElementById('deleteId').value;

    //if (modalButtonDeletePressed) {
        const regUser = await getRegisteredUser();
        if (id == regUser.id) {
            alert('Невозможно удалить Зарегистрированного пользователя')
        } else {
            fetch(`${url}/${id}`, {
                method: 'DELETE'
            }).then((response) => {
                if (response.ok) {
                    getUsers();
                    $('#deleteModal').modal('toggle');
                }
            })
        }
    //}
}

//Модальное окно добавления пользователя
function buttonNewUser(e) {
    e.preventDefault();
    $('#newModal').modal('show');
}

//Действия при нажатии на кнопку New в модальном окне
function newInModal(e) {
    e.preventDefault();

    let roles = [];

    if (newModal.querySelector('#newrId1').checked) {
        roleAdmin = {
            id: 1,
            name: 'ROLE_ADMIN',
            active: false,
        };
        roles.push(roleAdmin);
    }

    if (newModal.querySelector('#newrId2').checked) {
        roleUser = {
            id: 2,
            name: 'ROLE_USER',
            active: false,
        };
        roles.push(roleUser);
    }

    const formData = {
        name: newModal.querySelector('#newName').value,
        secondName: newModal.querySelector('#newSecondName').value,
        email: newModal.querySelector('#newEmail').value,
        password: newModal.querySelector('#newPassword').value,
        age: newModal.querySelector('#newAge').value,
        roles: roles,
    };

    //Запрос на добавление пользователя
    fetch(`${url}`, {
        method: 'POST',
        body: JSON.stringify(formData),
        headers: {'Content-Type': 'application/json'},
    })
        .then((response) => {
            console.log(response);
            if (response.status == 201) {
                getUsers();
                $('#newModal').modal('toggle');
            } else {
                console.log('Ошибка добавления пользователя');
            }
        })


}






















