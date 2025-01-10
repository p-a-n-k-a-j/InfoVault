/**
 * 
 */

// I need to check the name is enter is correct or not



const fname = document.getElementById("first");
const lname = document.getElementById("last");
fname.addEventListener("keypress", function (event) {
    // Check if the pressed key is not a valid letter
    if (!/[A-Za-z\s]/.test(event.key)) {
        alert("Name should only contain letters and spaces.");
        event.preventDefault(); // Prevent invalid character from being added
    }
});
lname.addEventListener("keypress", function (event) {
    // Check if the pressed key is not a valid letter
    if (!/[A-Za-z\s]/.test(event.key)) {
        alert("Name should only contain letters and spaces.");
        event.preventDefault(); // Prevent invalid character from being added
    }
});


const input_field= document.getElementById('pass');
const hide = document.getElementById('hide');
const view = document.getElementById('view');

hide.addEventListener("click", function(){
	hide.style.display='none';
	view.style.display='block';
	input_field.type='text';
	
});
view.addEventListener("click", function(){
	hide.style.display='block';
	view.style.display='none';
	input_field.type='password';
});

// this is for second pass that is confirm pass
const input_field2= document.getElementById('pass2');
const hide2 = document.getElementById('hide2');
const view2 = document.getElementById('view2');
hide2.addEventListener("click", function(){
	hide2.style.display='none';
	view2.style.display='block';
	input_field2.type='text';
	
});
view2.addEventListener("click", function(){
	hide2.style.display='block';
	view2.style.display='none';
	input_field2.type='password';
});


const passError = document.getElementById('pass-error');
//now validate password here 
function validate(){
const password = document.getElementById('pass').value;
const confi_pass = document.getElementById('pass2').value;
const configField =  document.getElementById('pass2');

	// Clear previous error messages
          passError.textContent = "";
    // Validation: Check password conditions
    if (password.length < 8) {
		passError.style.color='red';
		configField.style.borderBottomColor='red';
        passError.textContent = "Password must be at least 8 characters long.";
        event.preventDefault(); // Prevent form submission
        return false;
    }
	

    if (!/[A-Z]/.test(password)) {
		passError.style.color='red';
		configField.style.borderBottomColor='red';
        passError.textContent = "Password must contain at least one uppercase letter.";
        event.preventDefault(); // Prevent form submission
        return false;
    }

    if (!/[0-9]/.test(password)) {
		passError.style.color='red';
		configField.style.borderBottomColor='red';
        passError.textContent = "Password must contain at least one number.";
        event.preventDefault(); // Prevent form submission
        return false;
    }

	// Validation: Check if password matches confirm password
	if (password !== confi_pass) {
		passError.style.color='red';
		configField.style.borderBottomColor='red';
	    passError.textContent = "Incorrect password.";
	    event.preventDefault(); // Prevent form submission
	    return false;
	}


	   
	   configField.style.borderBottomColor='green';
	   
	   
	
    return true; // Allow form submission
}

const emailError = document.getElementById('emailError');
const email = document.getElementById('email');
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
let typingTimer; // Timer variable for debounce
const typingDelay = 1000; // Delay time in milliseconds (e.g., 1 second)


email.addEventListener("input", function(){

	//clear an existing timeOut
	clearTimeout(typingTimer)
	// Show "checking email..." message
	 emailError.style.color = 'blue';
	 emailError.textContent = "Checking email...";
	 
	typingTimer=setTimeout(() => {
	if(!emailRegex.test(email.value)){
		email.style.borderBottomColor='darkred';
		emailError.style.color='red';
		emailError.style.fontSize='15px';
			emailError.textContent="Invalid Email";
			
			
			
		}else{
			emailError.textContent="";
			email.style.borderBottomColor='darkgreen';
		emailError.style.color='green';
		emailError.textContent="valid email";
		}
	}, typingDelay);
		
	
});

// handling button animation here
const submit = document.getElementById('submit');

// Add animation on mouse enter
submit.addEventListener("mouseenter", () => {
  submit.classList.remove('animate-right');
  submit.classList.add('animate-left');
});

// Reverse animation on mouse leave
submit.addEventListener("mouseleave", () => {
  submit.classList.remove('animate-left');
  submit.classList.add('animate-right');
});

const clean = document.getElementById('clean');

// Add animation on mouse enter
clean.addEventListener("mouseenter", () => {
  clean.classList.remove('animate-right');
  clean.classList.add('animate-left');
});

// Reverse animation on mouse leave
clean.addEventListener("mouseleave", () => {
  clean.classList.remove('animate-left');
  clean.classList.add('animate-right');
});






