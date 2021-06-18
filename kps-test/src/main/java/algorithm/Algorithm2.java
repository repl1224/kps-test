package algorithm;

public class Algorithm2 {
	public static void main(String[] args) {
		
//		int x = 3;
//		int y = 4;
//		String param = "3,1,1,2,5,1,1,5,5,2,1,1";
		int x = 7;
		int y = 5;
		String param = "1,0,0,0,0,0,1,2,0,0,0,0,3,0,2,0,9,0,0,0,0,0,3,0,5,4,0,0,1,0,2,3,0,0,6";
		
		int[][] matrix = setMatrix(x, y, param);

		List<Integer> pathSumList = new LinkedList<Integer>();
		List<String> xyList = new LinkedList<String>();
		
		for (int i = 0; i < x; i++)
        {
			matrixTraversalTopDown(matrix, matrix[0][0], "0,0", 0, i, pathSumList, xyList);
        }

		int maxSumListIndex = 0;
		int maxSum = 0;
		for(int j = 0; j < pathSumList.size(); j++) {
			if(pathSumList.get(j) > maxSum) {
				maxSum = pathSumList.get(j);
				maxSumListIndex = j;
			}
		}

		String[] maxXyArray = xyList.get(maxSumListIndex).split("/");
		for(String xy : maxXyArray) {
			matrix[Integer.parseInt(xy.split(",")[1])][Integer.parseInt(xy.split(",")[0])] = 0;
		}

		pathSumList.clear();
		xyList.clear();
		
		for (int i = 0; i < x; i++)
        {
			matrixTraversalTopDown(matrix, matrix[0][x - 1], "0," + (x - 1), 0, i, pathSumList, xyList);
        }
		
		int maxSumListIndex2 = 0;
		int maxSum2 = 0;
		
		for(int j = 0; j < pathSumList.size(); j++) {
			if(pathSumList.get(j) > maxSum2) {
				maxSum2 = pathSumList.get(j);
				maxSumListIndex2 = j;
			}
		}
		
		System.out.println("Max Point : " + (maxSum + maxSum2));
	}
	
	public static int[][] setMatrix(int x, int y, String mapData) {
		int[][] map = new int[y][x];
		
		String[] mapDatas = mapData.split(",");
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				map[i][j] = Integer.parseInt(mapDatas[x * i + j]) ;
			}
		}
		
		return map;
	}
    
    public static void matrixTraversalTopDown(int[][] map, int pathSum, String path, int row, int col, List<Integer> pathSumList, List<String> xyList) {
    	
    	if (row == map.length - 1)
        {
            //경로의 숫자합 리스트에 추가
            pathSumList.add(pathSum);
            
            //경로의 좌표값 리스트에 추가 
            xyList.add(path);

            return;
        }
        //왼쪽 아래
        if (row + 1 < map.length && col - 1 >= 0)
        {
        	matrixTraversalTopDown(map, pathSum + map[row + 1][col - 1], path + "/" + (col - 1) + "," + (row + 1), row + 1, col - 1, pathSumList, xyList);
        }
        //아래
        if (row + 1 < map.length)
        {
        	matrixTraversalTopDown(map, pathSum + map[row + 1][col], path + "/" + col + "," + (row + 1), row + 1, col, pathSumList, xyList);
        }
        //오른쪽 아래
        if (row + 1 < map.length && col + 1 < map[0].length)
        {
        	matrixTraversalTopDown(map, pathSum + map[row + 1][col + 1], path + "/" + (col + 1) + "," + (row + 1), row + 1, col + 1, pathSumList, xyList);
        }
    }
}
