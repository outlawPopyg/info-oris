public class CezarCod {
 
    public static String getCodingMessage(String message, int key) {
        StringBuilder strBox = new StringBuilder(message.length());
        char tmp;
        for (int i = 0; i < message.length(); i++) {
            tmp = message.charAt(i);
            if (Character.isLetter(message.charAt(i))) {
                tmp += key % 26;
                if (tmp > 'z')
                    tmp = (char)(tmp % 'z' + 'a');
                else if (tmp < 'a')
                    tmp = (char)(tmp + 25);
            }
            strBox.append(tmp);
        }
        return strBox.toString();
    }
 
    public static void main(String[] args) {
        String message = "hello all!";
        String codeMessage = getCodingMessage(message, 44);
        System.out.println(message + " -> " + codeMessage);
        String deCodeMessage = getCodingMessage(codeMessage, -44);
        System.out.println(codeMessage + " -> " + deCodeMessage);
    }
}