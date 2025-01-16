
window.onload = function() {
	main();
};

 
function main(){
	const data = {
		      operation: "retrieve"  // The operation you want to execute
		  };
	    // Replace 'your_servlet_url' with the correct URL to your DataOperations servlet
		fetch('http://localhost:8080/EmployeeDataManager/DatabaseOperations', {
		    method: 'POST',  // Use POST method for sending data
		    headers: {
		        'Content-Type': 'application/json'  // Set the correct content type for JSON
		    },
		    body: JSON.stringify(data)  // Convert the data object to a JSON string
		})  // Adjust this URL as per your servlet mapping
	        .then(response => response.json())  // Parse the JSON response
	        .then(data => {
	           
	            populateTable(data);  // Pass the data to populate the table
	        })
	        .catch(err => {
	           alert("Error fetching data:", err);
	        });
}

function filterdata(){
	const searchQuery = document.getElementById('searchqueary').value.toLowerCase();
		const data = {
			      operation: "retrieve"  // The operation you want to execute
			  };
		    // Replace 'your_servlet_url' with the correct URL to your DataOperations servlet
			fetch('http://localhost:8080/EmployeeDataManager/DatabaseOperations', {
			    method: 'POST',  // Use POST method for sending data
			    headers: {
			        'Content-Type': 'application/json'  // Set the correct content type for JSON
			    },
			    body: JSON.stringify(data)  // Convert the data object to a JSON string
			})  // Adjust this URL as per your servlet mapping
		        .then(response => response.json())  // Parse the JSON response
		        .then(data => {
					const filteredData = data.filter(employee => 
					        employee.name.toLowerCase().includes(searchQuery) || 
					        employee.mobile.includes(searchQuery)  // You can adjust the condition if you need exact matches
					    );
						
		            populateTable(filteredData);  // Pass the data to populate the table
		        })
		        .catch(err => {
		           alert("Error fetching data:", err);
		        });
	
}
document.getElementById('searchqueary').addEventListener("input", filterdata);






	//this is for showtrash function
	function showTrashTable(data) {
	    const tableBody = document.querySelector('#employeeTable tbody');
	    tableBody.innerHTML = ''; // Clear the table body first

	    // Handle null or empty data
	    if (!data || !Array.isArray(data) || data.length === 0) {
	        const noRow = document.createElement('tr');
	        noRow.innerHTML = '<td colspan="4">No data available</td>';
	        tableBody.appendChild(noRow);
	        return;
	    }

	    // Populate table if data exists
	    data.forEach(trash => {
	        const row = document.createElement('tr');
	        row.innerHTML = `
	            <td>${trash.name}</td>
	            <td>${trash.mobile}</td>
	            <td>
	                <button class="trash-delete" trash-id="${trash.id}">Delete</button>
	            </td>
	            <td>
	                <button class="restore" trash-id="${trash.id}">Restore</button>
	            </td>
	        `;
	        tableBody.appendChild(row);

	        // Add event listeners for delete and restore buttons
	        row.querySelector('.trash-delete').addEventListener("click", function () {
	            const id = this.getAttribute("trash-id");
	            handleDeleteTrash(id);
	        });

	        row.querySelector('.restore').addEventListener("click", function () {
	            const id = this.getAttribute("trash-id");
	            handleRestoreId(id);
	        });
	    });
	}

	

	function handleRestoreId(id){
		const data={
			operation: "restoreid",
			id: "${id}"
		}
		fetch(`http://localhost:8080/EmployeeDataManager/DatabaseOperations?id=${id}`, {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/x-www-form-urlencoded', // You can change this header if needed
		        },
				body: JSON.stringify(data)
		    })
			
			.then(response => response.json())
			.then(result => {
				if (result.success) {
					if (result.success) {
					           
					           const row = document.querySelector(`[trash-id="${id}"]`);
					           if (row) {
					               row.closest('tr').remove();
					           } else {
					               alert(`Row with id ${id} not found in DOM.`);
					           }
					       } else {
					           alert(result.error || "Failed to restore the record.");
					       }
					} // Assuming showTrash is defined and handles the result
			    })
				.catch(err => {
				        alert("Error fetching data:", err);
				    });
	}
	// Function to populate the table with data
	function populateTable(data) {
	    const tableBody = document.querySelector('#employeeTable tbody');
	    tableBody.innerHTML = ''; // Clear any previous rows

	    // Check if the data is empty or not
	    if (data.length === 0) {
	        const noDataRow = document.createElement('tr');
	        noDataRow.innerHTML = '<td colspan="4">No data available</td>'; // Adjust colspan for the number of columns
	        tableBody.appendChild(noDataRow);
	    } else {
	        // Loop through the data and create rows
	        data.forEach(employee => {
	            const row = document.createElement('tr');
	            row.innerHTML = `
	                <td>${employee.name}</td>
	                <td>${employee.mobile}</td>
	                <td>
	                    <button class="delete-btn" data-id="${employee.id}">Delete</button>
	                </td>
					<td>
					<button class="update-btn"  data-id="${employee.id}" data-name="${employee.name}" data-mobile="${employee.mobile}">Update</button>
					</td>
	            `;
	            tableBody.appendChild(row);
	        });

	        // Add event listeners to all delete buttons
	        document.querySelectorAll('.delete-btn').forEach(button => {
	            button.addEventListener('click', function () {
	                const id = this.getAttribute('data-id');
	                handleDelete(id);
	            });
	        });
			document.querySelectorAll('.update-btn').forEach(update => {
			           update.addEventListener('click', function () {
			               const upid = this.getAttribute('data-id');
			               const name = this.getAttribute('data-name');
			               const mobile = this.getAttribute('data-mobile');

			                // Save the ID for update operations if needed
			               document.getElementById('update').style.display = 'block';
			               document.getElementById('insert').style.display = 'none';
			               document.querySelector('.wrapper').style.justifyContent = "space-between";
			               document.querySelector('.container').style.display = "block";

			               // Set the name and mobile values in the input fields
			               document.getElementById('namedata').value = name;
			               document.getElementById('mobiledata').value = mobile;
						   document.getElementById('idData').value=upid;
			           });
			       });
	    }
	}
	
// this method for update data()
function handleUpdate() {
	const upid = document.getElementById('idData').value;
	const name = document.getElementById('namedata').value;
	const mobile = document.getElementById('mobiledata').value;

	// Send update request to the server
	fetch(`http://localhost:8080/EmployeeDataManager/DatabaseOperations?id=${upid}`, {
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/json',
	    },
	    body: JSON.stringify({
	        operation: "update",
	        id: upid,
	        name: name,
	        mobile: mobile
	    })
	})
	.then(response => {
	    // Check if response status is OK (200)
	    if (!response.ok) {
	        throw new Error(`HTTP error! Status: ${response.status}`);
	    }
	    return response.json();  // Convert to JSON if response is OK
	})
    .then(result => {
        if (result.success) {
            // Reset form fields and hide update UI
            document.getElementById('namedata').value = '';
            document.getElementById('mobiledata').value = '';
            document.getElementById('idData').value = '';
            document.getElementById('update').style.display = 'none';
            document.getElementById('insert').style.display = 'block';
            document.querySelector('.wrapper').style.justifyContent = "center";
            document.querySelector('.container').style.display = "none";
			main();
		}else{
			console.log('update failed');
		}
	})
	.catch(error => {
	    console.error("Error occurred:", error); // Logs detailed error in the console
	    alert("An error occurred. Please check the console for details."); // Simple alert message
	});

	
 };
	
	//fetching show trash data
	function showTrashData() {
	    const data = {
	        operation: "showtrash"
	    };

	    fetch('http://localhost:8080/EmployeeDataManager/DatabaseOperations', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify(data)
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error(`HTTP error! Status: ${response.status}`);
	        }
	        return response.json();
	    })
	    .then(result => {
	       
	        showTrashTable(result); // Pass result to the table handler
	    })
	    .catch(err => {
	        alert("Error fetching data: " + err.message);
	    });
	}


	//deletetrash
	
	//this for delete trash data
	function handleDeleteTrash(id) {
	    // Construct the URL with the operation and id parameters
	   

		const data={
					operation: "deletetrash",
					id: "${id}"
				};

	    // Make the GET request without the body
	    fetch(`http://localhost:8080/EmployeeDataManager/DatabaseOperations?id=${id}`, {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded', // You can change this header if needed
	        },
			body: JSON.stringify(data)
	    })
	    .then(response => response.json()) // Assuming the server responds with JSON
	    .then(result => {
	        if (result.success) {
	            // Find the row with the trash ID and remove it
	            const row = document.querySelector(`[trash-id="${id}"]`).closest('tr');
	            if (row) {
	                row.remove(); // Remove the row from the DOM
	            } 
	        } else {
	            alert(result.error); // Display any error returned by the server
	        }
	    })
	    .catch(error => {
	        alert("Error Occurred: ", error); // Handle any errors that occur during the fetch
	    });
	}

	
	// this is for populating the table if the user select the operation name is delete then I add
	//column Action in the table 

	function handleDelete(id) {
		const data={
			operation: "delete",
			id: "${id}"
		};
	    console.log(`Deleting record with ID: ${id}`); // Debugging: Check if the correct ID is being passed
	    fetch(`http://localhost:8080/EmployeeDataManager/DatabaseOperations?operation=delete&id=${id}`, {
	        method: 'POST',
			body: JSON.stringify(data)
	    })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('Failed to delete record');
	            }
	            return response.json();
	        })
	        .then(result => {
	            if (result.success) {
	                // Find and remove the corresponding row
	                const row = document.querySelector(`[data-id="${id}"]`).closest('tr');
	                if (row) {
	                    row.remove(); // Remove the row from the table
	                }
	                
	            } else {
	                
	                alert(result.error); // Display error message
	            }
	        })
	        .catch(error => {
	            
	            alert('Failed to delete the record. Please try again.', error);
	        });
	}

	
	
	// this is for insert in the db:
	function handleInsert() {
	    const name = document.getElementById('namedata').value;
	    const mobile = document.getElementById('mobiledata').value;

	    // Make fetch request to insert data
	    fetch('http://localhost:8080/EmployeeDataManager/DatabaseOperations', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({
	            operation: "insert",
	            name: name,
	            mobile: mobile
	        })
	    })
	    .then(response => response.json())
	    .then(data => {
	        if (data.success) {
	            alert("Data inserted successfully!");

	            // Manually create a new row and append it to the table
	            const tableBody = document.querySelector('#employeeTable tbody');
	            const newRow = document.createElement('tr');
	            newRow.innerHTML = `
	                <td>${name}</td>
	                <td>${mobile}</td>
	                <td>
	                    <button class="delete-btn" data-id="${data.userId}">Delete</button>
	                </td>
					<td>
					<button class="update-btn"  data-id="${data.userId}">Update</button>
					</td>
	            `;
	            tableBody.appendChild(newRow);  // Append the new row to the table

	            // Optionally, clear the input fields after the insert
	            document.getElementById('namedata').value = '';
	            document.getElementById('mobiledata').value = '';

	        } else {
	            alert("Error: " + data.error);
	        }
	    })
	    .catch(error => {
	        alert("Error Occurred: " + error);
	    });
	}

	document.getElementById('insert').addEventListener('click', function(event){
		event.preventDefault();
		handleInsert()
		main()
		
	})



	
	
document.getElementById('logout').addEventListener("mouseenter", function(){
	document.querySelector('.logout').classList.remove('animate-right');
	document.querySelector('.logout').classList.add('animate-left');
});
document.getElementById('logout').addEventListener("mouseleave", function(){
	document.querySelector('.logout').classList.add('animate-right');
	document.querySelector('.logout').classList.remove('animate-left');
});

document.getElementById('close').addEventListener('click', function(){
	document.querySelector('.wrapper').style.justifyContent="center";
	document.querySelector('.container').style.display="none";
});
document.getElementById('showtrash').addEventListener('click', function () {
	document.querySelector('.wrapper').style.justifyContent="center";
	document.querySelector('.container').style.display="none";
    document.getElementById('restoreaction').style.display = 'block';
    document.getElementById('updatefield').style.display = 'none';
   document.getElementById('add').style.visibility = 'hidden';

    showTrashData(); // Ensure this function is implemented and working
});

document.getElementById('show').addEventListener('click', function () {
   
    main();
    document.getElementById('restoreaction').style.display = 'none';
	
	document.getElementById('add').style.visibility = 'visible';
    document.getElementById('updatefield').style.display = 'block';
});


// this code for plus button
document.getElementById('add').addEventListener('click', function(){
	document.querySelector('.wrapper').style.justifyContent="space-between";
	document.querySelector('.container').style.display="block";
	document.getElementById('namedata').value = '';
	document.getElementById('mobiledata').value = '';
	document.getElementById('idData').value='';
	document.getElementById('update').style.display = 'none';
	document.getElementById('insert').style.display = 'block';
});
document.getElementById('update').addEventListener('click', function(event){
	event.preventDefault();
	handleUpdate();
})






//here i design the theame section light and dark
const light = document.getElementById('light');
const dark = document.getElementById('dark');
const container = document.querySelector('.container');
const formFields = document.getElementById('insertForm');
const closebtn = document.getElementById('close');
const formButton = document.querySelectorAll('.form-btn');
const allFields = document.getElementsByTagName('input');
// Light mode event
light.addEventListener('click', function () {
    dark.style.display = 'block';
    light.style.display = 'none';
	formFields.style.color="#FFFFFF";
	closebtn.style.color="#FFFFFF";
	container.style.backgroundColor='#000000b5';
    document.body.style.backgroundColor = '#494242'; // White background
    document.querySelector('.navigation').style.backgroundColor = '#212121'; // Light color for manu
	document.querySelectorAll('.btn').forEach(button => {
	       button.style.color = 'yellow';
		  // Light text
		
	   }); // Dark text for buttons
	   document.querySelector('.logout-btn').style.color = 'yellow'; // Light text
	   //here i set the table body color:
	   document.querySelectorAll('#employeeTable td').forEach(tdElement => {
		tdElement.style.color="#FFFFFF";
		tdElement.style.backgroundColor='#212121';
	   });
	   document.querySelectorAll('#employeeTable th').forEach(thElement => {
	       thElement.style.color = "#FFFFFF";  // Set text color to white
	       thElement.style.background = 'linear-gradient(to right, rgba(225, 0, 0, 0.5), rgba(0, 0, 0, 0.3))';  // Set gradient background
	   });


	    
	   closebtn.addEventListener('mouseenter', function(){
	      	closebtn.style.color='red';
	      });
	      closebtn.addEventListener('mouseleave', function(){
	      	closebtn.style.color='#FFFFFF';
	      });
		  // this for form button
		  formButton.style.backgroundColor='lightseagreen';
		  formButton.style.color='#FFFFFF';
		  // this for form field
		  allFields.forEach(field => {
		      field.style.backgroundColor = "#000000b5";
		      field.style.borderColor = "lightseagreen";
		  });
		 
});

// Dark mode event
dark.addEventListener('click', function () {
    light.style.display = 'block';
    dark.style.display = 'none';
	document.querySelector('.logout-btn').style.color = '#212121';
	formFields.style.color="#212121";
	closebtn.style.color="#212121";
	container.style.backgroundColor='#f9f9f9';
    document.body.style.backgroundColor = '#ddd'; // Dark background
    document.querySelector('.navigation').style.backgroundColor = '#efeaea'; // Black color for manu
	document.querySelectorAll('.btn').forEach(button => {
	       button.style.color = '#212121'; // Light text
		   
	   }); // Light text for buttons
	   //here i set the table body color:
	   document.querySelectorAll('#employeeTable td').forEach(tdElement => {
	   	tdElement.style.color="#212121";
	   	tdElement.style.backgroundColor='#FFFFFF';
	      });
		  document.querySelectorAll('#employeeTable th').forEach(thElement => {
		         thElement.style.color = "#FFFFFF";  // Set text color to white
		         thElement.style.background = '#007bff';  // Set gradient background
		     });
	   closebtn.addEventListener('mouseleave', function(){
	      	closebtn.style.color='#212121';
	      })
	      closebtn.addEventListener('mouseenter', function(){
	      	closebtn.style.color='red';
	      })
		  
		  // this for form button
		    formButton.style.backgroundColor='blue';
		    formButton.style.color='#FFFFFF';
		    // this for form field
			allFields.forEach(field => {
				      field.style.backgroundColor="#FFFFFF";
				      field.style.borderColor = "lightseagreen";
				  });
		   
	  
});

document.querySelectorAll('.btn').forEach(button => {
    button.addEventListener('click', function () {
        // Remove 'active' from all buttons
        document.querySelectorAll('.btn').forEach(btn => btn.classList.remove('active'));
        // Add 'active' to the clicked button
        this.classList.add('active');
		
		
    });
});




