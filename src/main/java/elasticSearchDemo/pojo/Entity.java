package elasticSearchDemo.pojo;

import java.io.Serializable;
import java.util.List;

import elasticSearchDemo.esanno.Field;
import elasticSearchDemo.esanno.Id;
import elasticSearchDemo.esanno.Index;
import elasticSearchDemo.esanno.Strategies;
import elasticSearchDemo.esanno.Type;

public class Entity {
	@Index(name = "megacorp")
	public static final String INDEX="megacorp";
	@Type(name = "employee")
	public static final String TYPE="employee";
	@Id(strategy = Strategies.UUID)
	public Serializable ID;
	
	@Field(name = "first_name")
	private String firstName;
	@Field(name = "last_name")
	private String lastName;
	@Field(name = "age")
	private int age;
	@Field(name = "about")
	private String about;
	@Field(name =  "interests")
	private List<String> interests;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<String> getInterests() {
		return interests;
	}
	public void setInterests(List<String> interests) {
		this.interests = interests;
	}
	public Serializable getID() {
		return ID;
	}
	public void setID(Serializable iD) {
		ID = iD;
	}
}
