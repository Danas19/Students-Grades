var studentsTag = document.getElementById("students");

var ourRequest = new XMLHttpRequest();


ourRequest.open('GET', 'http://localhost:8080/api/students?sortingTypeString=LAST_NAME_DESC_FIRST_NAME_DESC');
ourRequest.onload = function () {
    var dataStudents = JSON.parse(ourRequest.responseText);
    console.log(dataStudents);
    liMakerStudents(dataStudents);
}
ourRequest.send();

function liMakerStudents(dataStudents) {
    var ulTag = document.createElement("ul");

    for (var i = 0; i < dataStudents.length; i++) {
        var liTag = document.createElement("li");
        liTag.innerHTML = "id: " + dataStudents[i].id + " " + dataStudents[i].firstName + " " + dataStudents[i].lastName + " birth date: " + dataStudents[i].birthDate + ", age: " + dataStudents[i].age + ". Grades: ";
        showGrades(dataStudents[i].id, liTag);
        ulTag.appendChild(liTag);
    }

    studentsTag.appendChild(ulTag);
}

function liMakerStudentGrades(dataGrades, liTag) {
    for (var i = 0; i < dataGrades.length; i++) {
        var gradeTagLi = document.createElement("li");
        gradeTagLi.innerHTML = dataGrades[i].schoolSubject + " --  grade: " + dataGrades[i].grade + ". Date: " + dataGrades[i].gradeDate;
        gradeTagLi.style.color = "green";
        gradeTagLi.fontSize = "0.5em";
        liTag.appendChild(gradeTagLi);
    }
}


function showGrades(studentId, liTag) {
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'http://localhost:8080/api/students/' + studentId + "/grades");
    ourRequest.onload = function () {
        var dataGrades = JSON.parse(ourRequest.responseText);
        console.log(dataGrades);
        liMakerStudentGrades(dataGrades, liTag);
    }
    ourRequest.send();
}



document.forms[0].addEventListener("submit", function (e) {
    var values = e.srcElement.elements;

    var newStudent = {
        firstName: values[0].value,
        lastName: values[1].value,
        birthDate: values[2].value,
        age: values[3].value
    }

    fetch('http://localhost:8080/api/students', {
        method: 'POST',
        body: JSON.stringify(newStudent),
        headers: {
            'Content-Type': 'application/json'
        }
    });
});