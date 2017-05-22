package utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil{
	public static String formatMoneyVietnam(double amount){
		NumberFormat nf = new DecimalFormat("#,###,###");
		String[] rs = nf.format(amount).split(",");
		String money = "";
		for(int i = 0; i < rs.length; i++){
			money += rs[i] + ".";
		}
		money = money.substring(0, money.length() - 1) + " VND";
		return money;
	}
}
