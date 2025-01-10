/**
 * 
 */

// I need to check the name is enter is correct or not



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








