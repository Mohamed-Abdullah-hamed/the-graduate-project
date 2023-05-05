/**
 * 
 */
 let searchBtn = document.querySelector('.btn-srch');
 let searchText = document.querySelector('.search');
 let cards = document.querySelectorAll('.card');
 let searchResultsCont = document.querySelector('.products-result');
 let userName = document.querySelector(".profile");
 let shopCart = document.querySelector(`.shop-cart`);
 let cartImg = document.querySelector(`.cart-img`);
 
 /*
 function checkUserName(userName){
	if(userName.textContent == 'UN'){
		shopCart.style.width=`85px`;
	}
}
*/
 let searchBtnOnClickFun = function srcbtnOnClick(e){
	
	if(searchText.value.length != 0)
	location.href=`http://localhost:8080/products/name/${searchText.value}`;
};

 let searchOnVal = function (e){
	removePrevResultDivs();
	if(searchText.value.length != 0){
		let url = `http://localhost:8080/rest/api/products/product/${searchText.value}`;		
		 fetch(url).then((rs)=>{
			
			rs.json().then((rs)=>{
				console.log(rs.length);
				
				if(rs.length > 0){
				rs.length = rs.length > 7 ? 7:rs.length;
					createResultDivs(rs);
				}
				
			})
			.catch((e=>{console.log("Error ........")}));
			
		});
		
	}
	
};
/*
for(let i=0;i < cards.length;i++){
	let item = cards[i];
	let price_container = item.getElementsByClassName('price-cont');
	
	if(price_container.length == 0){
		
		item.getElementsByClassName()('button')[0].style.cursor = 'no-drop';
	}
}
*/
 searchBtn.addEventListener('click',searchBtnOnClickFun);
 
 searchText.addEventListener('input',searchOnVal);
 
 // loop on result search and create div for each result item.
 function createResultDivs(arr){
	searchResultsCont.children=0;
	searchResultsCont.style.visibility=`hidden`;
	searchResultsCont.style.width=`84%`;
	
	for(let i = 0; i < arr.length;i++){
		let resultItem = document.createElement('div');
		let resultText = document.createTextNode(arr[i].name);
		resultItem.style.height=`40px`;
		resultItem.append(resultText);
		resultItem.classList.add('result-item');
		resultItem.setAttribute('proId',arr[i].id);
		searchResultsCont.append(resultItem);
		
	}
	searchResultsCont.style.visibility=`visible`;
}
// remove previous search  result divs 
 function removePrevResultDivs(){
	searchResultsCont.innerHTML="";
	searchResultsCont.style.width=`0`;
	searchResultsCont.style.visibilty='hidden';
}
 
document.addEventListener('click',(e)=>{
	if(e.target.hasAttribute("class") && e.target.getAttribute("class") == 'result-item'){
		let uri = `http://localhost:8080/products/id/${e.target.getAttribute('proId')}`;
		console.log(uri);
		location.href=uri;
	}
});

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
 
 
 
 
 
 
 
 
 
 
 
 