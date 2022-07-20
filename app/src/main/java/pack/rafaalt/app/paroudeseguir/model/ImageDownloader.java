package pack.rafaalt.app.paroudeseguir.model;


/**
 * Classe retirada do livro Google Android, 3a edição
 * @author R. Lecheta
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {

    public static Bitmap download(String url) throws IOException {
        Bitmap bitmap = null;

        InputStream inputStream = new URL(url).openStream();
        bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();

        return  bitmap;
    }
}