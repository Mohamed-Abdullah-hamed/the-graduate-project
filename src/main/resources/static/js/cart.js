/**
 * 
 */
let searchBtn = document.querySelector('.btn-srch');
let searchText = document.querySelector('.search');
let searchResultsCont = document.querySelector('.products-result');

let itemsPrice = document.querySelector(`.items-price`);
let itemsCount = document.querySelector(`.no-items`);
// list of select input on all cards
let selectList = document.querySelectorAll(`.item-count`);
let cardList = document.querySelectorAll(`.card`);
let totalProceed = document.querySelector(`.total-proceed`);
let deleteBtn = document.querySelectorAll(`.del-btn`);
let subTotal = document.querySelector(`.sub-total p span`);
let subTotalPrice = document.querySelector(`.sub-total .price`);
let prodsForm = document.getElementById(`checkedCart`);
let emptyCart = document.querySelector(`.empty-cart`);
if (emptyCart == null) {
	prodsForm.style.display = `grid`;
} else {
	prodsForm.style.display = `none`;
}
// list of all checkBox inputs eighter checked or not 
let allItemList = document.querySelectorAll(`.check-item`);
// list of all checked checkBox inputs 
let cheackedItemList = document.querySelectorAll(`.check-item`).checked;
let checkedItemsCard = {
	totalPrice: -1,
	count: -1
};
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

setItemsCount();

setInitialCheckedItemObject();
searchBtn.addEventListener('click', searchBtnOnClickFun);

searchText.addEventListener('input', searchOnVal);
// set number of each Item on the Select input, and call an change Event Listener on all of these select Inputs
// to change the total Number of them on displayed subtotal div
async function setItemsCount() {
	for (let i = 0; i < selectList.length; i++) {
		let prodId = selectList[i].getAttribute(`prod-id`);
		let count = +(selectList[i].getAttribute(`count`));
		let price = +(selectList[i].getAttribute("item-price"));
		let prodUrl = location.protocol + `//` + location.hostname + `:` + location.port + `/rest/api/products/id/${prodId}`;
		let prodPromise = fetch(prodUrl).then(rs => { return rs.json() });
		let product = await prodPromise;
		console.log(selectList[i]);
		console.log(cardList[i]);
		console.log(product);
		let cheackedItem = cardList[i].querySelector(`.check-item`);
		setNumberOfAvailableItems(product, count, selectList[i]);

		selectList[i].addEventListener('change', async (event) => {
			let cheacked = cheackedItem.checked;
			let currentVal = +(event.target.value);
			await updateCartItem(product, currentVal);
			let count = +(selectList[i].getAttribute(`count`));
			selectList[i].setAttribute(`count`, currentVal);
			if (cheacked) {
				currentVal = currentVal - count;
				let currPrice = currentVal * price;
				checkedItemsCard.count += currentVal;
				checkedItemsCard.totalPrice += currPrice;
				changesubTotalContent(checkedItemsCard);
			}

		});


		cheackedItem.addEventListener("change", (e) => {
			console.log(price);
			let newCount = +(selectList[i].value);
			let finalToPrice = (newCount * price);
			if (e.target.checked) {
				console.log(`cheacked`);
				checkedItemsCard.totalPrice += finalToPrice;
				console.log(`finalToPrice => ${finalToPrice}`);
				checkedItemsCard.count += newCount;
			}
			else {
				console.log(`Un-cheacked`);
				checkedItemsCard.totalPrice -= finalToPrice;
				checkedItemsCard.count -= newCount;
			}
			changesubTotalContent(checkedItemsCard);
			if (checkedItemsCard.count === 0) {
				totalProceed.style.display = `none`;
			}

			else {
				totalProceed.style.display = `flex`;
				changesubTotalContent(checkedItemsCard);
			}

		});

	}
}

async function setNumberOfAvailableItems(prod, count, ele) {

	let length = prod.unitNumbers > 10 ? 10 : prod.unitNumbers;
	for (let i = 1; i <= length; i++) {
		let opt = document.createElement('option');
		let optVal = document.createTextNode(i);
		opt.value = i;
		if (i == count) {
			opt.setAttribute(`selected`, true);
		}
		opt.append(optVal);
		ele.append(opt);

	}


}

async function updateCartItem(prod, item_Count) {
	let url = location.protocol + `//` + location.hostname + `:` + location.port + `/rest/api/cart/update`;
	let body = {
		product: prod,
		itemCount: item_Count
	};

	let updatedCartPromise = await fetch(url, {
		credentials: 'include',
		headers: {
			'Content-Type': 'application/json',
			'Referrer-Policy': `same-origin`
		},
		referrerPolicy: 'same-origin',
		mode: 'same-origin',
		method: `POST`,
		body: JSON.stringify(
			body
		)
	});
	return updatedCartPromise;
}


function printChexkedItemsValue(cheackedItems) {
	for (let i = 0; i < cheackedItems.length; i++) {
		console.log(cheackedItems[i].value);
	}
}

function changesubTotalContent(finalShopCart) {
	itemsPrice.textContent = finalShopCart.totalPrice;
	itemsCount.textContent = finalShopCart.count;
	subTotalPrice.textContent = finalShopCart.totalPrice;
	subTotal.textContent = finalShopCart.count;
}


function printInfo(parent) {
	let qty = parent.querySelector(`.item-count`).value;
	let price = parent.querySelector(`.items-price`).value;
	console.log(`item QTY -> ${qty} ..... and Item Price is  ${price}`);
}

async function setInitialCheckedItemObject() {
	if (checkedItemsCard.totalPrice == -1) {
		checkedItemsCard.totalPrice = +(itemsPrice.textContent.trim());
		checkedItemsCard.count = +(itemsCount.textContent.trim());
	}
}
// loop on result search and create div for each result item.
function createResultDivs(arr) {
	searchResultsCont.children = 0;
	searchResultsCont.style.visibility = `hidden`;
	searchResultsCont.style.width = `84%`;

	for (let i = 0; i < arr.length; i++) {
		let resultItem = document.createElement('div');
		let resultText = document.createTextNode(arr[i].name);
		resultItem.style.height = `40px`;
		resultItem.append(resultText);
		resultItem.classList.add('result-item');
		resultItem.setAttribute('proId', arr[i].id);
		searchResultsCont.append(resultItem);

	}
	searchResultsCont.style.visibility = `visible`;
}
// remove previous search  result divs 
function removePrevResultDivs() {
	searchResultsCont.innerHTML = "";
	searchResultsCont.style.width = `0`;
	searchResultsCont.style.visibilty = 'hidden';
}

document.addEventListener('click', (e) => {
	if (e.target.hasAttribute("class") && e.target.getAttribute("class") == 'result-item') {
		let uri = `http://localhost:8080/products/id/${e.target.getAttribute('proId')}`;
		console.log(uri);
		location.href = uri;
	}
});


function updateSubTotalAfterDelete(e) {
	let targetSelect = e.target.parentElement.querySelector(`.item-count`);
	let parentEle = e.target.parentElement.parentElement.parentElement;
	let checkedBox = parentEle.querySelector(`.check-item`);
	if (checkedBox.checked) {
		let itemCount = +(targetSelect.getAttribute(`count`));
		let itemPrice = +(targetSelect.getAttribute(`item-price`));
		checkedItemsCard.count -= itemCount;
		checkedItemsCard.totalPrice -= (itemCount * itemPrice);
	}
	removeparentDiv(parentEle);
}

async function removeCartItem(prod) {
	let url = location.protocol + `//` + location.hostname + `:` + location.port + `/rest/api/cart`;
	let body = {
		product: prod,
		itemCount: 0
	};
	let updatedCartPromise = await fetch(url, {
		credentials: 'include',
		headers: {
			'Content-Type': 'application/json',
			'Referrer-Policy': `same-origin`
		},
		referrerPolicy: 'same-origin',
		mode: 'same-origin',
		method: `DELETE`,
		body: JSON.stringify(
			body
		)
	});
	return updatedCartPromise;
}

for (let i = 0; i < deleteBtn.length; i++) {
	deleteBtn[i].addEventListener('click', async (e) => {
		let targetSelect = e.target.parentElement.querySelector(`.item-count`);
		let prodId = targetSelect.getAttribute(`prod-id`);
		let prod = {
			id: prodId
		};
		await removeCartItem(prod);

		updateSubTotalAfterDelete(e);
		if (prodsForm.childElementCount === 1) {
			location.reload();
		}
		console.log(checkedItemsCard.count);
		if (checkedItemsCard.count === 0) {
			totalProceed.style.display = `none`;
		}
		else {
			totalProceed.style.display = `flex`;
			changesubTotalContent(checkedItemsCard);
		}

	});

}
function removeparentDiv(ele) {
	ele.remove();
}
