<?php

/**
 * Created by PhpStorm.
 * User: florian
 * Date: 19.04.2016
 * Time: 13:53
 */

namespace AppBundle\DQL;

use \Doctrine\ORM\Query\AST\Functions\FunctionNode;
use Doctrine\ORM\Query\Lexer;
use \Doctrine\ORM\Query\Parser;
use \Doctrine\ORM\Query\SqlWalker;


class RadiusFunction extends FunctionNode {

    const UNIT_KM = 1;
    const UNIT_MI = 2;

    protected $latitude;
    protected $longitude;

    protected $unit = RadiusFunction::UNIT_KM;

    /**
     * @param Parser $parser
     *
     * @return void
     */
    public function parse(Parser $parser) {
        $parser->match(Lexer::T_IDENTIFIER);
        $parser->match(Lexer::T_OPEN_PARENTHESIS);

        $this->latitude = $parser->ArithmeticPrimary();
        $parser->match(Lexer::T_COMMA);

        $this->longitude = $parser->ArithmeticPrimary();
        $parser->match(Lexer::T_CLOSE_PARENTHESIS);
    }


    /**
     * @param SqlWalker $sqlWalker
     *
     * @return string
     */
    public function getSql(SqlWalker $sqlWalker) {


        $multiplier = 1;
        switch ($this->unit){
            case static::UNIT_KM:
                $multiplier = 6371;
                break;
            case static::UNIT_MI:
                $multiplier = 3959;
                break;
        }

        $latFieldName = 'latitude';
        $lngFieldName = 'longitude';

        // Mathematical Magic
        return '('. $multiplier.' * acos( 
            cos( radians('.$this->latitude->dispatch($sqlWalker).') ) * cos( radians('.$latFieldName.') ) 
                * cos( radians('.$lngFieldName.') - radians('.$this->longitude->dispatch($sqlWalker).') ) + sin( radians('.$this->latitude->dispatch($sqlWalker).') ) * sin( radians('.$latFieldName.') )
                 ) 
            )';
    }
}