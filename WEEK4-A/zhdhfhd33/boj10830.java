import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Unit {
	final long num;// 제곱에 대한 정보
	int arr[][];

	// shallow copy
	public Unit(long num, int[][] arr) {
		this.num = num;
		this.arr = arr;
	}

	@Override
	public String toString() {
		return "[num=" + num + ", arr=" + Arrays.toString(arr) + "]";
	}

}

public class Main {

	public static int[][] twoDArrayCopy(int arr[][]) {
		int[][] res = new int[arr.length][arr[0].length];
		// copy
		for (int i = 0; i < arr.length; ++i) {
			System.arraycopy(arr[i], 0, res[i], 0, arr[0].length);
		}
		return res;
	}

	// B보다 작은 2의 제곱수 반환. 생각보다 간단하게 구현되지는 않는다.
	public static long findMaxSquare(long B) {
		long a = 1;
		while (a * 2 < B) {
			a *= 2;
		}
		if (a * 2 == B) {
			return a * 2;
		}
		return a;
	}

	public static void printArr(int arr[][]) {
		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr.length; ++j) {
				System.out.printf("%d ", arr[i][j]);
			}
			System.out.println();
		}
	}

	// 복사해서 새로운 배열 반환
	public static int[][] multiply(int arr1[][], int arr2[][]) {

		int[][] res = new int[arr1.length][arr2[0].length];

		// square
		for (int i = 0; i < arr1.length; ++i) {
			for (int j = 0; j < arr2[0].length; ++j) {
				for (int k = 0; k < arr1[0].length; ++k) {
					res[i][j] += (arr1[i][k] * arr2[k][j]);
				}
				res[i][j] %= 1000;
			}
		}

		return res;
	}

	// solve가 return값이 없이 res를 반환하려 하면 올바르게 동작하지 않는다. java에서 매개변수는 포인터임.
	// res에 새로운 값을 주면 기대하는대로 동작하지 않는다. 역참조연산자(*)가 없어서 그럼.
	// 재귀로 구현할 수 있음.
	public static int[][] solve(int arr[][], long B, List<Unit> li, int res[][]) {
		// 이미 행렬이 li에 저장되어 있을 때
		if (B <= li.get(li.size() - 1).num) {
			// li에 있는지 찾는다.
			for (int i = 0; i < li.size(); ++i) {
				Unit u = li.get(i);
				if (u.num == B) {// 정답을 반환. 
					return res = multiply(res, u.arr);
				}

				else if (u.num*2 > B) {
					res = multiply(res, u.arr);
					
					return solve(arr, B - u.num, li, res); //return을 안붙여서 고생함.
				}
			}
		}

		while (true) {
			Unit a = li.get(li.size() - 1);
			if (a.num==B) {//이 부분이 없어서 고생함.
				return res;
			}
			else if (a.num * 2 > B) {
				return solve(arr, B - a.num, li, res);// B대신 diff를 넣음.

				// 재귀형식으로 이뤄져 있고 여기서 재귀가 호출되기 때문에 return을 해줘야 함수가 끝난다.
			}
			int newArr[][] = multiply(a.arr, a.arr);
			res = newArr;
			li.add(new Unit(a.num * 2, newArr));
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N;
		long B;
		N = sc.nextInt();
		B = sc.nextLong();
		int arr[][] = new int[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; j++) {
				arr[i][j] = sc.nextInt();
			}
		}


		final List<Unit> li = new ArrayList<>();
		li.add(new Unit(1, arr)); // li에는 1승 부터 들어간다.
		// 답을 저장할 공간.
		
		int res[][] = new int[N][N];
		res = solve(arr, B, li, res);
		printArr(res);

	}

}
