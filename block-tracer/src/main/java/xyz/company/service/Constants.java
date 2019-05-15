package xyz.company.service;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Constants {

    public static final Event TRANSFER_EVENT = new Event("Transfer",
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000000000000000000");
}
