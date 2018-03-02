package elasticSearchDemo.esutils;

import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.ToXContent;
import org.springframework.stereotype.Component;

import elasticSearchDemo.vo.EntityVO;
@Component
public class Client {
	private TransportClient client;
	
	public Client(ESFactory factory) {
		this.setClient(factory.getTransportClient());
	}
	
	private void setClient(TransportClient client) {
		this.client = client;
	}

	/**
	 * 创建索引
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public ToXContent index(Object object) throws Exception {
		EntityVO entityVO = ESObjectHandle.getObjectParameters(object);
		Map<String, Object> fieldMap = ESObjectHandle.getFields(object);
		client.prepareIndex(entityVO.getIndex(), entityVO.getType(), entityVO.getId()).
		
	}
	
	public ToXContent get(Object object) throws Exception {
		EntityVO entityVO = ESObjectHandle.getObjectParameters(object);
		GetResponse response = client
				.prepareGet(entityVO.getIndex(), entityVO.getType(), entityVO.getId())
				.setOperationThreaded(false)
				.get();
		return response;
	}
	
	public ToXContent delete(Object object) throws Exception {
		EntityVO entityVO = ESObjectHandle.getObjectParameters(object);
		DeleteResponse response = client
				.prepareDelete(entityVO.getIndex(), entityVO.getType(), entityVO.getId())
				.get();
		return response;
	}
	
	public ToXContent search(Object object) {
		return null;
	}
	
}
