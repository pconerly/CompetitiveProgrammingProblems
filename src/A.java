import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class A {
	static String enc = "ejp mysljylc kd kxveddknmc re jsicpdrysirbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcdde kr kd eoya kw aej tysr re ujdr lkgc jv";
	static String dec = "our language is impossible to understandthere are twenty six factorial possibilitiesso it is okay if you want to just give up";
	public static void main(String[] args) throws Exception {
		HashMap<Character, Character> hm = new HashMap<Character, Character>();
		hm.put('z', 'q');
		hm.put('q', 'z');
		
		for (int i = 0; i < enc.length(); i++) {
			hm.put(enc.charAt(i), dec.charAt(i));
		}
		PrintWriter out = new PrintWriter(new File("A.out"));
		Scanner in = new Scanner(new File("A.in"));
		int T = in.nextInt();
		in.nextLine();
		for(int zz = 1; zz <= T; zz++) {
			String ans = "";
			String S = in.nextLine();
			for (int i = 0; i < S.length(); i++) {
				ans += hm.get(S.charAt(i));
			}
			out.format("Case #%d: %s\n", zz, ans);
		}
		out.close();
	}
}
