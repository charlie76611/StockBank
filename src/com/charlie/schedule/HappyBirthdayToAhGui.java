package com.charlie.schedule;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2015年10月9日</td>
 * <td>這是要祝阿貴生日快樂臨時寫的</td>
 * <td> by 槍被警長拔掉的副警長</td>
 * </tr>
 * </table> 
 *
 * @author 槍被警長拔掉的副警長
 *
 */
public class HappyBirthdayToAhGui {

	public static void main(String[] abcd) {
		int count = 0;
		for(int i = 1; i<=9; i++) {
			for (int j=1; j<=9; j++) {
				System.out.println("阿貴生日快樂！");
				count++;
			}
		}
		System.out.println("其他人都太弱了，我總共說了" + count + "次生日快樂！我贏惹");
	}
}
