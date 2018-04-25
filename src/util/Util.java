package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author mingC
 * @date 2018/4/3
 */
public class Util {

	public static String getDateTimePretty() {
		java.util.Date date = Calendar.getInstance().getTime();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(date);
	}

	public static String getYear() {
		Calendar calendar = Calendar.getInstance();
		return "" + calendar.get(Calendar.YEAR);
	}

	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		return "" + month;
	}

	public static String getDay() {
		Calendar calendar = Calendar.getInstance();
		return "" + calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String getRandomId() {
		Date date = Calendar.getInstance().getTime();
		String string = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
		//Random random = new Random(System.currentTimeMillis());
		//string += random.nextInt(10000);
		return string;
	}

	public static void main(String[] args) {

	}

	public class ListNode {
		int val;
		ListNode next = null;

		ListNode(int val) {
			this.val = val;
		}
	}
	public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		ArrayList<Integer> list = new ArrayList();
		addNextItem(list, listNode);
		return list;
	}

	public static void addNextItem(ArrayList list, ListNode listNode) {
		//递归打印后一个结点
		if (listNode.next != null) {
			addNextItem(list, listNode.next);
		}
		list.add(listNode.val);
	}
}
