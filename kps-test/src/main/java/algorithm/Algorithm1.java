package algorithm;

public class Algorithm1 {
	public static void main(String[] args) {
		String test = "3[a4[c]a]2[ab]";
		
		System.out.println(replaceParentheses(test));
	}
	
	public static String replaceParentheses(String encStr) {
		System.out.println(encStr);
		//encStr = "3[a4[c]a]2[ab]";
		if(encStr.indexOf("[") == -1) {
			return encStr;
		}
		
		String temp = "";
		
		int depth = 0;
		boolean isTwoDepth = false;
		
		for(int i = 0; i < encStr.length(); i++) {
			if(encStr.charAt(i) == '[') {
				depth++;
			} else if(encStr.charAt(i) == ']') {
				depth--;
			}
			
			if(depth == 2) {
				isTwoDepth = true;
				break;
			}
		}
		
		String result = "";
		if(isTwoDepth) {
			int stack = 0;
			
			for(int i = 0; i < encStr.length(); i++) {
				if(encStr.charAt(i) == '[') {
					if(stack == 0 && (encStr.charAt(i) == '[' || encStr.charAt(i) == ']')) {
						temp += ",";
					} else {
						temp += encStr.charAt(i);
					}
					
					stack++;
				} else if(encStr.charAt(i) == ']') {
					stack--;
					
					if(stack == 0 && (encStr.charAt(i) == '[' || encStr.charAt(i) == ']')) {
						temp += ",";
					} else {
						temp += encStr.charAt(i);
					}
				} else {
					temp += encStr.charAt(i);
				}
				
				
				
			}
			System.out.println(temp);
			String[] tempArray = temp.split(",");
			
			int loop = 0;
			
			for(int j = 0; j < tempArray.length; j++) {
				
				if(isNumber(tempArray[j])) {
					loop = Integer.parseInt(tempArray[j]);
				} else {
					for(int k = 0; k < loop; k++) {
						result += tempArray[j];
					}
				}
			}
		} else {
			int loop = 0;
			
			for(int i = 0; i < encStr.length(); i++) {
				if(Character.isDigit(encStr.charAt(i))) {
					loop = Character.getNumericValue(encStr.charAt(i));
				} else {
					if(encStr.charAt(i) == '[' || encStr.charAt(i) == ']') {
						continue;
					} else {
						if(loop == 0) {
							result += encStr.charAt(i);
						} else {
							for(int j = 0; j < loop; j++) {
								result += encStr.charAt(i);
							}
							
							loop = 0;
						}
					}
				}
			}
		}
		
		
		//return result.indexOf("[") > - 1 ? replaceParentheses(result) : result;
		return result;
	}
	
	public static boolean isNumber(String str) {
		boolean isNum = true;
		
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				isNum = false;
				break;
			}
		}
		
		return isNum;
	}
}
