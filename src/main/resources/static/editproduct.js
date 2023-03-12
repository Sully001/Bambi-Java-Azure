function getName() {
    let file = document.getElementById('file');
    let name = file.files[0].name;
    document.getElementById("filename").value = name;
}

window.onload = function() {
    document.getElementById('filename').value = '';
}