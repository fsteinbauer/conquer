<?php
/**
 * Created by PhpStorm.
 * User: florian
 * Date: 19.04.2016
 * Time: 11:47
 */

namespace AppBundle\Controller;

use AppBundle\Entity\User;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;


/**
 * Class UserController
 * @package AppBundle\Controller
 *
 * @Route("/user")
 */
class UserController extends Controller {

    /**
     * @param $latitude
     * @param $longitude
     *
     * @Route("/register/{latitude}/{longitude}", requirements={"latitude" = "[-+]?(\d*[.])?\d+", "longitude" = "[-+]?(\d*[.])?\d+"})
     * @return JsonResponse
     */
    public function registerAction($latitude, $longitude){

        $user = new User();
        $user->setName('Anonymous Armadillo');

        $em = $this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();

        return new JsonResponse([
            'user'     => $user->toArray(),
            'powerups' => $this->getPowerups($latitude, $longitude)
        ]);
    }

    /**
     * @param User $user
     * @return JsonResponse
     *
     * @Route("/login/{id}/{latitude}/{longitude}", requirements={"latitude" = "[-+]?(\d*[.])?\d+", "longitude" = "[-+]?(\d*[.])?\d+"})
     * @ParamConverter("user", class="AppBundle:User")
     */
    public function loginAction(User $user, $latitude, $longitude){

        return new JsonResponse([
            'user'      => $user->toArray(),
            'powerups'  => $this->getPowerups($latitude, $longitude)
        ]);
    }



    protected function getPowerups($latitude, $longitude){

        $em = $this->getDoctrine()->getManager();

        $powerupRepository = $em->getRepository('AppBundle:Powerup');

        return $powerupRepository->findInRadius($latitude, $longitude);
    }


}