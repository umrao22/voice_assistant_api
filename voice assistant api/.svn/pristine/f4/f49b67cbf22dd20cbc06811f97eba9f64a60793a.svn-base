<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
    
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Voice Assistant Questions</title><link rel="icon" href="https://xenius.in/wp-content/uploads/2017/06/favicon.png" type="image/icon type"><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><link rel="stylesheet"href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src ="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>

<script type="text/javascript">

function myFunction() {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("ans_tab");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[3];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}


function Answertb() {
		  
	var k= document.getElementById("main_key_id").innerText ; 
	document.getElementById("new_mainkey").value = k ; 
	
	  var a =document.getElementById("ans_tab") ; 
      		a.style.zIndex="2" ;
      if (a.style.display === "none") {

          a.style.display = "block";
       } else {
           a.style.display = "none";
       }
  
  
	
}
function update_ans_tb() {
	console.log("updating answers")
	var d = document.getElementById("new_mainkey").value;
	var e = document.getElementById("new_ans").value;
	var a = document.getElementById("new_id").value;
	var b = document.getElementById("new_func").value;
	var c = document.getElementById("new_url").value;
	
	console.log(e)
	console.log(c)
	
	 var raw = JSON.stringify({
		   "ans_id": a,
		   "function": b,
		   "api_url" : c ,
		   "main_key" : d,
		   "answer" : e , 
		   
		 });
	 
	
	
	console.log(e)
	console.log(c)
	console.log(raw)
	var myHeaders = new Headers();
	 myHeaders.append("Content-Type", "application/json");

	

	 var requestOptions = {
	   method: 'POST',
	   headers: myHeaders,
	   body: raw,
	   redirect: 'follow'
	 };

	 fetch("http://localhost:7004/addAns", requestOptions)
	   .then(response => response.text())
	   
	   .then(result => alert(result))
	   .catch(error => console.log('error', error));
	
	location.reload()
}

function show() {
    var rowId = event.target.parentNode.parentNode.id;
    console.log(rowId)
  //this gives id of tr whose button was clicked
    var data = document.getElementById(rowId).querySelectorAll(".row-data"); 
  /*returns array of all elements with 
  "row-data" class within the row with given id*/

    var name = data[2].innerHTML;
    var age = data[3].innerHTML;

    alert("Name: " + name + "\nAge: " + age);
    
}

function Set_answer( x , a ) {
	console.log("queestion" + x)
	console.log("answer"+a)

	 var myHeaders = new Headers();
	 myHeaders.append("Content-Type", "application/json");

	 var raw = JSON.stringify({
	   "question_id": x,
	   "Answer_id": a 
	 });

	 var requestOptions = {
	   method: 'POST',
	   headers: myHeaders,
	   body: raw,
	   redirect: 'follow'
	 };
	console.log(x)
	console.log(a)
	 fetch("http://localhost:7004/setAns", requestOptions)
	   .then(response => response.text())
	   
	   .then(result => alert(result))
	   .catch(error => console.log('error', error));
	 
}
function update() {
	
	
	
}

</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style>body {background-color: #e6ecf0}
row {margin-top: 5px;margin-bottom: 5px;padding-top: 5px;padding-bottom: 5px;padding-left: 5px;padding-right: 5px;}
.container-fluid{ padding-left:1%!important; padding-right:1%!important; }
#myInput {
  
  background-position: 10px 12px; /* Position the search icon */
  background-repeat: no-repeat; /* Do not repeat the icon image */
  width: 100%; /* Full-width */
  font-size: 16px; /* Increase font-size */
  padding: 12px 20px 12px 40px; /* Add some padding */
  border: 1px solid #ddd; /* Add a grey border */
  margin-bottom: 12px; /* Add some space below the input */
}
</style>

</head><body>
<div class="container-fluid">
<div class="row">
<div class="col-sm-12">
<div class="container-fluid">
<br><h2>Voice Assistant Questions</h2>
<div class="table-responsive">

	<div class="ans_table" id="ans_tab"> 
		<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for key..">

		<div class="container-fluid">
		<div class="row">
		<div class="col-sm-12">
		<div class="container-fluid">


	<table class="table table-hover table-condensed table-dark table-bordered">
	<thead><tr><th>Ans_id</th><th>Function</th><th>Api_url</th><th>main_key</th><th>answer</th><th>Submit</th></tr></thead>
	
	<tbody>
	<c:forEach items="${list1}" var="map">
    	
		<tr><td>${map.ans_id}</td>
		<td>${map.function}</td>
		<td>${map.api_url}</td>
		<td>${map.main_key}</td>
		<td>${map.answer}</td>  
			
        	
        <td><button  class=" btn btn-primary "  value= <c:out value='${map.ans_id}' />>Submit</button> </td>
       
    	</tr>
    	
	</c:forEach>
		<tr>
		<td><input id="new_id" type="number" ></td>
		<td><input id="new_func"type="number"  ></td>
		<td><input id="new_url" type="url" ></td>
		<td><input id="new_mainkey" type="text" ></td>
		<td><input id="new_ans" type="text"></td>  
			
        	
        <td><button  class=" btn btn-primary " onclick="update_ans_tb()"  value= <c:out value='${map.ans_id}' />>Submit</button> </td>
		</tr>
	</tbody>
  	</table></div></div></div></div>
 </div>
<table class="table table-hover table-condensed table-dark table-bordered">

<thead><tr><th>Question_id</th><th>Question</th><th>Ans_id</th><th>Status</th><th>main_key</th><th>Set answer</th></tr></thead>





<tbody>
  
<c:forEach items="${list}" var="map">
		<tr	id="${map.ques_id}"><td class="row-data" id="question_id">${map.ques_id}</td>
		<td class="row-data">${map.text}</td>
		<td class="row-data"> <input type="number" id="Answer_id"  value= <c:out value='${map.ans_id}' />></td>
		<td><button onclick="show()">Update</button></td>
		<td class="row-data" id="main_key_id">${map.main_key}</td>  
		<td><button onclick="Answertb()" class=" btn btn-primary ">Set Answer</button> </td>   </tr>
</c:forEach>




</tbody></table></div></div></div></div></div></body></html>


