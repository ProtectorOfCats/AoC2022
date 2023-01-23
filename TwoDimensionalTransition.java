class TwoDimensionalTransition implements iTransition{
	private int minX,minY,maxX,maxY;
	public TwoDimensionalTransition(int mX,int mY,int MX,int MY){
		minX = mX; minY = mY; maxX = MX; maxY = MY;
	}

	public Traveler apply(Traveler t){
		int x = t.coords()[0], y = t.coords()[1], facing = t.direction();
		char face = t.face();
		if((x == minX && facing == 2) || (x == maxX && facing == 0)){
			if(face == 'A'){ face = 'B'; }else
			if(face == 'B'){ face = 'A'; }else
			if(face == 'D'){ face = 'E'; }else
			if(face == 'E'){ face = 'D'; }
			if(x == minX && facing == 2){
				x = maxX;
			}else{
				x = minX;
			}
		}else if(y == minY && facing == 3){
			if(face == 'A'){ face = 'E'; }else
			if(face == 'C'){ face = 'A'; }else
			if(face == 'E'){ face = 'C'; }else
			if(face == 'F'){ face = 'D'; }else
			if(face == 'D'){ face = 'F'; }
			y = maxY;
		}else if(y == maxY && facing == 1){
			if(face == 'A'){ face = 'C'; }else
			if(face == 'C'){ face = 'E'; }else
			if(face == 'E'){ face = 'A'; }else
			if(face == 'D'){ face = 'F'; }else
			if(face == 'F'){ face = 'D'; }
			y = minY;
		}
		return new Traveler(x,y,facing,face);
	}
}
