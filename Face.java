import java.util.*;
class Face{
	private char name;
	private char[][] map,backupMap;
	private char[][][] cube = null;

	public Face(char[][] m){
		map = m;
	}

	public void transform(){
		backupMap = map;
		cube = new char[50][50][50];
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map[0].length; x++){
				if(name == 'A'){ cube[0][y][x] = map[y][x] != '#' ? '.' : '#'; }else
				if(name == 'B'){ cube[x][y][cube.length - 1] = map[y][x] != '#' ? '.' : '#'; }else
				if(name == 'C'){ cube[y][cube[0].length - 1][x] = map[y][x] != '#' ? '.' : '#'; }else
				if(name == 'D'){ cube[x][(cube[0].length - 1) - y][0] = map[y][x] != '#' ? '.' : '#'; }else
				if(name == 'E'){ cube[cube.length - 1][(cube[0].length - 1) - y][x] = map[y][x] != '#' ? '.' : '#'; }
				else{ cube[x][0][y] = map[y][x] != '#' ? '.' : '#'; }
			}
		}
// 		D: (0,0) = (0,50,0); (50,0) = (0,50,50); (0,50) = (0,0,0); (50,50) = (0,0,50)
// 		X == 0;
// 		y = 50 - y
// 		z = x
		map = null;
	}

	public void print(){
		for(int Z = (name == 'E' ? 49 : 0); Z < (name == 'A' ? 1 : 50); Z++){
// 			Advent22.print("z = " + Z);
			for(int Y = (name == 'C' ? 49 : 0); Y < (name == 'F' ? 1 : 50); Y++){
// 				Advent22.print(", y = " + Y);
				for(int X = (name == 'B' ? 49 : 0); X < (name == 'D' ? 1 : 50); X++){
// 					Advent22.println(", x = " + X);
					Advent22.print(cube[Z][Y][X]);
				}
				if(name != 'D' && name != 'B') Advent22.println();
			}
			if(name != 'F' && name != 'C') Advent22.println();
		}
		if(name != 'A' && name != 'E') Advent22.println();
	}

	public void name(char n){ name = n; }
	public char name(){ return name; }

	public char[][] face(){ return map; }
	public char[][][] cube(){ return cube; };
}
