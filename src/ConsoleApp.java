import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args)throws IOException {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        String line = scanner.nextLine();
        while(!(line.equals("quit")||line.equals("exit")||line.equals("q"))){
            String[] data = line.split(" ");
            String text;
            switch(data[0]){
                case "print":
                    text = line.substring(data[0].length() + 1);
                    out.print(text);
                    break;
                case "println":
                    text = line.substring(data[0].length() + 1);
                    out.println(text);
                    break;
                case "read":
                    System.out.println(FileUtils.readFile(data[1]));
                    break;
                case "touch":
                    FileUtils.createFile(data[1]);
                    break;// TODO: 26.02.2019 сделать append в файл
                case "quit":
                case "exit":
                case "q":
                    break;
                case "help":
                    System.out.println("List of available commands:\nprint <data> - to write data to the file\n" +
                            "println <data> - to write data to the file with line break\n" +
                            "read <filename> - to show the data from the file\n" +
                            "touch <filename> - to create empty file\n" +
                            "help - to show this message again\n" +
                            "quit, exit,q - to close console application");
                    break;
                default:
                    System.out.println("Undefined command. Type \"help\" for a list of commands");
            }
            line = scanner.nextLine();
        }
        scanner.close();
        out.close();
    }
}
