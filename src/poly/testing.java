package poly;

public class testing {
	public static void main(String[] args){
		Polynomial a = new Polynomial();
		a.poly = new Node(2,0,new Node(3,1,new Node(4,2,new Node(11,3,null))));
		Polynomial b = new Polynomial();
		b.poly = new Node(3,1,new Node(5,2,new Node(7,3,null)));
		Polynomial c = new Polynomial();
		c.poly= new Node(0,0,null);
 		System.out.println((a.evaluate(0)));

	
	}

}
