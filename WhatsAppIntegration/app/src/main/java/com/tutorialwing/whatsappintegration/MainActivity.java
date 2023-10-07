package com.tutorialwing.whatsappintegration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private int SELECT_PICTURE_CODE = 101;
	private int SELECT_VIDEO_CODE = 102;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnShare = findViewById(R.id.shareIt);
		if (btnShare != null) {
			btnShare.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText editText = findViewById(R.id.shareText);
					if (editText != null) {
						String text = String.valueOf(editText.getText());
						if ((text != null) && !text.isEmpty()) {
							startShareText(text);
						} else {
							Toast.makeText(getApplicationContext(), R.string.enter_text, Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
		}

		Button btnShareImage = findViewById(R.id.shareImage);
		if (btnShareImage != null) {
			btnShareImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selectImage();
				}
			});
		}

		Button btnShareVideo = findViewById(R.id.shareVideo);
		if (btnShareVideo != null) {
			btnShareVideo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selectVideo();
				}
			});
		}
	}

	private void selectVideo() {
		Intent intent = new Intent();
		intent.setType("video/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, getString(R.string.select_video)), SELECT_VIDEO_CODE);
	}

	private void selectImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_PICTURE_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((data != null) && resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE_CODE) {
				startShareImage(getString(R.string.sharing_image), data.getData());
			} else if (requestCode == SELECT_VIDEO_CODE) {
				startShareVideo(getString(R.string.sharing_video), data.getData());
			}
		}
	}

	private void startShareText(String text) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, text);
		sendIntent.setType("text/plain");

		// Set package only if you do not want to show all the options by which you can share the text.
		// Setting package bypass the system picker and directly share the data on WhatsApp.
		// TODO uncomment code to show whatsapp directly
		// sendIntent.setPackage("com.whatsapp");

		startActivity(sendIntent);
	}

	private void startShareImage(String text, Uri uri) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("image/*");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.choose_media_title)));
	}

	private void startShareVideo(String text, Uri uri) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("video/*");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.choose_media_title)));
	}
}
