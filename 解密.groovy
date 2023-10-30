import cn.dong.*
import static cn.dong.RSAUtil.*;


def encryptData = vars.get("bizContent")

log.info("==========================================================");
log.info("获取跨线程组变量:" + encryptData);
log.info("==========================================================");

def runPath = System.getProperty("user.dir") + "/libfile/"
String result = new String(RSAUtil.decrypt(encryptData.getBytes(),runPath + "gt_rsa_privite_key.pem"));

log.info("==========================================================");
log.info("解密的业务报文:" + result);
log.info("==========================================================");