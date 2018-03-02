package elasticSearchDemo.controller;

import org.elasticsearch.common.xcontent.ToXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
