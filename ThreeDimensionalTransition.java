class ThreeDimensionalTransition implements iTransition{
	private int minX,minY,maxX,maxY,minZ,maxZ;
	public ThreeDimensionalTransition(int mX,int mY, int mZ, int MX, int MY, int MZ){
		minX = mX; minY = mY; minZ = mZ; maxX = MX; maxY = MY; maxZ = MZ;
	}

	public Traveler apply(Traveler t){
		int x = t.coords()[0], y = t.coords()[1], z = t.coords()[2];
		Facing facing = t.facing();
		char face = t.face(), newFace = '#';
		if(x == minX && facing == Facing.W){ newFace = 'D'; }else
		if(x == maxX && facing == Facing.E){ newFace = 'B'; }else
		if(y == minY && facing == Facing.N){ newFace = 'F'; }else
		if(y == maxY && facing == Facing.S){ newFace = 'C'; }else
		if(z == minZ && facing == Facing.M){ newFace = 'A'; }else
		if(z == maxZ && facing == Facing.L){ newFace = 'E'; }
		if(newFace != '#'){
			if(face == 'A'){ facing = Facing.L; }else
			if(face == 'B'){ facing = Facing.W; }else
			if(face == 'C'){ facing = Facing.N; }else
			if(face == 'D'){ facing = Facing.E; }else 
			if(face == 'E'){ facing = Facing.M; }
			else{ facing = Facing.S; }
		}else{
			return t;
		}
		return new Traveler(x,y,z,t.direction(),facing,newFace);
	}
}
