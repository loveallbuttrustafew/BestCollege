//Выпадающее меню
let menu = document.getElementById('menu');
let settings = document.getElementById('settings');
let points = document.getElementById('some_punct_menu');
let exit = document.getElementById('exit');

menu.addEventListener('mouseover',function(){
    settings.style.backgroundImage = 'url(/img/setting.png)';
    points.style.backgroundImage = 'url(/img/point.png)';
    exit.style.backgroundImage = 'url(/img/exit.png)';
});
menu.addEventListener('mouseout',function(){
    settings.style.backgroundImage = '';
    points.style.backgroundImage = '';
    exit.style.backgroundImage = '';
});

// Всплывающая форма
let add = document.getElementById('add'); // Кнопка для всплывающей формы добавки юзера и группы
let add_form = document.getElementById('add_toast_form'); // всплывающая форма добавки юзера и группы
let user_form = document.getElementById('user_form');  // форма для добавки юзера
let add_group_form = document.getElementById('add_group_wrapper'); // форма для добавки группы
let type_radio = document.getElementsByName('type_user'); // массив радиобаттонов для выбора типа юзера
const close_btn = document.getElementById('close_toast_form_button'); // кнопка закрытия формы
const add_discipline_form = document.getElementById('add_discipline_form');


close_btn.addEventListener('click',function(){
    add_form.style.display = 'none';
});
add.addEventListener('click',function(){
    add_form.style.display = 'block';
    add_group_form.style.display = 'block';
    user_form.style.display = 'none';
});



//-------------------------------------------------------------------------------------------------------------------------------//

// запросы на списки групп
let all_group_info = document.getElementById("group_info");
const groups = document.getElementById('group_list');
const xhr = new XMLHttpRequest();
xhr.open('GET','/admin/group/getall');// подготовка запроса для отправки на сервер
xhr.send(); // отправляем запрос
xhr.addEventListener('readystatechange',function(){
    if(xhr.readyState === 4){
       for(let i = 0;i<JSON.parse(xhr.responseText).length;i++){
           some_group_elem = document.createElement('li');
           some_group_elem.classList.add('close');
           some_group_elem.textContent = JSON.parse(xhr.responseText)[i]['name'];
           groups.appendChild(some_group_elem);

           some_group_elem.addEventListener('click',function(){
                 let isOpengroups = document.getElementsByClassName('close');
                 for(let i = 0;i<isOpengroups.length;i++){
                    if(isOpengroups[i].classList.contains('open')){
                        isOpengroups[i].classList.remove('open');
                    }
                 };
                 this.classList.add('open');


                 let xhr1 = new XMLHttpRequest();
                    xhr1.open('GET','/admin/group/info?id=' + JSON.parse(xhr.responseText)[i]["id"]);// подготовка запроса для отправки на сервер
                    xhr1.send();
                    xhr1.addEventListener('readystatechange',function(){
                    all_group_info.innerHTML = this.responseText;
                    });                
            });
       }
    }
});
