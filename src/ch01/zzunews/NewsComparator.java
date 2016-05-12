package ch01.zzunews;

import java.util.Comparator;

public class NewsComparator implements Comparator {

	// 排序开关，大到小和小到大切换, true 表示最新日期在前
	boolean orderSwitch = true;

	public void ResetSwitch() {
		orderSwitch = true;
	}

	public String SortTip() {
		if (orderSwitch)
			return "最新日期的新闻排在前";
		else
			return "最旧日期的新闻排在前";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compare(Object lhs, Object rhs) {

		News news1 = (News) lhs;
		News news2 = (News) rhs;

		String date1 = news1.getDate();
		String date2 = news2.getDate();
		if (orderSwitch) // 日期从大到小排序，最新日期在前
			return date2.compareTo(date1);
		else // 日期从小到大排序，旧新闻在前
			return date1.compareTo(date2);

	}

	public void Switch() {
		orderSwitch = !orderSwitch;
	}

}
