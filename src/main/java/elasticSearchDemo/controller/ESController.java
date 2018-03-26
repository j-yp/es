package elasticSearchDemo.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.xcontent.ToXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swetake.util.Qrcode;

import elasticSearchDemo.pojo.Entity;
import elasticSearchDemo.service.ESService;

@Controller
@RequestMapping("/es")
public class ESController {
	@Autowired
	private ESService esService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public String search() {
		return null;
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public ToXContent get(@RequestParam(name = "id") String id) {
		ToXContent toXContent = null;
		try {
			Entity entity = new Entity();
			entity.setID(id);
			toXContent = esService.get(entity);
			System.out.println(toXContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toXContent;
	}
	
	@GetMapping("/QRCode")
	@ResponseBody
	public String getQRCode(String QRData, HttpServletResponse response) {
		try {
			//计算二维码图片的高宽比
			// API文档规定计算图片宽高的方式 ，v是本次测试的版本号
			int v =6;
			int width = 67 + 12 * (v - 1);
			int height = 67 + 12 * (v - 1);


			Qrcode x = new Qrcode();
			/**
			 * 纠错等级分为
			 * level L : 最大 7% 的错误能够被纠正；
			 * level M : 最大 15% 的错误能够被纠正；
			 * level Q : 最大 25% 的错误能够被纠正；
			 * level H : 最大 30% 的错误能够被纠正；
			 */
			x.setQrcodeErrorCorrect('L');
			x.setQrcodeEncodeMode('B');//注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
			x.setQrcodeVersion(v);//版本号  1-40
			String qrData = "www.baidu.com";//内容信息

			byte[] d = qrData.getBytes("utf-8");//汉字转格式需要抛出异常

			//缓冲区
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

			//绘图
			Graphics2D gs = bufferedImage.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.setColor(Color.BLACK);
			gs.clearRect(0, 0, width, height);

			//偏移量
			int pixoff = 2;


			/**
			 * 容易踩坑的地方
			 * 1.注意for循环里面的i，j的顺序，
			 *   s[j][i]二维数组的j，i的顺序要与这个方法中的 gs.fillRect(j*3+pixoff,i*3+pixoff, 3, 3);
			 *   顺序匹配，否则会出现解析图片是一串数字
			 * 2.注意此判断if (d.length > 0 && d.length < 120)
			 *   是否会引起字符串长度大于120导致生成代码不执行，二维码空白
			 *   根据自己的字符串大小来设置此配置
			 */
			if (d.length > 0 && d.length < 120) {
			    boolean[][] s = x.calQrcode(d);

			    for (int i = 0; i < s.length; i++) {
			        for (int j = 0; j < s.length; j++) {
			            if (s[j][i]) {
			                gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
			            }
			        }
			    }
			}
			gs.dispose();
			bufferedImage.flush();
			//设置图片格式，与输出的路径
			//ImageIO.write(bufferedImage, "png", new File("D:/qrcode.png"));
			
			//将图片输出给浏览器  
		    //BufferedImage image = (BufferedImage) objs[1];  
		    response.setContentType("image/png");  
		    OutputStream os = response.getOutputStream();  
		    ImageIO.write(bufferedImage, "png", os);  
			os.flush();
			os.close();
			System.out.println("二维码生成完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
}
