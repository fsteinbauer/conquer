<?php
require_once('functions.php');

$REQUESTS = [
  //----------------------------------------------------------------------------
  'register' => function($data, $db){
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
    
    $db->query("
      INSERT INTO highscore
        (user_id, area_id, points, rank)
      VALUES
        ('$id', 0, 0, 0);
    ");

    updateRank($db, 0);
        
    return json_encode(['id' => $id, 'name' => $name]);
  },
  
  //----------------------------------------------------------------------------
  'highscore' => function($data, $db){
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
        
    return json_encode(['highscores' => $runs_trimmed]);
  },
  
  //----------------------------------------------------------------------------
  'addscore' => function($data, $db){
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
    ");
    
    updateRank($db, 0);
    updateRank($db, $area);
    
    return json_encode(['success' => true]);
  },
  
  //----------------------------------------------------------------------------
  'rename' => function($data, $db){
    if(count($data) != 3)
      throw new Exception("'{$data[0]}' expects {user}/{name}");
    $user = getUser($data[1]);
    $name = $db->mask(data[2]);
    
    $db->query("
      UPDATE user 
      SET name = '$name'
      WHERE id = '$user'
      LIMIT 1
    ");
    
    return json_encode(['success' => true]);
  },
  
  //----------------------------------------------------------------------------
  'updateranks' => function($data, $db){
    if(count($data) != 1)
      throw new Exception("'{$data[0]}' expects no parameters");

    for($area = 0; $area < 18; $area++){
      updateRank($db, $area);
    }

    return json_encode(['success' => true]);
  },
  
  //----------------------------------------------------------------------------
  'cleardata' => function($data, $db){
    if(count($data) != 1)
      throw new Exception("'{$data[0]}' expects no parameters");
    
    $db->multi_query("
        TRUNCATE TABLE highscore;
        TRUNCATE TABLE user;
      ");
      
    return json_encode(['success' => true]);
  }
];

?>
