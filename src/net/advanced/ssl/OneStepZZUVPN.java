package net.advanced.ssl;

/**
 * Created by liuyang on 16/4/10.
 */
public class OneStepZZUVPN {




    public static void main(String[] args) throws Exception {
        ZZUKYLoginSSL.main(args);
        ZZUVPNLoginSSL.vpnID = ZZUKYLoginSSL.vpnID;
        ZZUVPNLoginSSL.main(args);
    }
}
