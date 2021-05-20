pragma solidity ^0.4.2;

contract HelloWorld{
    string name;
	
    
    function set(string n){
		emit test(n);
    	name = n;
    }
    
	event test(string a);

    function HelloWorld(){
       name = "Hello, World!";
    }

    function get()constant returns(string){
        return name;
    }

}