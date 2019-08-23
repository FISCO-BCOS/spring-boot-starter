pragma solidity >=0.4.0 <0.7.0;

contract test{
    
    uint public aa=0;
    
    function set(uint tt) public{
        aa=tt;
    }

     function get() public view returns (uint) {
        return aa;
    }
}
