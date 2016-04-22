package tseng.min.c.widget.cropimageview.callback;

import android.graphics.Bitmap;

public interface CropCallback extends Callback {
	void onSuccess(Bitmap cropped);
//    void onError();
}
