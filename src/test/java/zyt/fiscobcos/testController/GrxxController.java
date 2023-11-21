package zyt.fiscobcos.testController;

import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.springframework.web.bind.annotation.*;
import zyt.fiscobcos.FManager;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.contract.MyContract;
import zyt.fiscobcos.entity.ResponseData;

import java.util.List;

@RestController
public class GrxxController {

    @GetMapping("/query/{id}")
    public ResponseData queryById(@PathVariable("id") String id) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        TransactionReceipt transactionReceipt = (TransactionReceipt) fiscoClient.queryById(id,"MyContract", MyContract.class);
        String output = transactionReceipt.getOutput();
        return ResponseData.success(fiscoClient.decodeMethodAndGetOutputAbiObjectParser(false,output,"selectById"));
    }

    @PostMapping("/insert")
    public ResponseData insert(@RequestBody List<String> value) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.insert(value,"MyContract", MyContract.class);
        return ResponseData.success("新增成功");
    }

    @PutMapping("/update")
    public ResponseData updateById(@RequestBody List<String> value) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.updateById(value,"MyContract", MyContract.class);
        return ResponseData.success("修改成功");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseData removeById(@PathVariable("id") String id) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.removeById(id,"MyContract", MyContract.class);
        return ResponseData.success("删除成功");
    }
}