//Выпадающее меню
let menu = document.getElementById('menu');
let settings = document.getElementById('settings');
let add_admin_or_teacher = document.getElementById('some_punct_menu');
let exit = document.getElementById('exit');

menu.addEventListener('mouseover', function () {
    settings.style.backgroundImage = 'url(/img/setting.png)';
    add_admin_or_teacher.style.backgroundImage = 'url(/img/point.png)';
    exit.style.backgroundImage = 'url(/img/exit.png)';
});
menu.addEventListener('mouseout', function () {
    settings.style.backgroundImage = '';
    add_admin_or_teacher.style.backgroundImage = '';
    exit.style.backgroundImage = '';
});

// Всплывающая форма
let add = document.getElementById('add'); // Кнопка для всплывающей формы добавки юзера и группы
let add_form = document.getElementById('add_toast_form'); // всплывающая форма добавки юзера и группы
add_admin_or_teacher.addEventListener('click', function () {
    add_form.style.display = 'block';
    add_form.innerHTML = `
    <!-------Кнопка закрытия формы------------>
    <div id="close_toast_form_button">
        <!--Кнопка закрыть-->
    </div>
    <form action="/admin/user/add" method="POST" id="users_toast_form">
    <input name="_csrf" type="hidden" value="{{_csrf.token}}" />
    <div id="add_user_wrapper">
        <div id="users">
            <div> Преподаватель<input type="radio" name="typeuser" value="TEACHER">Администратор<input
                    type="radio" name="typeuser" value="ADMIN"></div>
            <div><input type="text" name="firstname" placeholder="Имя"></div>
            <div><input type="text" name="secondname" placeholder="Фамилия"></div>
            <div><input type="text" name="lastname" placeholder="Отчество"></div>
            <div><input type="text" name="phonenumber" placeholder="Номер телефона"></div>
            <div><input type="text" name="username" placeholder="Логин"></div>
            <div><input type="password" name="password" placeholder="Пароль"></div>
            <div id="sub_users">
            <input type="submit" value="Добавить">
        </div>
    </div>
</form>
    `;
    let close_btn = document.getElementById('close_toast_form_button'); // кнопка закрытия формы
    close_btn.addEventListener('click', function () {
        add_form.style.display = 'none';
    });
    let form_for_admin_or_user = document.getElementById('users_toast_form');//форма добавки админа или препода
    form_for_admin_or_user.style.display = 'block';
});
add.addEventListener('click', function () {
    add_form.style.display = 'block';
    add_form.innerHTML = `
    <!-------Кнопка закрытия формы------------>
    <div id="close_toast_form_button">
        <!--Кнопка закрыть-->
    </div>
    <form action="/admin/group/add" method="POST">
            <input name="_csrf" type="hidden" value="{{_csrf.token}}" />
            <div id="add_group_wrapper">
                <div id="add_group_container">
                    <div id="group_name">
                        <input type="text" name="groupname" placeholder="Наименование группы:">
                    </div>

                    <div>
                        <input type="text" name="groupcourse" id="course" placeholder="Курс:">
                    </div>

                    <div id="group_number">
                        <input type="text" name="groupnumber" placeholder="Номер группы:">
                    </div>
                    <div id="sub_group">
                            <input type="submit" value="Добавить">
                    </div>
                </div>
            </div>
        </form>
    `;
    let close_btn = document.getElementById('close_toast_form_button'); // кнопка закрытия формы
    close_btn.addEventListener('click', function () {
        add_form.style.display = 'none';
    });
    let add_group_form = document.getElementById('add_group_wrapper'); // форма для добавки группы
    add_group_form.style.display = 'block';
});



//-------------------------------------------------------------------------------------------------------------------------------//

// запросы на списки групп
let all_group_info = document.getElementById("group_info");
const groups = document.getElementById('group_list');
const xhr = new XMLHttpRequest();
xhr.open('GET', '/admin/group/getall');// подготовка запроса для отправки на сервер
xhr.send(); // отправляем запрос
xhr.addEventListener('readystatechange', function () {
    if (xhr.readyState === 4) {
        for (let i = 0; i < JSON.parse(xhr.responseText).length; i++) {
            some_group_elem = document.createElement('li');
            some_group_elem.classList.add('close');
            some_group_elem.textContent = JSON.parse(xhr.responseText)[i]['name'];
            groups.appendChild(some_group_elem);

            some_group_elem.addEventListener('click', function () {
                let isOpengroups = document.getElementsByClassName('close');
                for (let i = 0; i < isOpengroups.length; i++) {
                    if (isOpengroups[i].classList.contains('open')) {
                        isOpengroups[i].classList.remove('open');
                    }
                };
                this.classList.add('open');





                let xhr1 = new XMLHttpRequest();
                xhr1.open('GET', '/admin/group/info?id=' + JSON.parse(xhr.responseText)[i]["id"]);// подготовка запроса для отправки на сервер
                xhr1.send();
                xhr1.addEventListener('readystatechange', function () {

                    all_group_info.innerHTML = this.responseText;
                    /*-----------------------------пробую навесить обработчик на студяг-----------------------------------------------------------*/
                    all_group_info.style.display = 'none';
                    all_group_info.style.display = 'block';

                    let student_list = document.querySelectorAll('#students_wrapper table');
                    let discipline_list = document.querySelectorAll('#discipline_wrapper table');
                    let add_user_form = document.getElementById('add_user_form');
                    let add_discipline_form = document.getElementById('add_discipline_form');
                    let user_toast_form = document.getElementById('user_toast_form');
                    let discipline_toast_form = document.getElementById('discipline_toast_form');
                    add_user_form.addEventListener('click', function () {
                        discipline_toast_form.style.display = 'none';
                        user_toast_form.style.display = 'block';
                    });
                    add_discipline_form.addEventListener('click', function () {
                        discipline_toast_form.style.display = 'block';
                        user_toast_form.style.display = 'none';
                    });

                    for (let i = 0; i < discipline_list.length; i++) {
                        discipline_list[i].addEventListener('click', function () {
                            let xhr2 = new XMLHttpRequest();
                            dispId = document.querySelectorAll('#group_info #about_group #discipline_wrapper table tbody tr');

                            dispId.forEach(element => {
                                element.addEventListener('click', function () {
                                    xhr2.open('GET', '/admin/subject/info?id=' + element.getAttribute('subject_id'));
                                    xhr2.send();
                                    xhr2.addEventListener('readystatechange', function () {

                                        add_form.innerHTML = this.responseText;
                                        all_group_info.style.display = 'none';
                                        all_group_info.style.display = 'block';
                                        add_form.style.display = 'block';

                                        close_btn2 = document.getElementById('close_toast_button');
                                        close_btn2.addEventListener('click', function () {
                                            add_form.style.display = 'none';
                                        });
                                    });
                                });
                            });

                        });
                    }
                    for (let i = 0; i < student_list.length; i++) {
                        student_list[i].addEventListener('click', function () {
                            student_list[i].style.color = 'green';
                        });
                    }


                });
            });
        }

    }
});


/*---------------------------------------------Всплывающая форма для препода и админа-----------------------------------------*/

