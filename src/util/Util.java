package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	public static String fileSizeConver(long size) {
		//如果字节数少于1024，则直接以B为单位
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		//如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			//因为如果以MB为单位的话，要保留最后1位小数，
			//因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			//否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
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
