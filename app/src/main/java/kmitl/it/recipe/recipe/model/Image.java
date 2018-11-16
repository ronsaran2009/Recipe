package kmitl.it.recipe.recipe.model;

import android.net.Uri;

import java.io.Serializable;

public class Image implements Serializable {

    Uri uriImage;

    public Image() { }

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }
}
