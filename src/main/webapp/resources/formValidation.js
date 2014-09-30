
function passwordValidation(pwd, mx, my) {
	var pwd_length = pwd.value.length;
	if (pwd_length > 0 && pwd_length < mx) {
		alert("Password is too short.(4 - 12 characters)");
		pwd.style.backgroundColor = "yellow";
		pwd.focus();
		return false;
	} else if (pwd_length > my) {
		alert("Password is too long.(4 - 12 characters)");
		pwd.style.backgroundColor = "yellow";
		pwd.focus();
		return false;
	}
	pwd.style.backgroundColor = "white";
	return true;
}
function allLetterValidation(component, msg) {
	var letters = /^[A-Za-z ]+$/;
	if (component.value.match(letters)) {
		component.style.backgroundColor = "white";
		return true;
	} else {
		alert(msg);
		component.style.backgroundColor = "yellow";
		component.focus();
		return false;
	}
}
function allNumericValidation(component, msg) {
	var numbers = /^[0-9]+$/;
	if(component.value.match(numbers)) {
		component.style.backgroundColor = "white";
		return true;
	} else {
		alert(msg);
		component.style.backgroundColor = "yellow";
		component.focus();
		return false;
	}
}
function letterNumericValidation(component, msg) {
	var letters = /^[0-9a-zA-Z. ]+$/;
	if(component.value.match(letters)) {
		component.style.backgroundColor = "white";
		return true;
	} else {
		alert(msg);
		component.style.backgroundColor = "yellow";
		component.focus();
		return false;
	}
}
function phoneValidation(phone) {
	//var phoneNum = /^\(?([0-9]{3})\)?[-.]?([0-9]{3}[-.]?([0-9]{4})$/;
	var phoneNum = /^\d{3}-\d{3}-\d{4}$/;
	if(phone.value.match(phoneNum)) {
		component.style.backgroundColor = "white";
		return true;
	} else {
		alert("Phone number must be xxx-xxx-xxxx.");
		phone.style.backgroundColor = "yellow";
		phone.focus();
		return false;
	}
}
