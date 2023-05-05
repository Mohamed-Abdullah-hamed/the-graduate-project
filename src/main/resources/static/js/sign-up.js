/**
 * 
 */
let submitBtn = document.querySelector(`#submit`);
let custName = document.querySelector(`#cust-name`);
let userEmail = document.querySelector(`#user-email`);
let userPass = document.querySelector(`#user-pass`);
let city = document.querySelector(`#cust-city`);
let district = document.querySelector(`#cust-district`);
let street = document.querySelector(`#cust-street`);
let zCode = document.querySelector(`#cust-zCode`);
let areTrueInputs = false;
submitBtn.addEventListener(`click`, async function(evt) {
	if (!areTrueInputs) {
		evt.preventDefault();
	}
	else {
		return true;
	}

	let bol = await validateInputs();
	if (bol) {
		areTrueInputs = true;
		submitBtn.click();
	}


});

async function validateInputs() {

	let nameUnq = await isNameUnique(custName.value)
	let emailUnq = await isEmailUnique(userEmail.value);
	console.log(nameUnq);
	console.log(emailUnq);
	if (custName.value != "" && nameUnq == true
		&& userEmail.value != "" && emailUnq == true
	 /*
	 && userPass.value!= ""
	 &&city.value!= ""
	 &&district.value!= "" 
	 &&street.value!= ""
	 &&zCode.value!= ""
	 */) {

		return true;
	}
	return false;
}

async function isEmailUnique(email) {

	let uri = `http://localhost:8080/rest/api/user/isEmailUnique/${email}`;
	let bool = await fetch(uri).then((rs) => {
		return rs.json();
	});
	return bool;
}
async function isNameUnique(name) {

	let uri = `http://localhost:8080/rest/api/user/isNameUnique/${name}`;
	let bool = await fetch(uri).then((rs) => {
		return rs.json();
	});
	return bool;
}


// add event listeners to show the validation msgs
//******************************************* 
custName.addEventListener(`blur`, async (e) => {
	let nameMsg = document.querySelector(`#valid-name-msg`);
	let nameUnq = await isNameUnique(custName.value);
	if (nameUnq != true) {
		if (nameMsg == null) {
			let parent1 = e.target.parentNode;
			let nde = document.createElement(`p`);
			if (custName.value != ``) {
				nde.innerText = `this name is already in use please choose another one `;
				nde.id = `valid-name-msg`;
				nde.style.color = `red`;
				parent1.insertBefore(nde, custName.nextSibling);
			}

		}
	}
	else {
		if (nameMsg != null) {
			nameMsg.remove();
		}
	}
});
userEmail.addEventListener(`blur`, async (e) => {
	let emailMsg = document.querySelector(`#valid-email-msg`);

	let emailUnq = await isEmailUnique(userEmail.value);
	console.log(emailUnq);
	if (emailUnq != true) {
		console.log(emailUnq);
		if (emailMsg == null) {
			let parent1 = e.target.parentNode;
			let nde = document.createElement(`p`);
			if (userEmail.value != ``){
				nde.innerText = `this email is already in use please choose another one `;
				nde.id = `valid-email-msg`;
				nde.style.color = `red`;
				parent1.insertBefore(nde, userEmail.nextSibling);
			}
		}
	}
	else {

		console.log(emailMsg);
		if (emailMsg != null) {
			//		console.log(emailMsg);
			emailMsg.remove();
		}
	}
});





















