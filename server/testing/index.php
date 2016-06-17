<?php
require_once('../settings.php');

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
    } while ( $this->mysqli->more_results() && $this->mysqli->next_result());
    if( $last_result and !$result)
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
try{
  $db = new DB(DB_SERVER, DB_USER, DB_PW, DB_DATABASE);
  
  if(!isset($_GET['data']))
    throw new Exception("invalid request");
  
  $data = explode('/', $_GET['data']);
  switch($data[0]){
    //--------------------------------------------------------------------------
    case 'register':{
      if(count($data) != 1)
        throw new Exception("'{$data[0]}' expects no parameters");
      
      do{
        $id = hash('ripemd128', time() + rand());
        $name = 'John Doe';
        $result = $db->query("
          INSERT INTO user(id, name)
          VALUES( '$id', '$name' )
        ");
      }while(!$result);
      
      echo json_encode(['id' => $id, 'name' => $name]);
      break;
    }
    
    //--------------------------------------------------------------------------
    case 'highscore':{
      if(count($data) != 3)
        throw new Exception("'{$data[0]}' expects {user}/{area}");
      $user = getUser($data[1]);
      $area = getArea($data[2]);
      
      $runs = $db->toArray($db->multi_query( "
        SET @user_rank := 0;
        SELECT
          rank,
          name,
          points,
          user
        FROM (
          SELECT 
            rank, 
            @user_rank := IF(user_id = '$user', rank, @user_rank),
            points, 
            user.name AS name,
            @user := IF(user_id = '$user', true, false) AS user
          FROM highscore JOIN user ON user.id = user_id WHERE area_id = $area
        ) AS subquery
        WHERE 
          rank BETWEEN 1 AND 5
          OR 
          rank BETWEEN (@user_rank-2) AND (@user_rank+2)
        ORDER BY rank ASC
      " , true));

      $runs_trimmed = [];
      foreach( $runs as $run ){
        array_push($runs_trimmed, [
          'rank' => intval($run['rank']), 
          'points' => intval($run['points']), 
          'name' => $run['name'], 
          'is_user' => boolval($run['user'])
        ]);
      }
      
      echo json_encode(['highscores' => $runs_trimmed]);
      break;
    }
  
   //--------------------------------------------------------------------------
    case 'addscore':{
      if(count($data) != 4)
        throw new Exception("'{$data[0]}' expects {user}/{area}/{points}");
      $user = getUser($data[1]);
      $area = getArea($data[2]);
      $points = getUInt($data[3]);
      
      if($area == 0)
        throw new Exception("'area' is not allowed to be 0");
      
      $db->multi_query("
        INSERT INTO highscore
          (user_id, area_id, points, rank)
        VALUES
          ('$user', $area, $points, 0),
          ('$user', 0, $points, 0)
        ON DUPLICATE KEY UPDATE
          points = points + VALUES(points);
          
        SET @rank = 0;
        UPDATE highscore 
        SET rank = (@rank := @rank + 1)
        WHERE area_id = $area
        ORDER BY points DESC;
        
        SET @rank = 0;
        UPDATE highscore 
        SET rank = (@rank := @rank + 1)
        WHERE area_id = 0
        ORDER BY points DESC;
      ");
      
      echo json_encode(['success' => true]);
      break;
    }
    
    //--------------------------------------------------------------------------
    case 'updateranks':{ //for debugging, call after added new scores manually
      if(count($data) != 1)
        throw new Exception("'{$data[0]}' expects no parameters");
      
      for($area = 0; $area < 18; $area++){
        $db->multi_query("
          SET @rank = 0;
          UPDATE highscore 
          SET rank = (@rank := @rank + 1)
          WHERE area_id = $area
          ORDER BY points DESC;
        ");
      }
      
      echo json_encode(['success' => true]);
      break;
    }
    
    //--------------------------------------------------------------------------
    case 'cleardata':{ //for debugging, call after added new scores manually
      if(count($data) != 2)
        throw new Exception("'{$data[0]}' expects {password}");
      if($data[1] != CLEAR_PW)
        throw new Exception("wrong password");
      
      $db->multi_query("
          TRUNCATE TABLE area;
          TRUNCATE TABLE highscore;
          TRUNCATE TABLE user;
        ");
        
      echo json_encode(['success' => true]);
      break;
    }
    
    //--------------------------------------------------------------------------
    default:{
      throw new Exception("invalid request '{$data[0]}'");
    }
  }
}
catch(Exception $e){
  echo json_encode(['success' => false, 'message' => $e->getMessage()]);
}
?>
