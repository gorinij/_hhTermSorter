package app;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gorjnich
 * @link
 */
public class Main {

    static boolean debugMode = true;

    static Map<String, Object> map = new HashMap<>();

    public static void main(String[] args) {
        V_swing mainView = new V_swing();
    }

    /**
     * ����������� ����� ������� ������ ������ �� �����
     *
     * @see System.out.println(String x);
     * @param msg
     */
    public static void echoLn(String msg) {
        System.out.println(msg);
    }

    /**
     * ����� ������ ������
     */
    public static void echoLn() {
        System.out.println("\n");
    }
}
