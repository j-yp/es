package elasticSearchDemo.esutils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import elasticSearchDemo.esanno.Id;
import elasticSearchDemo.esanno.Index;
import elasticSearchDemo.esanno.Type;
import elasticSearchDemo.vo.EntityVO;

public class ESObjectHandle {
	public static EntityVO getObjectParameters(Object object) throws Exception {
		checkAnnotation(object);
		Class<? extends Object> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		EntityVO vo = new EntityVO();
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(Index.class)) {
				Index anno = field.getAnnotation(Index.class);
				vo.setIndex(anno.name());
			}else if(field.isAnnotationPresent(Type.class)) {
				Type anno = field.getAnnotation(Type.class);
				vo.setType(anno.name());
			}else if(field.isAnnotationPresent(Id.class)) {
				Object obj = field.get(object);
				if(obj == null) {
					throw new Exception("Id value is null");
				}
				vo.setId(obj.toString());
			}
		}
		return vo;
	}
	
	public static Map<String, Object> getFields(Object object) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map = new HashMap<>();
		Class<? extends Object> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(elasticSearchDemo.esanno.Field.class)) {
				Object obj = field.get(object);
				map.put(field.getName(), obj);
			}
		}
		return map;
	}
	
	private static boolean checkAnnotation(Object object) throws Exception {
		boolean flag = false;
		Class<? extends Object> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		int indexCount = 0;
		int typeCount = 0;
		int idCount = 0;
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(Index.class)) {
				indexCount++;
			}else if(field.isAnnotationPresent(Type.class)) {
				typeCount++;
			}else if(field.isAnnotationPresent(Id.class)) {
				idCount++;
			}
		}
		if(indexCount == 1 && typeCount == 1 && idCount ==1) {
			flag = true;
		}else if(!(indexCount == 1)){
			throw new Exception("The num of Index annotations is wrong in " + clazz.getName());
		}else if(!(typeCount == 1)) {
			throw new Exception("The num of Type annotations is wrong in " + clazz.getName());
		}else if(!(idCount == 1)) {
			throw new Exception("The num of Id annotations is wrong in " + clazz.getName());
		}
		return flag;
	}
}
