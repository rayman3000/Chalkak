package bulgogi1216.gmail.photogenic;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;

import bulgogi1216.gmail.photogenic.databinding.ActivityCameraBinding;
import ly.img.android.PESDK;
import ly.img.android.sdk.models.constant.Directory;
import ly.img.android.sdk.models.state.CameraSettings;
import ly.img.android.sdk.models.state.EditorSaveSettings;
import ly.img.android.sdk.models.state.manager.SettingsList;
import ly.img.android.ui.activities.CameraPreviewBuilder;
import ly.img.android.ui.activities.ImgLyIntent;
import ly.img.android.ui.utilities.PermissionRequest;

public class CameraActivity extends Activity implements PermissionRequest.Response {
    private static final String FOLDER = "ImgLy";

    private ActivityCameraBinding mBinding;
    public static int CAMERA_PREVIEW_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SettingsList settingsList = new SettingsList();
        settingsList
                .getSettingsModel(CameraSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("camera_")
                .getSettingsModel(EditorSaveSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("result_")
                .setJpegQuality(80, false)
                .setSavePolicy(EditorSaveSettings.SavePolicy.KEEP_SOURCE_AND_CREATE_ALWAYS_OUTPUT);

        new CameraPreviewBuilder(this).setSettingsList(settingsList).startActivityForResult(this, CAMERA_PREVIEW_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_PREVIEW_RESULT) {
            String resultPath = data.getStringExtra(ImgLyIntent.RESULT_IMAGE_PATH);
            String sourcePath = data.getStringExtra(ImgLyIntent.SOURCE_IMAGE_PATH);

            if (resultPath != null) {
                // Add result file to Gallery
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(resultPath))));
            }

            if (sourcePath != null) {
                // Add sourceType file to Gallery
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(sourcePath))));
            }

            Toast.makeText(PESDK.getAppContext(), "Image saved on: " + resultPath, Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED && requestCode == CAMERA_PREVIEW_RESULT && data != null) {
            String sourcePath = data.getStringExtra(ImgLyIntent.SOURCE_IMAGE_PATH);
            Toast.makeText(PESDK.getAppContext(), "Editor canceled, sourceType image is:\n" + sourcePath, Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void permissionGranted() {

    }

    @Override
    public void permissionDenied() {
        finish();
        System.exit(0);
    }
}
