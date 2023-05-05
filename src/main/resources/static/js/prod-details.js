/**
 * 
 */

let inStock = document.querySelector('.available');
let outStock = document.querySelector('.unavailable');
let price = document.querySelector('.price');
let priceBefore = document.querySelector('.bfore-price');
let buy = document.querySelector('.buy');
let cart = document.querySelector('.cart');
let inp = document.querySelector('input');
let qty = document.querySelector('.qty');
let searchResultsCont = document.querySelector('.products-result');
let cartImg = document.querySelector(`.header-cart img`);
 let userName = document.querySelector(".profile");
let prodId = document.querySelector(`.hidden-id`);

let searchBtn = document.querySelector('.btn-srch');
let searchText = document.querySelector('.search');

let searchBtnOnClickFun = function srcbtnOnClick(e) {

	if (searchText.value.length != 0)
		location.href = `http://localhost:8080/products/name/${searchText.value}`;
};
let searchOnVal = function(e) {
	removePrevResultDivs();
	if (searchText.value.length != 0) {
		let url = `http://localhost:8080/rest/api/products/product/${searchText.value}`;
		fetch(url).then((rs) => {

			rs.json().then((rs) => {
				console.log(rs.length);

				if (rs.length > 0) {
					rs.length = rs.length > 7 ? 7 : rs.length;
					createResultDivs(rs);
				}

			})
				.catch((e => { console.log("Error ........") }));

		});

	}

};
// remove previous search  result divs 
function removePrevResultDivs() {
	searchResultsCont.style.width=`0`;
	searchResultsCont.innerHTML = "";
}
function createResultDivs(arr) {
	searchResultsCont.children=0;
	searchResultsCont.style.visibility=`hidden`;
	searchResultsCont.style.width=`84%`;
	for (let i = 0; i < arr.length; i++) {
		let resultItem = document.createElement('div');
		let resultText = document.createTextNode(arr[i].name);
		resultItem.append(resultText);
		resultItem.classList.add('result-item');
		resultItem.setAttribute('proId', arr[i].id);
		searchResultsCont.append(resultItem);

	}
	searchResultsCont.style.visibility=`visible`;
}
searchBtn.addEventListener('click', searchBtnOnClickFun);
searchText.addEventListener(`input`, searchOnVal);

if (+(price.innerText) < 0) {
	inStock.style.display = 'none';
	outStock.style.display = 'block';
	price.innerText = 'not present Now';
	price.style.fontSize = '12px';
	priceBefore.style.display = 'none';
	buy.style.cursor = 'no-drop';
	cart.style.cursor = 'no-drop';
	qty.style.display = 'none';
}
let locationSplit = location.href.split("/");
let itemId = prodId.getAttribute(`id-value`);
console.log(itemId);
let prodUrl = location.protocol + `//` + location.hostname + `:` + location.port + `/rest/api/products/id/${itemId}`;
console.log(prodUrl);
let prodPromise = fetch(prodUrl).then(rs => { return rs.json() });
setNumberOfAvailableItems(prodPromise);


async function setNumberOfAvailableItems(pro) {
	let prod = await pro;
	let itemCount = qty.getAttribute(`item-count`);
	console.log(itemCount);
	let length = prod.unitNumbers > 10 ? 10 : prod.unitNumbers;
	for (let i = 1; i <= length; i++) {
		let opt = document.createElement('option');
		let optVal = document.createTextNode(i);
		opt.value = i;
		opt.append(optVal);
		if(itemCount == i){
			opt.setAttribute(`selected`,true);
		}
		qty.append(opt);
	}

}

document.addEventListener('click', (e) => {
	if (e.target.hasAttribute("class") && e.target.getAttribute("class") == 'result-item') {
		let uri = `http://localhost:8080/products/id/${e.target.getAttribute('proId')}`;
		console.log(uri);
		location.href = uri;
	}
});



/*	.then(rs=>{
		console.log(rs);
		return rs;
	}).catch(e=>{
		Error(e);
	})});



*/
cartImg.addEventListener('click',(e)=>{
	console.log(userName.textContent.trim());
	if(userName.textContent.trim()==`UN`){
		let bool = window.confirm(`You should Login or SignUp first`);
		/*
		if(bool){
			let uri = `http://localhost:8080/cart`;
		}
		*/
	}else {
		let uri = `http://localhost:8080/cart`;
		location.href=uri;
	}
	
});





















