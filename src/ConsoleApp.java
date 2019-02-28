import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleApp {
    static String text = "";
    static String openedFile = "";
    static boolean fileIsOpened = false;
    static String partOfOpenedFile = "";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        File directory = new File("C:\\Users\\Александр\\Desktop\\Database\\ITMO\\Programming\\Java_Olimp_test");
        String[] list;
        while(!(line.equals("quit")||line.equals("exit")||line.equals("q"))){
            String[] data = line.split(" ");
            switch(data[0]){
                case "list":
                    list = directory.list();
                    try {
                        for (String f:list) {
                            System.out.println(f);
                        }
                    } catch (NullPointerException e){
                        System.out.println("Directory is empty");
                    }
                    break;
                case "open":
                    if(!fileIsOpened) {
                        if (Arrays.toString(directory.list()).contains(data[1])) {
                            try {
                                partOfOpenedFile = data[1];
                                openedFile = directory.toString() + "\\" + data[1] + "\\";
                                text = FileUtils.readFile(openedFile);
                                fileIsOpened = true;
                                if (text != null) {
                                    System.out.println("File " + data[1] + " is opened");
                                } else {
                                    System.out.println("File does not exist");
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Choose file");
                            }
                        } else {
                            FileUtils.createFile(data[1]);
                            System.out.println("File " + data[1] + " is created");
                        }
                    } else {
                        System.out.println("Close the previous file first");
                    }
                    break;
                case "print":
                    text += line.substring(data[0].length() + 1);
                    FileUtils.appendFile(openedFile,text);
                    break;
                case "println":
                    text += line.substring(data[0].length() + 1);
                    FileUtils.appendFile(openedFile, text +"\n");
                    break;
                case "showFile":
                    try {
                        System.out.println(text);
                    } catch (NullPointerException e){
                        System.out.println("File is empty");
                    }
                    break;
                case "touch":
                    FileUtils.createFile(data[1]);
                    break;
                case "close":
                    closeFile(partOfOpenedFile);
                    break;
                case "info":
                    if(fileIsOpened){
                        System.out.println("Active file: " + partOfOpenedFile);
                    } else {
                        System.out.println("No active file");
                    }
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
    }

    static private void closeFile(String filename){
        text = "";
        fileIsOpened = false;
        openedFile = openedFile.replace(filename, "");
        partOfOpenedFile = "";
    }
}
