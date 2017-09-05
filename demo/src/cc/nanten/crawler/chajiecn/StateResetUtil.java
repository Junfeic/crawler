package cc.nanten.crawler.chajiecn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StateResetUtil {

	public static int getCrawledPage(String fileName) {
		String page = null;
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					fileName));
			Object o = in.readObject();

			page = o.toString(); // (String) (in.readObject());

			in.close();
		} catch (Exception x) {
			return 1;
		}
		return page == null ? 1 : Integer.valueOf(page);
	}

	public static void setCrawledPage(String fileName, int page) {
		try {

			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName));

			out.writeObject(page);

			out.close();
		} catch (Exception x) {
			System.out.println(x.toString());
		}
	}

	public static void main(String[] args) {
		setCrawledPage("crawledpages", 22);
		int i = getCrawledPage("crawledpages");
		System.out.println(i);
	}
}
