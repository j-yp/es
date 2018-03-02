package elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;

import elasticSearchDemo.ElasticSearchApplication;
import elasticSearchDemo.prop.ESProps;

/**
 * 使用java API操作elasticSearch
 * 
 * @author jyp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ElasticSearchApplication.class)
@WebAppConfiguration
public class JavaESTest {

	@Autowired
	private ESProps esProps;
	
	@Value("${aaa.bbb}")
	private String aaa;
	
	private TransportClient client;
	
	@Before
	public void initClient() {
		client = new PreBuiltTransportClient(Settings.EMPTY);
		esProps.getEsNodes().forEach(x->{
			try {
				client.addTransportAddress(new TransportAddress(InetAddress.getByName(x.get("host")), Integer.valueOf(x.get("port"))));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		});
	}
	
	@After
	public void closeClient() {
		client.close();
	}
	
	
	@Test
    public void testGet() throws NumberFormatException, UnknownHostException {
    	GetResponse response = client.prepareGet("megacorp", "employee", "1")
    	        .setOperationThreaded(false)
    	        .get();
    	System.out.println(response);
    }
	
	@SuppressWarnings("unused")
	@Test
	public void testIndex() throws IOException {
		XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("user","kimchy");
		json.put("postDate",new Date());
		json.put("message","trying out Elasticsearch");
		String jsonStr = JSON.toJSONString(json);
		IndexResponse response = null;
		response = client.prepareIndex("twitter", "tweet", "1")
				.setSource(jsonStr, XContentType.JSON)
				.get();
		response = client.prepareIndex("twitter", "tweet", "1")
				.setSource(jsonBuilder
						.startObject()
							.field("user","kimchy")
							.field("postDate",new Date())
							.field("message","trying out Elasticsearch")
						.endObject()
						)
				.get();
	}
	
	@Test
	public void testDelete() {
		DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
		System.out.println(response);
	}
	
	@Test
	public void testDeleteQuery() {
		/*BulkByScrollResponse response =
			    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
			        .filter(QueryBuilders.matchQuery("gender", "male")) 
			        .source("twitter")                                  
			        .get();
		long num = response.getDeleted();
		System.out.println("Data deleted num: " + num);*/
		DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
	    .filter(QueryBuilders.matchQuery("gender", "male"))                  
	    .source("twitter")                                                   
	    .execute(new ActionListener<BulkByScrollResponse>() {           
	        @Override
	        public void onResponse(BulkByScrollResponse response) {
	            long num = response.getDeleted();
	            System.out.println("Data deleted num: " + num);
	        }
	        @Override
	        public void onFailure(Exception e) {
	            // Handle the exception
	        }
	    });
		System.out.println("end!!!!!!!!");
	}
	
	@Test
	public void testUpdate() throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("twitter");
		updateRequest.type("tweet");
		updateRequest.id("1");
		updateRequest.doc(jsonBuilder()
		        .startObject()
		            .field("gender", "male")
		        .endObject());
		client.update(updateRequest).get();
	}
	
	public XContentBuilder jsonBuilder() {
		XContentBuilder jsonBuilder = null;
		try {
			jsonBuilder = XContentFactory.jsonBuilder();
		} catch (IOException e) {
			e.printStackTrace();
		};
		return jsonBuilder;
	}
}