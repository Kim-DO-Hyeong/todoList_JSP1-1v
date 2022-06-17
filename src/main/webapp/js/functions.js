function isCorrectId(id) {
	if(id.length == 0) {
		alert("아이디를 입력하세요.");
		return false;
	} else if(id.length < 1 || id.length > 50) {
		alert("아이디는 1자 이상, 50자 이하로 입력해야합니다.");
		return false;
	} 
	
	let regExp = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
	if(!regExp.test(id)) {
		alert("이메일 형식이 아닙니다");
		return false;
	}
	
	return true;
}

function isCorrectPw(pw) {
	if(pw.length == 0) {
		alert("비밀번호를 입력하세요.");
		return false;
	} else if(pw.length < 4 || pw.length > 16) {
		alert("비밀번호는 4자 이상, 16자 이하로 입력해야합니다.");
		return false;
	}
	
	let regExp = /^(?=.*[a-z])(?=.*\d)[A-Za-z\d]+$/;
	if(!regExp.test(pw)) {
		alert("비밀번호는 영문 소문자, 숫자 각각 하나 이상 포함되야합니다.");
		return false; 
	}
	
	return true;
}

function isCorrectName(name) {
	if(name.length == 0) {
		alert("이름을 입력하세요.");
		return false;
	} else if(name.length < 2 || name.length > 8) {
		alert("이름은 2자 이상, 8자 이하로 입력해야합니다.");
		return false;
	}
	
	let regExp = /[ㄱ-힣]+$/;
	if(!regExp.test(name)) {
		alert("이름은 한글만 가능합니다.");
		return false;
	}
	
	return true;
}










