let searchInput = document.getElementById('searchInput');
searchInput.oninput = search;

function searchGroups(arr,value){
   let str = value.split('');
   let str2 = [];
   for (let i = 0;i<arr.length;i++){
       str2.push(arr[i].textContent.split(''));
   }
   for (let i=0;i<str2.length;i++){
       if(str2[i].slice(0,str.length).join('').toUpperCase() === str.join('').toUpperCase()){
           arr[i].style.display = 'block';
       }else {
           arr[i].style.display = 'none';
       }
   }
}

function search() {
    let flag = true;
    let searchValue = searchInput.value;
    let searchGroup = document.getElementsByClassName('close');
    let students = document.getElementsByClassName('student');
    if(searchInput.value!==''){

        for(let i = 0;i<searchGroup.length;i++){
            if(searchGroup[i].classList.contains('open')){
                let studentsNames = [];
                flag = false;
                for(let i = 0;i<students.length;i++){
                   if(students[i].children[0].firstChild.textContent.slice(0,searchValue).toUpperCase() === searchValue.toUpperCase()&& searchValue!=''){
                       students[i].style.display = 'block';
                    }else{
                       students[i].style.display = 'none';
                    }
                }

            }
        }
        if(flag){
            searchGroups(searchGroup,searchValue);
        }
    }else{
        for(let j = 0;j<searchGroup.length && j<students.length;j++){
            searchGroup[j].style.display = 'block';
            students[j].style.display = 'table-row';
        }
    }
}
