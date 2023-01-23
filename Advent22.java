import java.util.*;
class Advent22{
	static boolean print = false;
	static boolean part2 = false;
	static HashMap<Character,Face> faces;
	public static void main(String[] args){
		ArrayList<String> directions = processLine();
//		printCube();
		Traveler traveler = new Traveler(0,0,0,'A');
		traveler = walk(directions,traveler);
// 		Part I:		162186
		int[] coords = totUp(traveler.coords(),traveler.face());
		println("Part I:\t\t" + (((coords[1] + 1) * 1000) + ((coords[0] + 1) * 4) + traveler.direction()));

		part2 = true;
		for(Face f : faces.values()) f.transform();
		traveler = new Traveler(0,0,0,0,Facing.E,'A');
		traveler = walk(directions,traveler);
		coords = traveler.coords();
		char face = traveler.face();
		int x,y,z;
		if(face == 'A' || face == 'C' || face == 'E'){
			x = coords[0];
		}else{
			x = coords[2];
		}
		if(face == 'A' || face == 'B'){
			y = coords[1];
		}else if(face == 'D' || face == 'E'){
			y = 49 - coords[1];
		}else if(face == 'F'){
			y = coords[0];
		}else{
			y = coords[2];
		}
		coords = totUp(new int[]{ x,y },traveler.face());
		println("Part II:\t" + (((coords[1] + 1) * 1000) + ((coords[0] + 1) * 4) + traveler.direction()));
// 		printCube(traveler);
	}

	static int[] totUp(int[] coords, char face){
		int x = coords[0], y = coords[1];
		if(face == 'A' || face == 'C' || face == 'E') x += 50;
		if(face == 'B') x += 100;
		if(face == 'C') y += 50;
		if(face == 'D' || face == 'E') y += 100;
		if(face == 'F') y += 150;
		return new int[]{ x,y };
	}

	static Traveler walk(ArrayList<String> directions, Traveler traveler){
		for(int a = 0; a < directions.size(); a++){
			if(a % 2 == 0){
				int steps = Integer.parseInt(directions.get(a));
				if(part2){
 					if(print)print("Attempting to walk " + steps + " steps to (");
					int[] changes = new int[]{ 0,0,0 };
					int change = steps;
					Facing facing = traveler.facing();
					if(facing == Facing.N || facing == Facing.W || facing == Facing.M) change = -steps;
					if(facing == Facing.W || facing == Facing.E){
						changes[0] = change;
					}else if(facing == Facing.N || facing == Facing.S){
						changes[1] = change;
					}else{
						changes[2] = change;
					}
 					if(print)println((traveler.coords()[0] + changes[0] + 1) + "," + (traveler.coords()[1] + changes[1] + 1) + "," + (traveler.coords()[2] + changes[2] + 1) + ")");
					traveler = movingPt2(steps,traveler);
				}else{
					traveler = moving(steps,traveler);
				}
			}else{
				traveler.turn(directions.get(a));
			}
		}
		return traveler;
	}

	static void addMarker(char[][] map, int x, int y, int facing){
		if(map[y][x] != '#'){
			char step = '>';
			if(facing == 1){
				step = 'V';
			}else if(facing == 2){
				step = '<';
			}else if(facing == 3){
				step = '^';
			}
			map[y][x] = step;
		}
	}

	static Traveler moving(int steps, Traveler traveler){
		char[][] map = faces.get(traveler.face()).face();
		for(int i = 0; i < steps; i++){
			int x = traveler.coords()[0], y = traveler.coords()[1], facing = traveler.direction(), change = 1;
			if(facing > 1) change = -1;
			if(facing % 2 == 0){
				x += change;
			}else{
				y += change;
			}
			if(x >= 0 && x < map[0].length && y >= 0 && y < map.length){
				if(map[y][x] != '#'){
					addMarker(map,traveler.coords()[0],traveler.coords()[1],facing);
					traveler.walk();
				}else{
					return traveler;
				}
			}else{
				traveler = transition(traveler);
				map = faces.get(traveler.face()).face();
			}
		}
// 		printMiniMap(traveler.face(),traveler.coords()[0],traveler.coords()[1]);
		return traveler;
	}

	static void addMarker(char[][][] map, int x, int y, int z, Facing facing, char face){
		if(map[z][y][x] != '#'){
			char step = '>';
			if((face != 'E' && facing == Facing.S) || (face == 'E' && facing == Facing.N) || (face == 'C' && facing == Facing.L) || (face == 'F' && facing == Facing.M)){
				step = 'V';
			}else if(facing == Facing.W || (face == 'B' && facing == Facing.M) || (face == 'D' && facing == Facing.L)){
				step = '<';
			}else if((face != 'E' && facing == Facing.N) || (face == 'E' && facing == Facing.S) || (face == 'C' && facing == Facing.M) || (face == 'F' && facing == Facing.L)){
				step = '^';
			}
			map[z][y][x] = step;
		}
	}

	static Traveler movingPt2(int steps, Traveler t){
		char[][][] map = faces.get(t.face()).cube();
		for(int i = 0; i < steps; i++){
			int x = t.coords()[0], y = t.coords()[1], z = t.coords()[2], change = 1;
			Facing facing = t.facing();
			if(facing == Facing.N || facing == Facing.W || facing == Facing.M) change = -1;
			if(facing == Facing.W || facing == Facing.E){
				x += change;
			}else if(facing == Facing.N || facing == Facing.S){
				y += change;
			}else{
				z += change;
			}
			if(x >= 0 && y >= 0 && z >= 0 && x < map[0][0].length && y < map[0].length && z < map.length){
				if(map[z][y][x] != '#'){
					addMarker(map,t.coords()[0],t.coords()[1],t.coords()[2],facing,t.face());
					t.walk();
				}else{
					if(print){println("I'm blocked by a rock! I'm rock-blocked! Face " + t.face() + ", (" + (t.coords()[0] + 1) + "," + (t.coords()[1] + 1) + "," + (t.coords()[2] + 1) + ")");
					char face = t.face();
					int ex,why;
					if(face == 'A' || face == 'E' || face == 'C' || face == 'F'){ ex = t.coords()[0]; }
					else{ ex = t.coords()[2]; } if(face == 'D'){ ex = 49 - ex; }
					if(face == 'A' || face == 'E' || face == 'D' || face == 'B'){ why = t.coords()[1]; }
					else{ why = t.coords()[2]; }// if(face == 'F'){ why = 49 - why; }
					printMiniMap(t.face(),ex,why);}
					return t;
				}
			}else{
				Traveler newTraveler = transition(t);
				char[][][] newMap = faces.get(newTraveler.face()).cube();
				if(newMap[newTraveler.coords()[2]][newTraveler.coords()[1]][newTraveler.coords()[0]] != '#'){
 					if(print)println("Stepping from Face " + t.face() + " to " + newTraveler.face() + ", facing " + newTraveler.facing().valueOf());
					addMarker(map,t.coords()[0],t.coords()[1],t.coords()[2],facing,t.face());
					t = newTraveler;
					map = newMap;
					if(print)if(t.face() == 'F') faces.get(t.face()).print();
				}else{
					if(print){println("I'm blocked by a rock! I'm rock-blocked! Face " + t.face() + ", (" + (t.coords()[0] + 1) + "," + (t.coords()[1] + 1) + "," + (t.coords()[2] + 1) + ")");
					char face = t.face();
					int ex,why;
					if(face == 'A' || face == 'E' || face == 'C' || face == 'F'){ ex = t.coords()[0]; }
					else{ ex = t.coords()[2]; } if(face == 'D'){ ex = 49 - ex; }
					if(face == 'A' || face == 'E' || face == 'D' || face == 'B'){ why = t.coords()[1]; }
					else{ why = t.coords()[2]; } //if(face == 'F'){ why = 49 - why; }
					printMiniMap(t.face(),ex,why);}
					return t;
				}
			}
		}
		if(print){char face = t.face();
		int ex,why;
		if(face == 'A' || face == 'E' || face == 'C' || face == 'F'){ ex = t.coords()[0]; }
		else{ ex = t.coords()[2]; } if(face == 'D'){ ex = 49 - ex; }
		if(face == 'A' || face == 'E' || face == 'D' || face == 'B'){ why = t.coords()[1]; }
		else{ why = t.coords()[2]; } //if(face == 'F'){ why = 49 - why; }
		printMiniMap(t.face(),ex,why);}
		return t;
	}

	static Traveler transition(Traveler t){
		if(part2) return transitionPt2(t);
		char[][] map = faces.get(t.face()).face();
		iTransition transition = new TwoDimensionalTransition(0,0,map[0].length - 1,map.length - 1);
		Traveler newTraveler = transition.apply(t);
		map = faces.get(newTraveler.face()).face();
		if(map[newTraveler.coords()[1]][newTraveler.coords()[0]] != '#'){ addMarker(map,t.coords()[0],t.coords()[1],t.direction()); return newTraveler; }
		return t;
	}

	static Traveler transitionPt2(Traveler t){
		char[][][] map = faces.get(t.face()).cube();
		iTransition transition = new ThreeDimensionalTransition(0,0,0,map[0][0].length - 1,map[0].length - 1,map.length - 1);
		return transition.apply(t);
	}

	static void printCube(Traveler t){
		int j = 0;
		print("┏");
		for(int X = 0; X < 152; X++) if(X == 50 || X == 101){ print("┳"); }else{ print("━"); }
		println("┓");
		char safe = 'A';
		for(int a = 0; a < 4; a++){
			Face[] threeFaces = new Face[3];
			for(int b = 0; b < 3; b++){
				char name;
				if((a == 0 && b == 0) || (a == 1 && (b == 0 || b == 2)) || (a == 2 && b == 2) || (a == 3 && (b == 1 || b == 2))){
					name = (char)32;
				}else{
					name = safe;
					safe++;
				}
				threeFaces[b] = faces.get(name);
			}
			for(int y = 0; y < 50; y++){
				for(int x = 0; x < 150; x++){
					if(x % 50 == 0) print("┃");
					Face f = threeFaces[x / 50];
					if(f == null){
						print(" ");
					}else{
						if(!t.pt2()){
							if(t.face() == f.name() && y == t.coords()[1] && x % 50 == t.coords()[0]){
								print("X");
							}else{
								print(f.face()[y][x % 50]);
							}
						}else{
							boolean here = t.face() == f.name();
							if(f.name() == 'A'){
								if(here && y == t.coords()[1] && x % 50 == t.coords()[0]){
									print("X");
								}else{
									print(f.cube()[0][y][x % 50]);
								}
							}else if(f.name() == 'B'){
								if(here && y == t.coords()[1] && x % 50 == t.coords()[2]){
									print("X");
								}else{
									print(f.cube()[x % 50][y][49]);
								}
							}else if(f.name() == 'C'){
								if(here && y == t.coords()[2] && x % 50 == t.coords()[0]){
									print("X");
								}else{
									print(f.cube()[x % 50][49][y]);
								}
							}else if(f.name() == 'D'){
								if(here && y == 49 - t.coords()[1] && x % 50 == t.coords()[2]){
									print("X");
								}else{
									print(f.cube()[x % 50][49 - y][0]);
								}
							}else if(f.name() == 'E'){
								if(here && y == 49 - t.coords()[1] && x % 50 == t.coords()[0]){
									print("X");
								}else{
									print(f.cube()[49][49 - y][x % 50]);
								}
							}else{
								if(here && y == t.coords()[0] && x % 50 == t.coords()[2]){
									print("X");
								}else{
									print(f.cube()[x % 50][0][y]);
								}
							}
						}
					}
				}
				println("┃");
			}
			if(a == 3){
				print("┗");
			}else{
				print("┣");
			}
			for(int x = 0; x < 150; x++){
				if(x % 50 == 0 && x != 0){
					if(a == 3){
						print("┻");
					}else{
						print("╋");
					}
				}
				print("━");
			}
			if(a == 3){
				println("┛");
			}else{
				println("┫");
			}
		}
	}

	static ArrayList<String> breakUp(String directions){
		ArrayList<String> segments = new ArrayList<>();
		String segment = "";
		while(directions.length() > 0){
			char next = directions.charAt(0);
			directions = directions.substring(1);
			int tried = -1;
			try{
				tried = Integer.parseInt(Character.toString(next));
			}catch(Exception e){}
			if(tried != -1){
				segment += next;
			}else{
				segments.add(segment);
				segments.add(Character.toString(next));
				segment = "";
			}
		}
		if(segment.length() > 0) segments.add(segment);
		return segments;
	}

	public static void printMap(char[][] map){
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map[y].length; x++){
				print(map[y][x]);
			}println();
		}println();
	}

	public static void printMap(Face f){
		char[][] map = f.face();
		if(map == null) map = print3DMap(f);
		printMap(map);
	}

	static void printMiniMap(char face, int x, int y){
		char[][] map = faces.get(face).face();
		if(map == null) map = print3DMap(faces.get(face));
		println("\t  " + face + "\n\t(" + (x + 1) + "," + (y + 1) + ")");
		for(int Y = y - 10; Y < y + 10; Y++){
			if(Y < 0 || Y >= map.length){ println(); continue; }
			for(int X = x - 10; X < x + 10; X++){
				if(X < 0 || X >= map[0].length){ print(" "); continue; }
				if(y == Y && X == x){ print('X'); }else{ print(map[Y][X]); }
			}println();
		}
	}

	public static char[][] print3DMap(Face f){
		char[][][] map = f.cube();
		char[][] printable = new char[50][50];
		if(f.name() == 'A'){
			printable = map[0];
		}else if(f.name() == 'B'){
			for(int y = 0; y < map[0].length; y++) for(int x = 0; x < map.length; x++) printable[y][x] = map[x][y][map[0][0].length - 1];
		}else if(f.name() == 'C'){
			for(int y = 0; y < map.length; y++) for(int x = 0; x < map[0][0].length; x++) printable[y][x] = map[y][map[0].length - 1][x];
		}else if(f.name() == 'D'){
			for(int y = 0; y < map[0].length; y++) for(int x = 0; x < map.length; x++/* map.length - 1; x >= 0; x-- */) printable[y][49 - x] = map[x][y][0];
		}else if(f.name()== 'E'){
			printable = map[map.length - 1];
		}else{
			for(int y = map.length - 1; y >= 0; y--) for(int x = 0; x < map[0][0].length; x++) printable[y][x] = map[y][0][x];
		}
		return printable;
	}

	static ArrayList<String> processLine(){
		ArrayList<String> raw = readIn.stringList("input.txt");
		faces = new HashMap<>();
		ArrayList<Face> f = new ArrayList<>();
		String directions = raw.remove(raw.size() - 1);
		int width = 0,height = raw.size() / 4;
		for(String r : raw) width = Math.max(width,r.length());
// 		println("(" + (width / 3) + "," + height + ")");
		int x = 0, y = 0;
		for(int i = 0; i < 12; i++){
			if(x >= width){
				x = 0;
				y += height;
			}
			char[][] minimap = new char[width / 3][height];
			for(int Y = y; Y < (y + height); Y++){
				for(int X = x; X < (x + (width / 3)); X++){
					if(X >= raw.get(Y).length()){
						minimap[Y - y][X - x] = ' ';
					}else{
						minimap[Y - y][X - x] = raw.get(Y).charAt(X);
					}
				}
			}
			f.add(new Face(minimap));
			x += 50;
		}
		for(int i = 0; i < f.size(); i++){
			if(f.get(i).face()[0][0] == ' '){
				f.remove(i);
				i--;
			}
		}
		for(char i = 'A'; i < 'G'; i++){
			Face face = f.get(i - 65);
			face.name(i);
			faces.put(i, face);
		}
		return breakUp(directions);
	}

	public static HashMap<Character,Face> faces(){ return faces; }
	public static void print(Object val){ System.out.print(val); }
	public static void println(Object val){ System.out.println(val); }
	public static void println(){ System.out.println(); }
}
