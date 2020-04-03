//Выпадающее меню
let menu = document.getElementById('menu');
let settings = document.getElementById('settings');
let add_admin_or_teacher = document.getElementById('some_punct_menu');
let exit = document.getElementById('exit');
menu.addEventListener('mouseover', function () {
    settings.style.backgroundSize = '40px';
    add_admin_or_teacher.style.backgroundSize = '40px';
    exit.style.backgroundSize = '40px';
 });
 menu.addEventListener('mouseout', function () {
     settings.style.backgroundSize = '0px';
      add_admin_or_teacher.style.backgroundSize = '0px';
      exit.style.backgroundSize = '0px';
     
 });

// обработчик нажатия на предмент

let discipline_list = document.querySelectorAll('#discipline_list li');
let discp_info = document.getElementById('info');
let sub_value = document.getElementById('sub_value');
let labs_list = document.getElementById('labs_list');
let form = document.getElementById('form');
let done_labs = document.getElementById('done_labs');
discipline_list.forEach(element => {
    element.addEventListener('click', get_info);
});
function get_info() {
    for (let i = 0; i < discipline_list.length; i++) {
        if (discipline_list[i].classList.contains('select')) {
            discipline_list[i].classList.remove('select');
            discipline_list[i].style.backgroundColor = '';
            discipline_list[i].style.color = '#000000';
        }
    }
    this.classList.add('select');
    form.style.display = 'block';
    this.style.color = '#ffffff';
    this.style.backgroundColor = '#072269';
    let info = this.textContent;
    discp_info.innerHTML = "<div><h2>" + info + "</h2></div>";
    sub_value.value = this.getAttribute('subjectid');
   
    get_labs(sub_value.value);
    get_donelabs(sub_value.value);
};

function get_labs(valll) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/teacher/laboratory/get?subjectid=' + valll);
    xhr.send();
    xhr.addEventListener('readystatechange',function(){
        labs_list.innerHTML = xhr.responseText;
    });
}

function get_donelabs(valll) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/teacher/donelaboratory/get?subjectid=' + valll);
    xhr.send();
    xhr.addEventListener('readystatechange',function(){
        done_labs.innerHTML = xhr.responseText;
    });
}