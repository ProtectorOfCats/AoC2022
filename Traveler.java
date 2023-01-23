class Traveler{
	private boolean part2 = false;
	private int direction,x,y,z;
	private char face;
	private int[] progress = null;
	private static Facing n = Facing.N,s = Facing.S,e = Facing.E,w = Facing.W,m = Facing.M,l = Facing.L;
	private Facing facing = e;

	public Traveler(int X, int Y, int D, char F){
		x = X; y = Y; direction = D; face = F;
	}

	public Traveler(int X, int Y, int Z, int D, Facing C, char F){
		x = X; y = Y; z = Z; direction = D; facing = C; face = F;
		part2 = true;
		fixProgress();
	}

	public void turn(String s){
		if(s.equals("R")){
			turnRight();
		}else if(s.equals("L")){
			turnLeft();
		}
		if(progress != null){
			fixFacing(s);
// 			Advent22.println("Turning " + (s.equals("R") ? "right" : "left") + ", now going " + facing.valueOf() + ".");
		}
	}

	private void turnRight(){
		direction = (direction + 1) % 4;
// 		if(progress == null) Advent22.println("Turning right; now going " + (direction == 0 ? "east" : direction == 1 ? "south" : direction == 2 ? "west" : "north") + ".");
	}

	private void turnLeft(){
		direction += 4;
		direction = (direction - 1) % 4;
// 		if(progress == null) Advent22.println("Turning left; now going " + (direction == 0 ? "east" : direction == 1 ? "south" : direction == 2 ? "west" : "north") + ".");
	}

	private void fixProgress(){
		int change = 1;
		if(facing == n || facing == w || facing == m) change = -1;
		if(facing == w || facing == e){ progress = new int[]{ change,0,0 }; }else
		if(facing == n || facing == s){ progress = new int[]{ 0,change,0 }; }
		else{ progress = new int[]{ 0,0,change }; }
	}

	private void fixFacing(String S){
		boolean right = S.equals("R");
		if(face == 'A'){
			if(right){
				if(facing == e){ facing = s; }else
				if(facing == s){ facing = w; }else
				if(facing == w){ facing = n; }
				else{ facing = e; }
			}else{
				if(facing == e){ facing = n; }else
				if(facing == s){ facing = e; }else
				if(facing == w){ facing = s; }
				else{ facing = w; }
			}
		}else if(face == 'B'){
			if(right){
				if(facing == l){ facing = s; }else
				if(facing == s){ facing = m; }else
				if(facing == m){ facing = n; }
				else{ facing = l; }
			}else{
				if(facing == l){ facing = n; }else
				if(facing == s){ facing = l; }else
				if(facing == m){ facing = s; }
				else{ facing = m; }
			}
		}else if(face == 'C'){
			if(right){
				if(facing == e){ facing = l; }else
				if(facing == l){ facing = w; }else
				if(facing == w){ facing = m; }
				else{ facing = e; }
			}else{
				if(facing == e){ facing = m; }else
				if(facing == l){ facing = e; }else
				if(facing == w){ facing = l; }
				else{ facing = w; }
			}
		}else if(face == 'D'){
			if(right){
				if(facing == n){ facing = m; }else
				if(facing == m){ facing = s; }else
				if(facing == s){ facing = l; }
				else{ facing = n; }
			}else{
				if(facing == n){ facing = l; }else
				if(facing == m){ facing = n; }else
				if(facing == s){ facing = m; }
				else{ facing = s; }
			}
		}else if(face == 'E'){
			if(right){
				if(facing == e){ facing = n; }else
				if(facing == n){ facing = w; }else
				if(facing == w){ facing = s; }
				else{ facing = e; }
			}else{
				if(facing == e){ facing = s; }else
				if(facing == n){ facing = e; }else
				if(facing == w){ facing = n; }
				else{ facing = w; }
			}
		}else{
			if(right){
				if(facing == e){ facing = m; }else
				if(facing == m){ facing = w; }else
				if(facing == w){ facing = l; }
				else{ facing = e; }
			}else{
				if(facing == e){ facing = l; }else
				if(facing == m){ facing = e; }else
				if(facing == w){ facing = m; }
				else{ facing = w; }
			}
		}
// 		Advent22.println("Now facing " + facing.valueOf() + ".");
		fixProgress();
	}

	public void walk(){
 		if(progress == null){// Advent22.print("Walking " + (direction == 0 ? "east" : direction == 1 ? "south" : direction == 2 ? "west" : "north") + " one step, to (");
			int mod = 1;
			if(direction > 1) mod = -1;
			if(direction % 2 == 0){
				x += mod;
			}else{
				y += mod;
			}
//	 		Advent22.println(x + ", " + y + ").");
		}else{// Advent22.print("Walking " + facing.valueOf() + " one step, to (");
			x += progress[0]; y += progress[1]; z += progress[2];
// 			Advent22.println(x + "," + y + "," + z + ").");
		}
	}

	public boolean pt2(){ return part2; }
	public char face(){ return face; }
	public int[] coords(){ if(!part2) return new int[]{ x,y }; return new int[]{ x,y,z }; }

	public boolean equals(Traveler t){
		if(t == null) return false;
		if(x != t.x) return false;
		if(y != t.y) return false;
		if(part2 && z != t.z) return false;
		if(part2 && facing != t.facing) return false;
		if(direction != t.direction) return false;
		if(face != t.face) return false;
		return true;
	}

	public int direction(){ return direction; }
	public Facing facing(){ return facing; }

	public String toString(){
		if(part2) return toString2();
		return "(" + x + "," + y + "), facing " + (direction == 0 ? "right" : direction == 1 ? "down" : direction == 2 ? "left" : "up") + " on face " + face;
	}

	private String toString2(){
		return "(" + x + "," + y + "," + z + "), facing " + facing.valueOf() + " on face " + face;
	}
}
