package algorithm;

public class Algorithm2 {
	public static void main(String[] args) {
		int result = maxResult(3, 4, "3,1,1,2,5,1,1,5,5,2,1,1");
		System.out.println(result);
	}
	
	public static int maxResult(int x, int y, String mapData) {
		int[][] map = new int[y][x];
		
		String[] mapDatas = mapData.split(",");
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				map[i][j] = Integer.parseInt(mapDatas[x * i + j]) ;
			}
		}
		
//		System.out.print(map[0][0]);
//		System.out.print(map[0][1]);
//		System.out.println(map[0][2]);
//		System.out.print(map[1][0]);
//		System.out.print(map[1][1]);
//		System.out.println(map[1][2]);
//		System.out.print(map[2][0]);
//		System.out.print(map[2][1]);
//		System.out.println(map[2][2]);
//		System.out.print(map[3][0]);
//		System.out.print(map[3][1]);
//		System.out.println(map[3][2]);
		int max = 0;
		
		//15
//		for (int j = 0; j < x; j++) {
//	        max = max3(max, max, MaximumPath(map, 0, j));
//	    }
		
		for (int j = x - 1; j == 0; j--) {
	        max = max3(max, max, MaximumPath(map, 0, j));
	    }
		
		return max;
	}
	
	public static int max3(int i, int j, int k) {
	    if (i >= j && i >= k) {
	        return i;
	    } else
	    if (j >= i && j >= k) {
	        return j;
	    } else {
	        return k;
	    }
	}
	
	public static int MaximumPath(int Mat[][], int i, int j) {
		int xSize = Mat.length;
		System.out.println("xSize : " + xSize);
		int ySize = Mat[0].length;
		System.out.println("ySize : " + ySize);
		// out of matrix boundaries
	    if (i >= xSize || j < 0 || j >= ySize)
	        return 0;
	    // compute the maximum path from the current cell
	    // for all possible directions:
	    return max3(MaximumPath(Mat, i + 1, j - 1), 
	                MaximumPath(Mat, i + 1, j), 
	                MaximumPath(Mat, i + 1, j + 1)) + Mat[i][j];
	}
}
