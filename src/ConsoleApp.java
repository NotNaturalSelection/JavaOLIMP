import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleApp {
    private static String text = "";
    private static String openedFile = "";
    private static boolean fileIsOpened = false;
    private static String partOfOpenedFile = "";
    private static String path = "";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        path = "C:\\Users\\Александр\\Desktop\\Database\\ITMO\\Programming\\Java_Olimp_test\\";
        showPath();
        String line = scanner.nextLine();
        File directory = new File(path);
        String[] list;
        while(!(line.equals("quit")||line.equals("exit")||line.equals("q"))){
            String[] data = line.split(" ");
            switch(data[0]){
                case "list":
                    directory = new File(path);
                    list = directory.list();
                    try {
                        for (String filename : list) {
                            File f = new File(path + filename);
                            String fileFlag;
                            String hiddenFlag;
                            String executeFlag = "-";
                            String writeFlag = "-";
                            String readFlag = "-";
                            if (f.canExecute()) {
                                executeFlag = "x";
                            }
                            if (f.canRead()) {
                                readFlag = "r";
                            }
                            if (f.canWrite()) {
                                writeFlag = "w";
                            }
                            long value;
                            if (f.isFile()) {
                                fileFlag = "file";
                            } else {
                                try {
                                    if (f.listFiles().length > 0) {
                                        fileFlag = "+dir";//если директория не пустая
                                    } else {
                                        fileFlag = "-dir";//если пустая
                                    }
                                } catch (NullPointerException e){
                                    fileFlag = "-dir";
                                }
                            }
                            if (f.isHidden()) {
                                hiddenFlag = "hidden";
                            } else {
                                hiddenFlag = "displayed";
                            }
                            if (f.length() / 1024 < 1 && f.length() > 0) {
                                value = 1;
                            } else {
                                value =  f.length() / 1024;
                            }
                            System.out.printf("%s %-30s %dKB  %s%s%s  %s\n", fileFlag, f.getName(), value, readFlag, writeFlag, executeFlag, hiddenFlag);
                        }
                    } catch (NullPointerException e){
                        System.out.println("Directory is empty");
                    }
                    break;
                case "ls":
                    directory = new File(path);
                    list = directory.list();
                    try {
                        for (String f:list) {
                            System.out.println(f);
                        }
                    } catch (NullPointerException e){
                        System.out.println("Directory is empty");
                    }
                    break;
                /*case "chmod":
                    try {
                        File f = new File(path + data[2]);
                        f.setExecutable(false);
                        f.setReadable(false);
                        f.setWritable(false);
                        if (data[1].contains("r")) {
                            f.setReadable(true);
                        }
                        if (data[1].contains("w")) {
                            f.setWritable(true);
                        }
                        if (data[1].contains("x")) {
                            f.setExecutable(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break; */
                                                                                             //Закоменчен из-за неработоспособности по неизвестной причине
                case "open":
                    try {//Проверка на ошибку
                        if (!fileIsOpened) {//Проверка на открытый файл
                            if (Arrays.toString(directory.list()).contains(data[1])) {//Проверка файла на существование
                                try {//Проверка введенного имени файла
                                    if (new File(path + data[1] + "\\").isFile()) {
                                        partOfOpenedFile = data[1];
                                        openedFile = path + data[1] + "\\";
                                        text = FileUtils.readFile(openedFile);
                                        fileIsOpened = true;
                                        if (text != null) {
                                            System.out.println("File " + data[1] + " is opened");
                                        } else {
                                            System.out.println("File does not exist");
                                        }
                                    } else {
                                        path = directory.toString()+"\\" + data[1] + "\\";
                                        showPath();
                                    }
                                } catch (NullPointerException e) {
                                    System.out.println("Choose file first");
                                }
                            } else {
                                System.out.println("File does not exist");
                            }
                        } else {
                            System.out.println("Close the previous file first");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "print":
                    try {
                        text = line.substring(data[0].length() + 1);
                        FileUtils.appendFile(openedFile, text);
                    } catch (StringIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "println":
                    try {
                        text = line.substring(data[0].length() + 1);
                        FileUtils.appendFile(openedFile, text+"\n");
                    } catch (StringIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "read":
                    try {
                        if(fileIsOpened) {
                            System.out.println(FileUtils.readFile(openedFile));
                        } else {
                            System.out.println("Open the file first");
                        }
                    } catch (NullPointerException e){
                        System.out.println("File is empty");
                    }
                    break;
                case "touch":
                    try {
                        FileUtils.createFile(data[1]);
                        System.out.println("File " + data[1] + " was created");
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "back":
                    String[] s = path.split("\\\\");
                    path = "";
                    if(s.length > 2) {
                        for (int i = 0; i < s.length - 1; i++) {
                            path += s[i] + "\\";
                        }
                        showPath();
                    } else {
                        path = "C:\\";
                        System.out.println("The root directory \"C:\\\" is reached");
                        showPath();
                    }
                    break;
                case "close":
                    closeFile(partOfOpenedFile);
                    break;
                case "info":
                    showPath();
                    if(fileIsOpened){
                        System.out.println("Active file: " + partOfOpenedFile);
                    } else {
                        System.out.println("No active file");
                    }
                    break;
                case "delete":
                    try {
                        File f = new File(path + data[1]);
                        if (f.isFile()) {
                            f.delete();
                            if (f.exists()) {
                                System.out.println("Permission denied");
                            } else {
                                System.out.println("The file was deleted");
                            }
                        } else {
                            f.delete();
                            if (f.exists()) {
                                System.out.println("Permission denied");
                            } else {
                                System.out.println("The directory was deleted");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "mkdir":
                    try {
                        File f1 = new File(path + "\\" + data[1]);

                        if (f1.mkdir()) {
                            System.out.println("The directory was created successfully");
                        } else {
                            System.out.println("Permission denied");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "rename":
                    try {
                        File f2 = new File(path + "\\" + data[1]);
                        String newName;
                        try {
                            newName = data[2];
                            File newNameFile = new File(path + "\\" + newName);
                            f2.renameTo(newNameFile);
                            System.out.println("File was renamed");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Incorrect command format");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "help":
                    System.out.println("List of available commands:\n" +
                            "open <file or directory name> - open file or directory(if file does not exist, 'open' creates new file\n" +
                            "back - goes to the directory above the level\n" +
                            "close - to close the current file\n" +
                            "ls - to show the contents of current directory\n" +
                            "list - to show the contents of current directory with some information\n" +
                            //"chmod <mode> <filename> - to change access to the file\n" +
                            "touch <filename> - creates file in current directory\n" +
                            "print <data> - to write data into opened file\n" +
                            "println <data> - to write data into opened file with line break\n" +
                            "delete <file or directory name> - to delete the file or directory from current directory\n" +
                            "info - to show the current opened file and directory" +
                            "read - to show the data from the opened file\n" +
                            "touch <filename> - to create an empty file\n" +
                            "mkdir <directory name> - to create an emty directory\n" +
                            "rename <file or directory name> <new name> - to rename file or directory\n" +
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
        System.out.println("File "+partOfOpenedFile+" is closed now");
        partOfOpenedFile = "";
    }

    static  private void showPath(){
        System.out.println("You're in \'"+ path + "\' now");
    }
}
