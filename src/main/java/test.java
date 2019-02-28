import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.hd.Sha256Hash;
import org.ethereum.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

import java.math.BigDecimal;
import java.math.BigInteger;

public class test {

    public static void main(String[] args) {
        byte[] priKey = Sha256Hash.hash("pxq_test".getBytes());
        ECKey ecKey = ECKey.fromPrivate(priKey);
        System.out.println("0x" + Hex.toHexString(ecKey.getAddress()));
        //https://ropsten.etherscan.io/address/0x3819fb94de88507cd31a741fa15900a7d4626fb6 //查询交易

        String toAddress = "0x8b6f2084343649114a37035cb48c4be80ae6ea4d";
        BigInteger gasPrice = new BigInteger("3b9aca00", 16); //当前gas价格  https://api-ropsten.etherscan.io/api?module=proxy&action=eth_gasPrice
        BigInteger gasLimit = BigInteger.valueOf(21000);//最高允许消耗gas数量
        double sendValue = 2;//发送0.1个
        BigDecimal decimal = BigDecimal.valueOf(sendValue);
        BigInteger value = BigDecimal.valueOf(10).pow(18).multiply(decimal).toBigInteger();//发送金额


        int nonce = 0; //当前是第几笔输出


        if (toAddress.startsWith("0x")) {
            toAddress = toAddress.substring(2);
        }
        byte[] bytes_to_addr = Hex.decode(toAddress);
        byte[] bytes_gas_price = ByteUtil.bigIntegerToBytes(gasPrice);
        byte[] bytes_gas_limit = ByteUtil.bigIntegerToBytes(gasLimit);
        byte[] bytes_value = ByteUtil.bigIntegerToBytes(value);
        byte[] bytes_nonce = ByteUtil.longToBytesNoLeadZeroes(nonce);

        //chainId
        //1      eth mainnet
        //3      eth testnet ropsten
        //4      eth testnet rinkeby
        //61     etc mainnet
        //62     etc testnet

        Transaction mainTransaction = new Transaction(
                bytes_nonce,
                bytes_gas_price,
                bytes_gas_limit,
                bytes_to_addr,
                bytes_value,
                new byte[0],
                3);
        mainTransaction.sign(ecKey);
        System.out.println(mainTransaction.toString());
        System.out.println(Hex.toHexString(mainTransaction.getEncoded()));
        // https://ropsten.etherscan.io/pushTx 广播交易
    }
}
