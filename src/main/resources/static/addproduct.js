function getName() {
    let file = document.getElementById('file');
    let name = file.files[0].name;
    document.getElementById("filename").value = name;
}

window.onload = function() {
    document.getElementById('filename').value = '';
}


let form1 = document.getElementById("form1");
let form2 = document.getElementById("form2");


let next1 = document.getElementById("next1");
let back1 = document.getElementById("back1");

let progress = document.getElementById("progress");

next1.onclick = function () {
    form1.style.left = "-650px";
    form2.style.left = "40px";
    progress.style.width = "700px"
}

back1.onclick = function () {
    form1.style.left = "40px";
    form2.style.left = "750px";
    progress.style.width = "350px"
}

function remove() {
    let inputs = document.querySelectorAll('.size');
    inputs.forEach(element => {
        element.value = '';
    })
}

function set100() {
    let inputs = document.querySelectorAll('.size');
    inputs.forEach(element => {
        element.value = 100;
    })
}

function set200() {
    let inputs = document.querySelectorAll('.size');
    inputs.forEach(element => {
        element.value = 200;
    })
}