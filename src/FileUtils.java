import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class FileUtils {
    /**
     * Данная функция создает файл с указанным именем
     * @param fileName имя файла, который необходимо создать
     * @return true, если файл создан успешно, false если не создан
     */
    public static boolean createFile(String fileName) {
        Objects.requireNonNull(fileName, "qwe");
        File file = new File(fileName);
        boolean FileWasCreated;
        if (file.exists()) {
            FileWasCreated = true;
        } else {
            try {
                FileWasCreated = file.createNewFile() ;
            } catch (IOException ex) {
                ex.printStackTrace();
                FileWasCreated = false;
            }
        }
        return  FileWasCreated;
    }

    /**
     * Данная функция создает файлы с именами, которые были переданы в массиве
     * @param fileNames массив строк, содержащий имена файлов, которые необходимо создать
     * @return true, если файлы создан успешно, false если не создан хотя бы 1 из них
     */
    public static boolean createFiles(String[] fileNames) {
        boolean allFilesCreated = true;
        for(String fileName : fileNames){
            boolean success = createFile(fileName);
            if(!success){
                allFilesCreated = false;
            }
        }
        return allFilesCreated;
    }

    /**
     * Данная функция возвращает содержимое файла c переданным именем
     * @param fileName полный или частичный путь к файлу, из которого будут прочтены данные
     * @return строка, содержащаяя все символы в файле, включая пробелы, знаки переноса строки и так далее...
     */
    public static String readFile(String fileName) {
        File file = new File(fileName);
        boolean createSuccess = createFile(fileName);
        if(!file.exists()){
            throw new IllegalStateException("Given file could not be created");
        }
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch(FileNotFoundException ex){
            ex.printStackTrace();
            throw new RuntimeException("Created file, but it was deleted", ex);
        }
        String text = "";
        while ( scanner.hasNextLine()){
            text += (scanner.nextLine() + "\n");
        }
        return text;
    }

    /**
     * Данная функция записывает в файл с переданным именем заданный текст.
     * @param fileName полный или частичный путь к файлу, в который будет производиться запись
     * @param text данные, которые будут записаны в файл
     * @return true, если все данные были успешно записаны в этот файл, false, если запись не была выполнена по каким-либо причинам
     */
    public static boolean writeFile(String fileName, String text) {
        File file = new File(fileName);
        boolean createSuccess = createFile(fileName);
        if(!createSuccess){
            //throw new IllegalStateException("File could not be created");
            return false;
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch(FileNotFoundException ex){
            //throw new RuntimeException("Created file but it was removed ", ex);
            return false;
        }
        pw.print(text);
        pw.flush();
        pw.close();
        return true;
    }

    /**
     * Данная функция дозаписывает в файл с переданным именем заданный текст.
     * @param fileName полный или частичный путь к файлу, в который будет производиться запись
     * @param textToAppend данные, которые будут добавлены в файл
     * @return true, если дозапись прошла успешно, false, если дозапись в файл не состоялась
     */
    public static boolean appendFile(String fileName, String textToAppend){
        File file = new File(fileName);
        boolean createSuccess = createFile(fileName);
        if(!createSuccess){
            return false;
        }
        String text = FileUtils.readFile(fileName);
        text += textToAppend;
        return FileUtils.writeFile(fileName, text);
    }
}