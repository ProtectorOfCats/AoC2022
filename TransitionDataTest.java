class TransitionDataTest{

	public static void main(String[] args){
		testThreeDimensionalTransition();
	}

	private static void Test(Traveler traveler, iTransition trans, Traveler expected){
		Traveler got = trans.apply(traveler);
		output(got.equals(expected),traveler,got,expected); 
	}

	private static void output(boolean b,Traveler original,Traveler got,Traveler expected){
		if(!b){
			System.out.println("Test Failed: started with " + original.toString() + ", recieved " + got.toString() + ", but expected " + expected.toString() + ".");
		}else{
			System.out.println("test succeeded!");
		}
	}

	public static void testTwoDimensionalTransition(){
		Traveler traveler = new Traveler(49,0,0,'A');
		Traveler expected = new Traveler(0,0,0,'B');
		iTransition transition = new TwoDimensionalTransition(0,0,49,49);
		System.out.print("TwoDimensionalTransition ");
		Test(traveler, transition, expected);
		traveler = new Traveler(49,49,1,'C');
		expected = new Traveler(49,0,1,'E');
		System.out.print("TwoDimensionalTransition ");
		Test(traveler, transition, expected);
		traveler = new Traveler(0,49,2,'D');
		expected = new Traveler(49,49,2,'E');
		System.out.print("TwoDimensionalTransition ");
		Test(traveler, transition, expected);
		traveler = new Traveler(49,0,3,'F');
		expected = new Traveler(49,49,3,'D');
		System.out.print("TwoDimensionalTransition ");
		Test(traveler, transition, expected);
		traveler = new Traveler(49,0,3,'B');
		expected = new Traveler(49,49,3,'B');
		System.out.print("TwoDimensionalTransition ");
		Test(traveler, transition, expected);
	}

	public static void testThreeDimensionalTransition(){
		Traveler t = new Traveler (0,0,0,2,Facing.W,'A');
		Traveler e = new Traveler (0,0,0,2,Facing.L,'D');
		iTransition transition = new ThreeDimensionalTransition(0,0,0,49,49,49);
		System.out.print("ThreeDimensionalTransition ");
		Test(t,transition,e);
		t = new Traveler(0,49,0,2,Facing.M,'C');
		e = new Traveler(0,49,0,2,Facing.N,'A');
		System.out.print("ThreeDimensionalTransition ");
		Test(t,transition,e);
		t = new Traveler(0,49,49,2,Facing.L,'C');
		e = new Traveler(0,49,49,2,Facing.N,'E');
		System.out.print("ThreeDimensionalTransition ");
		Test(t,transition,e);
		t = new Traveler(0,49,0,2,Facing.S,'A');
		e = new Traveler(0,49,0,2,Facing.L,'C');
		System.out.print("ThreeDimensionalTransition ");
		Test(t,transition,e);
		t = new Traveler(0,0,49,2,Facing.N,'E');
		e = new Traveler(0,0,49,2,Facing.M,'F');
		System.out.print("ThreeDimensionalTransition ");
		Test(t,transition,e);
	}
}