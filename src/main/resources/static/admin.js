//Выпадающее меню
let menu = document.getElementById('menu');
let settings = document.getElementById('settings');
let add_admin_or_teacher = document.getElementById('some_punct_menu');
let exit= document.getElementById('exit');
let content = document.getElementById('content_wrapper');
let head = document.getElementById('header');
let add_txt = document.querySelector('#add span');
let group = document.getElementById('groups');
let all_group_info = document.getElementById("group_info");
let slide = document.getElementById('slide_container');
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

// Всплывающая форма
let teacher_list_wrapper = document.getElementById('teacher_list_wrapper');
let add = document.getElementById('add'); // Кнопка для всплывающей формы добавки и группы
let add_form1 = document.getElementById('add_toast_form1'); // всплывающая форма добавки группы
let add_form2 = document.getElementById('add_toast_form2'); // всплывающая форма добавки группы
let add_form3 = document.getElementById('add_toast_form3'); // всплывающая форма добавки группы
let fields_users = document.getElementById('fields_users');

let fields_groups = document.getElementById('fields_groups');
let close_btn = document.getElementsByClassName('close_toast_form_button');
let close_btn_little = document.getElementsByClassName('close_button');
let subid = document.getElementById('subid');

for(let i=0;i<close_btn.length;i++){
    close_btn[i].addEventListener('click',function(){
        close_btn[i].parentElement.style.display = 'none';
        content.style.opacity = '1';
        head.style.opacity = '1';
    });
}

add_admin_or_teacher.addEventListener('click', function () {
    
    add_form2.style.display = 'block';
    content.style.opacity = '0.3';
    head.style.opacity = '0.3';
    add_form2.style.opacity = '1';
    fields_users.innerHTML = `
    <div id="add_user_wrapper">
        <div id="users">
            <div> 
            <label>
           <input type="radio" name="typeuser" value="TEACHER" style='display:none;'>
            <span id='prepod'>Преподаватель</span>
            </label>
            <label>
           <input
            type="radio" name="typeuser" value="ADMIN" style='display:none;'>
            <span id='adminn'>Администратор</span>
            </label>
           </div>
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
    `;
    let prepod = document.getElementById('prepod');
        let adminn = document.getElementById('adminn');
        
        prepod.addEventListener('click',function(){
            if(!prepod.classList.contains('selected')){
                prepod.classList.add('selected');
            }
            if(adminn.classList.contains('selected')){
                adminn.classList.remove('selected');
            };
        });
        
        adminn.addEventListener('click',function(){
            if(!adminn.classList.contains('selected')){
                adminn.classList.add('selected');
            }
            if(prepod.classList.contains('selected')){
                prepod.classList.remove('selected');
            };
        });
});
add.addEventListener('click', function () {
    if(add_txt.textContent === 'Добавить группу'){
    add_form1.style.display = 'block';
    content.style.opacity = '0.3';
    head.style.opacity = '0.3';
    add_form2.style.opacity = '1';
    fields_groups.style.opacity = '1';
    fields_groups.innerHTML = `
 
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

    `;
}else{
    group.style.display = 'block';
    all_group_info.style.display = 'block';
    slide.style.display = 'none';
    add_txt.textContent = 'Добавить группу';
}});



//-------------------------------------------------------------------------------------------------------------------------------//

// запросы на списки групп

const groups = document.getElementById('group_list');
const xhr = new XMLHttpRequest();
xhr.open('GET', '/admin/group/getall');// подготовка запроса для отправки на сервер
xhr.send(); // отправляем запрос
xhr.addEventListener('readystatechange', function () {
    if (xhr.readyState === 4) {
        for (let i = 0; i < JSON.parse(xhr.responseText).length; i++) {

            let some_group_elem = document.createElement('li');
            some_group_elem.setAttribute('groupId',JSON.parse(xhr.responseText)[i]['id']);
            some_group_elem.style.position = 'relative';
            let closeBtn = document.createElement('img');
            closeBtn.setAttribute('src','/img/closeBtn.png');
            closeBtn.style.width = '30px';
            closeBtn.style.position = 'absolute';
            closeBtn.style.right = '10px';
            closeBtn.style.bottom = '4px';
            closeBtn.style.zIndex = '999';
            closeBtn.addEventListener('click',function () {
                let pt = confirm('Вы действительно хотите удалить группу?');
                if(pt) {
                    const xhr12 = new XMLHttpRequest();
                    xhr12.open('GET', '/admin/group/delete?groupId=' + some_group_elem.getAttribute('groupId'));// подготовка запроса для отправки на сервер
                    xhr12.send(); // отправляем запрос
                    closeBtn.parentElement.style.display = 'none';
                    all_group_info.innerHTML = '';
                }
            })
            some_group_elem.classList.add('close');
            some_group_elem.textContent = JSON.parse(xhr.responseText)[i]['name'];
            some_group_elem.append(closeBtn);
            groups.appendChild(some_group_elem);

            some_group_elem.addEventListener('click', function () {
                let isOpengroups = document.getElementsByClassName('close');
                for (let i = 0; i < isOpengroups.length; i++) {
                    if (isOpengroups[i].classList.contains('open')) {
                        isOpengroups[i].classList.remove('open');
                        isOpengroups[i].style.backgroundColor = '';
                        isOpengroups[i].style.color = "#000";
                    }
                };
                this.classList.add('open');
                this.style.backgroundColor = '#072269';
                this.style.color = '#ffffff';

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
                    let attache_container = document.getElementById('attache_container');
                    let attache_teacher_btn = document.getElementsByClassName('attache_teacher');
                  
                    for(let i=0;i<attache_teacher_btn.length;i++){
                        attache_teacher_btn[i].addEventListener('click',function(event){
                        subid.value = event.target.getAttribute('subject_id');
                        let xhr3 = new XMLHttpRequest();
                        xhr3.open('GET','/admin/users/get/teachers');
                        xhr3.send();
                        xhr3.addEventListener('readystatechange',function(){
                            attache_container.innerHTML =`
                            <select name="teacherid" id="select_teacher">

                            </select>
                        <input type="submit" value="Привязать">
                            `;
                            let select_teacher = document.getElementById('select_teacher');
                                JSON.parse(xhr3.responseText).forEach(element => {
                                    let prepod = document.createElement('option');
                                prepod.textContent = element['firstName'];  
                                prepod.textContent = prepod.textContent + element['middleName'];  
                                prepod.textContent = prepod.textContent + element['lastName']; 
                                prepod.value = element["id"]; 
                                select_teacher.appendChild(prepod);
                                add_form3.style.display = 'block';
                                content.style.opacity = '0.3';
                                head.style.opacity = '0.3';
                                add_form3.style.opacity = '1';
                                });
                        });
                    });
                   }
                      
                   let table_container_students = document.getElementById('students_wrapper');
                   let table_container_disc = document.getElementById('discipline_wrapper');
                   
                    add_user_form.addEventListener('click', function () {
                        discipline_toast_form.style.display = 'none';
                        table_container_disc.style.display = 'none';
                        table_container_students.style.display = 'none';
                        user_toast_form.style.display = 'block';
                    });
                    add_discipline_form.addEventListener('click', function () {
                        discipline_toast_form.style.display = 'block';
                        table_container_disc.style.display = 'none';
                        table_container_students.style.display = 'none';
                        user_toast_form.style.display = 'none';
                    });

                    for(let i=0;i<close_btn_little.length;i++){
                        close_btn_little[i].addEventListener('click',function(){
                            discipline_toast_form.style.display = 'none';
                             user_toast_form.style.display = 'none';
                             table_container_disc.style.display = 'block';
                        table_container_students.style.display = 'block';
                        });
                    }

                    let delDisc = document.getElementsByClassName('delDisc');

                    for (let i = 0;i<delDisc.length;i++) {
                        delDisc[i].addEventListener('click', function () {
                            let ptt = confirm('Вы действительно хотите удалить дисциплину?');
                            if (ptt) {
                                const xhr13 = new XMLHttpRequest();
                                xhr13.open('GET', '/admin/subject/delete?subjectId=' + this.getAttribute('delDisc'));// подготовка запроса для отправки на сервер
                                xhr13.send(); // отправляем запрос
                                delDisc[i].parentElement.parentElement.style.display = 'none';

                            }
                        });
                    }
                    let delSt = document.getElementsByClassName('delSt');

                    for (let i = 0;i<delSt.length;i++) {
                        delSt[i].addEventListener('click', function () {
                            let ptt = confirm('Вы действительно хотите удалить учащегося?');
                            if (ptt) {
                                const xhr13 = new XMLHttpRequest();
                                xhr13.open('GET', '/admin/user/delete?userId=' + this.getAttribute('stId'));// подготовка запроса для отправки на сервер
                                xhr13.send(); // отправляем запрос
                                delSt[i].parentElement.parentElement.style.display = 'none';


                            }
                        });
                    }
                    /*-------------------Обработчик нажатия на дисциплину---------------------------------------------------*/
                    for (let i = 0; i < discipline_list.length; i++) {
                        discipline_list[i].addEventListener('click', function () {
                            let xhr2 = new XMLHttpRequest();
                           let dispId = document.querySelectorAll('#group_info #about_group #discipline_wrapper table tbody tr td span');

                            dispId.forEach(element => {
                                element.addEventListener('click', function () {
                                    console.log(element.getAttribute('subject_id'));
                                    xhr2.open('GET', '/admin/subject/info?id=' + element.getAttribute('subject_id'));
                                    xhr2.send();
                                    xhr2.addEventListener('readystatechange', function () {

                                        teacher_list_wrapper.innerHTML = this.responseText;
                                       
                                    });
                                });
                            });

                        });
                    }

                });
            });
        }

    }
});


// слайдер

'use strict';
    var multiItemSlider = (function () {

      function _isElementVisible(element) {
        var rect = element.getBoundingClientRect(),
          vWidth = window.innerWidth || doc.documentElement.clientWidth,
          vHeight = window.innerHeight || doc.documentElement.clientHeight,
          elemFromPoint = function (x, y) { return document.elementFromPoint(x, y) };
        if (rect.right < 0 || rect.bottom < 0
          || rect.left > vWidth || rect.top > vHeight)
          return false;
        return (
          element.contains(elemFromPoint(rect.left, rect.top))
          || element.contains(elemFromPoint(rect.right, rect.top))
          || element.contains(elemFromPoint(rect.right, rect.bottom))
          || element.contains(elemFromPoint(rect.left, rect.bottom))
        );
      }

      return function (selector, config) {
        var
          _mainElement = document.querySelector(selector), // основный элемент блока
          _sliderWrapper = _mainElement.querySelector('.slider__wrapper'), // обертка для .slider-item
          _sliderItems = _mainElement.querySelectorAll('.slider__item'), // элементы (.slider-item)
          _sliderControls = _mainElement.querySelectorAll('.slider__control'), // элементы управления
          _sliderControlLeft = _mainElement.querySelector('.slider__control_left'), // кнопка "LEFT"
          _sliderControlRight = _mainElement.querySelector('.slider__control_right'), // кнопка "RIGHT"
          _wrapperWidth = parseFloat(getComputedStyle(_sliderWrapper).width), // ширина обёртки
          _itemWidth = parseFloat(getComputedStyle(_sliderItems[0]).width), // ширина одного элемента    
          _positionLeftItem = 0, // позиция левого активного элемента
          _transform = 0, // значение транфсофрмации .slider_wrapper
          _step = _itemWidth / _wrapperWidth * 100, // величина шага (для трансформации)
          _items = [], // массив элементов
          _interval = 0,
          _html = _mainElement.innerHTML,
          _states = [
            { active: false, minWidth: 0, count: 1 },
            { active: false, minWidth: 980, count: 2 }
          ],
          _config = {
            isCycling: false, // автоматическая смена слайдов
            direction: 'right', // направление смены слайдов
            interval: 5000, // интервал между автоматической сменой слайдов
            pause: true // устанавливать ли паузу при поднесении курсора к слайдеру
          };

        for (var key in config) {
          if (key in _config) {
            _config[key] = config[key];
          }
        }

        // наполнение массива _items
        _sliderItems.forEach(function (item, index) {
          _items.push({ item: item, position: index, transform: 0 });
        });

        var _setActive = function () {
          var _index = 0;
          var width = parseFloat(document.body.clientWidth);
          _states.forEach(function (item, index, arr) {
            _states[index].active = false;
            if (width >= _states[index].minWidth)
              _index = index;
          });
          _states[_index].active = true;
        }

        var _getActive = function () {
          var _index;
          _states.forEach(function (item, index, arr) {
            if (_states[index].active) {
              _index = index;
            }
          });
          return _index;
        }

        var position = {
          getItemMin: function () {
            var indexItem = 0;
            _items.forEach(function (item, index) {
              if (item.position < _items[indexItem].position) {
                indexItem = index;
              }
            });
            return indexItem;
          },
          getItemMax: function () {
            var indexItem = 0;
            _items.forEach(function (item, index) {
              if (item.position > _items[indexItem].position) {
                indexItem = index;
              }
            });
            return indexItem;
          },
          getMin: function () {
            return _items[position.getItemMin()].position;
          },
          getMax: function () {
            return _items[position.getItemMax()].position;
          }
        }

        var _transformItem = function (direction) {
          var nextItem;
          if (!_isElementVisible(_mainElement)) {
            return;
          }
          if (direction === 'right') {
            _positionLeftItem++;
            if ((_positionLeftItem + _wrapperWidth / _itemWidth - 1) > position.getMax()) {
              nextItem = position.getItemMin();
              _items[nextItem].position = position.getMax() + 1;
              _items[nextItem].transform += _items.length * 100;
              _items[nextItem].item.style.transform = 'translateX(' + _items[nextItem].transform + '%)';
            }
            _transform -= _step;
          }
          if (direction === 'left') {
            _positionLeftItem--;
            if (_positionLeftItem < position.getMin()) {
              nextItem = position.getItemMax();
              _items[nextItem].position = position.getMin() - 1;
              _items[nextItem].transform -= _items.length * 100;
              _items[nextItem].item.style.transform = 'translateX(' + _items[nextItem].transform + '%)';
            }
            _transform += _step;
          }
          _sliderWrapper.style.transform = 'translateX(' + _transform + '%)';
        }

        var _cycle = function (direction) {
          if (!_config.isCycling) {
            return;
          }
          _interval = setInterval(function () {
            _transformItem(direction);
          }, _config.interval);
        }

        // обработчик события click для кнопок "назад" и "вперед"
        var _controlClick = function (e) {
          if (e.target.classList.contains('slider__control')) {
            e.preventDefault();
            var direction = e.target.classList.contains('slider__control_right') ? 'right' : 'left';
            _transformItem(direction);
            clearInterval(_interval);
            _cycle(_config.direction);
          }
        };

        // обработка события изменения видимости страницы
        var _handleVisibilityChange = function () {
          if (document.visibilityState === "hidden") {
            clearInterval(_interval);
          } else {
            clearInterval(_interval);
            _cycle(_config.direction);
          }
        }

        var _refresh = function () {
          clearInterval(_interval);
          _mainElement.innerHTML = _html;
          _sliderWrapper = _mainElement.querySelector('.slider__wrapper');
          _sliderItems = _mainElement.querySelectorAll('.slider__item');
          _sliderControls = _mainElement.querySelectorAll('.slider__control');
          _sliderControlLeft = _mainElement.querySelector('.slider__control_left');
          _sliderControlRight = _mainElement.querySelector('.slider__control_right');
          _wrapperWidth = parseFloat(getComputedStyle(_sliderWrapper).width);
          _itemWidth = parseFloat(getComputedStyle(_sliderItems[0]).width);
          _positionLeftItem = 0;
          _transform = 0;
          _step = _itemWidth / _wrapperWidth * 100;
          _items = [];
          _sliderItems.forEach(function (item, index) {
            _items.push({ item: item, position: index, transform: 0 });
          });
        }

        var _setUpListeners = function () {
          _mainElement.addEventListener('click', _controlClick);
          if (_config.pause && _config.isCycling) {
            _mainElement.addEventListener('mouseenter', function () {
              clearInterval(_interval);
            });
            _mainElement.addEventListener('mouseleave', function () {
              clearInterval(_interval);
              _cycle(_config.direction);
            });
          }
          document.addEventListener('visibilitychange', _handleVisibilityChange, false);
          window.addEventListener('resize', function () {
            var
              _index = 0,
              width = parseFloat(document.body.clientWidth);
            _states.forEach(function (item, index, arr) {
              if (width >= _states[index].minWidth)
                _index = index;
            });
            if (_index !== _getActive()) {
              _setActive();
              _refresh();
            }
          });
        }

        // инициализация
        _setUpListeners();
        if (document.visibilityState === "visible") {
          _cycle(_config.direction);
        }
        _setActive();

        return {
          right: function () { // метод right
            _transformItem('right');
          },
          left: function () { // метод left
            _transformItem('left');
          },
          stop: function () { // метод stop
            _config.isCycling = false;
            clearInterval(_interval);
          },
          cycle: function () { // метод cycle 
            _config.isCycling = true;
            clearInterval(_interval);
            _cycle();
          }
        }

      }
    }());

    var slider = multiItemSlider('.slider', {
      isCycling: true
    })
