package pac1;

import java.io.*;
import java.net.*;
import java.util.*;

public class LocalHost{
    public static void main(String[] args) throws Exception {
        byte[] address = {12,23, (byte) 128, (byte) 209};// wraparound to negative number;
        //in java, there is no unsigned byte like in C.
        System.out.println((byte)129);//result is -127
        //you can't construct an InetAddress, only through static factory method.
        InetAddress lessWrong = InetAddress.getByAddress(address); //the IP won't necessary exist, that is, the creation always succeed.
        InetAddress lessWrongWithName = InetAddress.getByAddress("lessWrong",address);
        InetAddress addressByIP = InetAddress.getByName("127.0.0.1");//won't inquire DNS server.
        System.out.println(addressByIP.getHostName());// until now...
        InetAddress local  = InetAddress.getLocalHost();
        byte[] localAddress = local.getAddress();
        for(byte b : localAddress){
            System.out.print((b < 0 ? b+256:b) + ".");
//            System.out.print(b < 0 ? b+256:b + ".");// seems that this would println10.12992.101, miss a dot when the byte is negative;
        }
        System.out.println(local);
    }


}
