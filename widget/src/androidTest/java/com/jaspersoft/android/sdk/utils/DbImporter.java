package com.jaspersoft.android.sdk.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class DbImporter {

    public static File importDb(String dbName, File baseFolder, String dbResource) {
        InputStream dbIs = DbImporter.class.getClassLoader().getResourceAsStream(dbResource);
        String dbCopy = dbName + "-temp";
        File copyFile = new File(baseFolder.getParentFile(), dbCopy);

        try {
            copyDb(dbIs, copyFile);
            return copyFile;
        } catch (IOException e) {
            throw new RuntimeException("Can not copy DB");
        }
    }

    private static void copyDb(InputStream originalDbStream, File tempDb) throws IOException {
        OutputStream dbOut = new FileOutputStream(tempDb);

        byte[] buf = new byte[1024];
        int length;
        while ((length = originalDbStream.read(buf)) > 0) {
            dbOut.write(buf, 0, length);
        }
        originalDbStream.close();
        dbOut.close();
    }
}
