<?php
$a[] = "Tizio";
$a[] = "Caio";
$a[] = "Sal";
$a[] = "Luis";

$Persone = array (
  array("Uomo","18 anni","Politico"),
  array("Uomo","100 anni","Professore"),
  array("Donna","25 anni","Bidella"),
  array("Uomo","62 anni","Serramentista")
);

$q = $_REQUEST["q"];
$cont=1;
$hint = "";

if ($q !== "") {
  foreach($a as $name) {
  
	if($q==$name){
		$hint=" ".$Persone[$cont-1][0]." di ".$Persone[$cont-1][1]." professione: ".$Persone[$cont-1][2];
		break;
	}        
	else
		$hint = "Nome non esiste nel Database";
	$cont=$cont+1;	
    }
	
  }

// Output "no suggestion" if no hint was found or output correct values
echo $hint;
?>