<?php 



 require_once '/../datos/login_mysql.php';

 //this is our upload folder 
 $upload_path = 'images/';

 //Getting the server ip 
 $server_ip = gethostbyname(gethostname());
 
 //creating the upload url 
 $upload_url = 'http://'.$server_ip.'/walkappservices/v1/'.$upload_path; 
 
 //response array 
 $response = array(); 
 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
	 //checking the required parameters from the request 
	 if(isset($_POST['tipo']) and isset($_FILES['image']['name'])){
		 
		 //connecting to the database 
		 $con = mysqli_connect(NOMBRE_HOST,USUARIO,CONTRASENA,BASE_DE_DATOS) or die('Unable to Connect...');
		 

		$tipo = $_POST['tipo'];

		 if(isset($_POST['name'])){
		 			 //getting name from the request 
			 $name = $_POST['name'];
		 	//getting file info from the request 
		 	$fileinfo = pathinfo($_FILES['image']['name']);
		 			 //getting the file extension 
		 	$extension = $fileinfo['extension'];
		 			 //file url to store in the database 
			 $file_url = $upload_url .'users/'. $name . '.'. $extension;
			 
			 //file path to upload in the server 
			 $file_path = $upload_path. 'users/'. $name . '.'. $extension;


		 }else if(isset($_POST['idroute'])){
		 			 			 //getting name from the request 
			 $idroute = $_POST['idroute'];
		 	//getting file info from the request 
		 	$fileinfo = pathinfo($_FILES['image']['idroute']);
		 			 //getting the file extension 
		 	$extension = $fileinfo['extension'];
		 			 //file url to store in the database 
			 $file_url = $upload_url .'routes/'. $idroute . '.' . $extension;
			 
			 //file path to upload in the server 
			 $file_path = $upload_path. 'routes/' . $idroute . '.'. $extension;

		 }	  
		 


		 
		 //trying to save the file in the directory 
		 try{
			 //saving the file 
			 move_uploaded_file($_FILES['image']['tmp_name'],$file_path);

			 if($tipo == 'users'){
				$sql = "UPDATE user SET profilephoto ='$file_url' WHERE username = '$name'";
				$response['name'] = $name;
			 }else if($tipo == 'routes'){
			 	$sql = "UPDATE route SET photourl ='$file_url' WHERE idroute = '$idroute'";
			 	$response['idroute'] = $idroute;
			 }
			 
			 
			 //adding the path and name to database 
			 if(mysqli_query($con,$sql)){
			 
				 //filling response array with values 
				 $response['error'] = false; 
				 $response['url'] = $file_url; 
			}
		 //if some error occurred 
		 }catch(Exception $e){
			 $response['error']=true;
			 $response['message']=$e->getMessage();
		 } 
		 //displaying the response 
		 echo json_encode($response);
		 
		 //closing the connection 
	 	 mysqli_close($con);
	 }else{
		 $response['error']=true;
		 $response['message']='Please choose a file';
	 }
 }
 