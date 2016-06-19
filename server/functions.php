<?php
//------------------------------------------------------------------------------
class DB{
  public $mysqli;
  function __construct($server, $user, $pw, $database){
    $this->mysqli = new mysqli($server, $user, $pw, $database);
    if (mysqli_connect_errno()) {
      throw new Exception("cannot connect to database: ".mysqli_connect_error());
    }
  }
  
  function query($string){
    $result = $this->mysqli->query($string);
    if(!$result)
      throw new Exception("invalid query: {$this->mysqli->error}");
    return $result;
  }
  
  function multi_query($string, $last_result = false){
    if(!$this->mysqli->multi_query($string))
      throw new Exception("invalid query: {$this->mysqli->error}");
    do {
      $result = $this->mysqli->store_result();
      if( $this->mysqli->error )
        throw new Exception("invalid query: {$this->mysqli->error}");
    } while ( $this->mysqli->more_results() && $this->mysqli->next_result());
    if( $last_result and !$result)
        throw new Exception("invalid query: {$this->mysqli->error}");
    if( $this->mysqli->error )
        throw new Exception("invalid query: {$this->mysqli->error}");
    
    return $result;
  }
  
  function query_array($string){
    return $this->toArray($this->query($string));
  }
  
  function mask($string){
    return $this->mysqli->real_escape_string($string);
  }
  
  function toArray($result){
    $array = [];
    while($row = $result->fetch_array(MYSQLI_ASSOC)){
      array_push($array, $row);
    }
    return $array;
  }
}

//------------------------------------------------------------------------------
function getFloat($string){
  if( ! preg_match('/[\+\-]?[0-9]*\.?[0-9]+(e[0-9]+)?/', $string) )
    throw new Exception("'$string' is not convertable to float");
  return floatval($string);
}

function getUInt($string){
  if( ! preg_match('/[\+]?[0-9]+/', $string) )
    throw new Exception("'$string' is not convertable to int");
  return intval($string);
}

function getUser($string){
  global $db;
  $id = $db->mask($string);
  $user = $db->query_array( "SELECT * FROM user WHERE id = '$id' LIMIT 1" );
  if(count($user) != 1)
    throw new Exception("'$string' is not a valid user");
  return $user[0]['id'];
}

function getArea($string){
  global $db;
  $id = getUInt($string);
  $area = $db->query_array( "SELECT * FROM area WHERE id = $id LIMIT 1" );
  if(count($area) != 1)
    throw new Exception("$string is not a valid area");
  return $area[0]['id'];
}

//------------------------------------------------------------------------------
function updateRank($db, $area){
  $db->multi_query("
    SET @rank = 0;
    SET @last_points = -1;
    UPDATE highscore 
    SET 
      rank = IF(points = @last_points, @rank, @rank := @rank + 1),
      points = (@last_points := points)
    WHERE area_id = $area
    ORDER BY points DESC;
  ");
  
  if( $db->mysqli->error )
    throw new Exception($db->mysqli->error);
}
?>
