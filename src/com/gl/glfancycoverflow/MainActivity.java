package com.gl.glfancycoverflow;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.gl.glfancycoverflow.model.FilmInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author LittleLiByte
 * 
 */
public class MainActivity extends Activity {

	DisplayImageOptions options;

	private ImageLoader imageLoader;

	private TextView filmName;

	private FancyCoverFlow fancyCoverFlow;

	private List<FilmInfo> filmList;

	private ImageAdapter adapter;

	private boolean isalive = true;

	private int cur_index = 0;
	private int count_drawble;
	private static int MSG_UPDATE = 1;
	// ��ʱ����
	private ScheduledExecutorService scheduledExecutorService;

	// ͨ��handler������������
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == MSG_UPDATE) {
				fancyCoverFlow.setSelection(cur_index);
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		filmList = FilmInfoTest.getfilmInfo();

		// ����option
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.logo)
				.showImageForEmptyUri(R.drawable.logo)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();

		adapter = new ImageAdapter(this, filmList, options, imageLoader);

		fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
		filmName = (TextView) findViewById(R.id.tv_name);

		// ������Ӱ
		fancyCoverFlow.setReflectionEnabled(true);
		// ���õ�Ӱ����
		fancyCoverFlow.setReflectionRatio(0.3f);
		// ����ԭͼ�뵹Ӱ֮��ļ�϶
		fancyCoverFlow.setReflectionGap(0);
		// item֮��ļ�϶Ҫ�����趨����imageview�Ŀ���Լ���Ӱ�ĸ����
		fancyCoverFlow.setSpacing(-55);

		fancyCoverFlow.setAdapter(adapter);
		fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				cur_index = position;
				filmName.setText(filmList.get(position % filmList.size())
						.getFilmName());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// ����¼�
		fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,
						filmList.get(position % filmList.size()).getFilmName(),
						0).show();
			}
		});

		// �����Զ��ֲ�
		count_drawble = adapter.getCount();
		startPlay();

		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle_AutoPlay);
		toggleButton.setChecked(true);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					startPlay();
				} else {
					stopPlay();
				}
			}
		});
	}


	/**
	 * ��ʼ�ֲ�ͼ�л�
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new AutoPlayTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * ֹͣ�ֲ�ͼ�л�
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * ִ���ֲ�ͼ�л�����
	 * 
	 */
	private class AutoPlayTask implements Runnable {

		@Override
		public void run() {

			cur_index = cur_index % count_drawble; // ͼƬ����[0,count_drawable)
			Message msg = handler.obtainMessage(MSG_UPDATE);
			handler.sendMessage(msg);
			cur_index++;
		}

	}

	@Override
	protected void onStop() {
		imageLoader.stop();
		isalive = false;
		super.onStop();
	}

}