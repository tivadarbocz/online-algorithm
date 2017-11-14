package hu.tb.online;

import java.util.Map;

public class Util {
	public static int min(final int a, final int b, final int c) {
		return Math.min(a, Math.min(b, c));
	}

	public static int max(final int a, final int b, final int c) {
		return Math.max(a, Math.max(b, c));
	}

	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
}
