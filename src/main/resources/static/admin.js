//Выпадающая меню
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
let add = document.getElementById('add');
let add_form = document.getElementById('add_toast_form');
let add_user = document.getElementById('user_form');
let add_group = document.getElementById('add_group_wrapper');
let type_add = document.getElementsByName('choice');
let type_radio = document.getElementsByName('type_user');

add.addEventListener('click',function(){
    add_form.style.display = 'block';
});

for(let i=0;i<type_add.length;i++){
    if(type_add[i].value === 'add_user'){
        type_add[i].addEventListener('click',function(){
            add_user.style.display = 'block';
            add_group.style.display = 'none';
        });
    }
    if(type_add[i].value === 'add_group'){
        type_add[i].addEventListener('click',function(){
            add_group.style.display = 'block';
            add_user.style.display = 'none';
        });
}}

console.log(type_add);

