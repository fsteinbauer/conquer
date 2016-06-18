<?php
require_once('settings.php');
require_once('requests.php');

//------------------------------------------------------------------------------
try{
  $db = new DB( DB_SERVER, DB_USER, DB_PW, DB_DATABASE );
  $requests = $REQUESTS_PRODUCTIVE;
  
  if(!isset($_GET['data']))
    throw new Exception("invalid request");
  
  $data = explode('/', $_GET['data']);
  $request = $data[0];
  if( !in_array($request, $requests) )
    throw new Exception("invalid request '$request'");
  
  echo $request($data, $db);
}
catch(Exception $e){
  echo json_encode(['success' => false, 'message' => $e->getMessage()]);
}
?>