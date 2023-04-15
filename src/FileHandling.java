import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileHandling {
    public static String filePath = System.getProperty("user.dir") + File.separator  + "crawled";
    public static void deleteFilesDirectories(File f) {
        if (!f.exists()) {
            f.mkdir();
        } else {
            for (File file : f.listFiles()) {
                if (file.isDirectory()) {
                    deleteFilesDirectories(file);
                }
                file.delete();
            }
        }
    }

    public static void createDirectoryInCrawled(String path, String name) {
        String f = path + File.separator + name;
        File dir = new File(f);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void writeFileData(String path, String folder, String file, double data1, double data2, boolean secondLine) {
        try {
            FileOutputStream fileName = new FileOutputStream(path + File.separator + folder + File.separator + file);
            DataOutputStream bw = new DataOutputStream(fileName);
            bw.writeDouble(data1);
            if (secondLine) {
                bw.writeDouble(data2);
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesInFile(String path, String folder, String file, HashSet<String> data) {
        String name;
        if (folder == null) {
            name = path + File.separator + file;
        } else {
            name = path + File.separator + folder + File.separator + file;
        }
        try {
            FileWriter fileName = new FileWriter(name);
            BufferedWriter bw = new BufferedWriter(fileName);
            for (String str : data){
                bw.write(str);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double readFileData(String path, String folder, String file, boolean secondLine) {
        double ans = 0;
        try {
            FileInputStream fileName = new FileInputStream(path + File.separator + folder + File.separator + file);
            DataInputStream dat = new DataInputStream(fileName);
            if (secondLine) {
                dat.readDouble();
            }
            ans = dat.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static List<String> readFileLinks(String path, String folder, String file) {
        String name;
        if (folder == null) {
            name = path + File.separator + file;
        } else {
            name = path + File.separator + folder + File.separator + file;
        }
        List<String> links = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(name));
            String s;
            while ((s = br.readLine()) != null) {
                links.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}
