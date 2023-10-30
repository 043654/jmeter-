import cn.dong.*
import groovy.json.JsonOutput;
import static cn.dong.RSAUtil.*;
import cn.hutool.core.io.file.FileReader
import java.nio.charset.StandardCharsets;
import cn.hutool.json.JSONUtil
import cn.hutool.core.util.IdUtil

def runPath = System.getProperty("user.dir") + "/libfile/";

log.info("==========================================================");
log.info("授信加密脚本开始获取秘钥目录" + runPath);
log.info("==========================================================");

def filereader = new FileReader(runPath +"/yuefushouxinshenqing.txt","utf-8")
def bizContent = filereader.readString()


// 生成18位uuid，格式不带-
//def uuid = IdUtil.fastSimpleUUID().substring(0,18)
def uuid = IdUtil.fastSimpleUUID()
def jsonstr = JSONUtil.parseObj(bizContent)
jsonstr.set("account_id",uuid)
def jsonstrs = JSONUtil.toJsonStr(jsonstr)
log.info("==========================================================");
log.info("设置随机account_id" + jsonstr);
log.info("==========================================================");


props.put("bizContent1",jsonstrs)
def bizContent2 = props.get("bizContent1")
log.info("==========================================================");
log.info("读取的json报文设置为jmeter全局属性" + bizContent2);
log.info("==========================================================");

byte[] demo = jsonstrs.getBytes(StandardCharsets.UTF_8);
def encryptBizContent = new String(RSAUtil.encrypt(demo, runPath + "gt_rsa_public_key.pem"));
//def encryptBizContent = new String(RSAUtil.encrypt(bizContent.getBytes(), runPath + "gt_rsa_public_key.pem"));
def postRequest = ["merchantId": "test","transSeqNo": "123456","transReqTime": "20060102150405","version": "2.0"]
postRequest.put("bizContent",encryptBizContent)
def posttreeMap = new TreeMap()
posttreeMap.putAll(postRequest)
// 小微和放心借
def SALT ="***********"
// 抖音月付
def salt ="***********"
postRequest.put("sign",GetSign(posttreeMap,SALT))
def postJson = JsonOutput.toJson(postRequest).toString()
vars.put("postbody",postJson)
def d = vars.get("postbody")

log.info("==========================================================");
log.info("脚本执行完成" + d);
log.info("==========================================================");
