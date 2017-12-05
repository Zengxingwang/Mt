package chats;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RunMethod {
    static String username;
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("欢迎加入群聊，请输入您的昵称：");
        Scanner sc = new Scanner(System.in);
        username=sc.nextLine();
        Customer.customer();
        Producer.producer();

    }

}
