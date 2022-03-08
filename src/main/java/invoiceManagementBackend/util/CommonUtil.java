package invoiceManagementBackend.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

//    public String numsToThaiWord(double number) {
//        String numberToString = String.valueOf(number);
//        String thaiWord = "";
//        if (numberToString.length() == 1) {
//            thaiWord = numToWord(numberToString);
//        } else if (numberToString.length() == 2) {
//            if (numberToString.charAt(0) == '1') {
//                thaiWord = "สิบ";
//            } else if (numberToString.charAt(0) == '2') {
//                thaiWord = "ยี่สิบ";
//            } else {
//                thaiWord = numToWord(numberToString).concat("สิบ");
//            }
//        }
//        return thaiWord.concat("บาทถ้วน");
//    }

    public String numToWord(String num) {
        String thaiWord = "";
        switch (num) {
            case "1":
                thaiWord = "หนึ่ง";
                break;
            case "2":
                thaiWord = "สอง";
                break;
            case "3":
                thaiWord = "สาม";
                break;
            case "4":
                thaiWord = "สี่";
                break;
            case "5":
                thaiWord = "ห้า";
                break;
            case "6":
                thaiWord = "หก";
                break;
            case "7":
                thaiWord = "เจ็ด";
                break;
            case "8":
                thaiWord = "แปด";
                break;
            case "9":
                thaiWord = "เก้า";
                break;
        }
        return thaiWord;
    }
}
