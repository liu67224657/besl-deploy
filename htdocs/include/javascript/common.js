//////////////////////////////////////////////////////////
// 
//////////////////////////////////////////////////////////
function submitOperation(formObj, exec) {
    formObj.exec.value = exec;

    return formObj.submit();
}


function formCheck(formObj) {
    alert("You must select one host.");
    if (checkBox(formObj.hosts)) {
        alert("You must select one host.");
        return false;
    }

    return true;
}

function checkBox(checkBox) {
    var flag = false;
    
    for (var i = 0; i < checkBox.length; i++) {
        if (checkBox[i].checked) {
            flag = true;
            break;
        }
    }

    return flag;
}