<?php
/**
 * Created by PhpStorm.
 * User: florian
 * Date: 19.04.2016
 * Time: 16:14
 */

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;

/**
 * Class RunController
 * @package AppBundle\Controller
 *
 * @Route("/run")
 */
class RunController extends Controller{

    /**
     *
     * @Route("/add")
     * @Method("POST")
     * @return Response
     */
    public function addAction(){
        return new JsonResponse([
            'success' => true
        ]);
    }
}