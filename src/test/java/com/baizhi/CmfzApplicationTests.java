package com.baizhi;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.mapper.MenuMapper;
import com.baizhi.util.PathUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    MenuMapper menuMapper;

    @Test
    public void contextLoads() {
        List<Banner> banners = bannerMapper.selectAll();
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

    @Test
    public void test1() {
        List<Menu> menus = menuMapper.selfselectAll();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

    @Test
    public void testPOI() {
        //创建 Excel 工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("用户信息");
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"编号", "姓名", "出生年月"};
        //创建单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            //i 标示列索引
            //创建单元格
            cell = row.createCell(i);
            //给单元格赋值
            cell.setCellValue(title[i]);
        }
        //样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置日期处理格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy 年 MM 月 dd 日 "));
        //其他样式


        //处理数据行
        for (int i = 1; i < 10; i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue("张三" + i);
            //设置出生年月格式
            cell = row.createCell(2);
            cell.setCellValue(new Date());
            cell.setCellStyle(cellStyle);
        }
        try {
            workbook.write(new File("e:\\用户.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMd5() {
        //页面密码
        String password = "123456";
        //生成盐值
        String salt = UUID.randomUUID().toString().replace("-", "").substring(3, 7);
        //加密
        String pasw = DigestUtils.md5Hex(password + salt);
        System.out.println(pasw);
        System.out.println(salt);
    }

    @Test
    public void testMessage() throws ClientException {


        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIOICO10ykgC9d";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "8aPLs2us14X8LyATALB75ZQiNZ44ML";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers("15617933680");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("华睿论坛");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_164155992");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":123}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
        }
    }
}
