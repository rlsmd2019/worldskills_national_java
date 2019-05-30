package dto;

public class User {

	public int no;
	public String id;
	public String pwd;
	public String name;
	public String birthDate;
	public int point;
	public String grade;
	
	public int totalOrderedAmount;
	
	public User(int no, String id, String pwd, String name, String birthDate, int point, String grade, int totalOrderedAmount) {
		super();
		this.no = no;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.birthDate = birthDate;
		this.point = point;
		this.grade = grade;
		this.totalOrderedAmount = totalOrderedAmount;
	}
	
	public float getDiscount() {
		
		if (grade.equals("Bronze")) {
			return 0.03f;
		} else if (grade.equals("Silver")) {
			return 0.05f;
		} else if (grade.equals("Gold")) {
			return 0.1f;
		}
		
		return 0f;
	}
	
	
}
