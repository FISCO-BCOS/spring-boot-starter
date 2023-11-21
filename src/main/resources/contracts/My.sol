pragma solidity >=0.6.10 <0.8.20;
pragma experimental ABIEncoderV2;

import "./TableV320.sol";

contract My {
   event CreateResult(int256 count);
    event InsertResult(int256 count);
    event UpdateResult(int256 count);
    event RemoveResult(int256 count);

    Cast constant cast =  Cast(address(0x100f));  
    TableManager constant tm =  TableManager(address(0x1002));
    Table table;
    string constant TABLE_NAME = "z11";
    string []  properties = ["name","age","status"];
    string primaryKey = "id";
    string[] public dynamicStringArray;
    constructor () public {
        // create table
        TableInfo memory tf = TableInfo(KeyOrder.Lexicographic ,primaryKey, properties);

        tm.createTable(TABLE_NAME, tf);
        address t_address = tm.openTable(TABLE_NAME);
        require(t_address!=address(0x0),"");
        table = Table(t_address);
    }

    function selectById(string memory id) public payable returns (string []memory)
    {
       
        clearArray(dynamicStringArray);
        Entry memory entry = table.select(id);
        for (uint256 i=0;i<entry.fields.length;i++) {
            addElement(entry.fields[i]);
             
        }
        return dynamicStringArray;
    }
    
    function clearArray(string[] memory arr) public  {
        delete dynamicStringArray;
    }

    
    function addElement(string memory element) public {
        dynamicStringArray.push(element);
    }

    
    function insert(string[] memory value) public returns (int32){
       Entry memory entry = Entry(value[0], new string[](properties.length));
        for (uint256 i = 1; i < value.length; i++) {
             entry.fields[i-1] = value[i];
        }
        int32 result = table.insert(entry);
        emit InsertResult(result);
        return result;
    }

    function updateById(string[] memory value) public returns (int32){
        UpdateField[] memory updateFields = new UpdateField[](properties.length);
        for (uint256 i = 1; i < value.length; i++) {
              updateFields[i-1] = UpdateField(properties[i-1], value[i-1]);
        }

        int32 result = table.update(value[0], updateFields);
        emit UpdateResult(result);
        return result;
    }

    function removeById(string memory id) public returns(int32){
        int32 result = table.remove(id);
        emit RemoveResult(result);
        return result;
    }

    function createTable(string memory tableName, uint8 keyOrder, string memory key,string[] memory fields) public returns(int256){
        require(keyOrder == 0 || keyOrder == 1);
        KeyOrder _keyOrder = KeyOrder.Lexicographic;
        if (keyOrder == 1)
        {
            _keyOrder = KeyOrder.Numerical;
        }
        TableInfo memory tf = TableInfo(_keyOrder, key, fields);
        int32 result = tm.createTable(tableName,tf);
        emit CreateResult(result);
        return result;
    }

    function desc() public view returns(string memory, string[] memory){
        TableInfo memory ti = tm.descWithKeyOrder(TABLE_NAME);
        return (ti.keyColumn,ti.valueColumns);
    }
}
