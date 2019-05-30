package dto;

public class Orderlist {
	
	public int orderNo;
	public String date;
	public int userNo;
	public int menuNo;
	public String group;
	public String size;
	public int price;
	public int count;
	public int amount;
	public String menuName;
	
	public Orderlist(int orderNo, String date, int userNo, int menuNo, String group, String size, int price, int count,
			int amount) {
		this(orderNo, date, userNo, menuNo, group, size, price, count, amount, null);
	}
	
	public Orderlist(int orderNo, String date, int userNo, int menuNo, String group, String size, int price, int count,
			int amount, String menuName) {
		super();
		this.orderNo = orderNo;
		this.date = date;
		this.userNo = userNo;
		this.menuNo = menuNo;
		this.group = group;
		this.size = size;
		this.price = price;
		this.count = count;
		this.amount = amount;
		this.menuName = menuName;
	}
	
	
	
}
