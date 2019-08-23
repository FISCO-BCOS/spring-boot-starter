pragma solidity ^0.4.24;
//pragma solidity ^0.4.10;
import "./Table.sol";

contract TableTemp {
    event CreateResult(int count);
    event InsertResult(int count);
    event UpdateResult(int count);
    event RemoveResult(int count);

    // 创建表
    function create() public returns(int){
        TableFactory tf = TableFactory(0x1001);  // TableFactory的地址固定为0x1001
        // t_User，表的key_field为name，value_field为item_id,item_name
        // key_field表示AMDB主key value_field表示表中的列，可以有多列，以逗号分隔
        int count = tf.createTable("t_TableTemp", "name", "item_id,item_name,item_address");
        emit CreateResult(count);

        return count;
    }

   // 插入数据
    function insert(string name, int item_id, string item_name,string item_address) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_TableTemp");

        Entry entry = table.newEntry();
        entry.set("name", name);
        entry.set("item_id", item_id);
        entry.set("item_name", item_name);
        entry.set("item_address", item_address);
        int count = table.insert(name, entry);
        emit InsertResult(count);

        return count;
    }

       // 删除数据
          function remove(string name, int item_id) public returns(int){
              TableFactory tf = TableFactory(0x1001);
              Table table = tf.openTable("t_TableTemp");

              Condition condition = table.newCondition();
              condition.EQ("name", name);
              condition.EQ("item_id", item_id);

              int count = table.remove(name, condition);
              emit RemoveResult(count);
              return count;
          }

  struct dataDto {

        bytes32[] user_name_list;
        int[] item_id_list;
        bytes32[] item_name_list;
         bytes32[] item_address_list;
    }

// 查询数据
  function select(string name) public view returns(bytes32[], int[], bytes32[]){
      TableFactory tf = TableFactory(0x1001);
      Table table = tf.openTable("t_TableTemp");

      // 条件为空表示不筛选 也可以根据需要使用条件筛选
      Condition condition = table.newCondition();

      Entries entries = table.select(name, condition);
      bytes32[] memory user_name_bytes_list = new bytes32[](uint256(entries.size()));
      int[] memory item_id_list = new int[](uint256(entries.size()));
      bytes32[] memory item_name_bytes_list = new bytes32[](uint256(entries.size()));
      bytes32[] memory item_address_bytes_list = new bytes32[](uint256(entries.size()));

      for(int i=0; i<entries.size(); ++i) {
          Entry entry = entries.get(i);

          user_name_bytes_list[uint256(i)] = entry.getBytes32("name");
          item_id_list[uint256(i)] = entry.getInt("item_id");
            item_name_bytes_list[uint256(i)] = entry.getBytes32("item_name");
           item_address_bytes_list[uint256(i)] = entry.getBytes32("item_address");
        }


        return (user_name_bytes_list, item_id_list, item_name_bytes_list);
    }

 // 更新数据
    function update(string name, int item_id, string item_name,string item_address) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_TableTemp");

        Entry entry = table.newEntry();
        entry.set("item_name", item_name);
       entry.set("item_address", item_address);
        Condition condition = table.newCondition();
        condition.EQ("name", name);
        condition.EQ("item_id", item_id);

        int count = table.update(name, entry, condition);
        emit UpdateResult(count);
        return count;
    }
// 查询数据
  function selectNew(string name) public view returns(bytes32[], int[], bytes32[],bytes32[]){
      TableFactory tf = TableFactory(0x1001);
      Table table = tf.openTable("t_TableTemp");

      // 条件为空表示不筛选 也可以根据需要使用条件筛选
              Condition condition = table.newCondition();
              condition.EQ("name", name);
      Entries entries = table.select(name, condition);
      bytes32[] memory user_name_bytes_list = new bytes32[](uint256(entries.size()));
      int[] memory item_id_list = new int[](uint256(entries.size()));
      bytes32[] memory item_name_bytes_list = new bytes32[](uint256(entries.size()));
      bytes32[] memory item_address_bytes_list = new bytes32[](uint256(entries.size()));

      for(int i=0; i<entries.size(); i++) {
          Entry entry = entries.get(i);

          user_name_bytes_list[uint256(i)] = entry.getBytes32("name");
          item_id_list[uint256(i)] = entry.getInt("item_id");
            item_name_bytes_list[uint256(i)] = entry.getBytes32("item_name");
           item_address_bytes_list[uint256(i)] = entry.getBytes32("item_address");
        }


        return (user_name_bytes_list, item_id_list, item_name_bytes_list,item_address_bytes_list);
    }


}