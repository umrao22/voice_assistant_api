
function keywordstb(){
    url ="http://localhost:7004/keytb" ;
    console.log(url)

    let xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
   
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.addEventListener("readystatechange", processRequest, false);

    function processRequest(e) {
    
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);
            obis_data=JSON.stringify(response)
            
            console.log(response.STATUS)
            jsdata = response.keywordtable
         
            $(document).ready(function() {
          $("#tableid").DataTable({
          
              
              "bDestroy": true,
              "aaData": jsdata,
             
              lengthMenu: [ 10,15, 20,30,40 , 50],
              "aoColumns": [{
                               "mDataProp": "key_id"
                           }, {
                               "mDataProp": "main_key"
                           }, {
                               "mDataProp": "ans_id"
                           },{
                              "mDataProp": "keywords"
                          } 
                          ],
                           
                        
               });
           });
          }
      

         if (response.STATUS !== 'SUCCESS') {
            alert(response.MESSAGE)
            console.log('Error')
            console.log(response.DATA)
            // return jsdata;
            
            
        }
       
}
  

}
       
    

