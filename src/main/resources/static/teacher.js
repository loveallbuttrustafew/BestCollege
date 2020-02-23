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

// обработчик нажатия на предмент

let discipline_list = document.querySelectorAll('#discipline_list li');
let discp_info = document.getElementById('info');
let sub_value = document.getElementById('sub_value');
discipline_list.forEach(element => {
    element.addEventListener('click',get_info);
});
function get_info(){
    for(let i = 0;i<discipline_list.length;i++){
        if(discipline_list[i].classList.contains('select')){
            discipline_list[i].classList.remove('select');
            discipline_list[i].style.backgroundColor = 'rgba(255,255,255,0.8)';
            discipline_list[i].style.color = '#000000';
        }
    }
    this.classList.add('select');
    this.style.color = '#ffffff';
    this.style.backgroundColor = '#7908AA';
    let info = this.textContent;
    discp_info.innerHTML = "<div><h2>" + info + "</h2></div>"; 
    sub_value.value = this.getAttribute('subjectid');
};
