package org.fisco.bcos.solidity;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Asset extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061002861002d640100000000026401000000009004565b610185565b600061100190508073ffffffffffffffffffffffffffffffffffffffff1663c92a78016040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001848103835260078152602001807f6163636f756e74000000000000000000000000000000000000000000000000008152506020018481038252600b8152602001807f61737365745f76616c75650000000000000000000000000000000000000000008152506020019350505050602060405180830381600087803b15801561014657600080fd5b505af115801561015a573d6000803e3d6000fd5b505050506040513d602081101561017057600080fd5b81019080805190602001909291905050505050565b611e0f80620001956000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635b325d781461005c578063612d2bff146100e0578063b433c7ca146101ad575b600080fd5b34801561006857600080fd5b506100c3600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610234565b604051808381526020018281526020019250505060405180910390f35b3480156100ec57600080fd5b50610197600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061069e565b6040518082815260200191505060405180910390f35b3480156101b957600080fd5b5061021e600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506117d4565b6040518082815260200191505060405180910390f35b600080600080600080610245611cf4565b93508373ffffffffffffffffffffffffffffffffffffffff1663d8ac5957888673ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156102c857600080fd5b505af11580156102dc573d6000803e3d6000fd5b505050506040513d60208110156102f257600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156103a0578082015181840152602081019050610385565b50505050905090810190601f1680156103cd5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156103ed57600080fd5b505af1158015610401573d6000803e3d6000fd5b505050506040513d602081101561041757600080fd5b81019080805190602001909291905050509250600091508273ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561049257600080fd5b505af11580156104a6573d6000803e3d6000fd5b505050506040513d60208110156104bc57600080fd5b810190808051906020019092919050505060001415610503577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8281915095509550610695565b8273ffffffffffffffffffffffffffffffffffffffff16633dd2b61460006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561057357600080fd5b505af1158015610587573d6000803e3d6000fd5b505050506040513d602081101561059d57600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600b8152602001807f61737365745f76616c7565000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561065257600080fd5b505af1158015610666573d6000803e3d6000fd5b505050506040513d602081101561067c57600080fd5b8101908080519060200190929190505050819150955095505b50505050915091565b60008060008060008060008060008097506000965060009550600094506106c48c610234565b809750819850505060008714151561081a577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff97507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561076e578082015181840152602081019050610753565b50505050905090810190601f16801561079b5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156107d45780820151818401526020810190506107b9565b50505050905090810190601f1680156108015780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798506117c5565b6108238b610234565b8096508198505050600087141515610979577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe97507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156108cd5780820151818401526020810190506108b2565b50505050905090810190601f1680156108fa5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015610933578082015181840152602081019050610918565b50505050905090810190601f1680156109605780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798506117c5565b89861015610ac5577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd97507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b83811015610a195780820151818401526020810190506109fe565b50505050905090810190601f168015610a465780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015610a7f578082015181840152602081019050610a64565b50505050905090810190601f168015610aac5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798506117c5565b848a86011015610c13577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc97507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b83811015610b67578082015181840152602081019050610b4c565b50505050905090810190601f168015610b945780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015610bcd578082015181840152602081019050610bb2565b50505050905090810190601f168015610bfa5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798506117c5565b610c1b611cf4565b93508373ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610c8157600080fd5b505af1158015610c95573d6000803e3d6000fd5b505050506040513d6020811015610cab57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16631a391cb48d6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610d7e578082015181840152602081019050610d63565b50505050905090810190601f168015610dab5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610dcb57600080fd5b505af1158015610ddf573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff1663def426988b88036040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610e8d57600080fd5b505af1158015610ea1573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663664b37d68d858773ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610f2757600080fd5b505af1158015610f3b573d6000803e3d6000fd5b505050506040513d6020811015610f5157600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015611031578082015181840152602081019050611016565b50505050905090810190601f16801561105e5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561107f57600080fd5b505af1158015611093573d6000803e3d6000fd5b505050506040513d60208110156110a957600080fd5b8101908080519060200190929190505050915060018214151561120a577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb97507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561115e578082015181840152602081019050611143565b50505050905090810190601f16801561118b5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156111c45780820151818401526020810190506111a9565b50505050905090810190601f1680156111f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798506117c5565b8373ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561126e57600080fd5b505af1158015611282573d6000803e3d6000fd5b505050506040513d602081101561129857600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff16631a391cb48c6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561136b578082015181840152602081019050611350565b50505050905090810190601f1680156113985780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156113b857600080fd5b505af11580156113cc573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff1663def426988b87016040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561147a57600080fd5b505af115801561148e573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663664b37d68c838773ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561151457600080fd5b505af1158015611528573d6000803e3d6000fd5b505050506040513d602081101561153e57600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b8381101561161e578082015181840152602081019050611603565b50505050905090810190601f16801561164b5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561166c57600080fd5b505af1158015611680573d6000803e3d6000fd5b505050506040513d602081101561169657600080fd5b8101908080519060200190929190505050507f105af2c562df33af7eaa9de5fb0c18d8d30f281a18f95a8f76b44353a322693c888d8d8d604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561171d578082015181840152602081019050611702565b50505050905090810190601f16801561174a5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015611783578082015181840152602081019050611768565b50505050905090810190601f1680156117b05780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18798505b50505050505050509392505050565b600080600080600080600080955060009450600093506117f389610234565b8095508196505050600085141515611c155761180d611cf4565b92508273ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561187357600080fd5b505af1158015611887573d6000803e3d6000fd5b505050506040513d602081101561189d57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff16631a391cb48a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611970578082015181840152602081019050611955565b50505050905090810190601f16801561199d5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156119bd57600080fd5b505af11580156119d1573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def42698896040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611a7d57600080fd5b505af1158015611a91573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16634c6f30c08a846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611b50578082015181840152602081019050611b35565b50505050905090810190601f168015611b7d5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611b9d57600080fd5b505af1158015611bb1573d6000803e3d6000fd5b505050506040513d6020811015611bc757600080fd5b810190808051906020019092919050505090506001811415611bec5760009550611c10565b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe95505b611c39565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff95505b7f7ac7a04970319ae8fc5b92fe177d000fee3c00c92f8e78aae13d6571f17c351f868a8a6040518084815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b83811015611ca9578082015181840152602081019050611c8e565b50505050905090810190601f168015611cd65780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a185965050505050505092915050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611d9e57600080fd5b505af1158015611db2573d6000803e3d6000fd5b505050506040513d6020811015611dc857600080fd5b810190808051906020019092919050505090508092505050905600a165627a7a72305820a384a134ae2f23d7fb6ca4a68162ed2ae985de052f8414b5112f21e1b6f016400029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"from_account\",\"type\":\"string\"},{\"name\":\"to_account\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"},{\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"RegisterEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"from_account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"to_account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"TransferEvent\",\"type\":\"event\"}]";

    public static final String FUNC_SELECT = "select";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_REGISTER = "register";

    public static final Event REGISTEREVENT_EVENT = new Event("RegisterEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFEREVENT_EVENT = new Event("TransferEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> select(String account) {
        final Function function = new Function(FUNC_SELECT,
                Arrays.<Type>asList(new Utf8String(account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transfer(String from_account, String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Utf8String(from_account),
                new Utf8String(to_account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transfer(String from_account, String to_account, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Utf8String(from_account),
                new Utf8String(to_account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferSeq(String from_account, String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Utf8String(from_account),
                new Utf8String(to_account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> register(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Utf8String(account),
                new Uint256(asset_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void register(String account, BigInteger asset_value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Utf8String(account),
                new Uint256(asset_value)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerSeq(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Utf8String(account),
                new Uint256(asset_value)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<RegisterEventEventResponse> getRegisterEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREVENT_EVENT, transactionReceipt);
        ArrayList<RegisterEventEventResponse> responses = new ArrayList<RegisterEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.asset_value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegisterEventEventResponse> registerEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, RegisterEventEventResponse>() {
            @Override
            public RegisterEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTEREVENT_EVENT, log);
                RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.asset_value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegisterEventEventResponse> registerEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEREVENT_EVENT));
        return registerEventEventFlowable(filter);
    }

    public List<TransferEventEventResponse> getTransferEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFEREVENT_EVENT, transactionReceipt);
        ArrayList<TransferEventEventResponse> responses = new ArrayList<TransferEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventEventResponse typedResponse = new TransferEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.from_account = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to_account = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventEventResponse> transferEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventEventResponse>() {
            @Override
            public TransferEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFEREVENT_EVENT, log);
                TransferEventEventResponse typedResponse = new TransferEventEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.from_account = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.to_account = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventEventResponse> transferEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFEREVENT_EVENT));
        return transferEventEventFlowable(filter);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RegisterEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String account;

        public BigInteger asset_value;
    }

    public static class TransferEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String from_account;

        public String to_account;

        public BigInteger amount;
    }
}
