import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static final String ZIP_FILE = "d:\\Games\\savegames\\save.zip";
    private static final String SAVE_PATH = "d:\\Games\\savegames\\";

    public static void main(String[] args) {
        openZip(ZIP_FILE, SAVE_PATH);

        GameProgress gp = openProgress(SAVE_PATH + "save1.dat");
        System.out.println(gp);
        gp = openProgress(SAVE_PATH + "save2.dat");
        System.out.println(gp);
        gp = openProgress(SAVE_PATH + "save3.dat");
        System.out.println(gp);
    }

    public static void openZip(String zipPath, String unzipPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            while (true) {
                ZipEntry entry = zis.getNextEntry();
                if (entry == null) break;
                File tmp = new File(unzipPath + entry.getName());

                FileOutputStream fot = new FileOutputStream(tmp);
                byte[] buff = zis.readAllBytes();
                fot.write(buff);

                fot.flush();
                fot.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String path) throws IllegalArgumentException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (GameProgress) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("UnknownError");
    }
}