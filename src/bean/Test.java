package bean;

/**
 * @author mingC
 * @date 2018/3/24
 */
public class Test {
	public static void main(String[] args) {
		System.out.println("" + null);
	}

	private static int[] a = {0, 146527998, 205327308, 94243885, 138810487, 408218567, 77866117, 71548549, 563255818, 559010506, 449018203, 576200653, 307283021, 467607947, 314806739, 341420795, 341420795, 469998524, 417733494, 342206934, 392460324, 382290309, 185532945, 364788505, 210058699, 198137551, 360748557, 440064477, 319861317, 676258995, 389214123, 829768461, 534844356, 427514172, 864054312};
	private static int[] b = {13710, 46393, 49151, 36900, 59564, 35883, 3517, 52957, 1509, 61207, 63274, 27694, 20932, 37997, 22069, 8438, 33995, 53298, 16908, 30902, 64602, 64028, 29629, 26537, 12026, 31610, 48639, 19968, 45654, 51972, 64956, 45293, 64752, 37108};
	private static int[] c = {38129, 57355, 22538, 47767, 8940, 4975, 27050, 56102, 21796, 41174, 63445, 53454, 28762, 59215, 16407, 64340, 37644, 59896, 41276, 25896, 27501, 38944, 37039, 38213, 61842, 43497, 9221, 9879, 14436, 60468, 19926, 47198, 8406, 64666};
	private static int[] d = {0, -341994984, -370404060, -257581614, -494024809, -135267265, 54930974, -155841406, 540422378, -107286502, -128056922, 265261633, 275964257, 119059597, 202392013, 283676377, 126284124, -68971076, 261217574, 197555158, -12893337, -10293675, 93868075, 121661845, 167461231, 123220255, 221507, 258914772, 180963987, 107841171, 41609001, 276531381, 169983906, 276158562};

	public static boolean m(String paramString) {
		if (paramString.length() != b.length) {
			return false;
		}
		int[] arrayOfInt = new int[a.length];
		arrayOfInt[0] = 0;
		byte[] paramBytes = paramString.getBytes();
		int len = paramBytes.length;
		int index = 0;
		int arrayOfIntIndex = 1;
		while (index < len) {
			arrayOfInt[arrayOfIntIndex] = paramBytes[index]; //把1~34个字符赋值给arrOfInt，第0个字符是0
			arrayOfIntIndex += 1;
			index += 1;
		}
		index = 0;
		for (; ; ) {
			if (index >= c.length) {
				break;
			}
			if ((a[index] != b[index] * arrayOfInt[index] * arrayOfInt[index] + c[index] * arrayOfInt[index] + d[index]) ||
					(a[(index + 1)] != b[index] * arrayOfInt[(index + 1)] * arrayOfInt[(index + 1)] + c[index] * arrayOfInt[(index + 1)] + d[index])) {
				break;
			}
			index += 1;

		}

		return true;
	}
}


